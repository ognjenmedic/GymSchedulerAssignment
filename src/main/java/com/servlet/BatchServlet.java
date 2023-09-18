package com.servlet;

import com.members.Batch;
import com.members.Participant;
import com.dao.BatchRepository;
import com.dao.ParticipantRepository;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BatchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BatchRepository batchRepository = new BatchRepository();
    private ParticipantRepository participantRepository = new ParticipantRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String batchName = request.getParameter("batchName");
        String time = request.getParameter("time");

        Batch batch = new Batch(batchName, time);

        try {
            int batchId = batchRepository.addBatch(batch);  // Add the batch and get the generated ID

            // Handling participants
            for (int i = 1; i <= 3; i++) {
                String participantName = request.getParameter("participant" + i + "Name");
                String participantAgeStr = request.getParameter("participant" + i + "Age");
                String participantEmail = request.getParameter("participant" + i + "Email");

                if (participantName != null && !participantName.isEmpty() &&
                    participantAgeStr != null && !participantAgeStr.isEmpty() &&
                    participantEmail != null && !participantEmail.isEmpty()) {
                    
                    int participantAge = Integer.parseInt(participantAgeStr);

                    // Create a participant object
                    Participant participant = new Participant(participantName, participantAge, participantEmail);
                    
                    // Add the participant to the batch
                    batch.addParticipant(participant);
                    
                    // Save the participant to the database
                    participantRepository.addParticipant(participant, batchId);  // Use the batchId here
                }
            }

            response.setContentType("text/html");
            response.getWriter().write("Batch and participants added successfully. <a href='index.html'>Back to Home</a>");
        } catch(Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error adding batch or participants.");
        }
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Update an existing batch
        int id = Integer.parseInt(request.getParameter("id"));
        String batchName = request.getParameter("batchName");
        String time = request.getParameter("time");
        
        Batch batch = new Batch(batchName, time);
        batch.setId(id);
        
        try {
        	batchRepository.updateBatch(batch);
            response.getWriter().write("Batch updated successfully");
        } catch(Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error updating batch.");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Delete a batch
        int id = Integer.parseInt(request.getParameter("id"));
        
        try {
        	batchRepository.deleteBatch(id);
            response.getWriter().write("Batch deleted successfully");
        } catch(Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error deleting batch.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get batch details
        int id = Integer.parseInt(request.getParameter("id"));
        
        try {
            Batch batch = batchRepository.getBatchById(id);
            if(batch != null) {
                response.getWriter().write("Batch details: " + batch.toString());
            } else {
                response.getWriter().write("Batch not found.");
            }
        } catch(Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error retrieving batch details.");
        }
    }
}
