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
 * Servlet implementation class RefaireMdp
 */
@WebServlet("/ValiderPhrase")
public class ValiderPhrase extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String VUE = "/WEB-INF/dem_phrase.jsp";
	private static final String ID_USER = "id";
    private static final String PHRASE = "phrase";
    
    
    @EJB
    EJBUtilisateur ejbutilisateur;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
		
		
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter(ID_USER));
		String phrase = request.getParameter(PHRASE);
		String messageerreur = null;
		String messageinfo = null;
		Utilisateur user = ejbutilisateur.load(id);
		boolean champvide = false;
		
		if (phrase.trim().isEmpty()){
			messageerreur = "On ne peut pas être né, nulle part !";
			champvide = true;
		}
		
		if (phrase.equals(user.getPhrase())){
			request.setAttribute("utilisateur", user);
			
			//anticipation pour prévenir l'utilisateur pour la procedure pour mof mdp
			String message2 = "Ne cliquer pas sur accès menu tant que le mot de passe n'est pas enregistré";
			request.setAttribute("message2", message2);
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/refaire_mdp.jsp").forward(request, response);
		}else{
			messageinfo = "Vous n'avez pas indiqué la bonne ville, veuillez réessayer ou<br/>"
					+ "  Faîtes appel à un de vos modérateurs pour la création d'un nouveau compte"
					+ " <br/>et cliquer sur retour";
			
		}
		
			
		if (champvide == false){request.setAttribute("messageinfo", messageinfo);}
		request.setAttribute("messageerreur", messageerreur);
		request.setAttribute("utilisateur", user);
		request.setAttribute("id", user.getIdUser());
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
