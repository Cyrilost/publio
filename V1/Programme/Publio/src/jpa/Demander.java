package jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the demander database table.
 * 
 */
@Entity
@NamedQuery(name="Demander.findAll", query="SELECT d FROM Demander d")
public class Demander implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_dem")
	private Integer idDem;

	private Boolean reponse;

	//bi-directional many-to-one association to Livre
	@ManyToOne
	@JoinColumn(name="id_livre_dem")
	private Livre livre;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="id_user_dem")
	private Utilisateur utilisateur1;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="id_user_det")
	private Utilisateur utilisateur2;

	public Demander() {
	}

	public Integer getIdDem() {
		return this.idDem;
	}

	public void setIdDem(Integer idDem) {
		this.idDem = idDem;
	}

	public Boolean getReponse() {
		return this.reponse;
	}

	public void setReponse(Boolean reponse) {
		this.reponse = reponse;
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