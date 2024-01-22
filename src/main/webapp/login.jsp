
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<p>${loginError}</p>

<form method="POST" action="${pageContext.request.contextPath}/home">
  <label>Username</label>
  <input type="text" name="username"/>

  <br>

  <label>Password</label>
  <input type="password" name="password"/>

  <button>Login</button>
</form>

</body>
</html>
