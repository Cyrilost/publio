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
	var nombre = document.modifier.telephone.value;
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
<div>
<img src="img/Publio2.jpg" />
</div>
 
<div>
<form method="post" action="NouvelUtilisateur">
<fieldset>
<legend>Inscription d'un nouvel utilisateur</legend>
<p>
<label for="id">Numéro d'identificataion</label>
<input type="text" id="idMod"
name="idMod" value="${utilisateur.getIdUser()}" size="2" maxlength="2" readonly/>
</p>
<p>
<label for="nom">Nom du modérateur</label>
<input type="text" id="nomMod"
name="nomMod" value="${utilisateur.getNom()}" size="32" maxlength="32" readonly/>
</p>
<p>
<label for="prenom">Prenom du modérateur</label>
<input type="text" id="prenomMod"
name="prenomMod" value="${utilisateur.getPrenom()}" size="32" maxlength="32" readonly/>
</p>
<p>
<label for="nom">Nom du nouvel utilisateur <span class="requis"></span></label>
<input type="text" id="nomNew"
name="nomNew" value="${ param.nomNew }" size="32" maxlength="32" />
<span class="erreur">${erreurs['nom']}</span>
</p>
<p>
<label for="prenom">Prenom du nouvel utilisateur <span class="requis"></span></label>
<input type="text" id="prenomNew"
name="prenomNew" value="${ param.prenomNew }" size="32" maxlength="32" />
<span class="erreur">${erreurs['prenom']}</span>
</p>
<p>
<label for="adresse">Adresse <span class="requis"></span></label>
<input type="text" id="adresseNew"
name="adresseNew" value="${ param.adresseNew }" size="30" maxlength="64" />
</p>
<p>
<label for="telephone">Telephone <span class="requis"></span></label> 
<input type="text" id="telephoneNew"
name="telephoneNew" value="${ param.telephoneNew }" size="10" maxlength="10" />
</p>
</fieldset>
<input type="submit" value="Valider" onclick="valider_numero()"/>
<br/>
<span class="erreur"><b>${erreurs['message']}</b></span><br/>
<span class="succes"><b>${resultat}</b></span><br/>
</form>
<form>
 <input type = "button" value = "Retour"  onclick = "history.go(-2)">
</form> 
</div>
</body>
</html>