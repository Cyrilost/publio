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
<div>
 <fieldset>
<legend>Informations sur l'utilisateur</legend>
<p>
<label for="nom">Nom</label>
<input type="text" id="nom"
name="nom" value="${user.getNom()}" size="32" maxlength="32" />
</p>
<p>
<label for="prenom">Prenom</label>
<input type="text" id="prenom"
name="prenom" value="${user.getPrenom()}" size="32" maxlength="32" />
</p>
<p>
<label for="adresse">Adresse</label>
<input type="text" id="adresse"
name="adresse" value="${user.getAdresse()}" size="60" maxlength="64" />
</p>
<p>
<label for="telephone">Telephone</label> 
<input type="text" id="telephone"
name="telephone" value="${user.getTelephone()}" size="10" maxlength="10" />
</p>
<p>
<label for="biblio">Détient une Bibliothèque</label>
<input type="text" id="biblio"
name="biblio" value="${user.getBiblio()}" size="5" maxlength="5" />
<p>
<label for="id">Id</label>
<input type="text" id="id"
name="id" value="${user.getIdUser()}" size="2" maxlength="2" />
<p>
<label for="nbretard">Nombre d'avertissement de retard</label>
<input type="text" id="nbretard"
name="nbretard" value="${user.getNbAvertRetard()}" size="1" maxlength="1" />
</p>
<p>
<label for="nbretat">Nombre de livres rendus en mauvais état</label>
<input type="text" id="nbretat"
name="nbretat" value="${user.getNbAvertEtat()}" size="1" maxlength="1" />
</p>
<p>
<label for="nbrencours">Nombre d'emprunt de livre en cours</label>
<input type="text" id="nbrencours"
name="nbrencours" value="${user.getNbPretEncours()}" size="1" maxlength="1" />
</p>
 </fieldset>
 </div>
 <form>
 <input type = "button" value = "Retour"  onclick = "history.go(-2)">
 </form> 
</body>
</html>