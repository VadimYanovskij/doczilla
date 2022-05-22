<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Students DB Application</title>
    <style>
        table, th, td {
            border: 1px solid black;
        }
        table {
            margin: 0 auto;
        }
        th, td {
            padding: 6px;
        }
    </style>
</head>
<body>
<div style="text-align: center;">
    <h1>Students Management</h1>
    <h2>
        <a href="new">Add New Student</a>
        <a href="list">List All Students</a>
    </h2>
</div>
<div style="text-align: center;">
    <table>
        <caption><h2>List of Students</h2></caption>
        <tr>
            <th>ID</th>
            <th>Lastname</th>
            <th>Name</th>
            <th>Middle name</th>
            <th>Birthday</th>
            <th>Group</th>
        </tr>
        <c:forEach var="student" items="${students}">
            <tr>
                <td><c:out value="${student.id}" /></td>
                <td><c:out value="${student.lastname}" /></td>
                <td><c:out value="${student.name}" /></td>
                <td><c:out value="${student.middleName}" /></td>
                <td><c:out value="${student.birthday}" /></td>
                <td><c:out value="${student.group}" /></td>
                <td>
                    <a href="delete?id=<c:out value='${student.id}' />">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
