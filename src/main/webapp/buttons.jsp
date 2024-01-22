
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3> Choose an option below: </h3>

<form action="${pageContext.request.contextPath}/index.jsp" method="POST">
    <input type="submit" value="Register new receipt">
</form>

<form action="${pageContext.request.contextPath}/edit.jsp" method="POST">
   <input type="submit" value="Edit your receipt">
</form>

</body>
</html>
