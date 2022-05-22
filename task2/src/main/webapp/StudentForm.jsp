<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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

        .errors {
            display: flex;
            justify-content: center;
        }

        li {
            color: red;
        }
    </style>
</head>
<body>
<div style="text-align: center;">
    <h1>Students Management</h1>
    <h2>
        <a href="new">Add New Students</a>
        <a href="list">List All Students</a>
    </h2>
</div>
<div style="text-align: center;">
    <form action="insert" method="post">
        <table>
            <caption>
                <h2>
                    Add New Student
                </h2>
            </caption>
            <tr>
                <th>Name:</th>
                <td>
                    <input type="text" name="name" size="45" value="<c:out value='${student.name}'/>"/>
                </td>
            </tr>
            <tr>
                <th>Lastname:</th>
                <td>
                    <input type="text" name="lastname" size="45" value="<c:out value='${student.lastname}'/>"
                    />
                </td>
            </tr>
            <tr>
                <th>Middle name:</th>
                <td>
                    <input type="text" name="middleName" size="45" value="<c:out value='${student.middleName}'/>"
                    />
                </td>
            </tr>
            <tr>
                <th>Birthday:</th>
                <td>
                    <input type="date" name="birthday" size="10" value="<c:out value='${student.birthday}' />"
                    />
                </td>
            </tr>
            <tr>
                <th>Group:</th>
                <td>
                    <input type="text" name="group" size="45" value="<c:out value='${student.group}' />"
                    />
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: center;">
                    <input type="submit" value="Save"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<div class="errors">
    ${errors}
</div>
</body>
</html>
