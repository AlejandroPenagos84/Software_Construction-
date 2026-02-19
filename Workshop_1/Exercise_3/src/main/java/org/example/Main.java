package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.controlador.MenuController;
import org.example.controlador.service.EmpleadoService;
import org.example.controlador.service.EmpleadoServiceImpl;
import org.example.vista.VistaMenu;
import org.example.vista.VistaMenuJavaFX;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {
    private MenuController menuController;
    private VistaMenu vista;
    private EmpleadoService empleadoService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        vista = new VistaMenuJavaFX(stage);
        empleadoService = new EmpleadoServiceImpl();
        menuController = new MenuController(empleadoService,vista);
        menuController.start();
    }
}

