<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/style.css" />
<title>Verification</title>
<script type="text/javascript">
function valider_numero() {
	var nombre = document.modifier.telephone2.value;
	var chiffres = new String(nombre);

	// Enlever tous les charactères sauf les chiffres
	chiffres = chiffres.replace(/[^0-9]/g, '');

	// Le champs est vide
	if ( nombre == "" )
	{
	alert ( "Le champs est vide !" );
	return;
	}

	// Nombre de chiffres
	compteur = chiffres.length;

	if (compteur!=10)
	{
	alert("Assurez-vous de rentrer un numéro à 10 chiffres (xxxxxxxxxx)");
	return;
	}

	else
	{
	alert("Le numéro me semble bon !");
	}

	}
</script>
</head>
<body>

<body>
 <div>
 <img src="img/Publio2.jpg" />
 </div>
 
<div>
<form name="modifier" method="post" action="ModifCompte">
<fieldset>
<legend>Carte d'identité de l'utilisateur</legend>
<p>
<label for="nom">Nom</label>
<input type="text" id="nom"
name="nom" value="${utilisateur.getNom()}" size="32" maxlength="32" disabled/>
</p>
<p>
<label for="prenom">Prenom</label>
<input type="text" id="prenom"
name="prenom" value="${utilisateur.getPrenom()}" size="32" maxlength="32" disabled/>
</p>
<p>
<label for="adresse">Adresse</label>
<input type="text" id="adresse"
name="adresse" value="${utilisateur.getAdresse()}" size="32" maxlength="64" disabled/>
</p>
<p>
<label for="telephone">Telephone</label> 
<input type="text" id="telephone"
name="telephone" value="${utilisateur.getTelephone()}" size="10" maxlength="10" disabled/>
</p>
<p>
<label for="biblio">Détient une Bibliothèque</label>
<input type="text" id="biblio"
name="biblio" value="${utilisateur.getBiblio()}" size="5" maxlength="5" disabled/>
<p>
<label for="id">Id</label>
<input type="text" id="id"
name="id" value="${utilisateur.getIdUser()}" size="2" maxlength="2" readonly/>
</p>
</fieldset>
<fieldset>
<legend>Modifications</legend>
<p>
<label for="adresse2">Nouvelle Adresse</label>
<input type="text" id="adresse2"
name="adresse2" value="<c:out value="${param.adresse2}"/>" size="60" maxlength="64" />
</p>
<p>
<label for="telephone2">Nouveau Telephone</label> 
<input type="text" id="telephone2"
name="telephone2" value="<c:out value="${param.telephone2}"/>" size="10" maxlength="10" />
</p>
</fieldset>
<p>
<span class="info"><b>${msg1}</b></span>
<br/>
<span class="info"><b>${msg2}</b></span>
</p>
<p>
Si vous souhaitez modifier vos coordonnées, cliquez sur <b>modifier</b> sinom vous pouvez revenir au menu
</p>
<input type="submit" value="Modifier" onclick="valider_numero()"/>
<input type = "button" value = "Menu"  onclick = "history.go(-2)">
</form>
</div>
</body>
</html>