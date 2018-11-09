package servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ejb.EJBLivre;


/**
 * Servlet implementation class NouveauLivre
 */
@WebServlet("/NouveauLivre")
public class NouveauLivre extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static final String VUE = "/WEB-INF/choix12.jsp"; 
    public static final String NOM = "nom";
    public static final String PRENOM = "prenom";
    public static final String ID = "id";
    public static final String NEW_TITRE ="titre";
    public static final String NEW_SOUSTITRE ="sousTitre";
    public static final String NEW_TOME ="tome";
    public static final String NEW_AUTEUR ="auteur";
    public static final String NEW_ETATLIVRE ="etatLivre";
    public static final String NEW_NBPAGE ="nbPage";
    public static final String MESSAGE = "message";
	
   
	@EJB
	private EJBLivre ejblivre;
	
	
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
		
		//recuperation des champs du formulaire
		String nom = request.getParameter( NOM );
		String prenom = request.getParameter( PRENOM );
		int id = Integer.parseInt(request.getParameter( ID ));
		String titre = request.getParameter( NEW_TITRE );
		String soustitre = request.getParameter( NEW_SOUSTITRE );
		String tome = request.getParameter( NEW_TOME );
		String auteur = request.getParameter( NEW_AUTEUR );
		int nbpage = Integer.parseInt(request.getParameter( NEW_NBPAGE ));
		boolean etatlivre = Boolean.parseBoolean(request.getParameter( NEW_ETATLIVRE ));
		String message;
		boolean verifBDD;
		
		//verification dans le BDD des homonymes
		verifBDD = ejblivre.VerifTitreAuteur( titre , soustitre, tome, auteur );
		
		if (verifBDD == true){
			
			message = "ce livre existe déjà dans une bibliothèque";
		}else{
			ejblivre.enregistrementBook( titre , soustitre , tome , auteur , nbpage , etatlivre, id );
			message = "le livre vient d'être rajouté dans votre bibliothèque";
		}
		//stockage resultat et message dans l'objet request
		request.setAttribute( NOM , nom );
		request.setAttribute( PRENOM , prenom );
		request.setAttribute( ID , id );
		request.setAttribute( NEW_TITRE , titre );
		request.setAttribute( NEW_SOUSTITRE , soustitre );
		request.setAttribute( NEW_TOME , tome );
		request.setAttribute( NEW_AUTEUR , auteur );
		request.setAttribute( NEW_NBPAGE , nbpage );
		request.setAttribute( NEW_ETATLIVRE , etatlivre );
		request.setAttribute( "message" , message );

		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}
}