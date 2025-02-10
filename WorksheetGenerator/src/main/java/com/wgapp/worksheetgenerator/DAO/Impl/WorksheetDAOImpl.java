package com.wgapp.worksheetgenerator.DAO.Impl;


import com.sun.tools.javac.Main;
import com.wgapp.worksheetgenerator.Config.DatabaseConnection;
import com.wgapp.worksheetgenerator.DAO.Entities.ChoiceEntity;
import com.wgapp.worksheetgenerator.DAO.Entities.PassageEntity;
import com.wgapp.worksheetgenerator.DAO.Entities.QuestionEntity;
import com.wgapp.worksheetgenerator.DAO.Entities.WorksheetEntity;
import com.wgapp.worksheetgenerator.DAO.WorksheetDAO;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.DifficultyLevelOptions;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.MainSubjectOptions;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.SubSubjectOptionsEnglish;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.SubSubjectOptionsMaths;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorksheetDAOImpl implements WorksheetDAO {

    @Override
    public WorksheetEntity createWorksheet(WorksheetEntity worksheetEntity) {
        String sql1 = "SELECT  u.* FROM users u WHERE u.user_name = ?";

        String sql2 = "INSERT INTO worksheets (main_subject, sub_subject, difficulty_level, user_id) VALUES (?, ?, ?,?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt1 = connection.prepareStatement(sql1)) {

            pstmt1.setString(1, worksheetEntity.getUserEntity().getUsername());

            try (ResultSet resultSet1 = pstmt1.executeQuery()) {
                if (resultSet1.next()) {
                    worksheetEntity.getUserEntity().setId(resultSet1.getInt(1));
                }
                try (PreparedStatement pstmt2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS)) {

                    // Set values in prepared statement
                    pstmt2.setString(1, worksheetEntity.getMainSubject().toString());
                    pstmt2.setString(2, worksheetEntity.getSubSubject().toString());
                    pstmt2.setString(3, worksheetEntity.getDifficultyLevel().toString());
                    pstmt2.setInt(4, worksheetEntity.getUserEntity().getId());

                    int affectedRows = pstmt2.executeUpdate();

                    if (affectedRows > 0) {
                        // Get the generated ID
                        try (ResultSet rs1 = pstmt2.getGeneratedKeys()) {
                            if (rs1.next()) {
                                worksheetEntity.setWorksheetId(rs1.getInt(1));
                                createPassage(worksheetEntity.getPassage(), rs1.getInt(1));

                                // Create associated questions in DB
                                for (QuestionEntity questionEntity : worksheetEntity.getQuestionList()) {
                                    createQuestion(questionEntity, worksheetEntity.getWorksheetId());
                                }
                                return worksheetEntity;
                            }
                        }
                    }

                    throw new SQLException("Failed to create worksheet, no rows affected.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating worksheet in database", e);
        }
    }


    @Override
    public void createQuestion(QuestionEntity questionEntity, int worksheetId) {
        String sql = "INSERT INTO questions (worksheet_id,question_text,answer) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            pstmt.setInt(1, worksheetId);
            pstmt.setString(2, questionEntity.getQuestionText());
            pstmt.setString(3, questionEntity.getCorrectAnswerText());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs1 = pstmt.getGeneratedKeys()) {
                    if (rs1.next()) {
                        questionEntity.setQuestionId(rs1.getInt(1));

                        for (ChoiceEntity choiceEntity : questionEntity.getChoices()) {
                            createChoices(choiceEntity, rs1.getInt(1));
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
    public void createChoices(ChoiceEntity choiceEntity, int questionId) {
        String sql = "INSERT INTO choices (question_id,choice_text) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, questionId);
            pstmt.setString(2, choiceEntity.getChoiceText());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs1 = pstmt.getGeneratedKeys()) {
                    if (rs1.next()) {
                        choiceEntity.setChoiceId(rs1.getInt(1));
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
    public void createPassage(PassageEntity passageEntity, int worksheetId) {
        String sql = "INSERT INTO passages (worksheet_id, passage, title) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set values in prepared statement
            pstmt.setInt(1, worksheetId);
            pstmt.setString(2, passageEntity.getPassageText());
            pstmt.setString(3, passageEntity.getPassageTitle());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                // Get the generated ID
                try (ResultSet rs1 = pstmt.getGeneratedKeys()) {
                    if (rs1.next()) {
                        passageEntity.setPassageId(rs1.getInt(1));
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
    public WorksheetEntity findWorksheet(String title, int userId) {
        String sql1 = "SELECT w.*, p.* FROM worksheets w " +
                "JOIN passages p ON w.worksheet_id = p.worksheet_id" +
                " WHERE p.title LIKE ? AND w.user_id = ?";

        String sql2 = "SELECT * FROM questions WHERE worksheet_id = ?";

        String sql3 = "SELECT * FROM choices WHERE question_id= ?";


        WorksheetEntity worksheetEntity = new WorksheetEntity();
        PassageEntity passageEntity = new PassageEntity();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt1 = connection.prepareStatement(sql1)) {

            // Use '%' to match partial titles
            pstmt1.setString(1, "%" + title + "%");
            pstmt1.setInt(2, userId);

            try (ResultSet rs1 = pstmt1.executeQuery()) {
                if (rs1.next()) { // if a worksheet exists
                    worksheetEntity.setWorksheetId(rs1.getInt("worksheet_id"));
                    worksheetEntity.setMainSubject(MainSubjectOptions.valueOf(rs1.getString("main_subject")));
                    worksheetEntity.setSubSubject(SubSubjectOptionsEnglish.valueOf(rs1.getString("sub_subject")));
                    worksheetEntity.setDifficultyLevel(DifficultyLevelOptions.valueOf(rs1.getString("difficulty_level")));

                    // Setting passage
                    passageEntity.setPassageText(rs1.getString("passage"));
                    passageEntity.setPassageTitle(rs1.getString("title"));
                    worksheetEntity.setPassage(passageEntity);
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching worksheet from database", e);
        }

        // Fetch Questions for the Worksheet
        List<QuestionEntity> questionEntityList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt2 = connection.prepareStatement(sql2)) {
            pstmt2.setInt(1, worksheetEntity.getWorksheetId());

            try (ResultSet rs2 = pstmt2.executeQuery()) {
                while (rs2.next()) {
                    QuestionEntity questionEntity = new QuestionEntity();
                    questionEntity.setQuestionId(rs2.getInt("question_id"));
                    questionEntity.setQuestionText(rs2.getString("question_text"));
                    questionEntity.setCorrectAnswerText(rs2.getString("answer"));

                    // Fetch Choices for this question
                    List<ChoiceEntity> choicesList = new ArrayList<>();
                    try (PreparedStatement pstmt3 = connection.prepareStatement(sql3)) {
                        pstmt3.setInt(1, questionEntity.getQuestionId());
                        try (ResultSet rs3 = pstmt3.executeQuery()) {
                            while (rs3.next()) {
                                ChoiceEntity choiceEntity = new ChoiceEntity(
                                        rs3.getInt("choice_id"),
                                        rs3.getString("choice_text")
                                );

                                choicesList.add(choiceEntity);
                            }
                        }
                    }
                    questionEntity.setChoices(choicesList);
                    questionEntityList.add(questionEntity);

                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        worksheetEntity.setQuestionList(questionEntityList);
        return worksheetEntity;
    }

    @Override
    public WorksheetEntity findWorksheet(int worksheetId) {
//        String sql1 = "SELECT w.*, p.* FROM worksheets w " +
//                "JOIN passages p ON w.worksheet_id = p.worksheet_id" +
//                " WHERE w.worksheet_id = ?";
//
        String sql1 = "SELECT w.* FROM worksheets w " +
                " WHERE w.worksheet_id = ?";

        String sql2 = "SELECT * FROM questions WHERE worksheet_id = ?";

        String sql3 = "SELECT * FROM choices WHERE question_id= ?";

        String sql4 = "SELECT p.* FROM passages p WHERE p.worksheet_id= ?";

        WorksheetEntity worksheetEntity = new WorksheetEntity();
        PassageEntity passageEntity = new PassageEntity();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt1 = connection.prepareStatement(sql1)) {

            // Use '%' to match partial titles
            pstmt1.setInt(1, worksheetId);

            try (ResultSet rs1 = pstmt1.executeQuery()) {
                if (rs1.next()) { // if a worksheet exists
                    worksheetEntity.setWorksheetId(rs1.getInt("worksheet_id"));
                    worksheetEntity.setMainSubject(MainSubjectOptions.valueOf(rs1.getString("main_subject")));
                    if (rs1.getString("main_subject").equals("ENGLISH")) {
                        worksheetEntity.setSubSubject(SubSubjectOptionsEnglish.valueOf(rs1.getString("sub_subject")));

                        try (PreparedStatement pstmt = connection.prepareStatement(sql4)) {
                            pstmt.setInt(1, worksheetId);
                            try (ResultSet rs = pstmt.executeQuery()) {
                                if (rs.next()) {
                                    // Setting passage
                                    passageEntity.setPassageText(rs.getString("passage"));
                                    passageEntity.setPassageTitle(rs.getString("title"));
                                    worksheetEntity.setPassage(passageEntity);
                                }
                            }
                        }

                    } else if (rs1.getString("main_subject").equals("MATHS")) {
                        worksheetEntity.setSubSubject(SubSubjectOptionsMaths.valueOf(rs1.getString("sub_subject")));
                    }

                    worksheetEntity.setDifficultyLevel(DifficultyLevelOptions.valueOf(rs1.getString("difficulty_level")));

                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching worksheet from database", e);
        }

        // Fetch Questions for the Worksheet
        List<QuestionEntity> questionEntityList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt2 = connection.prepareStatement(sql2)) {
            pstmt2.setInt(1, worksheetEntity.getWorksheetId());

            try (ResultSet rs2 = pstmt2.executeQuery()) {
                while (rs2.next()) {
                    QuestionEntity questionEntity = new QuestionEntity();
                    questionEntity.setQuestionId(rs2.getInt("question_id"));
                    questionEntity.setQuestionText(rs2.getString("question_text"));
                    questionEntity.setCorrectAnswerText(rs2.getString("answer"));

                    // Fetch Choices for this question
                    List<ChoiceEntity> choicesList = new ArrayList<>();
                    try (PreparedStatement pstmt3 = connection.prepareStatement(sql3)) {
                        pstmt3.setInt(1, questionEntity.getQuestionId());
                        try (ResultSet rs3 = pstmt3.executeQuery()) {
                            while (rs3.next()) {
                                ChoiceEntity choiceEntity = new ChoiceEntity(
                                        rs3.getInt("choice_id"),
                                        rs3.getString("choice_text")
                                );
                              //  System.out.println("choive" + choiceEntity);
                                choicesList.add(choiceEntity);
                            }
                        }
                    }
                    questionEntity.setChoices(choicesList);
                    questionEntityList.add(questionEntity);

                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        worksheetEntity.setQuestionList(questionEntityList);

        return worksheetEntity;
    }


    @Override
    public void deleteWorksheet(int worksheetId, int userId) {
        // SQL query to delete a worksheet
        String sql = "DELETE FROM worksheets WHERE worksheet_id = ? AND user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Set the parameters for the SQL query
            pstmt.setInt(1, worksheetId);
            pstmt.setInt(2, userId);

            // Execute the delete query
            int rowsAffected = pstmt.executeUpdate();

            // Check if any row was affected
            if (rowsAffected == 0) {
                System.out.println("No worksheet found for the given ID and user ID.");
            } else {
                System.out.println("Worksheet deleted successfully.");
            }

        } catch (SQLException e) {
            // Handle exception and print the error
            e.printStackTrace();
            throw new RuntimeException("Error deleting worksheet from database", e);
        }
    }

    @Override
    public List<WorksheetEntity> listWorksheets(int userId) {
        List<WorksheetEntity> worksheetEntityList = new ArrayList<>();

        // SQL query to list worksheets with passages
        String sql = "SELECT w.*, p.title FROM worksheets w LEFT JOIN passages p ON w.worksheet_id = p.worksheet_id WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    WorksheetEntity worksheetEntity;

                    if (rs.getString("main_subject").equals("ENGLISH")) {
                        worksheetEntity = new WorksheetEntity(
                                rs.getInt("worksheet_id"),
                                MainSubjectOptions.valueOf(rs.getString("main_subject")),
                                SubSubjectOptionsEnglish.valueOf(rs.getString("sub_subject")),
                                DifficultyLevelOptions.valueOf(rs.getString("difficulty_level")),
                                new PassageEntity(rs.getString("title"))
                        );

                        worksheetEntityList.add(worksheetEntity);
                    } else {
                        worksheetEntity = new WorksheetEntity(
                                rs.getInt("worksheet_id"),
                                MainSubjectOptions.valueOf(rs.getString("main_subject")),
                                SubSubjectOptionsMaths.valueOf(rs.getString("sub_subject")),
                                DifficultyLevelOptions.valueOf(rs.getString("difficulty_level"))
                        );
                        worksheetEntityList.add(worksheetEntity);
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error listing worksheets from database", e);
        }
        return worksheetEntityList;
    }


}