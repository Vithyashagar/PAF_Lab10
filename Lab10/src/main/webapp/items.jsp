<%@page import = "com.item"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="utf-8">
		<meta name = "viewport" content="width=device-width, initial-scale=1" >
		<title>Items Management</title>
		<link href="Views/bootstrap.min.css" rel="stylesheet">
		<script src="components/jquery-3.4.0.min.js" type ="text/javascript"></script>
		<script src="components/items.js" type ="text/javascript"></script>
	</head>
	<body>
		<div class ="container">
			<div class ="row">
				<div class ="col">							
					<h1>Items Management</h1>
					
					<form id="formItem" name="formItem" method="post" action="items.jsp">
						Item code: <input id="itemCode" name="itemCode" type="text" class="form-control form-control-sm"><br>
						
						Item name: <input id="itemName" name="itemName" type="text" class="form-control form-control-sm"><br>
						
						Item price: <input id="itemPrice" name="itemPrice" type="text" class="form-control form-control-sm"><br>
						
						Item description: <input id="itemDesc" name="itemDesc" type="text" class="form-control form-control-sm"><br>
						
						<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
						<input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">
					</form>
					
					<!-- Alert Messages -->
					<div id="alertSuccess" class="alert alert-success"></div>
					<div id="alertError" class="alert alert-danger"></div>
					
					<br>
					<%
						item itemObj = new item();
						out.print(itemObj.readItems());
					%>			
				</div>
			</div>
		</div>
	</body>

	
</html>