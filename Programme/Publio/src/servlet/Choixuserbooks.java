package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpa.Utilisateur;
import ejb.EJBUtilisateur;
import ejb.EJBVisual;

/**
 * Servlet implementation class Choixuserbooks
 */
@WebServlet("/Choixuserbooks")
public class Choixuserbooks extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String NUM_CHOIX = "choixutil";
	public static final String VUE = "/WEB-INF/choix16livre.jsp";
	public static final String ID_USER = "id";
	
	// Injection de notre EJB (session bean stateless)
	@EJB
	private EJBVisual ejbvisual;
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
	@SuppressWarnings({"unchecked"})
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idMod = Integer.parseInt(request.getParameter(ID_USER));
		String message = null;
		Utilisateur mod = ejbutilisateur.load(idMod);
		
		try{
			Utilisateur user = new Utilisateur();
			List<Object[]> livreemprunte = new ArrayList<Object[]>();
			int choixutil = Integer.parseInt(request.getParameter(NUM_CHOIX));
						
			livreemprunte = (List<Object[]>) ejbvisual.dejaLus2( choixutil );
			user = (Utilisateur) ejbutilisateur.load(choixutil);
			
			request.setAttribute("livreemprunte", livreemprunte);
			request.setAttribute("nom" , user.getNom());
			request.setAttribute("prenom" , user.getPrenom());
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