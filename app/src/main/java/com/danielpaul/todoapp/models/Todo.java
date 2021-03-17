package com.danielpaul.todoapp.models;

public class Todo {
    private String id;
    private String titre;
    private String description;

    public Todo(String id, String titre, String description) {
        this.id = id;
        this.titre = titre;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
