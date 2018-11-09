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
<form method="post" action="Choixdemande">
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
 <legend>Liste de tous livres disponibles</legend>
 <p>Selectionnez le livre que vous souhaitez emprunter</p>
 <table border="1">
 <tr>
 <th>Choix</th>
 <th>Auteur</th>
 <th>Titre</th>
 <th>Sous-Titre</th>
 <th>Tome</th>
 <th>Nombre de pages</th>
 <th>Etat du livre</th> 
 </tr>
 <c:forEach items="${listlivre}" var="livre" varStatus="boucle">
 <tr>
 <td><input type="radio" name="choixlivre" value="<c:out value="${livre.getIdLivre() }"/>"></td>
 <td>${livre.getAuteur() }</td>
 <td>${livre.getTitre() }</td>
 <td>${livre.getSousTitre() }</td>
 <td>${livre.getTome() }</td>
 <td>${livre.getNbPage() }</td>
 <td>
 <c:if test = "${ livre.getEtatLivre().equals(true) }">
 <p><c:out value = "bon état" /></p>
 </c:if>
 <c:if test = "${ livre.getEtatLivre().equals(false) }">
 <p><c:out value = "usé" /></p>
 </c:if>
 </td>
 </tr>	
 </c:forEach>
 </table>
 </fieldset>
 <br/>
 <input type="submit" value="validez" />
 <br/>
 <span>
 <c:forEach items="${message}" var="m" varStatus="boucle">
 <p><span class="erreur">${ m }</span></p>
 </c:forEach>
 </span>
</div>
</form>
<form>
<br/>
<p>Retour au menu principal</p>
 <input type = "button" value = "Retour"  onclick = "history.go(-1)">
 </form> 
</body>
</html>