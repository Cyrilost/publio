package jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the autoriser database table.
 * 
 */
@Entity
@NamedQuery(name="Autoriser.findAll", query="SELECT a FROM Autoriser a")
public class Autoriser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_autorisation")
	private Integer idAutorisation;

	private Boolean reponse;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="id_mod")
	private Utilisateur utilisateur1;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="id_util")
	private Utilisateur utilisateur2;

	public Autoriser() {
	}

	public Integer getIdAutorisation() {
		return this.idAutorisation;
	}

	public void setIdAutorisation(Integer idAutorisation) {
		this.idAutorisation = idAutorisation;
	}

	public Boolean getReponse() {
		return this.reponse;
	}

	public void setReponse(Boolean reponse) {
		this.reponse = reponse;
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