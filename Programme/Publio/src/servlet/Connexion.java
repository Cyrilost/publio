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
 * Servlet implementation class Connexion
 */
@WebServlet( "/Connexion" )
public final class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String NOM_USER = "nom";
	public static final String PRENOM_USER = "prenom";
	public static final String MDP_USER = "motdepasse";
	public static int COMPT ;
	public static final String VUE = "/WEB-INF/connexion.jsp";
	
	public static final String MSG = "messageinfo";
	public static final String MSG1 = "messageerreur";
	
	
	// Injection de notre EJB (session bean stateless)
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
		request.setCharacterEncoding( "UTF-8" );
		response.setCharacterEncoding("UTF-8");

		String nom = request.getParameter(NOM_USER);
		String prenom = request.getParameter(PRENOM_USER);
		String mdp = request.getParameter(MDP_USER);
		Utilisateur user = new Utilisateur();
		String messageinfo = request.getParameter(MSG);
		String messageerreur = null;
		String messagesucces;
		boolean exist = false;
		boolean champvide = true;
		
		if ((nom.trim().isEmpty())&&(prenom.trim().isEmpty())){
			messageinfo = "Veuillez saisir votre nom et prenom";
		}else{
			user = ejbconnexion.load(nom, prenom);
			champvide = false;
			//System.out.println("mot de passse : "+user.getMdp());
			//System.out.println("phrase : "+user.getPhrase());
			if (user==null){
				exist = false;
			}else{
				exist = true;
			}
			
		
		}
		if (exist == true){
			//suite création d'un compte utilisateur par un modérateur
			if ((user.getMdp()==null)&&(user.getPhrase()==null)){
				request.setAttribute("user", user);
				//anticipation pour prévenir l'utilisateur pour la procedure pour mof mdp
				String message2 = "Ne cliquer pas sur accès menu tant que le mot de passe et la phrase ne sont pas enregistrés";
				request.setAttribute("message2", message2);
				this.getServletContext().getRequestDispatcher( "/WEB-INF/creationMdpPhrase.jsp" ).forward( request, response );
			}
			if(user.getMdp().equals(mdp)){
				messagesucces = "ok, connexion réussit, acces à votre compte";
				request.setAttribute("utilisateur", user);
				request.setAttribute("message",messagesucces);
				this.getServletContext().getRequestDispatcher("/WEB-INF/verif_donnees.jsp").forward(request, response);
				
			}else{
				messageerreur= "Erreur de mot de passe, il vous reste une tentative, veuillez le ressaisir,svp !";
				// un petit compteur static pour les 2 tentatives de connexion
				
				COMPT = COMPT + 1;
				System.out.print(COMPT);
				if (COMPT > 1){
					request.setAttribute("id", user.getIdUser());
					this.getServletContext().getRequestDispatcher("/WEB-INF/dem_phrase.jsp").forward(request, response);
					COMPT = 0;
					
				}
			}
			
		}
			
		if ((exist == false)&&(champvide == false)){messageerreur = "vérifier la création de votre compte, auprès de votre modérateur.";}
		
		
			
		
		
		
		// Stockage du message dans l'objet request
		
		
		request.setAttribute( "messageinfo", messageinfo );
		request.setAttribute( "messageerreur", messageerreur );
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
		}
	}
	
