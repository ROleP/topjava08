<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://example.com/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        tr.exceed {
            color: red;
        }
        tr.normal {
            color: green;
        }
        a.button {
            -webkit-appearance: button;
            -moz-appearance: button;
            appearance: button;

            text-decoration: none;
            color: initial;
            margin-outside: 10;
        }
        .hideextra { white-space: nowrap; overflow: hidden; text-overflow:ellipsis; }
    </style>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h1>Meals list</h1>
<form action="meals" method="get" id="filter">
    <h3>Filters:</h3>
    <button type="reset" form="filter">Reset</button>
        Date From: <input type="date" name="sd" value="${startDate}" min="2016-01-01" max="2037-12-31" pattern="\d{4}-\d{2}-\d{2}"><br>
        Date To: <input type="date" name="ed" value="${endDate}" min="2016-01-01" max="2037-12-31" pattern="\d{4}-\d{2}-\d{2}"><br>
    <hr>
        Time From: <input type="time" name="st" value="${startTime}" pattern="\d{2}:\d{2}:\d{2}"><br>
        Time To: <input type="time" name="et" value="${endTime}" pattern="\d{2}:\d{2}:\d{2}"><br>
    <hr>
        Daily calories limit: <input type="number" name="cal" value="${caloriesLimit}" min="0" max="99999"><br>
    <hr>
    <input type="submit" value="Submit">
</form>
<br>
<a href="meals?action=add" class="button">Add new meal</a>
<br>
    <form action="meals" method="get">
    <table>
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="meal" items="${meals}">
            <tr <c:choose><c:when test="${meal.exceed==true}"> class="exceed"</c:when>
                    <c:otherwise> class="normal"</c:otherwise></c:choose>>
                <td><c:out value="${f:formatLocalDateTime(meal.dateTime, 'yyyy-MM-dd HH:mm:ss')}"/></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                <td class="hideextra" width="150px" align="center">
                    <a href="meals?action=edit&id=${meal.id}" class="button">Edit</a>
                    <a href="meals?action=delete&id=${meal.id}" class="button">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</form>
</body>
</html>
