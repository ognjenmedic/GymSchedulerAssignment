<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.dao.ParticipantRepository" %>
<%@ page import="com.members.Participant" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>List of Participants</title>
</head>
<body>
    <h2>Participants:</h2>
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Age</th>
            <th>Email</th>
        </tr>
        <% 
           try {
        	   List<Participant> participants = com.dao.ParticipantRepository.getParticipants();
               for(Participant participant : participants) {
        %>
        <tr>
            <td><%= participant.getName() %></td>
            <td><%= participant.getAge() %></td>
            <td><%= participant.getEmail() %></td>
        </tr>
        <% 
               } 
           } catch(Exception e) {
               e.printStackTrace();
        %>
        <tr>
            <td colspan="3">Error fetching participants. Please try again later.</td>
        </tr>
        <% 
           }
        %>
    </table>
</body>
</html>
