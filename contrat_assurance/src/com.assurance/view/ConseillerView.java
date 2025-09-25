package com.assurance.view;

import com.assurance.model.Client;
import com.assurance.model.Conseiller;
import com.assurance.service.ClientService;
import com.assurance.service.ConseillerService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ConseillerView {
    private ConseillerService conseillerService;
    private ClientService clientService;
    private Scanner scanner;

    public ConseillerView(ConseillerService conseillerService, ClientService clientService, Scanner scanner) {
        this.conseillerService = conseillerService;
        this.clientService = clientService;
        this.scanner = scanner;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== Gestion des Conseillers ===");
            System.out.println("1. Ajouter un conseiller");
            System.out.println("2. Afficher un conseiller par ID");
            System.out.println("3. Afficher tous les conseillers");
            System.out.println("4. Modifier un conseiller");
            System.out.println("5. Supprimer un conseiller");
            System.out.println("6. Afficher les clients d'un conseiller par ID");
            System.out.println("7. Retour");
            System.out.print("Choix : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Nom : ");
                        String nom = scanner.nextLine();
                        System.out.print("Prénom : ");
                        String prenom = scanner.nextLine();
                        System.out.print("Email : ");
                        String email = scanner.nextLine();
                        Conseiller conseiller = new Conseiller(0, nom, prenom, email);
                        conseiller = conseillerService.addConseiller(conseiller);
                        System.out.println("Conseiller ajouté avec succès. ID: " + conseiller.getId());
                        break;
                    case 2:
                        System.out.print("ID du conseiller : ");
                        int conseillerId = scanner.nextInt();
                        Conseiller foundConseiller = conseillerService.getConseillerById(conseillerId);
                        if (foundConseiller != null) {
                            System.out.println(foundConseiller);
                        } else {
                            System.out.println("Conseiller non trouvé.");
                        }
                        break;
                    case 3:
                        List<Conseiller> conseillers = conseillerService.getAllConseillers();
                        for (Conseiller c : conseillers) {
                            System.out.println(c);
                        }
                        break;
                    case 4:
                        System.out.print("ID du conseiller à modifier : ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();
                        Conseiller existingConseiller = conseillerService.getConseillerById(updateId);
                        if (existingConseiller != null) {
                            System.out.print("Nouveau nom (actuel: " + existingConseiller.getNom() + ") : ");
                            String newNom = scanner.nextLine();
                            if (newNom.isEmpty()) newNom = existingConseiller.getNom();
                            System.out.print("Nouveau prénom (actuel: " + existingConseiller.getPrenom() + ") : ");
                            String newPrenom = scanner.nextLine();
                            if (newPrenom.isEmpty()) newPrenom = existingConseiller.getPrenom();
                            System.out.print("Nouvel email (actuel: " + existingConseiller.getEmail() + ") : ");
                            String newEmail = scanner.nextLine();
                            if (newEmail.isEmpty()) newEmail = existingConseiller.getEmail();
                            Conseiller updatedConseiller = new Conseiller(updateId, newNom, newPrenom, newEmail);
                            conseillerService.updateConseiller(updatedConseiller);
                            System.out.println("Conseiller modifié avec succès.");
                        } else {
                            System.out.println("Conseiller non trouvé.");
                        }
                        break;
                    case 5:
                        System.out.print("ID du conseiller à supprimer : ");
                        int deleteId = scanner.nextInt();
                        conseillerService.deleteConseiller(deleteId);
                        System.out.println("Conseiller supprimé (si existant).");
                        break;
                    case 6:
                        System.out.print("ID du conseiller : ");
                        int clientsConseillerId = scanner.nextInt();
                        List<Client> clients = clientService.getClientsByConseillerId(clientsConseillerId);
                        if (clients.isEmpty()) {
                            System.out.println("Aucun client trouvé pour ce conseiller.");
                        } else {
                            for (Client client : clients) {
                                System.out.println(client);
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