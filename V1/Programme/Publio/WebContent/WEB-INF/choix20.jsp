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
 <legend>Liste de tous les utilisateurs souhaitant accéder à votre bibliothèque</legend>
 <table border="1">
 <tr>
 <th>Numéro de l'autorisation</th>
 <th>Nom du future Utilisateur</th>
 <th>Prenom du futur Utilisateur</th>
 </tr>
 
 <c:forEach items="${listdem}" var="list" varStatus="boucle">
 <tr>
 <td>${list[0]} </td>
 <td>${list[1]} </td>
 <td>${list[2]} </td>
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