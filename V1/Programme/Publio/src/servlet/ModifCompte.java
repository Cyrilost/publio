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
 * Servlet implementation class ModifCompte
 */
@WebServlet("/ModifCompte")
public class ModifCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE = "/WEB-INF/choix2.jsp";
	
	//utilisateur
	public static final String ID_USER = "id";
	public static final String NOM = "nom";
	public static final String PRENOM = "prenom";
	public static final String ADRESSE = "adresse";
	public static final String TELEPHONE = "telephone";
	
	public static final String ADRESSE2 = "adresse2";
	public static final String TELEPHONE2 = "telephone2";
	
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
		
		int retourtel = 0;
		int retouradresse = 0;
		String messagetel = "Pas de modification sur le numéro de téléphone";
		String messageadresse = "Pas de modification sur l'adresse";
		String message = null;
		String numero = request.getParameter(TELEPHONE2);
		String domicile = request.getParameter(ADRESSE2);
		
		int id = Integer.parseInt(request.getParameter(ID_USER));	
		
		Utilisateur utilisateur = ejbutilisateur.load(id);
		
		boolean champvide = true;
		
		
		if(!numero.trim().isEmpty()){
			retourtel = ejbutilisateur.modiftel(numero,id);
		}
		if (retourtel != 0){
			messagetel = "Votre numéro de téléphone a été mis à jour";
			champvide = false;
		}
		
		if(!domicile.trim().isEmpty()){
			retouradresse = ejbutilisateur.modifadresse(domicile,id);
		}
		if (retouradresse !=0){
			messageadresse = "Votre adresse a été mis à jour";
			champvide = false;
		}
		
		request.setAttribute("utilisateur" , utilisateur);
		
		
		if (champvide == true){
			message = messagetel+"<br/>"+messageadresse;
			request.setAttribute("messageinfo" , message);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/verif_donnees.jsp" ).forward( request, response );
		}
		
		request.setAttribute("msg1" , messagetel);
		request.setAttribute("msg2" , messageadresse);
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

}
