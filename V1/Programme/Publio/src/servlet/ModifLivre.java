package servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ejb.EJBLivre;


/**
 * Servlet implementation class ModifLivre
 */
@WebServlet("/ModifLivre")
public class ModifLivre extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE = "/WEB-INF/choix14livre.jsp";
	public static final String ID_LIVRE = "idlivre";
	public static final String NEW_TITRE = "titre";
	public static final String NEW_SOUSTITRE = "sousTitre";
	public static final String NEW_TOME = "tome";
	public static final String NEW_AUTEUR = "auteur";
	public static final String NEW_DATE = "dateRetourPrevu";
	
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
		
		int infotitre = 0;
		int infosoustitre = 0;
		int infotome = 0;
		int infoauteur = 0;
		int infodate = 0;
		
		//int retour = 0;
		List<String> message = new ArrayList<String>();
		int id = Integer.parseInt(request.getParameter(ID_LIVRE));
		String titre = request.getParameter(NEW_TITRE);
		String soustitre = request.getParameter(NEW_SOUSTITRE);
		String tome = request.getParameter(NEW_TOME);
		String auteur = request.getParameter(NEW_AUTEUR);
		String date = request.getParameter(NEW_DATE);
		 
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date DateModifie = null;
		try {
			DateModifie = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if(!titre.equals("")){
			infotitre = ejblivre.modiftitre(titre,id);
		}
		if (infotitre != 0){
			message.add("Le titre du livre a été modifié");
		}
		if(!soustitre.equals("")){
			infosoustitre = ejblivre.modifsoustitre(soustitre,id);
		}
		if (infosoustitre != 0){
			message.add("Le sous-titre du livre a été modifié");
		}
		if(!tome.equals("")){
			infotome = ejblivre.modiftome(tome,id);
		}
		if (infotome != 0){
			message.add("Le tome du livre a été modifié");
		}
		if(!auteur.equals("")){
			infoauteur = ejblivre.modifauteur(tome,id);
		}
		if (infoauteur != 0){
			message.add("L'auteur du livre a été modifié");
		}
		if(!date.equals("")){
			infodate = ejblivre.modifdate(DateModifie,id);
		}
		if (infodate != 0){
			message.add("La date du retour prévu a été modifiée");
		}
		
		request.setAttribute("msg" , message);
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
		
	}
	

}
