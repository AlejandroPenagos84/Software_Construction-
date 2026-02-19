package org.example.vista;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleVistaMenu implements VistaMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final Map<Integer, Runnable> actions = new LinkedHashMap<>();
    private final Map<Integer, String> menuLabels = new LinkedHashMap<>();
    private final Map<String, String> datosFormulario = new LinkedHashMap<>();

    @Override
    public void renderMenu(Map<Integer, String> labels) {
        menuLabels.clear();
        menuLabels.putAll(labels);
    }

    @Override
    public void bindOption(int option, Runnable action) {
        actions.put(option, action);
    }

    @Override
    public void showMessage(String title, Object message) {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("  🗳️  " + title);
        System.out.println("══════════════════════════════════════════════");
        System.out.println("  " + message);
        System.out.println("╚══════════════════════════════════════════════╝\n");
    }

    @Override
    public void crearFormulario(String titulo, Runnable onConfirmar, String... campos) {
        datosFormulario.clear();

        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("  🗳️  " + titulo);
        System.out.println("  Ingresa los votos por cada candidato");
        System.out.println("══════════════════════════════════════════════╝\n");

        for (String campo : campos) {
            while (true) {
                System.out.print("  Votos por " + campo + " : ");
                String valor = scanner.nextLine().trim();
                if (valor.matches("\\d+")) {
                    datosFormulario.put(campo, valor);
                    break;
                }
                System.out.println("  ⚠ Solo se permiten números enteros.\n");
            }
        }

        // Resumen y confirmación
        System.out.println("\n  ¿Confirmar datos? [S/N]");
        printResumen(campos);
        System.out.print("  → ");
        String confirmar = scanner.nextLine().trim();

        if (confirmar.equalsIgnoreCase("S")) {
            onConfirmar.run();
        } else {
            System.out.println("\n  ❌ Formulario cancelado.\n");
            datosFormulario.clear();
        }
    }

    private void printResumen(String[] campos) {
        System.out.println("  ┌─────────────────────────────────┐");
        for (String campo : campos) {
            System.out.printf("  │  Votos por %-10s : %-6s │%n",
                    campo, datosFormulario.get(campo));
        }
        System.out.println("  └─────────────────────────────────┘");
    }

    @Override
    public Map<String, String> obtenerDatos() {
        return new LinkedHashMap<>(datosFormulario);
    }

    @Override
    public void show() {
        boolean running = true;
        while (running) {
            printMenu();
            int opcion = leerOpcion();

            Runnable action = actions.get(opcion);
            if (action != null) {
                action.run();
            } else {
                System.out.println("  ⚠ Opción no válida, intenta de nuevo.\n");
            }

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

    // ── Helpers ──────────────────────────────────────────────────────────────

    private void printMenu() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("  🗳️   Sistema Electoral                        ");
        System.out.println("       Selecciona una opción para continuar    ");
        System.out.println("══════════════════════════════════════════════");
        menuLabels.forEach((key, label) ->
                System.out.println("  [" + key + "] " + label));
        System.out.println("╚══════════════════════════════════════════════╝");
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