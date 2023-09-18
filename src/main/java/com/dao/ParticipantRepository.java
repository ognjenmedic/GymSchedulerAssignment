package com.dao;

import com.members.Participant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ParticipantRepository {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/zumba_gym_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "caltech123";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public void addParticipant(Participant participant, int batchId) throws Exception {
        String sql = "INSERT INTO participant (name, age, email, batch_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, participant.getName());
            statement.setInt(2, participant.getAge());
            statement.setString(3, participant.getEmail());
            statement.setInt(4, batchId); // Added this line to set batch_id
            statement.executeUpdate();
        }
    }

    public Participant getParticipant(int id) throws Exception {
        String sql = "SELECT * FROM participant WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Participant(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("age"),
                            resultSet.getString("email")
                    );
                }
                return null;
            }
        }
    }

    public void updateParticipant(Participant participant) throws Exception {
        String sql = "UPDATE participant SET name = ?, age = ?, email = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, participant.getName());
            statement.setInt(2, participant.getAge());
            statement.setString(3, participant.getEmail());
            statement.setInt(4, participant.getId());
            statement.executeUpdate();
        }
    }

    public void deleteParticipant(int id) throws Exception {
        String sql = "DELETE FROM participant WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public static List<Participant> getParticipants() throws Exception {
        List<Participant> participants = new ArrayList<>();
        String sql = "SELECT * FROM participant";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                participants.add(new Participant(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getString("email")
                ));
            }
        }
        return participants;
    }
    
    public void addParticipant(Participant participant) throws Exception {
        String sql = "INSERT INTO participant (name, age, email) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, participant.getName());
            statement.setInt(2, participant.getAge());
            statement.setString(3, participant.getEmail());
            statement.executeUpdate();
        }
    }

}
