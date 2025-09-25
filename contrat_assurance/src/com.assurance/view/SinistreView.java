package com.assurance.view;

import com.assurance.model.Sinistre;
import com.assurance.enums.TypeSinistre;
import com.assurance.service.SinistreService;
import java.sql.SQLException;
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
            System.out.println("6. Calculer les coûts totaux des sinistres d'un client");
            System.out.println("7. Afficher les sinistres d'un contrat par ID");
            System.out.println("8. Afficher les sinistres triés par montant décroissant");
            System.out.println("9. Afficher les sinistres d'un client par ID");
            System.out.println("10. Afficher les sinistres avant une date donnée");
            System.out.println("11. Afficher les sinistres avec coût supérieur à un montant");
            System.out.println("12. Retour");
            System.out.print("Choix : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
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
                        sinistre = sinistreService.addSinistre(sinistre);
                        System.out.println("Sinistre ajouté avec succès. ID: " + sinistre.getId());
                        break;
                    case 2:
                        System.out.print("ID du sinistre : ");
                        int sinistreId = scanner.nextInt();
                        Sinistre foundSinistre = sinistreService.getSinistreById(sinistreId);
                        if (foundSinistre != null) {
                            System.out.println(foundSinistre);
                        } else {
                            System.out.println("Sinistre non trouvé.");
                        }
                        break;
                    case 3:
                        List<Sinistre> sinistres = sinistreService.getAllSinistresWithContrats();
                        for (Sinistre s : sinistres) {
                            System.out.println(s);
                        }
                        break;
                    case 4:
                        System.out.print("ID du sinistre à modifier : ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();
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
                        break;
                    case 5:
                        System.out.print("ID du sinistre à supprimer : ");
                        int deleteId = scanner.nextInt();
                        sinistreService.deleteSinistre(deleteId);
                        System.out.println("Sinistre supprimé (si existant).");
                        break;
                    case 6:
                        System.out.print("ID du client : ");
                        int clientIdTotalCost = scanner.nextInt();
                        double totalCost = sinistreService.calculateTotalCostByClientId(clientIdTotalCost);
                        System.out.printf("Coût total des sinistres pour le client %d : %.2f%n", clientIdTotalCost, totalCost);
                        break;
                    case 7:
                        System.out.print("ID du contrat : ");
                        int contratSinistreId = scanner.nextInt();
                        List<Sinistre> contratSinistres = sinistreService.getSinistresByContratId(contratSinistreId);
                        if (contratSinistres.isEmpty()) {
                            System.out.println("Aucun sinistre trouvé pour ce contrat.");
                        } else {
                            contratSinistres.forEach(System.out::println);
                        }
                        break;
                    case 8:
                        List<Sinistre> sortedSinistres = sinistreService.getSinistresSortedByCostDescending();
                        if (sortedSinistres.isEmpty()) {
                            System.out.println("Aucun sinistre trouvé.");
                        } else {
                            sortedSinistres.forEach(System.out::println);
                        }
                        break;
                    case 9:
                        System.out.print("ID du client : ");
                        int clientSinistreId = scanner.nextInt();
                        List<Sinistre> clientSinistres = sinistreService.getSinistresByClientId(clientSinistreId);
                        if (clientSinistres.isEmpty()) {
                            System.out.println("Aucun sinistre trouvé pour ce client.");
                        } else {
                            clientSinistres.forEach(System.out::println);
                        }
                        break;
                    case 10:
                        System.out.print("Date (YYYY-MM-DD HH:MM:SS) : ");
                        String dateStr = scanner.nextLine();
                        List<Sinistre> sinistresBeforeDate = sinistreService.getSinistresBeforeDate(LocalDateTime.parse(dateStr.replace(" ", "T")));
                        if (sinistresBeforeDate.isEmpty()) {
                            System.out.println("Aucun sinistre trouvé avant cette date.");
                        } else {
                            sinistresBeforeDate.forEach(System.out::println);
                        }
                        break;
                    case 11:
                        System.out.print("Montant minimum : ");
                        double minCost = scanner.nextDouble();
                        List<Sinistre> sinistresAboveCost = sinistreService.getSinistresAboveCost(minCost);
                        if (sinistresAboveCost.isEmpty()) {
                            System.out.println("Aucun sinistre trouvé avec un coût supérieur à ce montant.");
                        } else {
                            sinistresAboveCost.forEach(System.out::println);
                        }
                        break;
                    case 12:
                        return;
                    default:
                        System.out.println("Choix invalide.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur : " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }
    }
}