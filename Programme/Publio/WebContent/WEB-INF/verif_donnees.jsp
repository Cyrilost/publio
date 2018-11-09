<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/style.css" />
<title>Verification</title>
</head>
<div>
 <img src="img/Publio2.jpg" />
 </div>
 <div>
<form method="post" action="Menu">
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
<label for="adresse">Adresse</label>
<input type="text" id="adresse"
name="adresse" value="${utilisateur.getAdresse()}" size="32" maxlength="64" readonly/>
</p>
<p>
<label for="telephone">Telephone</label> 
<input type="text" id="telephone"
name="telephone" value="${utilisateur.getTelephone()}" size="10" maxlength="10" readonly/>
</p>
<p>
<label for="biblio">Détient une Bibliothèque</label>
<input type="text" id="biblio"
name="biblio" value="${utilisateur.getBiblio()}" size="5" maxlength="5" readonly/>
<p>
<label for="id">Id</label>
<input type="text" id="id"
name="id" value="${utilisateur.getIdUser()}" size="2" maxlength="2" readonly/>
</p>
</fieldset>
<input type="submit" value="Accès Menu" />
<br/>
<p>
<span class="succes">${message}</span>
<span class="erreur">${messageerreur}</span>
<span class="info">${messageinfo}</span>
</p>
<br/>
</form>
</div>
</body>
</html>