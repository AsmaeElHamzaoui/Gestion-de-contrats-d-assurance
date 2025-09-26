package com.assurance.model;

public class Client extends Person {
    private int id;
    private int conseillerId;

    public Client(int id, String nom, String prenom, String email, int conseillerId) {
        super(nom, prenom, email);
        this.id = id;
        this.conseillerId = conseillerId;
    }

    // Getters
    public int getId() { return id; }
    public int getConseillerId() { return conseillerId; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setConseillerId(int conseillerId) { this.conseillerId = conseillerId; }

    @Override
    public String toString() {
        return super.toString() + ", Conseiller ID: " + conseillerId;
    }
}