package bean;

import java.util.ArrayList;

public class ListMenu {
	// Menu pour l'utilisateur qui n'a pas de bibliothèque
		protected ArrayList<String> m=new ArrayList<String>();
		
		public ListMenu()
		{// module utilisateur
			m.add("quitter");
			m.add("modifier son compte");
			m.add("visualiser l'ensemble des livres");
			m.add("visualiser les livres encours d'emprunt");
			m.add("visualiser les livres empruntés");
			m.add("rechercher les informations d'un livre");
			m.add("Demander le prêt d'un livre");
			m.add("visualiser les demandes de prêt");
			m.add("Demander une autorisation accès à une bibliothèque");
			m.add("visualiser les demandes d'autorisation");
			m.add("créer une bibliothèque");
		}
		public ArrayList<String> getListMenu() {
			return this.m;
		}
}
