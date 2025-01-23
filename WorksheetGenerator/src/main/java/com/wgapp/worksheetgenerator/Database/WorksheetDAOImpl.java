package com.wgapp.worksheetgenerator.Database;


import com.wgapp.worksheetgenerator.Models.Question;
import com.wgapp.worksheetgenerator.Models.Worksheet;

import java.sql.*;
import java.util.List;

public class WorksheetDAOImpl implements IWorksheetDAO {

    @Override
    public Worksheet getWorksheetById(long id) {
        return null;
    }

    @Override
    public List<Worksheet> getAllWorksheets() {
        return List.of();
    }

    @Override
    public List<Worksheet> getWorksheetsByTitle(String title) {
        return List.of();
    }

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
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        worksheet.setWorksheetId(rs.getInt(1));

                        //System.out.println(worksheet.getWorksheetId());
                        // System.out.println(rs.getInt(1));
                        // Create associated questions in DB
                        for (Question question : worksheet.getQuestionList()) {

                            System.out.println("Question: " + question);
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
    public Worksheet updateWorksheet(Worksheet worksheet) {
        return null;
    }

    @Override
    public void deleteWorksheet(int id) {

    }


    @Override
    public void createQuestion(Question question, int worksheetId) {
        String sql = "INSERT INTO questions (worksheet_id,question_text,answer) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            pstmt.setInt(1, worksheetId);
            pstmt.setString(2, question.getQuestionText());
            pstmt.setString(3, question.getAnswerText());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        question.setQuestionId(rs.getInt(1));
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
}
