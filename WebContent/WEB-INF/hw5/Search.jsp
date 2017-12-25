<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<% String ev = (String)request.getSession().getAttribute("ev"); %>
<% if(ev!=null){ %>
<%-- set data source --%>
<sql:setDataSource
    driver="com.mysql.jdbc.Driver"
    url="jdbc:mysql://localhost:8080"
   	"/>

<%-- query --%>
<sql:query var="entries">
<%=ev %>
</sql:query>
<% } %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Starbucks Locator</title>
<style> body{background-image: url(http://www.sweetcomments.net/glitterjoy/backgrounds/random/starbucks.jpg);} ul { background-color: white; display:table; margin:0 auto;}#wdth{width:150px; size:10} #wdth option{width:150px; size:10}</style><title>CoffeeShop</title><link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<br><br><br><br>
<body><center>
<form action="Search" method="post" >
		<link rel="stylesheet" href="http://albertcervantes.com/cs320/cdn/homework2/styles/animate.css">
		<div class="container"><div class="row"><div class="col-sm-offset-3 col-sm-6 newsletter-form"><div class="well text-center"><br><br>
		<h2>Starbucks Shop Locator</h2><br>
		Enter the Radius in miles: <input type="text" name="ev1">
		<br><br><br><select name="type" id="wdth"><option name="location" value="location" style="width:90%;">Location</option><option name="city" value="city">City</option><option name="zip" value="zip">Zip Code</option></select> (Location Format: Latitude,Longitude) <br><br><br>
		<div class="form-group"><div class="input-group"><div class="input-group-addon"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></div><input class="form-control" type="text" name="ev" placeholder="Enter the Selected Value"/></div></div>
		<br><br><button type="submit" class="btn btn-primary" value="Search">Search</button></form></center>
	<c:if test="${entries.rowCount == 0}">
		<p>There aren't any entries in that field</p>
	</c:if>
	<c:if test="${entries.rowCount > 0}">
		<center><b><h1 style="background-color:white;">Nearby Starbucks Stores</h1></b></center>
		<br>
		<ul type = "square">
			<br>Display Order:
			<li>City</li>
			<li>Address</li>
			<li>Zip Code</li>
			<li>Distance from Current Location (if searched by coordinates)&nbsp;&nbsp;&nbsp;</li><br>
		</ul><br>
		<ul id="results-list"><br>
			<c:forEach items="${entries.rowsByIndex}" var="row">
				<c:forEach items="${row}" var="col">
					<li id="result-item">${col}</li>
				</c:forEach>
			~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			</c:forEach><br>
		</ul>
	</c:if>
</body>
</html>