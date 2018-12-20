package com.example.beto.viagemplanejada;

import java.util.Date;

public class Publicacao {
    private String pais;
    private String cidade;
    private Date dtViagem;
    private Date dtPublicacao;
    private float rating;

    public Publicacao(){}


    public Publicacao(String pais, String cidade, Date dtViagem, Date dtPublicacao, float rating) {
        this.pais = pais;
        this.cidade = cidade;
        this.dtViagem = dtViagem;
        this.dtPublicacao = dtPublicacao;
        this.rating = rating;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Date getDtViagem() {
        return dtViagem;
    }

    public void setDtViagem(Date dtViagem) {
        this.dtViagem = dtViagem;
    }

    public Date getDtPublicacao() {
        return dtPublicacao;
    }

    public void setDtPublicacao(Date dtPublicacao) {
        this.dtPublicacao = dtPublicacao;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
