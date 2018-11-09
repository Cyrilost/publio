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
import ejb.EJBUtilisateur;
import ejb.EJBVisual;

/**
 * Servlet implementation class ChoixLivrePrModif
 */
@WebServlet("/ChoixLivrePrModif")
public class ChoixLivrePrModif extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String NUM_CHOIX = "choixlivre";
	public static final String VUE = "/WEB-INF/choix14livre.jsp";  
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int idUser = Integer.parseInt(request.getParameter(ID_USER));
		String message = null;
		
		try{
			int choixlivre = Integer.parseInt(request.getParameter(NUM_CHOIX));
			Livre livre = new Livre();
			
			livre = (Livre) ejbvisual.ChoixDuLivre(choixlivre);
			request.setAttribute("livre", livre);
		}
		catch(Exception e){
			Utilisateur user = ejbutilisateur.load(idUser);
			message = "Vous n'avez pas selectionné de livre, je vous propose de revenir au menu";
			request.setAttribute("messageerreur", message);
			request.setAttribute("utilisateur", user);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/verif_donnees.jsp" ).forward( request, response );
			
		}
		
		
		
		this.getServletContext().getRequestDispatcher( VUE  ).forward( request, response );
	
	
	}

}
