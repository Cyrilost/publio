package servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpa.Livre;
import jpa.Utilisateur;
import ejb.EJBLivre;
import ejb.EJBDemPret;
import ejb.EJBUtilisateur;

/**
 * Servlet implementation class Choixdemande
 */
@WebServlet("/Choixdemande")
public class Choixdemande extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public static final String ID_USER = "id";
	public static final String NOM = "nom";
	public static final String PRENOM = "prenom";
	public static final String NUM_CHOIX = "choixlivre";
	public static final String VUE = "/WEB-INF/choix7demande.jsp";
	
	// Injection de notre EJB (session bean stateless)
	@EJB
	private EJBLivre ejblivre;
	@EJB
	private EJBDemPret ejbdempret;
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
		Livre livre = new Livre();
		String message = null;
		
		int idUser = Integer.parseInt(request.getParameter(ID_USER));
		Utilisateur user = ejbutilisateur.load(idUser);
		try{
			int idLivre = Integer.parseInt(request.getParameter(NUM_CHOIX));
			boolean verifLien;
			// recuperation ID detenteur livre
			livre = ejblivre.load(idLivre);
			//System.out.println(livre.getUtilisateur().getIdUser()+" "+idUser+" "+idLivre);
			/* verifier que le demandeur est rattaché
			 * a la bibliotheque du detenteur
			 */
			verifLien = (boolean) ejblivre.verifLien(livre.getUtilisateur().getIdUser(),idUser);
			if (verifLien == false){
				message = "Vous n'appartenez pas à la bibliothèque de "+livre.getUtilisateur().getNom()+" "+livre.getUtilisateur().getPrenom()+".";
			}
			//Insertion de la demande dans la BDD
			else{
				try{
					ejbdempret.enregistrementDEM(livre.getUtilisateur().getIdUser(),idUser,idLivre);
					message = "votre demande est enregistrée";
				}
				catch(Exception e){
					message = "Vous informe que votre demande n'a pas été enregistrée";
				}
			}
		}
		catch(Exception e){
			
			message = "Vous n'avez pas selectionné de livre, je vous propose de revenir au menu";
			request.setAttribute("messageerreur", message);
			request.setAttribute("utilisateur", user);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/verif_donnees.jsp" ).forward( request, response );
		}	
		
		request.setAttribute("user", user);
		request.setAttribute("message", message);
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

}
