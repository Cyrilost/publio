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
 * Servlet implementation class Modif_Mdp
 */
@WebServlet("/Modif_Mdp")
public class Modif_Mdp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String ID_USER = "id";
	public static final String NOM_USER = "nom";
	public static final String PRENOM_USER = "prenom";
	public static final String MDP_USER = "motdepasse";
	public static final String MSG = "messagesucces";
	public static final String MSG1 = "messageerreur";
	public static final String MSG2 = "message2";
	
	public static final String VUE = "/WEB-INF/refaire_mdp.jsp";
	
	@EJB
	EJBUtilisateur ejbutilisateur;
	
	    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// prise en charge des accents et apostrophe
				request.setCharacterEncoding( "UTF-8" );
				response.setCharacterEncoding( "UTF-8" );
				
				int id = Integer.parseInt(request.getParameter(ID_USER));
				String nom = request.getParameter(NOM_USER);
				String prenom = request.getParameter(PRENOM_USER);
				String mdp = request.getParameter(MDP_USER);
				String messagesucces = request.getParameter(MSG);
				String messageerreur = request.getParameter(MSG1);
				String message2 = request.getParameter(MSG2);
				Utilisateur utilisateur = null;
				// message2 permet l'acces au menu
				
				
				int nbretard = 0;
				int nbAvertetat = 0;
				int nbPretEnCours = 0;
				boolean biblio = false; //par default
				boolean champvide = true;
				
				if(mdp.trim().isEmpty()){
					messageerreur = "Vous n'avez pas rempli le champ du mot de passe ";
					utilisateur = ejbutilisateur.load(id);
						
				}else{
					champvide = false;
					int retour = ejbutilisateur.UpdateMdp(mdp, id);
					if (retour != 0){
						messagesucces = "Votre mot de passe est enregistré dans la base de données";
						utilisateur = ejbutilisateur.load(id);
						message2 ="Veuillez cliquer sur le bouton Accès Menu";
						id = utilisateur.getIdUser();
						nbretard = utilisateur.getNbAvertRetard();
						nbAvertetat = utilisateur.getNbAvertEtat();
						nbPretEnCours = utilisateur.getNbPretEncours();
						biblio = utilisateur.getBiblio();
					}else{
						messageerreur = "veuillez recommencer l'enregistrement, ou vérifiez d'être connecter à la base de données";
					}
				}
				
				
				if ((utilisateur!=null)&&(champvide == false)){
					request.setAttribute("id", id);
					request.setAttribute("nom", nom);
					request.setAttribute("prenom", prenom);
					request.setAttribute("biblio", biblio);
					request.setAttribute("nbretard", nbretard);
					request.setAttribute("nbretat" , nbAvertetat);
					request.setAttribute("nbrencours" , nbPretEnCours);
					
					}
				request.setAttribute("utilisateur" , utilisateur);
				request.setAttribute("messagesucces", messagesucces);
				request.setAttribute("messageerreur", messageerreur);
				request.setAttribute("message2", message2);
				this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
			}

		}
