-- Création des tables

CREATE TABLE Utilisateur
(
	ID_user			SERIAL, 
	nom			CHARACTER VARYING(32)   NOT NULL,
	prenom			CHARACTER VARYING(32)   NOT NULL,
	adresse			CHARACTER VARYING(64)   NULL,
	telephone		CHARACTER(10)		NULL,
	biblio			BOOLEAN,
	nb_avert_retard		INTEGER			NULL,
	nb_avert_etat		INTEGER			NULL,
	nb_pret_encours		INTEGER			NULL,
	mdp			CHARACTER VARYING(32)   NULL,
	phrase			CHARACTER VARYING(64)   NULL,
	PRIMARY KEY(ID_user)
	
)
;

CREATE TABLE Livre
(
	ID_livre		SERIAL ,
	titre			CHARACTER VARYING(32)   NOT NULL,
	sous_titre		CHARACTER VARYING(32)   NULL,
	tome			CHARACTER VARYING(32)   NULL,
	auteur			CHARACTER VARYING(64)   NOT NULL,
	nb_page			INTEGER			NOT NULL,
	etat_livre		BOOLEAN,
	ID_mod			SERIAL,
	date_retour_prévu	DATE,
	PRIMARY KEY(ID_Livre),
	FOREIGN KEY(ID_mod) REFERENCES Utilisateur(ID_user)
)
;

CREATE TABLE Demander
(	
	ID_dem			SERIAL,
	ID_user_dem		SERIAL,
	ID_livre_dem	SERIAL,
	ID_user_det		SERIAL,
	reponse			BOOLEAN			NULL,
	PRIMARY KEY(ID_dem),
	FOREIGN KEY(ID_user_dem) REFERENCES Utilisateur(ID_user),
	FOREIGN KEY(ID_user_det) REFERENCES Utilisateur(ID_user),
	FOREIGN KEY(ID_livre_dem) REFERENCES Livre(ID_livre)
)
;

CREATE TABLE Pret_Ret
(
	ID_pret				SERIAL,
	ID_livre			SERIAL,
	ID_empr1			SERIAL,
	date_retour_prevu	DATE	NOT NULL,
	ID_det				SERIAL,
	etat_livre_rendu	BOOLEAN,
	date_retour_reel	DATE	NULL,
	PRIMARY KEY (ID_pret),
	FOREIGN KEY(ID_empr1) REFERENCES Utilisateur(ID_user),
	FOREIGN KEY(ID_det) REFERENCES Utilisateur(ID_user),
	FOREIGN KEY(ID_livre) REFERENCES Livre(ID_livre)
)
;

CREATE TABLE Autoriser
(	
	ID_autorisation		SERIAL ,
	ID_util				SERIAL,
	ID_mod				SERIAL,
	reponse				BOOLEAN		NULL,
	PRIMARY KEY (ID_autorisation),
	FOREIGN key (ID_util) REFERENCES Utilisateur(ID_user),
	FOREIGN key (ID_mod) REFERENCES Utilisateur(ID_user)
)
;

CREATE TABLE Lier
( 	
	ID_lien			SERIAL ,
	ID_util				SERIAL,
	ID_mod				SERIAL,
	PRIMARY KEY (ID_lien),
	FOREIGN key (ID_util) REFERENCES Utilisateur(ID_user),
	FOREIGN key (ID_mod) REFERENCES Utilisateur(ID_user)
)
;

-- Droits.

REVOKE ALL ON Utilisateur	FROM PUBLIC;
REVOKE ALL ON Livre		FROM PUBLIC;
REVOKE ALL ON Demander		FROM PUBLIC;
REVOKE ALL ON Pret_Ret		FROM PUBLIC;
REVOKE ALL ON Autoriser		FROM PUBLIC;
REVOKE ALL ON Lier		FROM PUBLIC;

GRANT SELECT ON Utilisateur	TO child;
GRANT SELECT ON Livre      	TO child;
GRANT SELECT ON Demander	TO child;
GRANT SELECT ON Pret_Ret 	    	TO child;
GRANT SELECT ON Autoriser   	TO child;
GRANT SELECT ON Lier		TO child;

GRANT INSERT ON Utilisateur 	TO child;
GRANT INSERT ON Livre      	TO child;
GRANT INSERT ON Demander   	TO child;
GRANT INSERT ON Pret_Ret		TO child;
GRANT INSERT ON Autoriser   	TO child;
GRANT INSERT ON Lier		TO child;

GRANT UPDATE ON Utilisateur TO child;
GRANT UPDATE ON Livre   	TO child;
GRANT UPDATE ON Demander	TO child;
GRANT UPDATE ON Pret_Ret		TO child;
GRANT UPDATE ON Autoriser	TO child;
GRANT UPDATE ON Lier		TO child;

GRANT ALL ON Utilisateur	TO postgres;
GRANT ALL ON Livre		TO postgres;
GRANT ALL ON Demander		TO postgres;
GRANT ALL ON Pret_Ret		TO postgres;
GRANT ALL ON Autoriser		TO postgres;
GRANT ALL ON Lier		TO postgres;

-- Droit particulier pour la création de 'SERIAL'
GRANT ALL ON SCHEMA public TO child;
GRANT usage ON ALL sequences in schema public TO child;

-- Fonctions (3)

-- calcul de la date prévu de retour en fonction du nombre de pages dans le livre
CREATE OR REPLACE FUNCTION CalCulRetourLivre(nb_page integer) RETURNS date
AS
$$
 DECLARE
  d date := null;
  r date;
 BEGIN
  if nb_page < 200 THEN
	r := current_date + integer '14' ;
  else  r := current_date + integer '21';
  end if;
 RETURN r;
 END
$$
LANGUAGE plpgsql; 

-- Verifier la difference enre la date de retour reel et celle qui etait prevue
-- Si la date reel est plus grande que la date prévue, ajout de penalité à l'utilisateur
CREATE OR REPLACE FUNCTION DifDatePrAvertissement(nmr_pret integer) RETURNS integer
AS
$$
 DECLARE
  datereel date := null;
  dateprevu date := null;
  idUtil integer := null;
  avertisUtil integer :=null;
  sanction integer := 1;
  r integer := 0;
 BEGIN
  -- recuperation donnees suite retour
  SELECT id_empr1, date_retour_prevu, date_retour_reel
  INTO idUtil, dateprevu, datereel
  FROM pret_ret
  WHERE id_pret = nmr_pret;
  -- recuperation donnees utilisateur
  SELECT nb_avert_retard
  INTO avertisUtil
  FROM utilisateur
  WHERE id_user = idUtil;
  -- comparaison
  IF (datereel > dateprevu) THEN
	avertisUtil = avertisUtil + sanction;
	r=1;
  END IF;
  -- mise Ã  jour du nombre d'avertissement chez l'utilisateur
  IF (r = 1) THEN
	UPDATE utilisateur
	SET nb_avert_retard = avertisUtil
	WHERE  id_user = idUtil;
END IF;
RETURN r;
END
$$
LANGUAGE plpgsql; 

-- fonction avec un retour text pour la mise en place d'un avertissement état pour un utilisateur
CREATE OR REPLACE FUNCTION AvertisEtat(nmr_util integer) RETURNS text
AS
$$
 DECLARE
  idUtil integer := null;
  avertisUtil integer :=null;
  sanction integer := 1;
  msg text ;
 BEGIN
  -- recuperation donnees utilisateur
  SELECT nb_avert_etat
  INTO avertisUtil
  FROM utilisateur
  WHERE id_user = nmr_util;
  -- application de la sanction
 avertisUtil = avertisUtil + sanction;
 msg = 'Un utilisateur a pris une avertissement sur un état de rendu du livre';
 UPDATE utilisateur
 SET nb_avert_etat = avertisUtil
 WHERE  id_user = idUtil;
RETURN msg;
END
$$
LANGUAGE plpgsql; 

--FIN