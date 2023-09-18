package com.servlet;

import com.members.Participant;
import com.dao.ParticipantRepository;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ParticipantServlet extends HttpServlet {
    private static final long serialVersionUID = 3291841912942734132L;
    private ParticipantRepository repository = new ParticipantRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Add a new participant
            String name = request.getParameter("name");
            int age = Integer.parseInt(request.getParameter("age"));
            String email = request.getParameter("email");
            
            Participant participant = new Participant(name, age, email);
            
            repository.addParticipant(participant);  
            
            response.setContentType("text/html");
            response.getWriter().write("Participant added successfully. <a href='index.html'>Back to Home</a>");
        } catch(Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Update an existing participant
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            int age = Integer.parseInt(request.getParameter("age"));
            String email = request.getParameter("email");
            
            Participant participant = new Participant(name, age, email);
            participant.setId(id);
            
            repository.updateParticipant(participant);
            
            response.getWriter().write("Participant updated successfully");
        } catch(Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Delete a participant
            int id = Integer.parseInt(request.getParameter("id"));
            repository.deleteParticipant(id);
            
            response.getWriter().write("Participant deleted successfully");
        } catch(Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get participant details
            int id = Integer.parseInt(request.getParameter("id"));
            Participant participant = repository.getParticipant(id);
            
            if(participant != null) {
                response.getWriter().write("Participant details: " + participant.toString());
            } else {
                response.getWriter().write("Participant not found");
            }
        } catch(Exception e) {
            throw new ServletException(e);
        }
    }
}
