package jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the utilisateur database table.
 * 
 */
@Entity
@NamedQuery(name="Utilisateur.findAll", query="SELECT u FROM Utilisateur u")
public class Utilisateur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_user")
	private Integer idUser;

	private String adresse;

	private Boolean biblio;

	private String mdp;

	@Column(name="nb_avert_etat")
	private Integer nbAvertEtat;

	@Column(name="nb_avert_retard")
	private Integer nbAvertRetard;

	@Column(name="nb_pret_encours")
	private Integer nbPretEncours;

	private String nom;

	private String phrase;

	private String prenom;

	private String telephone;

	//bi-directional many-to-one association to Autoriser
	@OneToMany(mappedBy="utilisateur1")
	private List<Autoriser> autorisers1;

	//bi-directional many-to-one association to Autoriser
	@OneToMany(mappedBy="utilisateur2")
	private List<Autoriser> autorisers2;

	//bi-directional many-to-one association to Demander
	@OneToMany(mappedBy="utilisateur1")
	private List<Demander> demanders1;

	//bi-directional many-to-one association to Demander
	@OneToMany(mappedBy="utilisateur2")
	private List<Demander> demanders2;

	//bi-directional many-to-one association to Lier
	@OneToMany(mappedBy="utilisateur1")
	private List<Lier> liers1;

	//bi-directional many-to-one association to Lier
	@OneToMany(mappedBy="utilisateur2")
	private List<Lier> liers2;

	//bi-directional many-to-one association to Livre
	@OneToMany(mappedBy="utilisateur")
	private List<Livre> livres;

	//bi-directional many-to-one association to PretRet
	@OneToMany(mappedBy="utilisateur1")
	private List<PretRet> pretRets1;

	//bi-directional many-to-one association to PretRet
	@OneToMany(mappedBy="utilisateur2")
	private List<PretRet> pretRets2;

	public Utilisateur() {
	}

	public Integer getIdUser() {
		return this.idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public Boolean getBiblio() {
		return this.biblio;
	}

	public void setBiblio(Boolean biblio) {
		this.biblio = biblio;
	}

	public String getMdp() {
		return this.mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public Integer getNbAvertEtat() {
		return this.nbAvertEtat;
	}

	public void setNbAvertEtat(Integer nbAvertEtat) {
		this.nbAvertEtat = nbAvertEtat;
	}

	public Integer getNbAvertRetard() {
		return this.nbAvertRetard;
	}

	public void setNbAvertRetard(Integer nbAvertRetard) {
		this.nbAvertRetard = nbAvertRetard;
	}

	public Integer getNbPretEncours() {
		return this.nbPretEncours;
	}

	public void setNbPretEncours(Integer nbPretEncours) {
		this.nbPretEncours = nbPretEncours;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPhrase() {
		return this.phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<Autoriser> getAutorisers1() {
		return this.autorisers1;
	}

	public void setAutorisers1(List<Autoriser> autorisers1) {
		this.autorisers1 = autorisers1;
	}

	public Autoriser addAutorisers1(Autoriser autorisers1) {
		getAutorisers1().add(autorisers1);
		autorisers1.setUtilisateur1(this);

		return autorisers1;
	}

	public Autoriser removeAutorisers1(Autoriser autorisers1) {
		getAutorisers1().remove(autorisers1);
		autorisers1.setUtilisateur1(null);

		return autorisers1;
	}

	public List<Autoriser> getAutorisers2() {
		return this.autorisers2;
	}

	public void setAutorisers2(List<Autoriser> autorisers2) {
		this.autorisers2 = autorisers2;
	}

	public Autoriser addAutorisers2(Autoriser autorisers2) {
		getAutorisers2().add(autorisers2);
		autorisers2.setUtilisateur2(this);

		return autorisers2;
	}

	public Autoriser removeAutorisers2(Autoriser autorisers2) {
		getAutorisers2().remove(autorisers2);
		autorisers2.setUtilisateur2(null);

		return autorisers2;
	}

	public List<Demander> getDemanders1() {
		return this.demanders1;
	}

	public void setDemanders1(List<Demander> demanders1) {
		this.demanders1 = demanders1;
	}

	public Demander addDemanders1(Demander demanders1) {
		getDemanders1().add(demanders1);
		demanders1.setUtilisateur1(this);

		return demanders1;
	}

	public Demander removeDemanders1(Demander demanders1) {
		getDemanders1().remove(demanders1);
		demanders1.setUtilisateur1(null);

		return demanders1;
	}

	public List<Demander> getDemanders2() {
		return this.demanders2;
	}

	public void setDemanders2(List<Demander> demanders2) {
		this.demanders2 = demanders2;
	}

	public Demander addDemanders2(Demander demanders2) {
		getDemanders2().add(demanders2);
		demanders2.setUtilisateur2(this);

		return demanders2;
	}

	public Demander removeDemanders2(Demander demanders2) {
		getDemanders2().remove(demanders2);
		demanders2.setUtilisateur2(null);

		return demanders2;
	}

	public List<Lier> getLiers1() {
		return this.liers1;
	}

	public void setLiers1(List<Lier> liers1) {
		this.liers1 = liers1;
	}

	public Lier addLiers1(Lier liers1) {
		getLiers1().add(liers1);
		liers1.setUtilisateur1(this);

		return liers1;
	}

	public Lier removeLiers1(Lier liers1) {
		getLiers1().remove(liers1);
		liers1.setUtilisateur1(null);

		return liers1;
	}

	public List<Lier> getLiers2() {
		return this.liers2;
	}

	public void setLiers2(List<Lier> liers2) {
		this.liers2 = liers2;
	}

	public Lier addLiers2(Lier liers2) {
		getLiers2().add(liers2);
		liers2.setUtilisateur2(this);

		return liers2;
	}

	public Lier removeLiers2(Lier liers2) {
		getLiers2().remove(liers2);
		liers2.setUtilisateur2(null);

		return liers2;
	}

	public List<Livre> getLivres() {
		return this.livres;
	}

	public void setLivres(List<Livre> livres) {
		this.livres = livres;
	}

	public Livre addLivre(Livre livre) {
		getLivres().add(livre);
		livre.setUtilisateur(this);

		return livre;
	}

	public Livre removeLivre(Livre livre) {
		getLivres().remove(livre);
		livre.setUtilisateur(null);

		return livre;
	}

	public List<PretRet> getPretRets1() {
		return this.pretRets1;
	}

	public void setPretRets1(List<PretRet> pretRets1) {
		this.pretRets1 = pretRets1;
	}

	public PretRet addPretRets1(PretRet pretRets1) {
		getPretRets1().add(pretRets1);
		pretRets1.setUtilisateur1(this);

		return pretRets1;
	}

	public PretRet removePretRets1(PretRet pretRets1) {
		getPretRets1().remove(pretRets1);
		pretRets1.setUtilisateur1(null);

		return pretRets1;
	}

	public List<PretRet> getPretRets2() {
		return this.pretRets2;
	}

	public void setPretRets2(List<PretRet> pretRets2) {
		this.pretRets2 = pretRets2;
	}

	public PretRet addPretRets2(PretRet pretRets2) {
		getPretRets2().add(pretRets2);
		pretRets2.setUtilisateur2(this);

		return pretRets2;
	}

	public PretRet removePretRets2(PretRet pretRets2) {
		getPretRets2().remove(pretRets2);
		pretRets2.setUtilisateur2(null);

		return pretRets2;
	}

}