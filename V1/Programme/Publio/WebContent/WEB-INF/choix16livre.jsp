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
 <legend>Liste de tous livres déjà empruntés par ${nom} ${prenom}</legend>
 <table border="1">
 <tr>
 <th>Titre</th>
 <th>Sous-Titre</th>
 <th>Tome</th>
 <th>Date du retour</th>
 <th>Nom du détenteur</th>
 <th>Prenon du détenteur</th>
 </tr>
 <c:forEach items="${livreemprunte}" var="list" varStatus="boucle">
 <tr>
 <td>${list[0]} </td>
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
 <form>
 <input type = "button" value = "Retour"  onclick = "history.go(-2)">
 </form> 
</body>
</html>