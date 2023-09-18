<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.dao.BatchRepository" %>
<%@ page import="com.members.Batch" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>List of Batches</title>
</head>
<body>
    <h2>Batches:</h2>
    <table border="1">
        <tr>
            <th>Batch Name</th>
            <th>Time</th>
            <th>Participants</th>
        </tr>
        <% 
           try {
               BatchRepository repository = new BatchRepository();
               List<Batch> batches = repository.getBatches();
               for(Batch batch : batches) {
        %>
        <tr>
            <td><%= batch.getBatchName() %></td>
            <td><%= batch.getTime() %></td>
            <td><%= repository.getParticipantCountForBatch(batch.getId()) %></td>
        </tr>
        <% 
               } 
           } catch (Exception e) {
               e.printStackTrace();
        %>
           <tr>
               <td colspan="3">Error fetching batches: <%= e.getMessage() %></td>
           </tr>
        <% 
           }
        %>
    </table>
</body>
</html>
