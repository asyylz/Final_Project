package com.wgapp.worksheetgenerator.Database;


import com.wgapp.worksheetgenerator.Models.*;
import com.wgapp.worksheetgenerator.Views.ISubSubjectOptions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorksheetDAOImpl implements WorksheetDAO {

    @Override
    public Worksheet createWorksheet(Worksheet worksheet) {
        String sql = "INSERT INTO worksheets (main_subject, sub_subject, difficulty_level) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set values in prepared statement
            pstmt.setString(1, worksheet.getMainSubject().toString());
            pstmt.setString(2, worksheet.getSubSubject().toString());
            pstmt.setString(3, worksheet.getDifficultyLevel().toString());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Get the generated ID
                try (ResultSet rs1 = pstmt.getGeneratedKeys()) {
                    if (rs1.next()) {
                        worksheet.setWorksheetId(rs1.getInt(1));
                        createPassage(worksheet.getPassage(), rs1.getInt(1));

                        // Create associated questions in DB
                        for (Question question : worksheet.getQuestionList()) {
                            System.out.println(question);
                            createQuestion(question, worksheet.getWorksheetId());
                        }

                        return worksheet;
                    }
                }
            }

            throw new SQLException("Failed to create worksheet, no rows affected.");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating worksheet in database", e);
        }
    }


    @Override
    public void createQuestion(Question question, int worksheetId) {
        String sql = "INSERT INTO questions (worksheet_id,question_text,answer) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            pstmt.setInt(1, worksheetId);
            pstmt.setString(2, question.getQuestionText());
            pstmt.setString(3, question.getCorrectAnswerText());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs1 = pstmt.getGeneratedKeys()) {
                    if (rs1.next()) {
                        question.setQuestionId(rs1.getInt(1));

                        for (Choice choice : question.getChoices()) {
                            createChoices(choice, rs1.getInt(1));
                        }
                    }
                }
            } else {
                // Only throw an exception if no rows were affected
                throw new SQLException("Failed to create question, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating questions in database", e);
        }

    }

    @Override
    public void createChoices(Choice choice, int questionId) {
        String sql = "INSERT INTO choices (question_id,choice_text) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, questionId);
            pstmt.setString(2, choice.getChoiceText());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs1 = pstmt.getGeneratedKeys()) {
                    if (rs1.next()) {
                        choice.setChoiceId(rs1.getInt(1));
                    }
                }
            } else {
                // Only throw an exception if no rows were affected
                throw new SQLException("Failed to create choice, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating choices in database", e);
        }

    }

    @Override
    public void createPassage(Passage passage, int worksheetId) {
        String sql = "INSERT INTO passages (worksheet_id, passage, title) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set values in prepared statement
            pstmt.setInt(1, worksheetId);
            pstmt.setString(2, passage.getPassageText().toString());
            pstmt.setString(3, passage.getPassageTitle().toString());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                // Get the generated ID
                try (ResultSet rs1 = pstmt.getGeneratedKeys()) {
                    if (rs1.next()) {
                        passage.setPassageId(rs1.getInt(1));
                    }
                }
            } else {
                // Only throw an exception if no rows were affected
                throw new SQLException("Failed to create choice, no rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating passage in database", e);
        }

    }

    @Override
    public Worksheet findWorksheet(String title) {
        String sql1 = "SELECT w.*, p.* FROM worksheets w " +
                "JOIN passages p ON w.worksheet_id = p.worksheet_id" +
                " WHERE p.title LIKE ?";

        String sql2 = "SELECT * FROM questions WHERE worksheet_id = ?";

        String sql3 = "SELECT * FROM choices WHERE question_id= ?";


        Worksheet worksheet = new Worksheet();
        Passage passage = new Passage();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt1 = connection.prepareStatement(sql1)) {

            // Use '%' to match partial titles
            pstmt1.setString(1, "%" + title + "%");

            try (ResultSet rs1 = pstmt1.executeQuery()) {
                if (rs1.next()) { // if a worksheet exists
                    worksheet.setWorksheetId(rs1.getInt("worksheet_id"));
                    worksheet.setMainSubject(MainSubjectOptions.valueOf(rs1.getString("main_subject")));
                    worksheet.setSubSubject(SubSubjectOptionsEnglish.valueOf(rs1.getString("sub_subject")));
                    worksheet.setDifficultyLevel(DifficultyLevelOptions.valueOf(rs1.getString("difficulty_level")));

                    // Setting passage
                    passage.setPassageText(rs1.getString("passage"));
                    passage.setPassageTitle(rs1.getString("title"));
                    worksheet.setPassage(passage);
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching worksheet from database", e);
        }

        // Fetch Questions for the Worksheet
        List<Question> questionList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt2 = connection.prepareStatement(sql2)) {
            pstmt2.setInt(1, worksheet.getWorksheetId());

            try (ResultSet rs2 = pstmt2.executeQuery()) {
                while (rs2.next()) {
                    Question question = new Question();
                    question.setQuestionId(rs2.getInt("question_id"));
                    question.setQuestionText(rs2.getString("question_text"));
                    question.setCorrectAnswerText(rs2.getString("answer"));

                    // Fetch Choices for this question
                    List<Choice> choicesList = new ArrayList<>();
                    try (PreparedStatement pstmt3 = connection.prepareStatement(sql3)) {
                        pstmt3.setInt(1, question.getQuestionId());
                        try (ResultSet rs3 = pstmt3.executeQuery()) {
                            while (rs3.next()) {
                                Choice choice = new Choice(
                                     rs3.getInt("choice_id"),
                                     rs3.getString("choice_text")
                                );

                                choicesList.add(choice);
                            }
                        }
                    }
                    question.setChoices(choicesList);
                    questionList.add(question);

                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        worksheet.setQuestionList(questionList);
        return worksheet;
    }

    @Override
    public Worksheet updateWorksheet(Worksheet worksheet) {
        return null;
    }

    @Override
    public void deleteWorksheet(int id) {

    }

    @Override
    public Worksheet getWorksheetById(long id) {
        return null;
    }

    @Override
    public List<Worksheet> getAllWorksheets() {
        return List.of();
    }


}
