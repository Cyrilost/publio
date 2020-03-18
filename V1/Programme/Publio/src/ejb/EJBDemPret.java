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

import jpa.Demander;
import jpa.PretRet;


/**
 * Session Bean implementation class EJBPret
 */
@Stateless
@LocalBean
public class EJBDemPret {
    // 
	private static final String PARAM_NOM = "nom";
	private static final String PARAM_PRENOM = "prenom";
	private static final String PARAM_ID = "id";
	private static final String REPONSE = "reponse";
	private static final String PARAM_ID_RETOUR = "idretour";
	private static final String PARAM_ETAT = "etat";
	
	// Injection du manager, qui s'occupe de la connexion BDD
	@PersistenceContext (unitName = "publio")
	private EntityManager em;
	
	
	//Liste des requêtes
	private static final String JPQL_CALCUL_AVERT_RETARD = "select difdatepravertissement(?)";
	
	private static final String JPQL_SELECT_PAR_ID = "select d from Demander d where d.idDem = :id";
		
	private static final String JPQL_INSERT_DEM = "insert into Demander"
			+ "(id_user_det,id_user_dem,id_livre_dem)"
			+ " VALUES"
			+ " (?,?,?)";
	
	private static final String JPQL_UPDATE_REPONSE = "update Demander d"
			+ " set d.reponse= :reponse"
			+ " where d.idDem = :id";
	
	private static final String JPQL_INSERT_PRET_RET = "insert into Pret_Ret"
			+ "(id_livre,id_empr1,date_retour_prevu,id_det)"
			+ " VALUES"
			+ " (?,?,?,?)";
	
	private static final String JPQL_CHOIX_RETOUR = "select pr.idPret, pr.livre.titre, pr.livre.sousTitre, pr.livre.tome, pr.utilisateur2.nom, pr.utilisateur2.prenom"
			+ " from PretRet pr"
			+ " where pr.utilisateur1.idUser = :id"
			+ " and pr.dateRetourReel IS NULL";
	
	private static final String JPQL_LOAD_PRETRET = "select pr from PretRet pr where pr.idPret = :idretour";
	
	private static final String JPQL_UPDATE_DATE_RETOUR = "update PretRet pr"
			+ " set pr.dateRetourReel = current_date"
			+ " where pr.idPret = :idretour";
	
	private static final String JPQL_UPDATE_ETAT_RETOUR = "update PretRet pr"
			+ " set pr.etatLivreRendu = :etat"
			+ " where pr.idPret = :idretour";
	
	//Methodes et fonctions
	public int CompareDate (int id)throws EJBException{
		
		int result;	
		StringBuilder sql = new StringBuilder(JPQL_CALCUL_AVERT_RETARD);
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter(1, id);
		result = (int) query.getSingleResult();
		return result;
	}
	
	
	public Demander load(int id)throws EJBException{
		
		Demander acces = new Demander();
	    try{
	    	Query requete = em.createQuery( JPQL_SELECT_PAR_ID );
	    	requete.setParameter ( PARAM_ID, id );
	    	acces = (Demander) requete.getSingleResult();
	    	} catch (NoResultException e) {
	    		return null;
	    	} catch (Exception e) {
	    		throw new EJBException (e);
	    	}
	    	return acces;
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
	
	public int reponsedem(boolean rep, int id)throws EJBException{
		int retour;
		try{
			Query requete = em.createQuery( JPQL_UPDATE_REPONSE );
			requete.setParameter( REPONSE , rep);
			requete.setParameter(PARAM_ID, id);
			retour = requete.executeUpdate();
		}catch (NoResultException e) {
    		return 0;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return retour;
    }
	
	public void enregistrementPret(int idLivre, int idDem, Date DateRetourPrevu, int idDet){
		try{
			em.createNativeQuery(JPQL_INSERT_PRET_RET)
			.setParameter(1, idLivre)
			.setParameter(2, idDem)
			.setParameter(3, DateRetourPrevu)
			.setParameter(4, idDet)
			.executeUpdate();
			
		}catch (NoResultException e) {
    		
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    }
	@SuppressWarnings("unchecked")
	public List<?> retour_pretA_traiter (int det )throws EJBException{
		List<?> listretour = new ArrayList<Object[]>();
		Query requete = em.createQuery ( JPQL_CHOIX_RETOUR );
		requete.setParameter ( PARAM_ID, det );
		
		
		try{
			listretour.addAll(requete.getResultList());
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new EJBException (e);
		}
	return listretour;
	}
	
	public PretRet charger(int id)throws EJBException{
		
		PretRet retour = new PretRet();
	    try{
	    	Query requete = em.createQuery( JPQL_LOAD_PRETRET );
	    	requete.setParameter ( PARAM_ID_RETOUR, id );
	    	retour = (PretRet) requete.getSingleResult();
	    	} catch (NoResultException e) {
	    		return null;
	    	} catch (Exception e) {
	    		throw new EJBException (e);
	    	}
	    	return retour;
	    }
	
	public int MajDateRetour (int numeropret)throws EJBException{
		int retour;
		try{
			Query requete = em.createQuery( JPQL_UPDATE_DATE_RETOUR );
			requete.setParameter(PARAM_ID_RETOUR, numeropret);
			retour = requete.executeUpdate();
		}catch (NoResultException e) {
    		return 0;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return retour;
	}
	
	public int SaisiEtatlivre (boolean etat,int id)throws EJBException{
		int retour;
		try{
			Query requete = em.createQuery( JPQL_UPDATE_ETAT_RETOUR );
			requete.setParameter(PARAM_ID_RETOUR, id);
			requete.setParameter(PARAM_ETAT, etat);
			retour = requete.executeUpdate();
		}catch (NoResultException e) {
    		return 0;
    	} catch (Exception e) {
    		throw new EJBException (e);
    	}
    	return retour;
	}
	}

