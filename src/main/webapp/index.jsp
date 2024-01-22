<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Register your receipt</title>
</head>
<body>
<h3>Register new receipt below: </h3>

<form action="${pageContext.request.contextPath}/InvoiceServlet/" method="POST">
  <label>Title</label>
  <input type="text" name="title"/>
  <br>
  <label>Date</label>
  <input type="date" name="date"/>
  <br>
  <label>Description</label>
  <textarea name="description"></textarea>
  <br>
  <label>Category</label>
  <input type="text" name="category"/>
  <br>
  <label>Price</label>
  <input type="number" name="price"/>

  <button>Submit</button>
</form>

</body>
</html>