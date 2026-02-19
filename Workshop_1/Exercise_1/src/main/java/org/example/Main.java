package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.controlador.MenuController;
import org.example.controlador.service.EstudianteService;
import org.example.controlador.service.EstudianteServiceImpl;
import org.example.repository.EstudianteRepository;
import org.example.repository.EstudiantesProperties;
import org.example.vista.ConsoleVistaMenu;
import org.example.vista.JavaFXVistaMenu;
import org.example.vista.VistaMenu;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main  extends Application {
    private MenuController menuController;
    private VistaMenu vista;
    private EstudianteService estudianteService;
    private EstudianteRepository estudianteRepository;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        vista = new ConsoleVistaMenu();
        estudianteRepository= new EstudiantesProperties();
        estudianteService = new EstudianteServiceImpl(estudianteRepository);

        menuController = new MenuController(estudianteService, vista);

        menuController.start();
    }
}
