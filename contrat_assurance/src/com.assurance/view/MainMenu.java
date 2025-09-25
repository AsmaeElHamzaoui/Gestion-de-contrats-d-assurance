package com.assurance.view;

import com.assurance.service.ClientService;
import com.assurance.service.ConseillerService;
import com.assurance.service.ContratService;
import com.assurance.service.SinistreService;

import java.util.Scanner;

public class MainMenu {
    private ClientService clientService;
    private ConseillerService conseillerService;
    private ContratService contratService;
    private SinistreService sinistreService;
    private Scanner scanner;

    public MainMenu(ClientService clientService,
                    ConseillerService conseillerService,
                    ContratService contratService,
                    SinistreService sinistreService) {
        this.clientService = clientService;
        this.conseillerService = conseillerService;
        this.contratService = contratService;
        this.sinistreService = sinistreService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Gérer les conseillers");
            System.out.println("2. Gérer les clients");
            System.out.println("3. Gérer les contrats");
            System.out.println("4. Gérer les sinistres");
            System.out.println("5. Quitter");
            System.out.print("Choix : ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // pour consommer le retour à la ligne

            switch (choice) {
                case 1:
                    new ConseillerView(conseillerService, clientService, scanner).showMenu(); // ✅ corrigé
                    break;
                case 2:
                    new ClientView(clientService, scanner).showMenu();
                    break;
                case 3:
                    new ContratView(contratService, scanner).showMenu();
                    break;
                case 4:
                    new SinistreView(sinistreService, scanner).showMenu();
                    break;
                case 5:
                    System.out.println("Au revoir!");
                    return;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }
}
