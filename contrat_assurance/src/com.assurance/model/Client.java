package com.assurance.model;

public class Client extends Person {
    private int id;
    private Conseiller conseiller; // un client est suivi par un conseiller

    public Client(int id, String nom, String prenom, String email, Conseiller conseiller) {
        super(nom, prenom, email);
        this.id = id;
        this.conseiller = conseiller;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Conseiller getConseiller() {
        return conseiller;
    }
    public void setConseiller(Conseiller conseiller) {
        this.conseiller = conseiller;
    }

    @Override
    public String toString() {
        return "Client{id=" + id + ", nom=" + getNom() + ", prenom=" + getPrenom() +
                ", email=" + getEmail() + ", conseiller=" + conseiller.getId() + "}";
    }
}
