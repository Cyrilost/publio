package bean;

import java.util.ArrayList;

public class ListMenuplus extends ListMenu{
	
	// extention du memu, pour ceux qui possèdent une bibliothèque
	public ListMenuplus(){
		// module modérateur

		m.remove(10);
		m.add("créer un compte utilisateur");
		m.add("créer un livre");
		m.add("modifier dans un compte utilisateur");
		m.add("modifier dans un livre de sa bibliothèque, ainsi que la date de prêt");
		m.add("visualiser les livres de sa bibliothèque");
		m.add("Rechercher la liste des livres lus par l'un des ses utilisateurs");
		m.add("Rechercher les informations d'un membre");
		m.add("visualiser les demandes de prêt à traiter");
		m.add("Répondre à un utilisateur sur le prêt d'un livre");
		m.add("visualiser les demandes d'autorisation à traiter");
		m.add("Répondre à une demande d'autorisation d'acces à la bibliothèque");
		m.add("Valider le retour d'un livre");
	}
	public ArrayList<String> getListMenuplus() {
		return m;
	}
}













