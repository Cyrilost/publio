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
<form method="post" action="Choixuserbooks">
<div>
<fieldset>
<legend>Information de l'utilisateur</legend>
<p>
<label for="id">Id</label>
<input type="text" id="id"
name="id" value="${ user.getIdUser() }" size="2" maxlength="2" readonly/>
</p>
<p>
<label for="nom">Nom</label>
<input type="text" id="nom"
name="nom" value="${ user.getNom() }" size="32" maxlength="32" readonly/>
</p>
<p>
<label for="prenom">Prenom</label>
<input type="text" id="prenom"
name="prenom" value="${ user.getPrenom() }" size="32" maxlength="32" readonly/>
</p>
</fieldset>
</div>
<div>
 <fieldset>
 <legend>Liste de tous ses utilisateurs</legend>
 <table border="1">
 <tr>
 <th>Choix</th>
 <th>Nom</th>
 <th>Prenon</th>
 </tr>
 
 <c:forEach items="${listutil}" var="list" varStatus="boucle">
 <tr>
 <td><input type="radio" name="choixutil" value="<c:out value="${list[0]}"/>"></td>
 <td>${list[1]}</td>
 <td>${list[2]}</td>
 </tr>	
 </c:forEach>
 </table>
 </fieldset>
 </div>
 <input type="submit" value="validez" />
</form> 
</body>
</html>