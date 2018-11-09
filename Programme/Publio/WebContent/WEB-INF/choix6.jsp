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
<form method="post" action="Choixlivre">
<div>
 <fieldset>
 <legend>Liste de tous livres</legend>
 <table border="1">
 <tr>
 <th>Choix</th>
 <th>Auteur</th>
 <th>Titre</th>
 <th>Sous-Titre</th>
 <th>Tome</th>
 </tr>
 <c:forEach items="${listlivre}" var="livre" varStatus="boucle">
 <tr>
 <td><input type="radio" name="choixlivre" value="<c:out value="${livre.getIdLivre()}"/>"></td>
 <td>${livre.getAuteur() } </td>
 <td>${livre.getTitre() } </td>
 <td>${livre.getSousTitre() }</td>
 <td>${livre.getTome() }</td>
 </tr>	
 </c:forEach>
 </table>
 </fieldset>
 </div>
 <input type="submit" value="validez" />
 </form> 
</body>
</html>