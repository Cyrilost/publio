<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/style.css" />
<title>Traitement_Retour</title>
</head>
<body>
<div>
<img src="img/Publio2.jpg" />
</div>
<div>

<fieldset>
<legend>traitement du rendu du livre</legend>
<br />
<p>
<label for="id">Identification de la demande</label>
<input type="text" id="choixretour"
name="choixretour" value="${id}" size="2" maxlength="3" />
</p>
<p>
<label for="nom">Nom du détenteur du livre</label>
<input type="text" id="nomDet"
name="nomDet" value="${nomDet}" size="32" maxlength="32" disabled/>
</p>
<p>
<label for="prenom">Prenom du détenteur du livre</label>
<input type="text" id="prenomDet"
name="prenomDet" value="${prenomDet}" size="32" maxlength="32" disabled/>
</p>
<p>
<label for="titre">Titre</label>
<input type="text" id="titre"
name="titre" value="${titre}" size="32" maxlength="32" disabled/>
</p>
<p>
<label for="soustitre">Sous titre</label>
<input type="text" id="soustitre"
name="soustitre" value="${soustitre}" size="32" maxlength="32" disabled/>
</p>
<p>
<label for="tome">Tome</label>
<input type="text" id="tome"
name="tome" value="${tome}" size="32" maxlength="32" disabled/>
</p>
<p>
<label for="nom">Nom du réintégrateur du livre</label>
<input type="text" id="nomDem"
name="nomDem" value="${nomDem}" size="32" maxlength="32" disabled/>
</p>
<p>
<label for="prenom">Prenom du réintégrateur du livre</label>
<input type="text" id="prenomDem"
name="prenomDem" value="${prenomDem}" size="32" maxlength="32" disabled/>
</p>
<p>
<label for="date">Date de réintégration</label>
<input type="text" id="dateretour"
name="dateretour" value="${dateretour}" size="32" maxlength="32" disabled/>
</p>
<label for="reponse">Etat du livre<span class="requis">*</span></label>
<input type="text" id="etatlivre"
name="etatlivre" value="${etat}" size="32" maxlength="32" disabled/>
<br />
</fieldset>
<br/>
<span class="succes">${message}</span>
<br/>
<span class="info">${message2}</span>
<br/>
 <input type = "button" value = "Retour Menu"  onclick = "history.go(-3)">
 
</div>
</body>
</html>