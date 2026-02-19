package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.controlador.MenuContoller;
import org.example.controlador.services.ClienteService;
import org.example.controlador.services.ClienteServiceImpl;
import org.example.repositorios.ClienteRepository;
import org.example.repositorios.ClienteRepositoryProperties;
import org.example.vista.VistaMenu;
import org.example.vista.VistaMenuConsola;
import org.example.vista.VistaMenuJavaFX;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {
    private MenuContoller menuContoller;
    private VistaMenu vista;
    private ClienteService clienteService;
    private ClienteRepository clienteRepository;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        vista = new VistaMenuJavaFX(stage);
        clienteRepository = new ClienteRepositoryProperties();
        clienteService = new ClienteServiceImpl(clienteRepository);

        menuContoller = new MenuContoller(clienteService, vista);

        menuContoller.start();
    }
}
