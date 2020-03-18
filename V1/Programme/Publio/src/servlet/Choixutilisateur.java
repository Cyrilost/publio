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
 * Servlet implementation class Choixutilisateur
 */
@WebServlet("/Choixutilisateur")
public class Choixutilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String NUM_CHOIX = "choixutil";
	public static final String VUE = "/WEB-INF/choix17util.jsp";
	public static final String ID_MOD = "id";
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
		
		int idMod = Integer.parseInt(request.getParameter(ID_MOD));
		String message = null;
		Utilisateur mod = ejbutilisateur.load(idMod);
		
		try{
			int choixutil = Integer.parseInt(request.getParameter(NUM_CHOIX));
			Utilisateur user = new Utilisateur();
			
			user = (Utilisateur) ejbutilisateur.load(choixutil);
			request.setAttribute("user", user);
		}
		catch(Exception e){
			message = "Vous n'avez pas selectionné l'un de vos utilisateurs, je vous propose de revenir au menu";
			request.setAttribute("messageerreur", message);
			request.setAttribute("utilisateur", mod);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/verif_donnees.jsp" ).forward( request, response );
			
		}
	this.getServletContext().getRequestDispatcher( VUE  ).forward( request, response );
	
	}
}	

	