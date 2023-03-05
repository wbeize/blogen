package com.gen.blogen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "tb_posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "É necessário colocar um título em seu texto! :)")
    @Size(min = 5, max = 50, message = "Há um limite de 50 caracteres, belê?")
    private String title;

    @NotNull(message = "Agora desenrole teu texto!")
    @Size(min = 5, max = 500, message = "Tens 500 caracteres. É um pouquinho maior que o passarinho azul há uns cinco anos, mas não tanto =D")
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new java.sql.Date(System.currentTimeMillis());

    @ManyToOne
    @JsonIgnoreProperties("post")
    private Theme theme;

    @ManyToOne
    @JsonIgnoreProperties("post")
    private User user;

    public Post(Long id,
                String title,
                String text,
                Date date,
                Theme theme,
                User user) {

        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.theme = theme;
        this.user = user;
    }

    public Post() {}

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
