<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.dao.BatchRepository" %>
<%@ page import="com.members.Batch" %>
<%@ page import="com.members.Participant" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Participants of Batch</title>
</head>
<body>
    <%
    String batchName = request.getParameter("batchName");
    BatchRepository batchRepo = new BatchRepository();  // Create an instance of the repository
    Batch batch = batchRepo.getBatchByName(batchName);  // Use the instance to call the method
    if(batch != null) {
%>

        <h2>Participants of Batch: <%= batch.getBatchName() %></h2>
        <table border="1">
            <tr>
                <th>Name</th>
                <th>Age</th>
                <th>Email</th>
            </tr>
            <% 
               for(Participant participant : batch.getParticipants()) {
            %>
            <tr>
                <td><%= participant.getName() %></td>
                <td><%= participant.getAge() %></td>
                <td><%= participant.getEmail() %></td>
            </tr>
            <% 
               } 
            %>
        </table>
    <% 
        } else {
    %>
        <h3>No batch found with name: <%= batchName %></h3>
    <% 
        }
    %>
</body>
</html>
