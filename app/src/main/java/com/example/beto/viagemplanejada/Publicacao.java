package com.example.beto.viagemplanejada;

import java.util.Date;

public class Publicacao {
    private String pais;
    private String cidade;
    private Date dtViagem;
    private Date dtPublicacao;

    public Publicacao(String pais, Date dtViagem) {
        this.pais = pais;
        this.dtViagem = dtViagem;
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

    public String getPais() {

        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
