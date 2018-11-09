package jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the lier database table.
 * 
 */
@Entity
@NamedQuery(name="Lier.findAll", query="SELECT l FROM Lier l")
public class Lier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_lien")
	private Integer idLien;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="id_mod")
	private Utilisateur utilisateur1;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="id_util")
	private Utilisateur utilisateur2;

	public Lier() {
	}

	public Integer getIdLien() {
		return this.idLien;
	}

	public void setIdLien(Integer idLien) {
		this.idLien = idLien;
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