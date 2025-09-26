package com.assurance.view;

import com.assurance.model.Sinistre;
import com.assurance.enums.TypeSinistre;
import com.assurance.service.SinistreService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class SinistreView {
    private SinistreService sinistreService;
    private Scanner scanner;

    public SinistreView(SinistreService sinistreService, Scanner scanner) {
        this.sinistreService = sinistreService;
        this.scanner = scanner;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== Gestion des Sinistres ===");
            System.out.println("1. Ajouter un sinistre");
            System.out.println("2. Afficher un sinistre par ID");
            System.out.println("3. Afficher tous les sinistres");
            System.out.println("4. Modifier un sinistre");
            System.out.println("5. Supprimer un sinistre");
            System.out.println("6. Retour");
            System.out.print("Choix : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Type de sinistre (ACCIDENT_VOITURE, INCENDIE_MAISON, MALADIE) : ");
                    String type = scanner.nextLine().toUpperCase();
                    System.out.print("Date et heure (YYYY-MM-DD HH:MM:SS) : ");
                    String dateTimeStr = scanner.nextLine();
                    System.out.print("Coût : ");
                    double cout = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Description : ");
                    String description = scanner.nextLine();
                    System.out.print("ID du contrat : ");
                    int contratId = scanner.nextInt();
                    Sinistre sinistre = new Sinistre(0, TypeSinistre.valueOf(type), LocalDateTime.parse(dateTimeStr.replace(" ", "T")),
                            cout, description, contratId);
                    try {
                        sinistre = sinistreService.addSinistre(sinistre);
                        System.out.println("Sinistre ajouté avec succès. ID: " + sinistre.getId());
                    } catch (Exception e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("ID du sinistre : ");
                    int sinistreId = scanner.nextInt();
                    try {
                        Sinistre foundSinistre = sinistreService.getSinistreById(sinistreId);
                        if (foundSinistre != null) {
                            System.out.println(foundSinistre);
                        } else {
                            System.out.println("Sinistre non trouvé.");
                        }
                    } catch (Exception e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        List<Sinistre> sinistres = sinistreService.getAllSinistresWithContrats();
                        for (Sinistre s : sinistres) {
                            System.out.println(s);
                        }
                    } catch (Exception e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("ID du sinistre à modifier : ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        Sinistre existingSinistre = sinistreService.getSinistreById(updateId);
                        if (existingSinistre != null) {
                            System.out.print("Nouveau type de sinistre (actuel: " + existingSinistre.getTypeSinistre() + ") : ");
                            String newType = scanner.nextLine().toUpperCase();
                            if (newType.isEmpty()) newType = existingSinistre.getTypeSinistre().name();
                            System.out.print("Nouvelle date et heure (actuel: " + existingSinistre.getDateTime() + ") : ");
                            String newDateTime = scanner.nextLine();
                            if (newDateTime.isEmpty()) newDateTime = existingSinistre.getDateTime().toString().replace(" ", "T");
                            System.out.print("Nouveau coût (actuel: " + existingSinistre.getCout() + ") : ");
                            String coutInput = scanner.nextLine();
                            double newCout = coutInput.isEmpty() ? existingSinistre.getCout() : Double.parseDouble(coutInput);
                            System.out.print("Nouvelle description (actuel: " + existingSinistre.getDescription() + ") : ");
                            String newDescription = scanner.nextLine();
                            if (newDescription.isEmpty()) newDescription = existingSinistre.getDescription();
                            System.out.print("Nouvel ID du contrat (actuel: " + existingSinistre.getContratId() + ") : ");
                            String contratIdInput = scanner.nextLine();
                            int newContratId = contratIdInput.isEmpty() ? existingSinistre.getContratId() : Integer.parseInt(contratIdInput);
                            Sinistre updatedSinistre = new Sinistre(updateId, TypeSinistre.valueOf(newType),
                                    LocalDateTime.parse(newDateTime.replace(" ", "T")), newCout, newDescription, newContratId);
                            sinistreService.updateSinistre(updatedSinistre);
                            System.out.println("Sinistre modifié avec succès.");
                        } else {
                            System.out.println("Sinistre non trouvé.");
                        }
                    } catch (Exception e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.print("ID du sinistre à supprimer : ");
                    int deleteId = scanner.nextInt();
                    try {
                        sinistreService.deleteSinistre(deleteId);
                        System.out.println("Sinistre supprimé (si existant).");
                    } catch (Exception e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }
}