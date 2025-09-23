package com.assurance.model;

import com.assurance.enums.TypeSinistre;
import java.time.LocalDateTime;

public class Sinistre {
    private int id;
    private TypeSinistre typeSinistre;
    private LocalDateTime dateTime;
    private double cout;
    private String description;
    private Contrat contrat;

    public Sinistre(int id, TypeSinistre typeSinistre, LocalDateTime dateTime, double cout, String description, Contrat contrat) {
        this.id = id;
        this.typeSinistre = typeSinistre;
        this.dateTime = dateTime;
        this.cout = cout;
        this.description = description;
        this.contrat = contrat;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public TypeSinistre getTypeSinistre() {
        return typeSinistre;
    }
    public void setTypeSinistre(TypeSinistre typeSinistre) {
        this.typeSinistre = typeSinistre;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getCout() {
        return cout;
    }
    public void setCout(double cout) {
        this.cout = cout;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Contrat getContrat() {
        return contrat;
    }
    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    @Override
    public String toString() {
        return "Sinistre{id=" + id + ", type=" + typeSinistre +
                ", dateTime=" + dateTime + ", cout=" + cout +
                ", description='" + description + "', contrat=" + contrat.getId() + "}";
    }
}
