package com.assurance.view;

import com.assurance.model.Contrat;
import com.assurance.service.ContratService;
import com.assurance.enums.TypeContrat;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ContratView {
    private ContratService contratService;
    private Scanner scanner;

    public ContratView(ContratService contratService, Scanner scanner) {
        this.contratService = contratService;
        this.scanner = scanner;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== Gestion des Contrats ===");
            System.out.println("1. Ajouter un contrat");
            System.out.println("2. Afficher un contrat par ID");
            System.out.println("3. Afficher tous les contrats");
            System.out.println("4. Modifier un contrat");
            System.out.println("5. Supprimer un contrat");
            System.out.println("6. Afficher les contrats d'un client par ID");
            System.out.println("7. Retour");
            System.out.print("Choix : ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Type de contrat (AUTOMOBILE, MAISON, MALADIE) : ");
                        String type = scanner.nextLine().toUpperCase();
                        System.out.print("Date de début (YYYY-MM-DD) : ");
                        String dateDebut = scanner.nextLine();
                        System.out.print("Date de fin (YYYY-MM-DD) : ");
                        String dateFin = scanner.nextLine();
                        System.out.print("ID du client : ");
                        int clientId = scanner.nextInt();
                        Contrat contrat = new Contrat(0, TypeContrat.valueOf(type), LocalDate.parse(dateDebut), LocalDate.parse(dateFin), clientId);
                        contrat = contratService.addContrat(contrat);
                        System.out.println("Contrat ajouté avec succès. ID: " + contrat.getId());
                        break;
                    case 2:
                        System.out.print("ID du contrat : ");
                        int contratId = scanner.nextInt();
                        Contrat foundContrat = contratService.getContratById(contratId);
                        if (foundContrat != null) {
                            System.out.println(foundContrat);
                        } else {
                            System.out.println("Contrat non trouvé.");
                        }
                        break;
                    case 3:
                        List<Contrat> contrats = contratService.getAllContratsWithClients();
                        for (Contrat c : contrats) {
                            System.out.println(c);
                        }
                        break;
                    case 4:
                        System.out.print("ID du contrat à modifier : ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();
                        Contrat existingContrat = contratService.getContratById(updateId);
                        if (existingContrat != null) {
                            System.out.print("Nouveau type de contrat (actuel: " + existingContrat.getTypeContrat() + ") : ");
                            String newType = scanner.nextLine().toUpperCase();
                            if (newType.isEmpty()) newType = existingContrat.getTypeContrat().name();
                            System.out.print("Nouvelle date de début (actuel: " + existingContrat.getDateDebut() + ") : ");
                            String newDateDebut = scanner.nextLine();
                            if (newDateDebut.isEmpty()) newDateDebut = existingContrat.getDateDebut().toString();
                            System.out.print("Nouvelle date de fin (actuel: " + existingContrat.getDateFin() + ") : ");
                            String newDateFin = scanner.nextLine();
                            if (newDateFin.isEmpty()) newDateFin = existingContrat.getDateFin().toString();
                            System.out.print("Nouvel ID du client (actuel: " + existingContrat.getClientId() + ") : ");
                            String clientIdInput = scanner.nextLine();
                            int newClientId = clientIdInput.isEmpty() ? existingContrat.getClientId() : Integer.parseInt(clientIdInput);
                            Contrat updatedContrat = new Contrat(updateId, TypeContrat.valueOf(newType),
                                    LocalDate.parse(newDateDebut), LocalDate.parse(newDateFin), newClientId);
                            contratService.updateContrat(updatedContrat);
                            System.out.println("Contrat modifié avec succès.");
                        } else {
                            System.out.println("Contrat non trouvé.");
                        }
                        break;
                    case 5:
                        System.out.print("ID du contrat à supprimer : ");
                        int deleteId = scanner.nextInt();
                        contratService.deleteContrat(deleteId);
                        System.out.println("Contrat supprimé (si existant).");
                        break;
                    case 6:
                        System.out.print("ID du client : ");
                        int clientContratId = scanner.nextInt();
                        List<Contrat> clientContrats = contratService.getContratsByClientId(clientContratId);
                        if (clientContrats.isEmpty()) {
                            System.out.println("Aucun contrat trouvé pour ce client.");
                        } else {
                            for (Contrat c : clientContrats) {
                                System.out.println(c);
                            }
                        }
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Choix invalide.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur : " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }
    }
}