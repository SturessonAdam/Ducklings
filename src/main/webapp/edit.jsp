
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Page</title>
</head>
<body>
<h3>Delete receipt below:</h3>
<form action="${pageContext.request.contextPath}/InvoiceServlet/delete" method="POST">
  <label>ID-number on the receipt to delete</label>
  <input type="number" name="id">
  <button>
    Confirm
  </button>
</form>

<h3>Update receipt below:</h3>
<form action="${pageContext.request.contextPath}/InvoiceServlet/update" method="POST">
  <label>ID-number on the receipt to update</label>
  <input type="number" name="id">
  <br>
  <label>New title</label>
  <input type="text" name="title">
  <br>
  <label>New date</label>
  <input type="date" name="date">
  <br>
  <label>New description</label>
  <textarea name="description"></textarea>
  <br>
  <label>New category</label>
  <input type="text" name="category"/>
  <br>
  <label>new price</label>
  <input type="number" name="price"/>

  <button>Confirm</button>
</form>

</body>
</html>
