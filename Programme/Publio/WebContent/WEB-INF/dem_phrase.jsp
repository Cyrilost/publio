<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<link rel="stylesheet" href="css/style.css" />
<title>Publio_Phrase</title>
</head>
<body>
<div id="phrase" > 
<form method="post" action="ValiderPhrase">
<fieldset>
<legend>Connexion de l'utilisateur par la question</legend>
<p>
<label for="id">Id</label>
<input type="text" id="id"
name="id" value="${id}" size="2" maxlength="2" readonly/>
</p>
<label for="phrase">Quelle est votre ville de naissance ? <span class="requis">*</span></label>
<input type="text" id="phrase"
name="phrase" value="<c:out value="${param.phrase}"/>" size="20" maxlength="32" />
<br />
</fieldset>
<input type="submit"  value="Modifier mot de passe" />
<br/>
<span class="info">${messageinfo}</span>
<br/>
<span class="erreur">${messageerreur}</span>
</form>
</div>
<input type = "button" value = "Retour"  onclick = "history.go(-1)">
</body>
</html>