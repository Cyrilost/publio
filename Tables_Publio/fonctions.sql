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