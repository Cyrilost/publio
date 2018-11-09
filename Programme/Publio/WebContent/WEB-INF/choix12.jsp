<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/style.css" />
<title>création_Livre</title>
</head>
<body>
<div>
<img src="img/Publio2.jpg" />
</div>
<div>
<form method="post" action="NouveauLivre">
<fieldset>
<legend>Détenteur du livre</legend>
<label for="nom">Nom </label>
<input type="text" id="nom"
name="nom" value="<c:out value="${user.getNom()}"/>" size="20" maxlength="32" disabled />
<br />
<label for="prenom">Prénom </label>
<input type="text" id="prenom"
name="prenom" value="<c:out value="${user.getPrenom()}"/>" size="20" maxlength="32" disabled />
<br />
<label for="id">Identification</label>
<input type="text" id="id"
name="id" value="<c:out value="${user.getIdUser()}"/>" size="2" maxlength="2" readonly/>
<br />
</fieldset>
<fieldset>
<legend>Fiche Nouveau livre</legend>
<label for="titre">Titre<span class="requis">*</span></label>
<input type="text" id="titre"
name="titre" value="<c:out value="${param.titre}"/>" size="20" maxlength="32" />
<br />
<label for="sousTitre">Sous Titre</label>
<input type="text" id="sousTitre"
name="sousTitre" value="<c:out value="${param.sousTitre}"/>" size="20" maxlength="32" />
<br />
<label for="tome">Tome</label>
<input type="text" id="tome"
name="tome" value="<c:out value="${param.tome}"/>" size="20" maxlength="32" />
<br />
<label for="auteur">Auteur (nom + prenom)<span class="requis">*</span></label>
<input type="text" id="auteur"
name="auteur" value="<c:out value="${param.auteur}"/>" size="32" maxlength="64" />
<br />
<label for="nbPage">nombre de page<span class="requis">*</span></label>
<input type="text" id="nbPage"
name="nbPage" value="<c:out value="${param.nbPage}"/>" size="6" maxlength="6" />
<br />
<label for="etatLivre">état du livre<span class="requis">*</span></label>
<input type="radio" name="etatLivre"  value="<c:out value="true" />">Bon état
<input type="radio" name="etatLivre"  value="<c:out value="false" />">A déjà bien voyagé
<br />
</fieldset>
<input type="submit" value="Enregistrement" />
<br/>
<span class="message">${message}</span>
<br/>
</form>
<form>
 <input type = "button" value = "Retour"  onclick = "history.go(-2)">
</form> 
</div>
</body>
</html>