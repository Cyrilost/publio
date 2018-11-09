package servlet;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpa.Livre;
import jpa.PretRet;
import jpa.Utilisateur;
import ejb.EJBDemPret;
import ejb.EJBLivre;
import ejb.EJBUtilisateur;

/**
 * Servlet implementation class ValidationRetour
 */
@WebServlet("/ValidationRetour")
public class ValidationRetour extends HttpServlet {
	
	    
	
	private static final long serialVersionUID = 1L;
		public static final String VUE = "/WEB-INF/choix22validation.jsp";
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
			
			String msg = null;
			String msg2 = null;
			
			
			int numeropret = Integer.parseInt(request.getParameter(CHOIX_RETOUR));
			//info pretRet		
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
			
			Boolean rep = null;
			
			Boolean rep1 = Boolean.parseBoolean(request.getParameter(REPONSE1));
			Boolean rep2 = Boolean.parseBoolean(request.getParameter(REPONSE2));
			
			
			
			/*
			 * evaluation etat du livre
			 */
			if ((rep1 == true)&&(rep2==false)){
				System.out.println("Livre rendu en Bon état");
				rep = true;
				// Update PretRet => cloture du pret
				int retourMajEtatLivre = ejbdempret.SaisiEtatlivre(rep , numeropret);
				if (retourMajEtatLivre != 0){
					//mise à jour état du livre dans table Livre
					retourMajEtatLivre = ejblivre.modifetat(rep , idLivre);
					if(retourMajEtatLivre != 0){
						msg = "votre livre est retourné dans votre bibliothèque en bon état";
					}
				}
			}
			
			if ((rep2 == true)&&(rep1 == false)){
				
				//System.out.println("refuser");
				rep = false;
				
				//creation avertissement PL/SQL
				String avert = ejbutilisateur.Averto(idDem);
				System.out.println(avert);
				
				// Update PretRet => cloture du pret
				int retourMajEtatLivre = ejbdempret.SaisiEtatlivre(rep , numeropret);
				if (retourMajEtatLivre != 0){
					//mise à jour état du livre dans table Livre
					retourMajEtatLivre = ejblivre.modifetat(rep , idLivre);
					if(retourMajEtatLivre != 0){
						msg2 = "votre livre est retourné dans la bibliothèque en mauvais état, j'en suis navré"	;	
					}
				}
				
			}
			
			request.setAttribute("id", numeropret);
			request.setAttribute("choixretour", CHOIX_RETOUR );
			request.setAttribute("nomDet", nomDet );
			request.setAttribute("prenomDet", prenomDet );
			request.setAttribute("idDem", idDem );
			
			request.setAttribute("titre", Titrelivre);
			request.setAttribute("soustitre", SousTitrelivre);
			request.setAttribute("tome" , Tomelivre);
			
			
			request.setAttribute(REPONSE1, rep1 );
			request.setAttribute(REPONSE2, rep2 );
			
			request.setAttribute("message", msg );
			request.setAttribute("message2", msg2 );
			
			request.setAttribute("etat", rep);
			request.setAttribute("dareretour", retour.getDateRetourReel());
			
			this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );	
			
		}

	}
