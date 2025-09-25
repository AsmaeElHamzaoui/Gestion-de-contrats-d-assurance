package com.assurance.model;

import com.assurance.enums.TypeSinistre;
import java.time.LocalDateTime;

public class Sinistre {
    private int id;
    private TypeSinistre typeSinistre;
    private LocalDateTime dateTime;
    private double cout;
    private String description;
    private int contratId;

    public Sinistre(int id, TypeSinistre typeSinistre, LocalDateTime dateTime, double cout, String description, int contratId) {
        this.id = id;
        this.typeSinistre = typeSinistre;
        this.dateTime = dateTime;
        this.cout = cout;
        this.description = description;
        this.contratId = contratId;
    }

    // Getters
    public int getId() { return id; }
    public TypeSinistre getTypeSinistre() { return typeSinistre; }
    public LocalDateTime getDateTime() { return dateTime; }
    public double getCout() { return cout; }
    public String getDescription() { return description; }
    public int getContratId() { return contratId; }

    // Setters (optionnels, selon les besoins)
    public void setId(int id) { this.id = id; }
    public void setTypeSinistre(TypeSinistre typeSinistre) { this.typeSinistre = typeSinistre; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public void setCout(double cout) { this.cout = cout; }
    public void setDescription(String description) { this.description = description; }
    public void setContratId(int contratId) { this.contratId = contratId; }

    @Override
    public String toString() {
        return "Sinistre { " +
                "id=" + id +
                ", typeSinistre=" + typeSinistre +
                ", dateTime=" + dateTime +
                ", cout=" + cout +
                ", description='" + description + '\'' +
                ", contratId=" + contratId +
                " }";
    }

}