package jpa;

import java.io.Serializable;



import javax.persistence.*;

import java.util.Date;




/**
 * The persistent class for the pret_ret database table.
 * 
 */
@Entity
@Table(name="pret_ret")
@NamedQuery(name="PretRet.findAll", query="SELECT p FROM PretRet p")
public class PretRet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_pret")
	private Integer idPret;

	@Temporal(TemporalType.DATE)
	@Column(name="date_retour_prevu")
	private Date dateRetourPrevu;

	@Temporal(TemporalType.DATE)
	@Column(name="date_retour_reel")
	private Date dateRetourReel;

	@Column(name="etat_livre_rendu")
	private Boolean etatLivreRendu;

	//bi-directional many-to-one association to Livre
	@ManyToOne
	@JoinColumn(name="id_livre")
	private Livre livre;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="id_det")
	private Utilisateur utilisateur1;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="id_empr1")
	private Utilisateur utilisateur2;

	public PretRet() {
	}

	public Integer getIdPret() {
		return this.idPret;
	}

	public void setIdPret(Integer idPret) {
		this.idPret = idPret;
	}

	public Date getDateRetourPrevu() {
		return this.dateRetourPrevu;
	}

	public void setDateRetourPrevu(Date dateRetourPrevu) {
		this.dateRetourPrevu = dateRetourPrevu;
	}

	public Date getDateRetourReel() {
		return this.dateRetourReel;
	}

	public void setDateRetourReel(Date dateRetourReel) {
		this.dateRetourReel = dateRetourReel;
	}

	public Boolean getEtatLivreRendu() {
		return this.etatLivreRendu;
	}

	public void setEtatLivreRendu(Boolean etatLivreRendu) {
		this.etatLivreRendu = etatLivreRendu;
	}

	public Livre getLivre() {
		return this.livre;
	}

	public void setLivre(Livre livre) {
		this.livre = livre;
	}

	public Utilisateur getUtilisateur1() {
		return this.utilisateur1;
	}

	public void setUtilisateur1(Utilisateur utilisateur1) {
		this.utilisateur1 = utilisateur1;
	}

	public Utilisateur getUtilisateur2() {
		return this.utilisateur2;
	}

	public void setUtilisateur2(Utilisateur utilisateur2) {
		this.utilisateur2 = utilisateur2;
	}

}