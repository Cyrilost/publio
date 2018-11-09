package ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jpa.Lier;
import jpa.Utilisateur;
/**
 * Session Bean implementation class EJBUtilisateur
 */
@Stateless
@LocalBean
public class EJBUtilisateur {
	
	private static final String PARAM_DETENTEUR = "idDet";
	private static final String PARAM_DEMENDEUR = "idDem";
	private static final String PARAM_ID = "id";
	private static final String PARAM_TEL = "tel";
	private static final String PARAM_ADRESSE = "adresse";
	private static final String PARAM_NOM = "nom";
	private static final String PARAM_PRENOM = "prenom";
	private static final String PARAM_NBLIVRE = "nblivre";
	private static final String PARAM_MDP = "mdp";
	
	private static final String JPQL_CALCUL_AVERT_ETAT = "select avertisetat(?)";
	
	private static final String JPQL_SELECT_PAR_NOM_PRENOM = "select u from Utilisateur u where u.idUser = :id";
	
	private static final String JPQL_UPDATE_TELEPHONE = "update Utilisateur u"
			+ " set u.telephone= :tel"
			+ " where u.idUser= :id ";
	private static final String JPQL_UPDATE_ADRESSE = "update Utilisateur u"
			+ " set u.adresse= :adresse"
			+ " where u.idUser= :id ";
	private static final String JPQL_VERIF_LIEN = "select l"
			+ " from Lier l"
			+ " where l.utilisateur1.idUser = :idDet"
			+ " and l.utilisateur2.idUser = :idDem";
	private static final String JPQL_CREER_MOD = "UPDATE Utilisateur u"
			+ " SET u.biblio="+true+" "
			+ " where u.idUser = :id";
	private static final String JPQL_CONTROLE_NEW_UTILISATEUR ="select u"
			+ " from Utilisateur u"
			+ " where u.nom = :nom"
			+ " and u.prenom = :prenom";
	private static final String JPQL_INSERT_USER = "insert into Utilisateur"
			+ "(nom,prenom,adresse,telephone,biblio,nb_avert_retard,nb_avert_etat,nb_pret_encours,mdp,phrase)"
			+ " VALUES"
			+ " (?,?,?,?,false,0,0,0,null,null)";
	private static final String JPQL_UPDATE_NBLIVRE = "UPDATE Utilisateur u"
			+ " SET u.nbPretEncours = :nblivre"
			+ " where u.idUser = :id";
	private static final String JPQL_UPDATE_MDP = "update Utilisateur u"
			+ " set u.mdp= :mdp"
			+ " where u.idUser= :id ";
	
	@PersistenceContext (unitName = "publio")
	private EntityManager em;

	/* 
	 * Méthodes et fonctions
	 */
	
public String Averto (int id)throws EJBException{
		
		String result;	
		StringBuilder sql = new StringBuilder(JPQL_CALCUL_AVERT_ETAT);
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter(1, id);
		result = (String) query.getSingleResult();
		return result;
	}
	
	
	// Recherche d'un utilisateur à parir de son id
	
	public Utilisateur load(int id)throws EJBException{
		
		Utilisateur utilisateur = new Utilisateur();
	    try{
	    	Query requete = em.createQuery( JPQL_SELECT_PAR_NOM_PRENOM );
	    	requete.setParameter ( PARAM_ID, id );
	    	utilisateur = (Utilisateur) requete.getSingleResult();
	    	} catch (NoResultException e) {
	    		return null;
	    	} catch (Exception e) {
	    		throw new EJBException (e);
	    	}
	    	return utilisateur;
	    }
	
	public int modiftel(String tel, int id)throws EJBException{
		int retour;
		try{
			Query requete = em.createQuery( JPQL_UPDATE_TELEPHONE );
			requete.setParameter( PARAM_TEL , tel);
			requete.setParameter(PARAM_ID, id);
			retour = requete.executeUpdate();
		}catch (NoResultException e) {
    		return 0;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return retour;
    }
	
	public int modifadresse(String adresse, int id)throws EJBException{
		int retour;
		try{
			Query requete = em.createQuery( JPQL_UPDATE_ADRESSE );
			requete.setParameter( PARAM_ADRESSE , adresse);
			requete.setParameter(PARAM_ID, id);
			retour = requete.executeUpdate();
		}catch (NoResultException e) {
    		return 0;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return retour;
    }
	
	public boolean verifLien (int det, int dem)throws EJBException{
		boolean verif = false ;
		Lier lien = new Lier();
		try{
			Query requete = em.createQuery(JPQL_VERIF_LIEN);
			requete.setParameter( PARAM_DETENTEUR, det );
			requete.setParameter( PARAM_DEMENDEUR, dem );
			lien = (Lier) requete.getSingleResult();
			if (lien != null ){ verif = true ;}
		}catch (NoResultException e) {
		return false;
		} catch (Exception e) {
		throw new EJBException (e);
		}
		return verif;
	}
	
	public int creerbiblio(int id)throws EJBException{
		int retour;
	try{
		Query requete = em.createQuery( JPQL_CREER_MOD );
		requete.setParameter( PARAM_ID , id);
		retour = requete.executeUpdate();
	}catch (NoResultException e) {
		return 0;
	} catch (Exception e) {
		throw new EJBException (e);
	}
	return retour;
	}
	
	public boolean VerifNomPrenom (String nom , String prenom)throws EJBException{
		boolean verif = false;
		Utilisateur utilisateur = new Utilisateur();
	    try{
	    	Query requete = em.createQuery( JPQL_CONTROLE_NEW_UTILISATEUR );
	    	requete.setParameter ( PARAM_NOM, nom );
	    	requete.setParameter ( PARAM_PRENOM, prenom );
	    	utilisateur = (Utilisateur) requete.getSingleResult();
	    	if (utilisateur.getIdUser()!= null){
	    		verif = true;
	    	}
	    	} catch (NoResultException e) {
	    		return false;
	    	} catch (Exception e) {
	    		throw new EJBException (e);
	    	}
	    	return verif;
	    }
	public void enregistrementUser(String nom, String prenom, String adresse, String telephone ) {
		try{
			em.createNativeQuery(JPQL_INSERT_USER)
			.setParameter(1, nom)
			.setParameter(2, prenom)
			.setParameter(3, adresse)
			.setParameter(4, telephone)
			.executeUpdate();
			
		}catch (NoResultException e) {
		}catch (Exception e) {
    		throw new EJBException (e);
    	}
    }
	
	public int UpdateNbLivre(int nb , int id)throws EJBException{
		int retour;
		try{
			Query requete = em.createQuery( JPQL_UPDATE_NBLIVRE );
			requete.setParameter( PARAM_NBLIVRE , nb);
			requete.setParameter(PARAM_ID, id);
			retour = requete.executeUpdate();
		}catch (NoResultException e) {
    		return 0;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return retour;
    }
	
	public int UpdateMdp (String motdepasse , int id)throws EJBException{
		int retour;
		try{
			Query requete = em.createQuery( JPQL_UPDATE_MDP );
			requete.setParameter( PARAM_MDP , motdepasse);
			requete.setParameter(PARAM_ID, id);
			retour = requete.executeUpdate();
		}catch (NoResultException e) {
    		return 0;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return retour;
    }
}
	
