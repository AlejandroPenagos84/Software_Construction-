package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.controlador.MenuContoller;
import org.example.controlador.services.GranjeroService;
import org.example.controlador.services.GranjeroServiceImpl;
import org.example.repositorios.GranjeroRepository;
import org.example.repositorios.GranjeroRepositoryProperties;
import org.example.vista.ConsoleVistaMenu;
import org.example.vista.VistaMenu;
import org.example.vista.VistaMenuJavaFX;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main  extends Application {
    private MenuContoller menuController;
    private VistaMenu vista;
    private GranjeroService granjeroService;
    private GranjeroRepository granjeroRepository;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        vista = new ConsoleVistaMenu();
        granjeroRepository = new GranjeroRepositoryProperties();
        granjeroService= new GranjeroServiceImpl(granjeroRepository);

        menuController = new MenuContoller(granjeroService, vista);

        menuController.start();
    }
}
