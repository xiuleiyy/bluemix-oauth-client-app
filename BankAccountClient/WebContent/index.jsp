<%@page import="com.bluemix.bankacct.lib.ConfigParms"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to Bank Account Client Application</title>
</head>
<body text="black" background="background.jpg">
<center>
<h1>Welcome to Bank Account Client Application</h1>

<table>
<tr><td width="450">

<A href="LogIn?scope=<%=ConfigParms.SCOPE%>""><IMG border="0" src="uaa_plugin.jpg"
	width="350" height="200"></A>
<BR>
<font size=2><i>Access Protected Resource <b><font color="red"">WITH</font></b> Request Scope</i></font><br>
<br><br>
</td>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<td>
<A href="LogIn"><IMG border="0" src="uaa.jpg"
	width="350" height="200"></A>
<BR>
<font size=2><i>Access Protected Resource <b><font color="red"">WITHOUT</font></b> Request Scope</i></font><br>
<br><br>
</td></tr>

</td></tr>

</table>

 
</center>
<center>
<p><font color="blue"><b>Default user/pass is BankAdmin/pass4icap</b></font> </p><br><br><br><br>
<a href="GetEnv">Get System Env</a><br><br><br>
</center>
</body>
</html>