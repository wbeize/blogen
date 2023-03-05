package com.spring.blogen.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "tb_postagens")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "É necessário colocar um título em seu texto! :)")
    @Size(min = 5, max = 50, message = "Há um limite de 50 caracteres, belê?")
    private String titulo;

    @NotNull(message = "Agora desenrole teu texto!")
    @Size(min = 5, max = 500, message = "Tens 500 caracteres. É um pouquinho maior que o passarinho azul há uns cinco anos, mas não tanto =D")
    private String texto;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data = new java.sql.Date(System.currentTimeMillis());

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
