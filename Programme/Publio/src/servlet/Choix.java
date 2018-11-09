package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpa.Livre;
import jpa.Utilisateur;
import ejb.EJBDemPret;
import ejb.EJBLivre;
import ejb.EJBSeek;
import ejb.EJBUtilisateur;
import ejb.EJBVisual;



/**
 * Servlet implementation class Choix
 */
@WebServlet("/Choix")
public class Choix extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE = "/WEB-INF/menu.jsp";
	public static final String NUM_CHOIX = "choixmenu";
	public static final String NOM = "nom";
	public static final String PRENOM = "prenom";
	public static final String ID_USER = "id";
	public static final String MENU_USER = "menu";
	// Injection de notre EJB (session bean stateless)
	@EJB
	private EJBVisual ejbvisual;
	@EJB
	private EJBUtilisateur ejbutilisateur;
	@EJB
	private EJBLivre ejblivre;
	@EJB
	private EJBSeek ejbseek;
	@EJB
	private EJBDemPret ejbdempret;
	    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	
	@SuppressWarnings({ "unchecked" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding( "UTF-8" );
		response.setCharacterEncoding( "UTF-8" );
		String messageerreur = null;
		int choixmenu = 0;
		try{
			choixmenu = Integer.parseInt(request.getParameter(NUM_CHOIX));
		}
		catch(Exception e){
			
			messageerreur = "Redirection suite à une erreur dans le menu :<br/>Choisir une action du menu, avant de valider ! Merci !";
			
		}
		String nom = request.getParameter(NOM);
		String prenom = request.getParameter(PRENOM);
		int id = Integer.parseInt(request.getParameter(ID_USER));
		
		
		Utilisateur user = new Utilisateur();
		List<Livre> listlivre = new ArrayList<Livre>();
		List<Object[]> livreemprunte = new ArrayList<Object[]>();
		List<Object[]> listdem = new ArrayList<Object[]>();
		List<Object[]> listutil = new ArrayList<Object[]>();
		List<Object[]> listmod = new ArrayList<Object[]>();
		List<Object[]> listretour = new ArrayList<Object[]>();
		
		
		if (choixmenu == 0){
			Utilisateur utilisateur = ejbutilisateur.load(id);
			request.setAttribute("utilisateur" , utilisateur);
			request.setAttribute("messageerreur" , messageerreur);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/verif_donnees.jsp" ).forward( request, response );
		}
		
		
		if (choixmenu == 1){
			request.getSession().invalidate();
			//this.getServletContext().getRequestDispatcher( "Connexion" ).forward( request, response );
			response.sendRedirect("Connexion");
		}
		if (choixmenu == 2){
			//modif compte utilisateur
			user = ejbutilisateur.load(id);
			request.setAttribute("utilisateur", user);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/choix2.jsp" ).forward( request, response );
		}
		if (choixmenu == 3){
			listlivre = (List<Livre>) ejbvisual.TousLeslivres();
			request.setAttribute("listlivre", listlivre);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/choix3.jsp" ).forward( request, response );
		}		
		
		if (choixmenu == 4){
			//System.out.print(choixmenu+","+nom+","+prenom);
			livreemprunte = (List<Object[]>) ejbvisual.pret_encours( nom, prenom );
			request.setAttribute("livreemprunte", livreemprunte);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/choix4.jsp" ).forward( request, response );
		}

		if (choixmenu == 5){
			//System.out.print(choixmenu+","+nom+","+prenom);
			livreemprunte = (List<Object[]>) ejbseek.dejaLus( nom, prenom );
			request.setAttribute("livreemprunte", livreemprunte);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/choix5.jsp" ).forward( request, response );
		}
		if (choixmenu == 6){
			//choisir un livre
			listlivre = (List<Livre>) ejbvisual.TousLeslivres();
			request.setAttribute("listlivre", listlivre);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/choix6.jsp"  ).forward( request, response );
		}
		if (choixmenu == 7){
			user = (Utilisateur) ejbutilisateur.load(id);
			//liste des messages
			ArrayList<String> m=new ArrayList<String>();
			if(user.getNbAvertEtat()>1){
				m.add("Impossibilité de demander un livre car vous avez rendu des livres dans un mauvais état");
			}else{
				if(user.getNbAvertRetard()>4){
					m.add("Impossibilité de demander un livre car vous avez rendu trop de livres en retard");
				}else{
					if(user.getNbPretEncours()>1){
						m.add(user.getNom()+" "+user.getPrenom()+" a déjà 2 livres en court de prêt");
					}else{
						listlivre = (List<Livre>) ejblivre.SansSeslivres( id );
						request.setAttribute("listlivre", listlivre);
						request.setAttribute("message", m);
						request.setAttribute("user", user);
						this.getServletContext().getRequestDispatcher( "/WEB-INF/choix7.jsp" ).forward( request, response );
					}
				}
			}
			
			request.setAttribute("nom" , nom );
			request.setAttribute("prenom" , prenom );
			request.setAttribute("message", m);
			request.setAttribute("nbretard", user.getNbAvertRetard());
			request.setAttribute("nbretat" , user.getNbAvertEtat());
			
			
			this.getServletContext().getRequestDispatcher( "/WEB-INF/nonAutorise.jsp" ).forward( request, response );
		}
		if (choixmenu == 8){
			//System.out.print(choixmenu+","+nom+","+prenom);
			listdem = (List<Object[]>) ejbvisual.dem_pret( nom, prenom );
			request.setAttribute("listdem", listdem);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/choix8.jsp" ).forward( request, response );
		}
		if (choixmenu == 9){
			user = (Utilisateur) ejbutilisateur.load(id);
			//System.out.print(choixmenu+","+nom+","+prenom);
			listmod = (List<Object[]>) ejbseek.DEMautorisation( id );
			request.setAttribute("listmod", listmod);
			request.setAttribute("user", user);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/choix9.jsp" ).forward( request, response );
		}
		if (choixmenu == 10){
			//System.out.print(choixmenu+","+nom+","+prenom);
			listdem = (List<Object[]>) ejbvisual.dem_autorisation( nom, prenom );
			request.setAttribute("listdem", listdem);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/choix10.jsp" ).forward( request, response );
		}
		if (choixmenu == 11){
			user = (Utilisateur) ejbutilisateur.load(id);
			int retourinfo = 0;
			String msg = "";
			if (user.getBiblio() == false){
				retourinfo = ejbutilisateur.creerbiblio(id);
				if (retourinfo!=0){
					msg = "veuillez vous déconnecter pour prise en charge module Modérateur";
				}
				request.setAttribute("msg", msg);
				this.getServletContext().getRequestDispatcher( "/WEB-INF/choix11.jsp" ).forward( request, response );
			
			}else{
				// ceation new utilisateur, par un formulaire
				// interdiction forwarding si le même nom+prenom
				// interrogation BD avant
				request.setAttribute("utilisateur", user);
				this.getServletContext().getRequestDispatcher( "/WEB-INF/choix11bis.jsp" ).forward( request, response );
			}
		}
		if (choixmenu == 12){
			user = (Utilisateur) ejbutilisateur.load(id);
			request.setAttribute("user", user);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/choix12.jsp" ).forward( request, response );
		}
		if (choixmenu == 13){
			//choisir un de ses utilisateurs
			listutil = (List<Object[]>) ejbvisual.ChoixUtilisateur( id );
			Utilisateur mod = ejbutilisateur.load(id);
			request.setAttribute("moderateur", mod);
			request.setAttribute("listutil", listutil);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/choix13.jsp" ).forward( request, response );
		}
		if (choixmenu == 14){
			user = (Utilisateur) ejbutilisateur.load(id);
			listlivre = (List<Livre>) ejbvisual.Seslivres( id );
			request.setAttribute("listlivre", listlivre);
			request.setAttribute("user", user);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/choix14.jsp" ).forward( request, response );
		}
		
		if (choixmenu == 15){
			//System.out.print(choixmenu+","+id+");
			listlivre = (List<Livre>) ejbvisual.Seslivres( id );
			request.setAttribute("listlivre", listlivre);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/choix15.jsp" ).forward( request, response );
		}
		if (choixmenu == 16){
			//choisir un des ses utilisateurs + visu de ses livres
			listutil = (List<Object[]>) ejbvisual.ChoixUtilisateur( id );
			if (listutil == null){
				//logiquement situation impossible
				String message = "Vous n'avez pas d'utilisateur";
				request.setAttribute("message", message);
			}
			else{
				user = (Utilisateur) ejbutilisateur.load(id);
				request.setAttribute("user", user);
				request.setAttribute("listutil", listutil);
				this.getServletContext().getRequestDispatcher( "/WEB-INF/choix16.jsp" ).forward( request, response );
			}
		}
		if (choixmenu == 17){
			user = (Utilisateur) ejbutilisateur.load(id);
			request.setAttribute("user", user);
			//choisir un de ses utilisateurs
			listutil = (List<Object[]>) ejbvisual.ChoixUtilisateur( id );	
			request.setAttribute("listutil", listutil);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/choix17.jsp" ).forward( request, response );
		
		}
		if (choixmenu == 18){
			//System.out.print(choixmenu+","+nom+","+prenom);
			listdem = (List<Object[]>) ejbvisual.dem_pret_A_Traiter( nom, prenom );
			request.setAttribute("listdem", listdem);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/choix18.jsp" ).forward( request, response );
		}
		if (choixmenu == 19){
			user = (Utilisateur) ejbutilisateur.load(id);
			//System.out.print(choixmenu+","+nom+","+prenom);
			listdem = (List<Object[]>) ejbvisual.dem_pret_A_Traiter( nom, prenom );
			request.setAttribute("listdem", listdem);
			request.setAttribute("user", user);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/choix19.jsp" ).forward( request, response );
		}
		if (choixmenu == 20){
			//System.out.print(choixmenu+","+nom+","+prenom);
			listdem = (List<Object[]>) ejbvisual.dem_autorisationA_Traiter( nom, prenom );
			request.setAttribute("listdem", listdem);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/choix20.jsp" ).forward( request, response );
		}
		if (choixmenu == 21){
			user = (Utilisateur) ejbutilisateur.load(id);
			request.setAttribute("user", user);
			//System.out.print(choixmenu+","+nom+","+prenom);
			listdem = (List<Object[]>) ejbvisual.dem_autorisationA_Traiter( nom, prenom );
			request.setAttribute("listdem", listdem);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/choix21.jsp" ).forward( request, response );
		}
		if (choixmenu == 22){
			//retour d'un livre
			listretour = (List<Object[]>) ejbdempret.retour_pretA_traiter ( id );
			request.setAttribute("listretour", listretour);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/choix22.jsp" ).forward( request, response );
		}
		

	}
}

