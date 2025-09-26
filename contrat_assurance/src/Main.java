package com.assurance;

import com.assurance.view.MainMenu;
import com.assurance.service.ClientService;
import com.assurance.service.ConseillerService;
import com.assurance.service.ContratService;
import com.assurance.service.SinistreService;

public class Main {
    public static void main(String[] args) {

        // Instanciation des services
        ConseillerService conseillerService = new ConseillerService();
        ClientService clientService = new ClientService();
        ContratService contratService = new ContratService();
        SinistreService sinistreService = new SinistreService();

        // Passer les services au menu
        MainMenu menu = new MainMenu(clientService, conseillerService, contratService, sinistreService);
        menu.start();
    }
}
