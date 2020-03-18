<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/style.css" />
<title>Reponse acces</title>
</head>
<body>
<form method="post" action="ReponseDem">
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
<legend>Réponse à la demande d'autorisation</legend>
<br />
<p>
<label for="id">Identification de la demande</label>
<input type="text" id="choixdem"
name="choixdem" value="${id}" size="2" maxlength="3" readonly/>
</p>
<p>
<label for="nom">Nom</label>
<input type="text" id="nom"
name="nom" value="${nom}" size="32" maxlength="32" disabled/>
</p>
<p>
<label for="prenom">Prenom</label>
<input type="text" id="prenom"
name="prenom" value="${prenom}" size="32" maxlength="32" disabled/>
</p>
<label for="reponse">Reponse<span class="requis">*</span></label>
<input type="radio" name="reponse1"  value="<c:out value="true" />">Accepter
<input type="radio" name="reponse2"  value="<c:out value="true" />">Refuser
<br />
</fieldset>
</div>
<input type="submit" value="Enregistrement" />
<br/>
<span class="info">${messageinfo}</span>
<br/>
<span class="succes">${message}</span>
<br/>
 <input type = "button" value = "Retour Menu"  onclick = "history.go(-3)">
</form> 
</body>
</html>