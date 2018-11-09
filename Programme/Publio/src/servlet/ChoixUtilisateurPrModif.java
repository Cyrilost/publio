package servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import jpa.Utilisateur;
import ejb.EJBUtilisateur;


/**
 * Servlet implementation class ChoixUtilisateurPrModif
 */
@WebServlet("/ChoixUtilisateurPrModif")
public class ChoixUtilisateurPrModif extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String NUM_CHOIX = "choixutil";
	public static final String ID_MOD = "idMod";
	public static final String VUE = "/WEB-INF/choix2plus.jsp";   
	// Injection de notre EJB (session bean stateless)
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
		int id_mod = Integer.parseInt(request.getParameter(ID_MOD));
		String message = null;
		Utilisateur mod = ejbutilisateur.load(id_mod);
		try{
			int choixutil = Integer.parseInt(request.getParameter(NUM_CHOIX));
			Utilisateur user = new Utilisateur();
			user = (Utilisateur) ejbutilisateur.load(choixutil);
			request.setAttribute("moderateur", mod);
			request.setAttribute("utilisateur2", user);
			this.getServletContext().getRequestDispatcher( VUE  ).forward( request, response );
		}
		catch(Exception e){
			message = "Vous n'avez pas selectionné l'un de vos utilisateurs, je vous propose de revenir au menu";
			request.setAttribute("messageerreur", message);
			request.setAttribute("utilisateur", mod);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/verif_donnees.jsp" ).forward( request, response );
			
		}
	}
		
}
	

	