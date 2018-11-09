package servlet;

import java.io.IOException;


import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpa.Livre;
import ejb.EJBVisual;

/**
 * Servlet implementation class Choixlivre
 */
@WebServlet("/Choixlivre")
public class Choixlivre extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String NUM_CHOIX = "choixlivre";
	public static final String VUE = "/WEB-INF/choix6livre.jsp";
	
	// Injection de notre EJB (session bean stateless)
		@EJB
		private EJBVisual ejbvisual;
		 
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
		
		String message = null; 
		try{
			int choixlivre = Integer.parseInt(request.getParameter(NUM_CHOIX));
			Livre livre = new Livre();
			
			livre = (Livre) ejbvisual.ChoixDuLivre(choixlivre);
			request.setAttribute("livre", livre);
		}
		catch(Exception e){
			message = "Il n'y a pas de livre dans les bibliothèques<br/>"
					+ " ou <br/>"
					+ "Peut-être avez-vous oublié de selectionner une livre ?<br/>"; 
			request.setAttribute("message", message);
			
		}
		
		this.getServletContext().getRequestDispatcher( VUE  ).forward( request, response );
	
	
	}

}
