package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.controlador.MenuContoller;
import org.example.controlador.services.EmpleadoService;
import org.example.controlador.services.EmpleadoServiceImpl;
import org.example.repository.EmpleadoRepository;
import org.example.repository.EmpleadoRepositoryProperties;
import org.example.vista.ConsoleVistaMenu;
import org.example.vista.VistaMenu;
import org.example.vista.VistaMenuJavaFX;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {
    private MenuContoller menuContoller;
    private VistaMenu vista;
    private EmpleadoService empleadoService;
    private EmpleadoRepository empleadoRepository;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        vista = new ConsoleVistaMenu();
        empleadoRepository = new EmpleadoRepositoryProperties();
        empleadoService = new EmpleadoServiceImpl(empleadoRepository);

        menuContoller = new MenuContoller(empleadoService, vista);

        menuContoller.start();
    }
}
