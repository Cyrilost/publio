package jpa;

import java.io.Serializable;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the livre database table.
 * 
 */

@Entity
@NamedQuery(name="Livre.findAll", query="SELECT l FROM Livre l")
public class Livre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_livre")
	private Integer idLivre;

	private String auteur;

	@Temporal(TemporalType.DATE)
	@Column(name="date_retour_prévu")
	private Date dateRetourPrévu;

	@Column(name="etat_livre")
	private Boolean etatLivre;

	@Column(name="nb_page")
	private Integer nbPage;

	@Column(name="sous_titre")
	private String sousTitre;

	private String titre;

	private String tome;

	//bi-directional many-to-one association to Demander
	@OneToMany(mappedBy="livre")
	private List<Demander> demanders;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="id_mod")
	private Utilisateur utilisateur;

	//bi-directional many-to-one association to PretRet
	@OneToMany(mappedBy="livre")
	private List<PretRet> pretRets;

	public Livre() {
	}

	public Integer getIdLivre() {
		return this.idLivre;
	}

	public void setIdLivre(Integer idLivre) {
		this.idLivre = idLivre;
	}

	public String getAuteur() {
		return this.auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public Date getDateRetourPrévu() {
		return this.dateRetourPrévu;
	}

	public void setDateRetourPrévu(Date dateRetourPrévu) {
		this.dateRetourPrévu = dateRetourPrévu;
	}

	public Boolean getEtatLivre() {
		return this.etatLivre;
	}

	public void setEtatLivre(Boolean etatLivre) {
		this.etatLivre = etatLivre;
	}

	public Integer getNbPage() {
		return this.nbPage;
	}

	public void setNbPage(Integer nbPage) {
		this.nbPage = nbPage;
	}

	public String getSousTitre() {
		return this.sousTitre;
	}

	public void setSousTitre(String sousTitre) {
		this.sousTitre = sousTitre;
	}

	public String getTitre() {
		return this.titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getTome() {
		return this.tome;
	}

	public void setTome(String tome) {
		this.tome = tome;
	}

	public List<Demander> getDemanders() {
		return this.demanders;
	}

	public void setDemanders(List<Demander> demanders) {
		this.demanders = demanders;
	}

	public Demander addDemander(Demander demander) {
		getDemanders().add(demander);
		demander.setLivre(this);

		return demander;
	}

	public Demander removeDemander(Demander demander) {
		getDemanders().remove(demander);
		demander.setLivre(null);

		return demander;
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List<PretRet> getPretRets() {
		return this.pretRets;
	}

	public void setPretRets(List<PretRet> pretRets) {
		this.pretRets = pretRets;
	}

	public PretRet addPretRet(PretRet pretRet) {
		getPretRets().add(pretRet);
		pretRet.setLivre(this);

		return pretRet;
	}

	public PretRet removePretRet(PretRet pretRet) {
		getPretRets().remove(pretRet);
		pretRet.setLivre(null);

		return pretRet;
	}

}