package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.controlador.MenuController;
import org.example.controlador.service.VentaService;
import org.example.controlador.service.VentaServiceImpl;
import org.example.vista.ConsoleVistaMenu;
import org.example.vista.VistaMenu;
import org.example.vista.VistaMenuJavaFX;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {
    private MenuController menuController;
    private VistaMenu vista;
    private VentaService ventaService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        vista = new ConsoleVistaMenu();
        ventaService = new VentaServiceImpl();
        menuController = new MenuController(vista,ventaService);
        menuController.start();
    }
}
