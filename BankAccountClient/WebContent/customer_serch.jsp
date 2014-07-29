<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search a Customer</title>
</head>
<body>


<form name="customerSearch" action="CustomerSearch">



Customer Name 
<select id="nameSelect" name="param">
	<option>Ben Franklin</option>
	<option>Albert Einstein</option>
	<option>Groucho Marx</option>
	<option>Harpo Marx</option>
	<option>Chico Marx</option>
	<option>Zeppo Marx</option>
	<option>Eleanor Roosevelt</option>
	<option>Jean-Paul Sartre</option>
</select>
<input id="search" name="search" type="submit" value="Search"></input>
</form>

</body>
</html>