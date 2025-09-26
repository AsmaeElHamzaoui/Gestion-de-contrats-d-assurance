package com.assurance.view;

import com.assurance.model.Client;
import com.assurance.service.ClientService;

import java.util.List;
import java.util.Scanner;

public class ClientView {
    private ClientService clientService;
    private Scanner scanner;

    public ClientView(ClientService clientService, Scanner scanner) {
        this.clientService = clientService;
        this.scanner = scanner;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== Gestion des Clients ===");
            System.out.println("1. Ajouter un client");
            System.out.println("2. Afficher un client par ID");
            System.out.println("3. Afficher tous les clients");
            System.out.println("4. Modifier un client");
            System.out.println("5. Supprimer un client");
            System.out.println("6. Retour");
            System.out.print("Choix : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Nom : ");
                    String nom = scanner.nextLine();
                    System.out.print("Prénom : ");
                    String prenom = scanner.nextLine();
                    System.out.print("Email : ");
                    String email = scanner.nextLine();
                    System.out.print("ID du conseiller : ");
                    int conseillerId = scanner.nextInt();
                    Client client = new Client(0, nom, prenom, email, conseillerId);
                    try {
                        client = clientService.addClient(client);
                        System.out.println("Client ajouté avec succès. ID: " + client.getId());
                    } catch (Exception e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("ID du client : ");
                    int clientId = scanner.nextInt();
                    try {
                        Client foundClient = clientService.getClientById(clientId);
                        if (foundClient != null) {
                            System.out.println(foundClient);
                        } else {
                            System.out.println("Client non trouvé.");
                        }
                    } catch (Exception e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        List<Client> clients = clientService.getAllClientsWithConseillers();
                        for (Client c : clients) {
                            System.out.println(c);
                        }
                    } catch (Exception e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("ID du client à modifier : ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        Client existingClient = clientService.getClientById(updateId);
                        if (existingClient != null) {
                            System.out.print("Nouveau nom (actuel: " + existingClient.getNom() + ") : ");
                            String newNom = scanner.nextLine();
                            if (newNom.isEmpty()) newNom = existingClient.getNom();
                            System.out.print("Nouveau prénom (actuel: " + existingClient.getPrenom() + ") : ");
                            String newPrenom = scanner.nextLine();
                            if (newPrenom.isEmpty()) newPrenom = existingClient.getPrenom();
                            System.out.print("Nouvel email (actuel: " + existingClient.getEmail() + ") : ");
                            String newEmail = scanner.nextLine();
                            if (newEmail.isEmpty()) newEmail = existingClient.getEmail();
                            System.out.print("Nouvel ID du conseiller (actuel: " + existingClient.getConseillerId() + ") : ");
                            String conseillerIdInput = scanner.nextLine();
                            int newConseillerId = conseillerIdInput.isEmpty() ? existingClient.getConseillerId() : Integer.parseInt(conseillerIdInput);
                            Client updatedClient = new Client(updateId, newNom, newPrenom, newEmail, newConseillerId);
                            clientService.updateClient(updatedClient);
                            System.out.println("Client modifié avec succès.");
                        } else {
                            System.out.println("Client non trouvé.");
                        }
                    } catch (Exception e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.print("ID du client à supprimer : ");
                    int deleteId = scanner.nextInt();
                    try {
                        clientService.deleteClient(deleteId);
                        System.out.println("Client supprimé (si existant).");
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