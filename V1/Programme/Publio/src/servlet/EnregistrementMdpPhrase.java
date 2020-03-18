package servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpa.Utilisateur;
import ejb.EJBConnexion;

/**
 * Servlet implementation class EnregistrementMdpPhrase
 */
@WebServlet("/EnregistrementMdpPhrase")
public class EnregistrementMdpPhrase extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String NOM_USER = "nom";
	public static final String PRENOM_USER = "prenom";
	public static final String MDP_USER = "motdepasse";
	public static final String PHRASE_USER = "phrase";
	
	public static final String VUE = "/WEB-INF/creationMdpPhrase.jsp";
	
	public static final String MSG = "message";   
	public static final String MSG2 = "message2";  
	
	/*
	 *  Injection de notre EJB (session bean stateless)
	 */
	@EJB
	private EJBConnexion ejbconnexion;
	
	
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
		// prise en charge des accents et apostrophe
		request.setCharacterEncoding( "UTF-8" );
		response.setCharacterEncoding( "UTF-8" );
		
		String nom = request.getParameter(NOM_USER);
		String prenom = request.getParameter(PRENOM_USER);
		String mdp = request.getParameter(MDP_USER);
		String phrase = request.getParameter(PHRASE_USER);
		String message = null;
		String message2 = null;
		String message3 = null;
		int id = 0;
		int nbretard = 0;
		int nbAvertetat = 0;
		int nbPretEnCours = 0;
		// par évidence un nouvel utilisateur ne peut pas être moderateur =>biblio=false
		boolean biblio = false;
		Utilisateur user = ejbconnexion.load(nom, prenom);
		
		if((!mdp.trim().isEmpty())&&(!phrase.trim().isEmpty())){
			int retour = ejbconnexion.record(nom, prenom, mdp, phrase);
			if (retour != 0){
				message3 = "Votre mot de passe et votre lieu de naissance sont enregistrés dans la base de données";
				message2 ="Veuillez cliquer sur le bouton Accès Menu";
				id = user.getIdUser();
				nbretard = user.getNbAvertRetard();
				nbAvertetat = user.getNbAvertEtat();
				nbPretEnCours = user.getNbPretEncours();
			}else{
				message = "veuillez recommencer l'enregistrement, ou vérifiez d'être connecter à la base de données";
			}	
		}else{
			message = "Vous n'avez pas rempli le champ mot de passe ou/et lieu de naissance";
		}
		
		request.setAttribute("message", message); //erreur
		request.setAttribute("message2", message2);// info
		request.setAttribute("message3", message3);//acces
		request.setAttribute("user" , user);
		
		if (id != 0){
			request.setAttribute("id", id);
			request.setAttribute("nom", nom);
			request.setAttribute("prenom", prenom);
			request.setAttribute("biblio", biblio);
			request.setAttribute("nbretard", nbretard);
			request.setAttribute("nbretat" , nbAvertetat);
			request.setAttribute("nbrencours" , nbPretEnCours);
			}
		
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

}
