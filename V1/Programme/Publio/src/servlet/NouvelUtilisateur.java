package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpa.Utilisateur;
import ejb.EJBAutoriser;
import ejb.EJBConnexion;
import ejb.EJBUtilisateur;

/**
 * Servlet implementation class NouvelUtilisateur
 */
@WebServlet("/NouvelUtilisateur")
public class NouvelUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static final String VUE = "/WEB-INF/choix11bis.jsp";
    public static final String ID_MOD = "idMod";
    public static final String NEWU_NOM = "nomNew";
    public static final String NEWU_PRENOM = "prenomNew";
    public static final String NEWU_ADRESSE = "adresseNew";
    public static final String NEWU_TELEPHONE ="telephoneNew";
    public static final String ERRORS = "erreurs";
    public static final String RESULT = "resultat";
    public static final String MESSAGE = "message";
	
    @EJB
	private EJBUtilisateur ejbutilisateur;
	@EJB
	private EJBAutoriser ejbautoriser;
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
		String resultat = "";
		Map<String,String> erreurs = new HashMap<String,String>();
		//recuperation infos moderateur
		int idMod = Integer.parseInt(request.getParameter(ID_MOD));
		Utilisateur utilisateur = ejbutilisateur.load(idMod);
		//recuperation des champs du formulaire
		String nom = request.getParameter( NEWU_NOM );
		String prenom = request.getParameter( NEWU_PRENOM );
		String adresse = request.getParameter( NEWU_ADRESSE );
		String telephone = request.getParameter( NEWU_TELEPHONE );
		
		boolean verifBDD;
		
		//validation du nom et du prenom
		try{
			validationNom( nom );
		}catch(Exception e){
			erreurs.put( NEWU_NOM , e.getMessage() );
		}
		try{
			validationPrenom (prenom );
		}catch(Exception e){
			erreurs.put( NEWU_PRENOM , e.getMessage() );
		}
		
		//verification dans le BDD des homonymes
		verifBDD = ejbutilisateur.VerifNomPrenom( nom , prenom );
		try{
			validationVerif(verifBDD);
		}catch(Exception e){
			erreurs.put( MESSAGE , e.getMessage() );
		}
		
		//initialisation du resultat par rapport aux erreurs
		if (erreurs.isEmpty()){
			resultat = "Votre nouvel utilisateur est inscrit dans Publio";
			//System.out.println (nom+prenom);
			ejbutilisateur.enregistrementUser(nom,prenom,adresse,telephone);
			Utilisateur user = ejbconnexion.load(nom, prenom);
			/*
			 *  création du lien et valider automatiquement la demande d'accès biblio
			 */
			ejbautoriser.enregistrementAUTO(idMod, user.getIdUser());
			ejbautoriser.enregistrementLier(user.getIdUser(), idMod);
			
			
		}else{
			resultat = "echec de l'inscription";
		}
		
		//stockage resultat et message dans l'objet request
		request.setAttribute( "utilisateur", utilisateur);
		request.setAttribute( ERRORS, erreurs );
		request.setAttribute( RESULT, resultat );
		request.setAttribute( NEWU_NOM , nom );
		request.setAttribute( NEWU_PRENOM , prenom );
		request.setAttribute( NEWU_ADRESSE , adresse );
		request.setAttribute( NEWU_TELEPHONE , telephone );
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
		
	}
	
	private void validationNom (String nom)	throws Exception{
		if (nom !=null && nom.trim().length() < 3){
			throw new Exception ("Le nom de l'utilisateur doit contenir au moins 3 caractères. ");
		}
	}
	private void validationPrenom (String prenom) throws Exception{
		if (prenom !=null && prenom.trim().length() < 3){
			throw new Exception ("Le prenom de l'utilisateur doit contenir au moins 3 caractères. ");
		}
	}
	private void validationVerif (boolean verification)throws Exception{
		if (verification == true){
			throw new Exception ("Le prenom et le nom de l'utilisateur sont deja utilisés");
		}
	}

}
