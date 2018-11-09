package ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jpa.Utilisateur;

/**
 * Session Bean implementation class EJBSeek
 */
@Stateless
@LocalBean
public class EJBSeek {

	
	private static final String PARAM_DEMENDEUR = "idDem";
	private static final String PARAM_NOM = "nom";
	private static final String PARAM_PRENOM = "prenom";
	
	private static final String JPQL_DETENTEUR_BIBLIO = "select u.idUser,u.nom,u.prenom"
			+ " from Utilisateur u"
			+ " where u.idUser != :idDem"
			+ " and u.biblio = true";
	
	private static final String JPQL_LIVRES_EMPRUNTES = "select p.livre.titre,p.livre.sousTitre,p.livre.tome,p.dateRetourReel,p.livre.utilisateur.nom,p.livre.utilisateur.prenom"
			+ " from PretRet p"
			+ " where p.utilisateur2.nom =:nom"
			+ " and p.utilisateur2.prenom =:prenom"
			+ " and p.dateRetourReel IS NOT NULL";
	// Injection du manager, qui s'occupe de la connexion BDD
	@PersistenceContext (unitName = "publio")
	
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<?> DEMautorisation(int id)throws EJBException{
    	List<?> listbiblio = new ArrayList<Utilisateur>();
    	Query requete = em.createQuery( JPQL_DETENTEUR_BIBLIO , Utilisateur.class  );
    	requete.setParameter ( PARAM_DEMENDEUR , id );
    	
    	try{
    		listbiblio.addAll(requete.getResultList());
    		
    	} catch (NoResultException e) {
    		return null;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return listbiblio;
    }
	
	@SuppressWarnings("unchecked")
	public List<?> dejaLus(String nom, String prenom)throws EJBException{
		
		List<?> listlivre = new ArrayList<Object[]>();
		Query requete = em.createQuery ( JPQL_LIVRES_EMPRUNTES );
		requete.setParameter ( PARAM_NOM, nom );
		requete.setParameter ( PARAM_PRENOM, prenom );
		
		try{
			listlivre.addAll(requete.getResultList());
		}catch (NoResultException e) {
		return null;
		} catch (Exception e) {
		throw new EJBException (e);
		}
	return listlivre;
	}
	
}
	
	