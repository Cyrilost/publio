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
<form method="post" action="ModifLivre">
<div>
 <fieldset>
 <legend>liste des informations pouvant être modifiées</legend>
 <p>
 Numéro d'identification du livre : 
 <input type="text" id="idlivre"
name="idlivre" value="<c:out value="${livre.getIdLivre()}"/>" size="2" maxlength="3" />
 <table border="1">
 <tr>
 <td>Catégorie</td>
 <td>l'existant</td>
 <td>la modification</td>
 </tr>
 <tr>
 <td>le titre</td>
 <td>${livre.getTitre() } </td>
 <td><input type="text" id="titre"
name="titre" value="<c:out value="${param.titre}"/>" size="20" maxlength="32" />
 </td>
 </tr>
 <tr>
 <td>le sous-titre</td>
 <td>${livre.getSousTitre() }</td>
<td><input type="text" id="sousTitre"
name="sousTitre" value="<c:out value="${param.sousTitre}"/>" size="20" maxlength="32" />
</td>
 </tr>
 <tr>
 <td>le tome</td>
 <td>${livre.getTome() }</td>
 <td><input type="text" id="tome"
name="tome" value="<c:out value="${param.tome}"/>" size="20" maxlength="32" />
</td>
</tr>
 <tr>
 <td>l'auteur</td>
 <td>${livre.getAuteur() } </td>
 <td><input type="text" id="auteur"
name="auteur" value="<c:out value="${param.auteur}"/>" size="32" maxlength="64" />
 </td>
 </tr>
 <tr>
 <td>La date de retour prévu</td>
 <td>${livre.getDateRetourPrévu() }</td>
 <td><input type="text" id="dateRetourPrevu"
name="dateRetourPrevu" value="<c:out value="${param.dateRetourPrevu}"/>" size="32" maxlength="64" />
 </td>
 </tr>	
 </table>
 </fieldset>
 </div>
 <p><span class="erreur">Veuillez inscrire la date sous le format suivant : YYYY-MM-DD, merci.</span></p>
 <input type="submit" value="Modifier" />
 </form>
 <c:forEach items="${msg}" var="message" varStatus="boucle">
 <p><span class="info">${message }</span></p>
 </c:forEach>
 <form>
 <input type = "button" value = "Retour"  onclick = "history.go(-2)">
</form>  
</body>
</html>