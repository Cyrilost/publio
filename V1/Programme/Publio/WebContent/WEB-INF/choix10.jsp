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
 <legend>Liste de tous livres encours d'emprunt</legend>
 <table border="1">
 <tr>
 <th>Nom Utilisateur</th>
 <th>Prenom Utilisateur</th>
 <th>Nom du moderateur</th>
 <th>Prenom du moderateur</th>
 <th>Reponse du moderateur</th>
 </tr>
 
 <c:forEach items="${listdem}" var="list" varStatus="boucle">
 <tr>
 <td>${list[0]} </td>
 <td>${list[1]} </td>
 <td>${list[2]} </td>
 <td>${list[3]} </td>
 <td><c:if test = "${ list[4].equals(true) }">
 <p><c:out value = "Accepter" /></p>
 </c:if>
 <c:if test = "${ list[4].equals(false) }">
 <p><c:out value = "Refuser" /></p>
 </c:if>
 <c:if test = "${ list[4] == null }">
 <p><c:out value = "En cours" /></p>
 </c:if> </td>
 </tr>	
 </c:forEach>
 </table>
 </fieldset>
 </div>
 <form>
 <input type = "button" value = "Retour"  onclick = "history.go(-1)">
 </form> 
</body>
</html>