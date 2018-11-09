package ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jpa.Utilisateur;

/**
 * Session Bean implementation class Connexion
 */
@Stateless
@LocalBean
public class EJBConnexion {
	
	/*
	 * déclaration
	 */
	private static final String PARAM_NOM = "nom";
	private static final String PARAM_PRENOM = "prenom";
	private static final String PARAM_MDP = "motdepasse";
	private static final String PARAM_PHRASE = "phrase";
	
	/*
	 *JPQL 
	 */
	private static final String JPQL_SELECT_PAR_NOM_PRENOM = "select u from Utilisateur u where u.nom = :nom and u.prenom = :prenom";
	private static final String JPQL_UPDATE_MDP_PHRASE = "update Utilisateur u"
			+ " set u.mdp = :motdepasse"
			+ " , u.phrase = :phrase"
			+ " where u.nom = :nom"
			+ " and u.prenom = :prenom";
	
	/* 
	 * Injection du manager, qui s'occupe de la connexion BDD
	 */
	@PersistenceContext (unitName = "publio")
	private EntityManager em;

    /*
     * Fonctions et Méthodes        
     */
    // Recherche d'un utilisateur à parir de son nom et prenom
    public Utilisateur load(String nom,String prenom)throws EJBException{
    	
    	Utilisateur utilisateur = null;
    	try{
    		
    		Query requete = em.createQuery( JPQL_SELECT_PAR_NOM_PRENOM );
    		requete.setParameter ( PARAM_NOM, nom );
    		requete.setParameter ( PARAM_PRENOM, prenom );
    	
    		utilisateur = (Utilisateur) requete.getSingleResult();
    		
    	} catch (NoResultException e) {
    		return null;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return utilisateur;
    }
    //Enregistrement dans la BDD du mot de passe et de la phrase
    public int record(String nom, String prenom, String mdp, String lieunaissance){
    	int retour;
		try{
			Query requete = em.createQuery( JPQL_UPDATE_MDP_PHRASE );
			requete.setParameter( PARAM_NOM , nom);
			requete.setParameter( PARAM_PRENOM , prenom);
			requete.setParameter( PARAM_MDP , mdp);
			requete.setParameter( PARAM_PHRASE , lieunaissance);
			retour = requete.executeUpdate();
		}catch (NoResultException e) {
    		return 0;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return retour;
    }
    
}
