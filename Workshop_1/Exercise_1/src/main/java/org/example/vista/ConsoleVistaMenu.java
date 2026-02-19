package org.example.vista;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleVistaMenu implements VistaMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final Map<Integer, Runnable> bindings = new LinkedHashMap<>();
    private final Map<Integer, String> menuLabels = new LinkedHashMap<>();

    @Override
    public void renderMenu(Map<Integer, String> labels) {
        menuLabels.clear();
        menuLabels.putAll(labels);
    }

    @Override
    public void bindOption(int option, Runnable action) {
        bindings.put(option, action);
    }

    @Override
    public void showMessage(String title, Object message) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("  " + title);
        System.out.println("══════════════════════════════════════");
        System.out.println("  " + message);
        System.out.println("╚══════════════════════════════════════╝\n");
    }

    @Override
    public void show() {
        boolean running = true;
        while (running) {
            printMenu();
            int opcion = leerOpcion();

            Runnable action = bindings.get(opcion);
            if (action != null) {
                action.run();
            } else {
                System.out.println("Opción no válida, intenta de nuevo.\n");
            }

            // Si la última opción es "Salir" y fue seleccionada, salimos
            int lastKey = menuLabels.keySet().stream()
                    .mapToInt(Integer::intValue)
                    .max()
                    .orElse(-1);
            if (opcion == lastKey) running = false;
        }
    }

    @Override
    public void close() {
        System.out.println("\n  Hasta luego 👋\n");
        scanner.close();
    }

    private void printMenu() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("        Panel de Estadísticas          ");
        System.out.println("  Selecciona una opción para continuar ");
        System.out.println("══════════════════════════════════════");
        menuLabels.forEach((key, label) ->
                System.out.println("  [" + key + "] " + label));
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("  → Opción: ");
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}