package servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ejb.EJBAutoriser;
import ejb.EJBUtilisateur;
import jpa.Autoriser;
import jpa.Utilisateur;

/**
 * Servlet implementation class ReponseDem
 */
@WebServlet("/ReponseDem")
public class ReponseDem extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public static final String VUE = "/WEB-INF/choix21reponse.jsp";
	public static final String MESSAGE = "message";
	public static final String REPONSE1 = "reponse1";
	public static final String REPONSE2 = "reponse2";
	public static final String CHOIX_DEM = "choixdem";
	public static final String ID_USER = "id";
	// Injection de notre EJB (session bean stateless)
	@EJB
	private EJBAutoriser ejbautoriser;
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
		
		int idUser = Integer.parseInt(request.getParameter(ID_USER));
		Utilisateur user = ejbutilisateur.load(idUser);
		String message = null;
		String messageinfo = null;
		Autoriser acces = new Autoriser();
		int retour = 0;
		String msg ="";
		
		
		try{
			
			int numerodem = Integer.parseInt(request.getParameter(CHOIX_DEM));
			
			acces = ejbautoriser.load(numerodem);
			String nomDem = acces.getUtilisateur2().getNom();
			String prenomDem = acces.getUtilisateur2().getPrenom();
			int idDem = acces.getUtilisateur2().getIdUser();
			int idDet = acces.getUtilisateur1().getIdUser();
			
			Boolean rep;
			
			Boolean rep1 = Boolean.parseBoolean(request.getParameter(REPONSE1));
			Boolean rep2 = Boolean.parseBoolean(request.getParameter(REPONSE2));
			
			
			if ((rep1 == false)&&(rep2 == false)){
				msg = "Il faut choisir !! le choix sera définitif ... ";
			}
			
			if (rep1 == true){
				//System.out.println("accepter");
				rep = true;
				retour = ejbautoriser.reponseacces(rep, numerodem);
				if ( retour != 0 ){
					ejbautoriser.enregistrementLier(idDem,idDet);
				}
				msg = nomDem+" "+prenomDem+" est lié à votre bibliothèque";
				messageinfo = "Veuillez cliquer sur Retour Menu";
			}
			
			if (rep2 == true ){
				//System.out.println("refuser");
				rep = false;
				retour = ejbautoriser.reponseacces(rep, numerodem);
				msg = "votre réponse a bien été enregistrée sur la BDD";
				messageinfo = "Vous n'avez pas autorisé l'accès à votre bibliothèque à "+nomDem+" "+prenomDem;
			}
			request.setAttribute("user",user);
			request.setAttribute("id", numerodem);
			request.setAttribute("nom", nomDem );
			request.setAttribute("prenom", prenomDem );
	
			request.setAttribute(REPONSE1, rep1 );
			request.setAttribute(REPONSE2, rep2 );
			request.setAttribute("messageinfo", messageinfo );
			request.setAttribute("message", msg );
		}
		catch(Exception e){
			
			
			message = "Vous n'avez pas selectionné d'utilisateur qui attend votre réponse, je vous propose de revenir au menu";
			request.setAttribute("messageerreur", message);
			request.setAttribute("utilisateur", user);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/verif_donnees.jsp" ).forward( request, response );
			
			
		}
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );	
		
	}

}
