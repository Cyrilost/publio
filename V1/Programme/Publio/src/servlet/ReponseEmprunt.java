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
import jpa.Utilisateur;
import ejb.EJBDemPret;
import ejb.EJBUtilisateur;
import jpa.Livre;
import ejb.EJBLivre;

/**
 * Servlet implementation class ReponseEmprunt
 */
@WebServlet("/ReponseEmprunt")
public class ReponseEmprunt extends HttpServlet {
private static final long serialVersionUID = 1L;
    
	public static final String VUE = "/WEB-INF/choix19reponse.jsp";
	public static final String MESSAGE = "message";
	public static final String REPONSE1 = "reponse1";
	public static final String REPONSE2 = "reponse2";
	public static final String CHOIX_EMPR1 = "choixemprunt";
	public static final String ID_USER = "id";
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
		
		int idUser = Integer.parseInt(request.getParameter(ID_USER));
		Utilisateur user = ejbutilisateur.load(idUser);
		Demander dem = new Demander();
		int retour = 0;
		String msg = null;
		String msg2 = null;
		String msg3 = null;
		String message = null;
		Date retourprevu = null;
		
		try{
			int numerodem = Integer.parseInt(request.getParameter(CHOIX_EMPR1));
			
			dem = ejbdempret.load(numerodem);
			
			// info livre
			int idLivre = dem.getLivre().getIdLivre();
			Livre livre = ejblivre.load(idLivre);
			String Titrelivre = livre.getTitre();
			String SousTitrelivre = livre.getSousTitre();
			String Tomelivre = livre.getTome();
			int nbpage = livre.getNbPage();
					
			
			//Utilisateur 1 == demandeur 
			String nomDem = dem.getUtilisateur1().getNom();
			String prenomDem = dem.getUtilisateur1().getPrenom();
			int idDem = dem.getUtilisateur1().getIdUser();
			int nblivre = dem.getUtilisateur1().getNbPretEncours();
			int RetourUpdateNbLivre = 0;
			//Utilisateur 2 == détenteur
			int idDet = dem.getUtilisateur2().getIdUser();
			
			Boolean rep;
			
			Boolean rep1 = Boolean.parseBoolean(request.getParameter(REPONSE1));
			Boolean rep2 = Boolean.parseBoolean(request.getParameter(REPONSE2));
			
			/* 
			 * vérifier si le livre est déjà en prêt
			 */
			Date test = livre.getDateRetourPrévu();
			if (test != null){
				msg3 = "Votre livre est déjà en prêt";
				rep1 = true;
				rep2 = true;
			}
			
			if ((rep1 == true)&&(rep2 == true)){
				msg2 = "je vous conseille d'attendre le retour du livre, pour valider cette demande";
			}
				
			if ((rep1 == false)&&(rep2 == false)){
				msg2 = "Il faut choisir !! le choix sera définitif ... ";
			}
			
			if ((rep1 == true)&&(rep2 == false)){
				//System.out.println("accepter");
				rep = true;
				/*
				 *  Pas plus de 2 livres en prêt chez un utilisateur			
				 */
				if (nblivre>1){
					msg3 = "Aucun livre ne peut être mis en prêt chez "+nomDem+" "+prenomDem+" car il détient déjà 2 livres";
					msg2 = "Veuillez attendre que l'utilisateur rende au moins un livre, pour valider sa demande";
				}else{
					
					/*
					 * rajouter quantité de 1 livre pour le demandeur (l'utilisateur1)
					 */
					nblivre = nblivre + 1;
					RetourUpdateNbLivre = ejbutilisateur.UpdateNbLivre(nblivre, idDem);
					if (RetourUpdateNbLivre!=0){
						System.out.print("ajout de 1 dans le nb livre de l'utilisateur");
					}
					
					
					/*
					 * date retourprevu = date du jour + delais (fonction de la BDD)
					 */
					
					retourprevu = ejblivre.retourdate(nbpage);
					//System.out.println("la date est :"+retourprevu);
								
					/*
					 * Modifier la date retour prévu du livre
					 */
					int infodate = 0;
					infodate = ejblivre.modifdate(retourprevu,idLivre);
					
					if (infodate != 0){
					//System.out.println("date dans la bdd : "+livre.getDateRetourPrévu());
						msg2 = "La date du retour prévu a été rajouté aux informations du livre";
					}
					
					/*
					 * saisie de la réponse dans BDD
					 */
					retour = ejbdempret.reponsedem(rep, numerodem);
					if ( retour != 0 ){
						// Création dans la Table Pret_Ret 
						ejbdempret.enregistrementPret(idLivre,idDem,retourprevu,idDet);
					}
					msg = "Le livre est mis en prêt chez "+nomDem+" "+prenomDem;
				}
				
				
			}
			
			if ((rep1 == false)&&(rep2 == true )){
				//System.out.println("refuser");
				rep = false;
				retour = ejbdempret.reponsedem(rep, numerodem);
				msg = "votre réponse a bien été enregistrée sur la BDD"	;	
			}
			request.setAttribute("utilisateur", user);
			request.setAttribute("id", numerodem);
			request.setAttribute("nom", nomDem );
			request.setAttribute("prenom", prenomDem );
			
			request.setAttribute("titre", Titrelivre);
			request.setAttribute("soustitre", SousTitrelivre);
			request.setAttribute("tome" , Tomelivre);
			
			
			request.setAttribute(REPONSE1, rep1 );
			request.setAttribute(REPONSE2, rep2 );
			
			request.setAttribute("message", msg );
			request.setAttribute("message2", msg2 );
			request.setAttribute("message3", msg3 );
		}
		
		catch(Exception e){
			
			
			message = "Vous n'avez pas selectionné d'utilisateur qui attend votre réponse, je vous propose de revenir au menu";
			request.setAttribute("messageerreur", message);
			request.setAttribute("utilisateur", user);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/verif_donnees.jsp" ).forward( request, response );
			
		}
		
		request.setAttribute("user", user);
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );	
		
	}

}
