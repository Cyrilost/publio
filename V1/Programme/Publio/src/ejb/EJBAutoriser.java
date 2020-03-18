package ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jpa.Lier;
import jpa.Autoriser;

/**
 * Session Bean implementation class EJBAutoriser
 */
@Stateless
@LocalBean
public class EJBAutoriser {
	
	private static final String PARAM_ID = "id";
	private static final String REPONSE = "reponse";
	
	private static final String JPQL_INSERT_AUT = "insert into Autoriser"
			+ "(id_mod,id_util)"
			+ " VALUES"
			+ " (?,?)";
	
	private static final String JPQL_SELECT_PAR_ID = "select a from Autoriser a where a.idAutorisation = :id";
	
	private static final String JPQL_UPDATE_REPONSE = "update Autoriser a"
			+ " set a.reponse= :reponse"
			+ " where a.idAutorisation = :id";
	private static final String JPQL_INSERT_LIEN = "insert into Lier"
			+ "(id_util,id_mod)"
			+ " VALUES"
			+ " (?,?)";
	private static final String JPQL_INSERT_AUTO = "insert into Autoriser"
			+ "(id_mod,id_util,reponse)"
			+ " VALUES"
			+ " (?,?,true)";
	
	@PersistenceContext (unitName = "publio")
	private EntityManager em;
	
	
		public void enregistrementAUT(int det, int dem) {
			try{
				em.createNativeQuery(JPQL_INSERT_AUT)
				.setParameter(1, det)
				.setParameter(2, dem)
				.executeUpdate();
				
			}catch (NoResultException e) {
			}catch (Exception e) {
	    		throw new EJBException (e);
	    	}
	    }
	
	// Recherche d'une autorisation à parir de son id
	
		public Autoriser load(int id)throws EJBException{
			
			Autoriser acces = new Autoriser();
		    try{
		    	Query requete = em.createQuery( JPQL_SELECT_PAR_ID );
		    	requete.setParameter ( PARAM_ID, id );
		    	acces = (Autoriser) requete.getSingleResult();
		    	} catch (NoResultException e) {
		    		return null;
		    	} catch (Exception e) {
		    		throw new EJBException (e);
		    	}
		    	return acces;
		    }
		
		public int reponseacces(boolean rep, int id)throws EJBException{
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
		
		public void enregistrementLier(int dem, int det) {
			
			try{
				em.createNativeQuery( JPQL_INSERT_LIEN )
				.setParameter(1, dem)
				.setParameter(2, det)
				.executeUpdate();
				
			}catch (NoResultException e) {
	    		
	    	} catch (Exception e) {
	    		throw new EJBException (e);
	    	}
	    }
		
		public void enregistrementAUTO(int dem, int det) {
			
			try{
				em.createNativeQuery( JPQL_INSERT_AUTO )
				.setParameter(1, dem)
				.setParameter(2, det)
				.executeUpdate();
				
			}catch (NoResultException e) {
	    		
	    	} catch (Exception e) {
	    		throw new EJBException (e);
	    	}
	    }
		
}
