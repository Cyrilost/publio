package ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jpa.Livre;



/**
 * Session Bean implementation class EJBVisual
 */
@Stateless
@LocalBean
public class EJBVisual {
	
	private static final String PARAM_NOM = "nom";
	private static final String PARAM_PRENOM = "prenom";
	private static final String PARAM_ID = "id";
	private static final String PARAM_CHOIX_LIVRE = "choixlivre";
	private static final String JPQL_SELECT_TOUS_LIVRES = "select l from Livre l";
	
	private static final String JPQL_PRET_ENCOURS = "select p.livre.titre,p.livre.sousTitre,p.livre.tome,p.livre.dateRetourPrévu,p.livre.utilisateur.nom,p.livre.utilisateur.prenom"
				+ " from PretRet p"
				+ " where p.utilisateur2.nom =:nom"
				+ " and p.utilisateur2.prenom =:prenom"
				+ " and p.dateRetourReel IS NULL";
	private static final String JPQL_LIVRES_EMPRUNTES2 = "select p.livre.titre,p.livre.sousTitre,p.livre.tome,p.dateRetourReel,p.livre.utilisateur.nom,p.livre.utilisateur.prenom"
				+ " from PretRet p"
				+ " where p.utilisateur2.idUser = :id"
				+ " and p.dateRetourReel IS NOT NULL";

	private static final String JPQL_DEM_PRET = "select d.livre.titre,d.livre.sousTitre,d.livre.tome,d.utilisateur2.nom,d.utilisateur2.prenom,d.reponse"
				+ " from Demander d"
				+ " where d.utilisateur1.nom = :nom"
				+ " and d.utilisateur1.prenom = :prenom";
	private static final String JPQL_DEM_AUTORISATION = "select a.utilisateur2.nom, a.utilisateur2.prenom, a.utilisateur1.nom, a.utilisateur1.prenom, a.reponse"
				+ " from Autoriser a"
				+ " where a.utilisateur2.nom = :nom"
				+ " and a.utilisateur2.prenom = :prenom";
	private static final String JPQL_SES_LIVRES = "select l"
				+ " from Livre l"
				+ " where l.utilisateur.idUser = :id";
	private static final String JPQL_DEM_PRET_A_TRAITER = "select d.idDem,d.livre.titre,d.livre.sousTitre,d.livre.tome,d.utilisateur1.nom,d.utilisateur1.prenom"
				+ " from Demander d"
				+ " where d.utilisateur2.nom = :nom"
				+ " and d.utilisateur2.prenom = :prenom"
				+ " and d.reponse IS NULL";
	private static final String JPQL_DEM_AUTORISATION_A_TRAITER = "select a.idAutorisation,a.utilisateur2.nom, a.utilisateur2.prenom"
				+ " from Autoriser a"
				+ " where a.utilisateur1.nom = :nom"
				+ " and a.utilisateur1.prenom = :prenom"
				+ " and a.reponse IS NULL";
	private static final String JPQL_CHOIX_LIVRE = "select l from Livre l where l.idLivre = :choixlivre";
	
	private static final String JPQL_CHOIX_UTILISATEUR = "select lier.utilisateur2.idUser, lier.utilisateur2.nom, lier.utilisateur2.prenom"
				+ " from Lier lier"
				+ " where lier.utilisateur1.idUser = :id"
				+ " and lier.utilisateur2.idUser != :id";
	
	
	// Injection du manager, qui s'occupe de la connexion BDD
	@PersistenceContext (unitName = "publio")
	
	private EntityManager em;
	
	// Recherche de tous les livres de toute la BDD
    
	@SuppressWarnings("unchecked")
	public List<?> TousLeslivres()throws EJBException{
		    	
    	List<?> listlivre = new ArrayList<Livre>();
    	Query requete = em.createQuery( JPQL_SELECT_TOUS_LIVRES , Livre.class  );
    	
    	try{
    		listlivre.addAll(requete.getResultList());
    		
    	} catch (NoResultException e) {
    		return null;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return listlivre;
    }
	
	@SuppressWarnings("unchecked")
	public List<?> pret_encours(String nom, String prenom)throws EJBException{
		
		List<?> listlivre = new ArrayList<Object[]>();
		Query requete = em.createQuery ( JPQL_PRET_ENCOURS );
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
		
	@SuppressWarnings("unchecked")
	public List<?> dejaLus2(int id)throws EJBException{
		
		List<?> listlivre = new ArrayList<Object[]>();
		Query requete = em.createQuery ( JPQL_LIVRES_EMPRUNTES2 );
		requete.setParameter ( PARAM_ID, id );
				
		try{
			listlivre.addAll(requete.getResultList());
		}catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new EJBException (e);
		}
	return listlivre;
	}
	
	@SuppressWarnings("unchecked")
	public List<?> dem_pret(String nom, String prenom)throws EJBException{
		
		List<?> listdem = new ArrayList<Object[]>();
		Query requete = em.createQuery ( JPQL_DEM_PRET );
		requete.setParameter ( PARAM_NOM, nom );
		requete.setParameter ( PARAM_PRENOM, prenom );
		
		try{
			listdem.addAll(requete.getResultList());
		}catch (NoResultException e) {
		return null;
		} catch (Exception e) {
		throw new EJBException (e);
		}
	return listdem;
	}
	
	@SuppressWarnings("unchecked")
	public List<?> dem_autorisation(String nom, String prenom)throws EJBException{
		
		List<?> listdem = new ArrayList<Object[]>();
		Query requete = em.createQuery ( JPQL_DEM_AUTORISATION );
		requete.setParameter ( PARAM_NOM, nom );
		requete.setParameter ( PARAM_PRENOM, prenom );
		
		try{
			listdem.addAll(requete.getResultList());
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new EJBException (e);
		}
	return listdem;
	}
	
	@SuppressWarnings("unchecked")
	public List<?> Seslivres(int id)throws EJBException{

    	
    	List<?> listlivre = new ArrayList<Livre>();
    	Query requete = em.createQuery( JPQL_SES_LIVRES , Livre.class  );
    	requete.setParameter ( PARAM_ID, id );
    	
    	try{
    		listlivre.addAll(requete.getResultList());
    		
    	} catch (NoResultException e) {
    		return null;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return listlivre;
    }
	
	@SuppressWarnings("unchecked")
	public List<?> dem_pret_A_Traiter(String nom, String prenom)throws EJBException{
		
		List<?> listdem = new ArrayList<Object[]>();
		Query requete = em.createQuery ( JPQL_DEM_PRET_A_TRAITER );
		requete.setParameter ( PARAM_NOM, nom );
		requete.setParameter ( PARAM_PRENOM, prenom );
		
		try{
			listdem.addAll(requete.getResultList());
		}catch (NoResultException e) {
		return null;
		} catch (Exception e) {
		throw new EJBException (e);
		}
	return listdem;
	}
	
	@SuppressWarnings("unchecked")
	public List<?> dem_autorisationA_Traiter(String nom, String prenom)throws EJBException{
		
		List<?> listdem = new ArrayList<Object[]>();
		Query requete = em.createQuery ( JPQL_DEM_AUTORISATION_A_TRAITER );
		requete.setParameter ( PARAM_NOM, nom );
		requete.setParameter ( PARAM_PRENOM, prenom );
		
		try{
			listdem.addAll(requete.getResultList());
		}catch (NoResultException e) {
		return null;
		} catch (Exception e) {
		throw new EJBException (e);
		}
	return listdem;
	}
	
	public Livre ChoixDuLivre( int choixlivre)throws EJBException{

    	
    	Livre livre = new Livre();
    	Query requete = em.createQuery( JPQL_CHOIX_LIVRE , Livre.class  );
    	requete.setParameter ( PARAM_CHOIX_LIVRE, choixlivre );
    	try{
    		livre = (Livre) requete.getSingleResult();
    		
    	} catch (NoResultException e) {
    		return null;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return livre;
    }
	
	@SuppressWarnings("unchecked")
	public List<?> ChoixUtilisateur (int id)throws EJBException{
		List<?> ListUtil = new ArrayList<Object[]>();
		Query requete = em.createQuery( JPQL_CHOIX_UTILISATEUR );
		requete.setParameter (PARAM_ID , id );
		try{
			ListUtil.addAll(requete.getResultList());
		}catch (NoResultException e) {
			return null;
		}catch (Exception e) {
			throw new EJBException (e);
		}
		return ListUtil;
	}
}