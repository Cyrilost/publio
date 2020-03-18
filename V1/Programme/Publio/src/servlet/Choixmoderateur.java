package servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpa.Utilisateur;
import ejb.EJBAutoriser;
import ejb.EJBUtilisateur;

/**
 * Servlet implementation class Choixmoderateur
 */
@WebServlet("/Choixmoderateur")
public class Choixmoderateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public static final String ID_USER = "id";
	public static final String NOM = "nom";
	public static final String PRENOM = "prenom";
	public static final String NUM_CHOIX = "choixmod";
	public static final String VUE = "/WEB-INF/choix9autorisation.jsp";
	
	// Injection de notre EJB (session bean stateless)
	@EJB
	private EJBUtilisateur ejbutilisateur;
	@EJB
	private EJBAutoriser ejbautoriser;
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
		Utilisateur moderateur = new Utilisateur();
		String message = null;
		
		int idUser = Integer.parseInt(request.getParameter(ID_USER));
		try{
			int idMod = Integer.parseInt(request.getParameter(NUM_CHOIX));
			moderateur = ejbutilisateur.load(idMod);
			
			boolean verifLien;
					
			/* verifier que le demandeur n'est pas déjà rattaché
			 * a la liste des autorisation du detenteur
			 */
			verifLien = (boolean) ejbutilisateur.verifLien(idMod,idUser);
			if (verifLien == true){
				message = "Vous appartenez deja à la bibliothèque de "+moderateur.getNom()+" "+moderateur.getPrenom()+".";
			}
			//Insertion de la demande dans la BDD
			else{
				try{
					ejbautoriser.enregistrementAUT(idMod,idUser);
					message = "votre demande est enregistrée";
				}
				catch(Exception e){
					message = "Vous informe que votre demande n'a pas été enregistrée";
				}
			}
		}
		catch(Exception e){
			Utilisateur user = ejbutilisateur.load(idUser);
			message = "Vous n'avez pas selectionné de détenteur de bibliothèque, je vous propose de revenir au menu";
			request.setAttribute("messageerreur", message);
			request.setAttribute("utilisateur", user);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/verif_donnees.jsp" ).forward( request, response );
			
		}
		
		request.setAttribute("message", message);
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

}
