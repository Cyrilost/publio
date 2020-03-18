package ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;


import jpa.Lier;
import jpa.Livre;
import jpa.Utilisateur;

/**
 * Session Bean implementation class EJBLivre
 */
@Stateless
@LocalBean
public class EJBLivre {
	
	private static final String PARAM_ID = "idLivre";
	private static final String PARAM_TITRE = "titre";
	private static final String PARAM_SOUSTITRE = "sousTitre";
	private static final String PARAM_TOME = "tome";
	private static final String PARAM_AUTEUR = "auteur";
	private static final String PARAM_NBPAGE = "nbPage";
	private static final String PARAM_ETATLIVRE = "etatLivre";
	private static final String PARAM_DETENTEUR = "idDet";
	private static final String PARAM_DEMENDEUR = "idDem";
	
	private static final String JPQL_CALCUL_RETOUR = "SELECT calculretourlivre(?)";
	
	private static final String JPQL_SANS_SES_LIVRES = "select l"
			+ " from Livre l"
			+ " where l.utilisateur.idUser != :idDet";
	private static final String JPQL_INFO_LIVRE = "select l"
			+ " from Livre l"
			+ " where l.idLivre = :idLivre";
	private static final String JPQL_VERIF_LIEN = "select l"
			+ " from Lier l"
			+ " where l.utilisateur1.idUser = :idDet"
			+ " and l.utilisateur2.idUser = :idDem";
	private static final String JPQL_INSERT_DEM = "insert into Demander"
			+ "(id_user_dem,id_user_det,id_livre_dem)"
			+ " VALUES"
			+ " (?,?,?)";
	private static final String JPQL_CONTROLE_NEW_LIVRE ="select l"
			+ " from Livre l"
			+ " where l.titre = :titre"
			+ " and l.sousTitre = :sousTitre"
			+ " and l.tome = :tome"
			+ " and l.auteur = :auteur";
	private static final String JPQL_INSERT_BOOK = "insert into Livre"
			+ "(titre,sous_titre,tome,auteur,nb_page,etat_livre,id_mod)"
			+ " VALUES"
			+ " (?,?,?,?,?,?,?)";
	private static final String JPQL_UPDATE_TITRE = "update Livre l"
			+ " set l.titre= :titre"
			+ " where l.idLivre= :idLivre ";
	private static final String JPQL_UPDATE_SOUSTITRE = "update Livre l"
			+ " set l.sousTitre= :sousTitre"
			+ " where l.idLivre= :idLivre ";
	private static final String JPQL_UPDATE_TOME = "update Livre l"
			+ " set l.tome= :tome"
			+ " where l.idLivre= :idLivre ";
	private static final String JPQL_UPDATE_AUTEUR = "update Livre l"
			+ " set l.auteur= :auteur"
			+ " where l.idLivre= :idLivre ";
	private static final String JPQL_UPDATE_DATE_RETOUR = "update Livre l"
			+ " set l.dateRetourPrévu= :dateRetourPrévu"
			+ " where l.idLivre= :idLivre ";
	private static final String JPQL_UPDATE_ETAT = "update Livre l"
			+ " set l.etatLivre = :etatLivre"
			+ " where l.idLivre = :idLivre ";
	
	
	// Injection du manager, qui s'occupe de la connexion BDD
	@PersistenceContext (unitName = "publio")
	
	private EntityManager em;
	
	
	
	// Methodes et fonctions
	public Date retourdate (int nb)throws EJBException{
	
		Date result;	
		StringBuilder sql = new StringBuilder(JPQL_CALCUL_RETOUR);
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter(1, nb);
		result = (Date) query.getSingleResult();
		return result;
	}
		
	@SuppressWarnings("unchecked")
	public List<?> SansSeslivres(int id)throws EJBException{
    	List<?> listlivre = new ArrayList<Utilisateur>();
    	Query requete = em.createQuery( JPQL_SANS_SES_LIVRES , Livre.class  );
    	requete.setParameter ( PARAM_DETENTEUR , id );
    	
    	try{
    		listlivre.addAll(requete.getResultList());
    		
    	} catch (NoResultException e) {
    		return null;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return listlivre;
    }
	
	public Livre load(int id)throws EJBException{
		Livre livre = new Livre();
		Query requete = em.createQuery ( JPQL_INFO_LIVRE );
		requete.setParameter ( PARAM_ID, id );
		try{
			livre = (Livre) requete.getSingleResult();
		} catch (NoResultException e) {
    		return null;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return livre;
    }
	
	public boolean verifLien (int det, int dem)throws EJBException{
		boolean verif = false ;
		Lier lien = new Lier();
		Query requete = em.createQuery(JPQL_VERIF_LIEN);
		requete.setParameter( PARAM_DETENTEUR, det );
		requete.setParameter( PARAM_DEMENDEUR, dem );
		try{
		lien = (Lier) requete.getSingleResult();
		if (lien != null ){ verif = true ;}
		} catch (NoResultException e) {
    		return false;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
		return verif;
	}

	public void enregistrementDEM(int det, int dem, int id) {
		
		try{
			em.createNativeQuery(JPQL_INSERT_DEM)
			.setParameter(1, det)
			.setParameter(2, dem)
			.setParameter(3, id)
			.executeUpdate();
			
		}catch (NoResultException e) {
    		
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    }

	public boolean VerifTitreAuteur (String titre, String sousTitre, String tome , String auteur)throws EJBException{
		boolean verif = false;
		Livre livre = new Livre();
	    try{
	    	Query requete = em.createQuery( JPQL_CONTROLE_NEW_LIVRE );
	    	requete.setParameter ( PARAM_TITRE, titre );
	    	requete.setParameter ( PARAM_SOUSTITRE, sousTitre );
	    	requete.setParameter ( PARAM_TOME, tome );
	    	requete.setParameter ( PARAM_AUTEUR, auteur );
	    	livre = (Livre) requete.getSingleResult();
	    	if (livre.getIdLivre()!= null){
	    		verif = true;
	    	}
	    	} catch (NoResultException e) {
	    		return false;
	    	} catch (Exception e) {
	    		throw new EJBException (e);
	    	}
	    	return verif;
	    }
	public void enregistrementBook(String titre ,String sousTitre ,String tome ,String auteur ,int nbPage ,boolean etatLivre, int idDet ){
		try{
			em.createNativeQuery(JPQL_INSERT_BOOK)
			.setParameter(1, titre)
			.setParameter(2, sousTitre)
			.setParameter(3, tome)
			.setParameter(4, auteur)
			.setParameter(5, nbPage)
			.setParameter(6, etatLivre)
			.setParameter(7, idDet)
			.executeUpdate();
			
		}catch (NoResultException e) {
		}catch (Exception e) {
    		throw new EJBException (e);
    	}
    }
	
	public int modiftitre(String titre, int id)throws EJBException{
		int retour;
		try{
			Query requete = em.createQuery( JPQL_UPDATE_TITRE );
			requete.setParameter( PARAM_TITRE , titre);
			requete.setParameter(PARAM_ID, id);
			retour = requete.executeUpdate();
		}catch (NoResultException e) {
    		return 0;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return retour;
    }
	
	public int modifsoustitre(String soustitre, int id)throws EJBException{
		int retour;
		try{
			Query requete = em.createQuery( JPQL_UPDATE_SOUSTITRE );
			requete.setParameter( PARAM_SOUSTITRE , soustitre);
			requete.setParameter(PARAM_ID, id);
			retour = requete.executeUpdate();
		}catch (NoResultException e) {
    		return 0;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return retour;
    }
	
	public int modiftome(String tome, int id)throws EJBException{
		int retour;
		try{
			Query requete = em.createQuery( JPQL_UPDATE_TOME );
			requete.setParameter( PARAM_TOME , tome);
			requete.setParameter(PARAM_ID, id);
			retour = requete.executeUpdate();
		}catch (NoResultException e) {
    		return 0;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return retour;
    }
	
	public int modifauteur(String auteur, int id)throws EJBException{
		int retour;
		try{
			Query requete = em.createQuery( JPQL_UPDATE_AUTEUR );
			requete.setParameter( PARAM_AUTEUR , auteur);
			requete.setParameter(PARAM_ID, id);
			retour = requete.executeUpdate();
		}catch (NoResultException e) {
    		return 0;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return retour;
    }
	
	public int modifdate(Date date, int id)throws EJBException{
		int retour;
		try{
			Query requete = em.createQuery( JPQL_UPDATE_DATE_RETOUR );
			requete.setParameter( "dateRetourPrévu" , date , TemporalType.DATE);
			requete.setParameter(PARAM_ID, id);
			retour = requete.executeUpdate();
		}catch (NoResultException e) {
    		return 0;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return retour;
    }
	public int modifetat(boolean etatretour, int id)throws EJBException{
		int retour;
		try{
			Query requete = em.createQuery( JPQL_UPDATE_ETAT );
			requete.setParameter( PARAM_ETATLIVRE , etatretour);
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
