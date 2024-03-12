package com.AppRH.AppRH.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import javax.validation.constraints.NotEmpty;

@Entity
public class Funcionario implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String data;
	private String nome;
	private String email;
	
    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.REMOVE)
	private List<Dependentes> dependentes;


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Dependentes> getDependentes() {
        return this.dependentes;
    }

    public void setDependentes(List<Dependentes> dependentes) {
        this.dependentes = dependentes;
    }

}
