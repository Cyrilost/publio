<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/style.css" />
<title>choix</title>
</head>
<body>
<div>
 <fieldset>
 <legend>Bravo vous êtes devenu un Moderateur !!!</legend>
 <p><span class="info">${ msg }</span></p>
 </fieldset>
 </div>
 <form>
 <input type = "button" value = "Deconnexion"  onclick = "history.go(-1)">
 </form> 
</body>
</html>