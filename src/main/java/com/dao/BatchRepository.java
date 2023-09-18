package com.dao;

import com.members.Batch;
import com.members.Participant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BatchRepository {

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

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public int addBatch(Batch batch) throws Exception {
        String sql = "INSERT INTO batch (batch_name, time) VALUES (?, ?)";  // Notice the change here
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, batch.getBatchName());
            statement.setString(2, batch.getTime());
            statement.executeUpdate();

            // Get the generated batch ID
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating batch failed, no ID obtained.");
                }
            }
        }
    }


    public Batch getBatchById(int id) throws Exception {
        String sql = "SELECT * FROM batch WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Batch batch = new Batch();
                    batch.setId(resultSet.getInt("id"));
                    batch.setBatchName(resultSet.getString("batch_name"));
                    batch.setTime(resultSet.getString("time"));
                    return batch;
                }
                return null;
            }
        }
    }

    public void updateBatch(Batch batch) throws Exception {
        String sql = "UPDATE batch SET batch_name = ?, time = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, batch.getBatchName());
            statement.setString(2, batch.getTime());
            statement.setInt(3, batch.getId());
            statement.executeUpdate();
        }
    }

    public void deleteBatch(int id) throws Exception {
        String sql = "DELETE FROM batch WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public List<Batch> getBatches() throws Exception {
        List<Batch> batches = new ArrayList<>();
        String sql = "SELECT * FROM batch";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Batch batch = new Batch();
                batch.setId(resultSet.getInt("id"));
                batch.setBatchName(resultSet.getString("batch_name"));
                batch.setTime(resultSet.getString("time"));
                batches.add(batch);
            }
        }
        return batches;
    }
    
    public int getParticipantCountForBatch(int batchId) throws Exception {
        String sql = "SELECT COUNT(*) FROM participant WHERE batch_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, batchId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
                return 0;
            }
        }
    }
    
    public Batch getBatchByName(String batchName) throws Exception {
        Batch batch = null;
        String sql = "SELECT * FROM batch WHERE batch_name = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, batchName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    batch = new Batch();
                    batch.setId(resultSet.getInt("id"));
                    batch.setBatchName(resultSet.getString("batch_name"));
                    batch.setTime(resultSet.getString("time"));
                    
                    // Now, fetch participants for this batch using its ID
                    List<Participant> participants = fetchParticipantsForBatch(batch.getId(), connection);
                    for (Participant p : participants) {
                        batch.addParticipant(p);
                    }
                }
            }
        }
        return batch;
    }

    private List<Participant> fetchParticipantsForBatch(int batchId, Connection connection) throws Exception {
        List<Participant> participants = new ArrayList<>();
        String sql = "SELECT * FROM participant WHERE batch_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, batchId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Participant participant = new Participant();
                    participant.setName(resultSet.getString("name"));
                    participant.setAge(resultSet.getInt("age"));
                    participant.setEmail(resultSet.getString("email"));
                    participants.add(participant);
                }
            }
        }
        return participants;
    }


}
