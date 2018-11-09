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
<form method="post" action="RetourLivre">
<div>
 <fieldset>
 <legend>Choisir le livre à retourner dans votre bibliothèque</legend>
 <table border="1">
 <tr>
 <th>Choix</th>
 <th>Titre</th>
 <th>Sous-Titre</th>
 <th>Tome</th>
 <th>Nom de l'emprunteur</th>
 <th>Prenon de l'emprunteurr</th>
 </tr>
 <c:forEach items="${listretour}" var="list" varStatus="boucle">
 <tr>
 <td><input type="radio" name="choixretour" value="${list[0]}"/></td>
 <td>${list[1]} </td>
 <td>${list[2]} </td>
 <td>${list[3]} </td>
 <td>${list[4]} </td>
 <td>${list[5]} </td>
 </tr>	
 </c:forEach>
 </table>
 </fieldset>
 </div>
 <input type="submit" value="validez" />
</form>
 <form>
 <input type = "button" value = "Retour"  onclick = "history.go(-1)">
 </form> 
</body>
</html>
