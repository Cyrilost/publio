/* Test unitaire pour la phase 2
 * vérification du fonctionnement de la connection :
 * utilisateur avec un mot de passe valide : test avec lenaic
 * utilisateur avec un mot de pass invalide mais avec une phrase valide : test avec yvain
 * utilisateur 1er connection sans mot de passe ni de phrase : test avec cyril
*/ 
INSERT INTO utilisateur(adresse,biblio,mdp,nb_avert_retard,nb_avert_etat,nb_pret_encours,nom,phrase,prenom,telephone)
VALUES ('7 rue de l_orge',true,'az',0,0,0,'guillaume','courcouronnes','lenaic','0104070205');
INSERT INTO utilisateur(nom,prenom,adresse,telephone,biblio,nb_avert_retard,nb_avert_etat,nb_pret_encours,mdp,phrase)
VALUES ('guillaume','yvain','7 rue de l_orge','0104070205',true,0,0,0,'qs','courcouronnes');
INSERT INTO utilisateur(nom,prenom,adresse,telephone,biblio,nb_avert_retard,nb_avert_etat,nb_pret_encours,mdp,phrase)
VALUES ('guillaume','cyril','7 rue de l_orge','0104070205',false,0,0,0,null,null);

-- Test unitaire de la phase 3 : visualisation

-- Visualisation de l'ensemble des livres
INSERT INTO livre(titre,sous_titre,tome,auteur,nb_page,etat_livre,id_mod)
VALUES ('Achille',null,null,'claude merle',172,true,1);
INSERT INTO livre(titre,sous_titre,tome,auteur,nb_page,etat_livre,id_mod)
VALUES ('Méthode mathématique','cours et exercices',null,'Jacqu Velu',421,true,2);
INSERT INTO livre(titre,sous_titre,tome,auteur,nb_page,etat_livre,id_mod)
VALUES ('Star Wars','legacy','4','Jan Duursema',104,true,1);
/* Visualisation de livre en cours d'emprunt
 (cyril emprunte à lenaïc, le livre Achille)*/
 INSERT INTO autoriser (id_util,id_mod,reponse)
 VALUES(3,1,true);
 INSERT INTO demander (id_user_dem,id_livre_dem,id_user_det,reponse)
 VALUES (3,1,1,true);
 INSERT INTO lier (id_util,id_mod)
 VALUES (3,1);
 INSERT INTO pret_ret(id_livre,id_empr1,date_retour_prevu,id_det)
 VALUES (1,3,'20180629',1);
 UPDATE livre SET date_retour_prévu='20180629' WHERE id_livre=1;
 UPDATE utilisateur SET nb_pret_encours = 1 WHERE id_user=3;
 /* visualiser les demande de prêt
 Lenaic demande l'emprunt du livre d'yvain */
 INSERT INTO autoriser (id_util,id_mod,reponse)
 VALUES(1,2,true);
 INSERT INTO demander (id_user_dem,id_livre_dem,id_user_det)
 VALUES (1,2,2);
 INSERT INTO lier (id_util,id_mod)
 VALUES (1,2);
 /* visualiser les demande d'autorisation
 yvain a fait une demande d'acces au livre de lenaïc*/
 INSERT INTO autoriser (id_util,id_mod)
 VALUES(2,1);
 /* visualiser les livre de sa bibliothèque
 visualsation de ceux de Lenaïc qui en possède 2
 visualsation de ceux d'Yvain qui en possède 1
 
 * visualiser les demandes de prêt à traiter
 * visualiser les demande d'autorisation à traiter
 */

-- Test unitaire de la phase 4 : interrogation

/*rechercher les livres empruntés
 *rechercher les informations d'un livre
 *Rechercher la liste des livres lus par l'un des ses utilisateurs
 *Rechercher les informations d'un membre
 */
INSERT INTO livre(titre,sous_titre,tome,auteur,nb_page,etat_livre,id_mod)
VALUES ('télérama','condamnée a l exil','3466','groupe le monde',146,true,2);
INSERT INTO autoriser (id_util,id_mod,reponse)
VALUES(3,2,true);
INSERT INTO demander (id_user_dem,id_livre_dem,id_user_det,reponse)
VALUES (3,4,2,true);
INSERT INTO lier (id_util,id_mod)
VALUES (3,2);
INSERT INTO pret_ret(id_livre,id_empr1,date_retour_prevu,id_det,etat_livre_rendu,date_retour_reel)
VALUES (4,3,'20180624',2,true,'20180623');

/* Test unitaire de la phase 5 : les entrées

Connection avec le compte de Lenaïc pour faire :
	modifier son compte
	créer un compte utilisateur
	créer un livre
	modifier dans un compte utilisateur
	modifier dans un livre de sa bibliothèque, ainsi que la date de prêt

Avec le nouveau compte créer faire :	
	Demander une autorisation accès à une bibliothèque	(celle d'Yvain)
	Demander le prêt d'un livre (au près de lenaic)

Se connecter avec le compte de yvain pour :
	Répondre à une demande d'autorisation d'acces à la bibliothèque

Se connecter avec le compte de Lenaïc pour :	
	Répondre à un utilisateur sur le prêt d'un livre
*/
