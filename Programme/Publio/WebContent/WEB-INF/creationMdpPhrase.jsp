<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/style.css" />
<title>CréationSécurité</title>
</head>
<body>
<div>
<img src="img/Publio2.jpg" />
</div>
<div>
<form method="post" action="EnregistrementMdpPhrase">
<fieldset>
<legend>Création du mot de passe et phrase d'oublie pour l'utilisateur</legend>
<label for="nom">Nom </label>
<input type="text" id="nom"
name="nom" value="<c:out value="${user.nom}"/>" size="20" maxlength="32" readonly/>
<br />
<label for="prenom">Prénom </label>
<input type="text" id="prenom"
name="prenom" value="<c:out value="${user.prenom}"/>" size="20" maxlength="32" readonly/>
</fieldset>
<fieldset>
<br />
<legend>Création du mot de passe</legend>
<span class="info">${message2}</span>
<p>
<label for="motdepasse">veuillez saisir votre mot de passe</label>
<input type="text" id="motdepasse"
name="motdepasse" value="<c:out value="${param.motdepasse}"/>" size="20" maxlength="32" />
</p>
</fieldset>
<span class="erreur">${message}</span>
<fieldset>
<legend>Création du mot de passe subsidiaire, appelé la phrase</legend>
<p>
<a>Veuillez répondre à la question suivantes :</a>
<br/>
<label for="phrase">Dans quelle ville êtes-vous né(e) ? </label> 
<input type="text" id="phrase"
name="phrase" value="<c:out value="${param.phrase}"/>" size="20" maxlength="32" />
</p>
<br/>
</fieldset>
<input type="submit" value="Enregistrement" />
</form>
</div>
<div>
<form method="post" action="Menu">
<fieldset>
<legend>Accès au Menu</legend>
<span class="succes">${message3}</span>
<p>
<label for="id">Id</label>
<input type="text" id="id"
name="id" value="${id}" size="2" maxlength="2" readonly/>
</p>
<p>
<label for="biblio">Détient une Bibliothèque</label>
<input type="text" id="biblio"
name="biblio" value="${biblio}" size="5" maxlength="5" readonly/>
<p>
<label for="nom">Nom</label>
<input type="text" id="nom"
name="nom" value="${nom}" size="32" maxlength="32"  readonly/>
</p>
<p>
<label for="prenom">Prenom</label>
<input type="text" id="prenom"
name="prenom" value="${prenom}" size="32" maxlength="32"  readonly/>
</p>
</fieldset>
<br/>
<input type="submit" value="Accès Menu" />
<br/>
</form>
</div>
</body>
</html>