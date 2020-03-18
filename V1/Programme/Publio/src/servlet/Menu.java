package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import ejb.EJBUtilisateur;
import jpa.Utilisateur;
import bean.*;
/**
 * Servlet implementation class Menu
 */
@WebServlet("/Menu")
public class Menu extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public static final String VUE = "/WEB-INF/menu.jsp";
    public static final String USER_MOD = "biblio";
    public static final String USER_ID = "id";
    public static final String USER_NOM = "nom";
    public static final String USER_PRENOM = "prenom";
    
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
		request.setCharacterEncoding( "UTF-8" );
		
		boolean biblio = Boolean.parseBoolean(request.getParameter(USER_MOD));
		
		String nom = request.getParameter(USER_NOM);
		String prenom = request.getParameter(USER_PRENOM);
		
		
		try{
			int id_user = Integer.parseInt(request.getParameter(USER_ID));
					
			Utilisateur user = ejbutilisateur.load(id_user);
			
			int nbAvertetat = user.getNbAvertEtat();
			int nbAverRetard = user.getNbAvertRetard();
			int nbPretEnCours = user.getNbPretEncours();
			
			ListMenu m = new ListMenu();
			ListMenuplus mx = new ListMenuplus();
			ArrayList<String> menu ;
			
			if (biblio == false) {menu = m.getListMenu();}
			else { menu = mx.getListMenu();}
			
			request.setAttribute("nbretard" , nbAverRetard);
			request.setAttribute("nbretat" , nbAvertetat);
			request.setAttribute("nbrencours" , nbPretEnCours);
			request.setAttribute("menu", menu);
			request.setAttribute("id", id_user); 
			request.setAttribute("nom", nom);
			request.setAttribute("prenom" , prenom);
			this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
		}
		catch (Exception e){
			request.getSession().invalidate();
			//this.getServletContext().getRequestDispatcher( "Connexion" ).forward( request, response );
			response.sendRedirect("Connexion");
		}
		
	}

}

	
