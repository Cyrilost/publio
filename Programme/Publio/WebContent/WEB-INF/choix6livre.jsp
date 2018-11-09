<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/style.css" />
<title>choix</title>
</head>
<body>
<div>
 <fieldset>
 <legend>Informations sur le livre</legend>
 <p><span class="erreur">${message}</span></p> 
 <table border="1">
 <tr>
 <th>Auteur</th>
 <th>Titre</th>
 <th>Sous-Titre</th>
 <th>Tome</th>
 <th>Nb de pages</th>
 <th>Etat du livre</th>
 <th>Date retour prévu</th>
 <th>Nom du détenteur</th>
 <th>Prenom du détenteur</th>
 </tr>
 <tr>
 <td>${livre.getAuteur()} </td>
 <td>${livre.getTitre() } </td>
 <td>${livre.getSousTitre()}</td>
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
 <td>
 <fmt:formatDate type = "date" pattern="yyyy-MM-dd" value = "${livre.getDateRetourPrévu()}" />
 </td>
 <td>${livre.utilisateur.getNom() }</td>
 <td>${livre.utilisateur.getPrenom() }</td>
 </tr>	
 </table>
 </fieldset>
 </div>
 <form>
 <input type = "button" value = "Retour"  onclick = "history.go(-2)">
 </form> 
</body>
</html>