<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <link rel="stylesheet" href="css/style.css" />
<title>Publio_Menu</title>
</head>

<body>
<form method="post" action="Choix">
 <div>
 <img src="img/Publio2.jpg" />
 </div>
 <div>
 <fieldset>
 <legend>Information de l'utilisateur</legend>
<p><span class="info">${message}</span></p>
<p>
<label for="id">Id</label>
<input type="text" id="id"
name="id" value="${id}" size="2" maxlength="2" readonly/>
</p>
<p>
<label for="nom">Nom</label>
<input type="text" id="nom"
name="nom" value="${nom}" size="32" maxlength="32" readonly/>
</p>
<p>
<label for="prenom">Prenom</label>
<input type="text" id="prenom"
name="prenom" value="${prenom}" size="32" maxlength="32" readonly/>
</p>
<p>
<label for="nbretard">Nombre d'avertissement de retard</label>
<input type="text" id="nbretard"
name="nbretard" value="${nbretard}" size="1" maxlength="1" readonly/>
</p>
<p>
<label for="nbretat">Nombre de livres rendus en mauvais état</label>
<input type="text" id="nbretat"
name="nbretat" value="${nbretat}" size="1" maxlength="1" readonly/>
</p>
<p>
<label for="nbrencours">Nombre d'emprunt de livre en cours</label>
<input type="text" id="nbrencours"
name="nbrencours" value="${nbrencours}" size="1" maxlength="1" readonly/>
</p>
<p><span class="erreur">${messageerreur}</span>
 </fieldset>
 </div>
 <div>
 <fieldset>
 <legend>Menu de l'utilisateur</legend>
 <p> Selectionnez votre choix puis validez </p>
 <c:forEach items="${menu}" var="menu" varStatus="boucle">
 <p><input type="radio" name="choixmenu" value="<c:out value="${boucle.count}"/>">${ menu }</p>
 </c:forEach>
 </fieldset>
 <input type="submit" value="valider" />
 </div>
 </form>
 </body>
</html>