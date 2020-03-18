<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/style.css" />
<title>Verification</title>
</head>
<body>

<body>
 <div>
 <img src="img/Publio2.jpg" />
 </div>
 <div>
<form method="post" action="Modif_Mdp">
<fieldset>
<legend>Carte d'identité de l'utilisateur</legend>
<p>
<label for="nom">Nom</label>
<input type="text" id="nom"
name="nom" value="${utilisateur.getNom()}" size="32" maxlength="32" readonly/>
</p>
<p>
<label for="prenom">Prenom</label>
<input type="text" id="prenom"
name="prenom" value="${utilisateur.getPrenom()}" size="32" maxlength="32" readonly/>
</p>
<p>
<label for="id">Id</label>
<input type="text" id="id"
name="id" value="${utilisateur.getIdUser()}" size="2" maxlength="2" readonly/>
</p>
</fieldset>
<fieldset>
<legend>Création du mot de passe</legend>
<p>
<span class="info">${message2}</span>
<br/>
<span class="succes">${messagesucces}</span>
<span class="erreur">${messageerreur}</span>
</p>
<br/>
<label for="motdepasse">veuillez saisir votre mot de passe <span class="requis">*</span></label>
<input type="text" id="motdepasse"
name="motdepasse" value="<c:out value="${param.motdepasse}"/>" size="20" maxlength="32" />
<br/>
</fieldset>
<input type="submit" value="Enregistrement" />
</form>
</div>
<div>
<form method="post" action="Menu">
<fieldset>
<legend>Accès au Menu</legend>
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