package servlet;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpa.Demander;
import jpa.Livre;
import jpa.PretRet;
import jpa.Utilisateur;
import ejb.EJBDemPret;
import ejb.EJBLivre;
import ejb.EJBUtilisateur;

/**
 * Servlet implementation class RetourLivre
 */
@WebServlet("/RetourLivre")
public class RetourLivre extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public static final String VUE = "/WEB-INF/choix22retour.jsp";
	public static final String MESSAGE = "message";
	public static final String REPONSE1 = "reponse1";
	public static final String REPONSE2 = "reponse2";
	public static final String CHOIX_RETOUR = "choixretour";
	public static final String DATE_RETOUR = "dateretour";
	public static final String ETAT_LIVRE = "etatlive";
	
	// Injection de notre EJB (session bean stateless)
	@EJB
	private EJBDemPret ejbdempret;
	@EJB
	private EJBLivre ejblivre;
	@EJB
	private EJBUtilisateur ejbutilisateur;
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding( "UTF-8" );
		
		
		PretRet retour = new PretRet();
		
		String msg =null;
		String msg2 = null;
		
		
		int numeropret = Integer.parseInt(request.getParameter(CHOIX_RETOUR));
		
		
		
		
		retour = ejbdempret.charger(numeropret);
		
		// info livre
		int idLivre = retour.getLivre().getIdLivre();
		Livre livre = ejblivre.load(idLivre);
		String Titrelivre = livre.getTitre();
		String SousTitrelivre = livre.getSousTitre();
		String Tomelivre = livre.getTome();
		Date retourprevu = null;
		
		//Utilisateur 1 == detenteur
		int idDet = retour.getUtilisateur1().getIdUser();
		Utilisateur Det = ejbutilisateur.load(idDet);
		int RetourUpdateNbLivre = 0;
		String nomDet = Det.getNom();
		String prenomDet = Det.getPrenom();
		
		//Utilisateur 2 == emprunteur
		int idDem = retour.getUtilisateur2().getIdUser();
		Utilisateur Dem = ejbutilisateur.load(idDem);
		String nomDem = Dem.getNom();
		String prenomDem = Dem.getPrenom();
		
		
		int nblivre = Dem.getNbPretEncours();
		
		Boolean rep;
		
		Boolean rep1 = Boolean.parseBoolean(request.getParameter(REPONSE1));
		Boolean rep2 = Boolean.parseBoolean(request.getParameter(REPONSE2));
		
		
		if ((rep1 == false)&&(rep2 == false)){
			msg = "Il faut choisir !! le choix sera définitif ... ";
		}
		
		
		/*
		 * retirer 1 livre pour l'emprunteur (l'utilisateur2)
		 */
		nblivre = nblivre - 1;
		RetourUpdateNbLivre = ejbutilisateur.UpdateNbLivre(nblivre, idDem);
		if (RetourUpdateNbLivre!=0){
			System.out.print("retrait de un dans le nb livre emprunté par l'utilisateur");
		}
		
		/*
		 * Retirer la date de retour prévu
		 */
		retourprevu = null;
					
		/*
		 * Modifier la date retour prévu du livre
		 */
		int infodate = 0;
		infodate = ejblivre.modifdate(retourprevu,idLivre);
		
		if (infodate != 0){
		//System.out.println("date dans la bdd : "+livre.getDateRetourPrévu());
			msg2 = "La date du retour prévu a été remise à zéro dans les informations du livre";
		}
		
		/*
		 * saisie du retour du livre dans BDD
		 */
		// ajout date du jour dans la table Pret_Ret "Update"(automatique)
		int retourUpdate = ejbdempret.MajDateRetour(numeropret);
		if (retourUpdate != 0 ){
			System.out.println ("la date de retour du pret est mis à jour");
		// si date retour prévu > date du jour = creation avertissement pour utilisateur2(PL/SQL)
			int retourAvertissement = ejbdempret.CompareDate(numeropret);
			if (retourAvertissement != 0){
				System.out.println("L'utilisateur a pris une pénalité");
			}
		}
		
		
		request.setAttribute("id", numeropret);
		request.setAttribute("choixretour", CHOIX_RETOUR );
		request.setAttribute("nomDet", nomDet );
		request.setAttribute("prenomDet", prenomDet );
		request.setAttribute("nomDem", nomDem );
		request.setAttribute("prenomDem", prenomDem );
		request.setAttribute("idDem", idDem );
		
		request.setAttribute("titre", Titrelivre);
		request.setAttribute("soustitre", SousTitrelivre);
		request.setAttribute("tome" , Tomelivre);
		
		
		request.setAttribute(REPONSE1, rep1 );
		request.setAttribute(REPONSE2, rep2 );
		
		request.setAttribute("message", msg );
		request.setAttribute("message2", msg2 );
		
		
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );	
		
	}

}
