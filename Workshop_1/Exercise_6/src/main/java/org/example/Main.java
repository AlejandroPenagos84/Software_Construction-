package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.controlador.MenuController;
import org.example.controlador.service.CandidatoService;
import org.example.controlador.service.CandidatoServiceImpl;
import org.example.vista.ConsoleVistaMenu;
import org.example.vista.VistaMenu;
import org.example.vista.VistaMenuJavaFX;

public class Main extends Application {
    private MenuController menuController;
    private VistaMenu vista;
    private CandidatoService candidatoService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        vista = new ConsoleVistaMenu();
        candidatoService = new CandidatoServiceImpl();

       menuController = new MenuController(candidatoService, vista);

        menuController.start();
    }
}
