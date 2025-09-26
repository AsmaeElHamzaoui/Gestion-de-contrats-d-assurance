package com.assurance.model;

public class Conseiller extends Person {
    private int id;

    public Conseiller(int id, String nom, String prenom, String email) {
        super(nom, prenom, email);
        this.id = id;
    }

    // Getters
    public int getId() { return id; }

    // Setters
    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return super.toString();
    }
}