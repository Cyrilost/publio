<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<link rel="stylesheet" href="css/style.css" />
<title>Publio_Connect</title>
</head>
<body>
<div>
<img src="img/Publio2.jpg" />
</div>
<div id="zoneTexte">
<form id="connex_form" method="post" action="Connexion" >
<fieldset>
<legend>Connexion utilisateur</legend>
<label for="nom">Nom <span class="requis">*</span></label>
<input type="text" id="nom"
name="nom" value="<c:out value="${param.nom}"/>" size="20" maxlength="32" />
<br />
<label for="prenom">Prénom <span class="requis">*</span></label>
<input type="text" id="prenom"
name="prenom" value="<c:out value="${param.prenom}"/>" size="20" maxlength="32" />
<br />
<label for="motdepasse">mot de passe</label>
<input type="text" id="motdepasse"
name="motdepasse" value="<c:out value="${param.motdepasse}"/>" size="20" maxlength="32" />
<br />
</fieldset>
<input type="submit" value="Connexion" />
<br/>
<span class="info">${messageinfo}</span>
<br/>
<span class="erreur">${messageerreur}</span>
<br/>
</form>
</div>
</body>
</html>