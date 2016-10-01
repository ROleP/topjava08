<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://example.com/functions" %>
<html>
<head>
    <title>Add or Edit meal</title>
</head>
<body>
<form action="meals" method="post" id="params">
    <button type="reset" form="params">Reset</button>
    Description: <input type="text" name="desc" value="${meal.description}"/>
    Date: <input type="datetime" name="dt" value="${f:formatLocalDateTime(meal.dateTime, 'yyyy-MM-dd HH:mm:ss')}" pattern="\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}"/>
    Calories: <input type="number" name="cal" value="${meal.calories}" min="0" max="9999"/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
