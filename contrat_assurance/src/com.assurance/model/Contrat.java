package com.assurance.model;

import com.assurance.enums.TypeContrat;
import java.time.LocalDate;

public class Contrat {
    private int id;
    private TypeContrat typeContrat;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int clientId;

    public Contrat(int id, TypeContrat typeContrat, LocalDate dateDebut, LocalDate dateFin, int clientId) {
        this.id = id;
        this.typeContrat = typeContrat;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.clientId = clientId;
    }

    // Getters
    public int getId() { return id; }
    public TypeContrat getTypeContrat() { return typeContrat; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public int getClientId() { return clientId; }

    // Setters (optionnels, selon les besoins)
    public void setId(int id) { this.id = id; }
    public void setTypeContrat(TypeContrat typeContrat) { this.typeContrat = typeContrat; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public void setClientId(int clientId) { this.clientId = clientId; }
}