package org.example.vista;

import org.example.controlador.DTO.ArticuloRequestDTO;

import java.util.*;

public class ConsoleVistaMenu implements VistaMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final Map<Integer, Runnable> actions = new LinkedHashMap<>();
    private final Map<Integer, String> menuLabels = new LinkedHashMap<>();
    private String[] campos;
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
        System.out.println("  🛒 " + title);
        System.out.println("══════════════════════════════════════════════");
        System.out.println("  " + message);
        System.out.println("╚══════════════════════════════════════════════╝\n");
    }

    @Override
    public void crearFormulario(String titulo, Runnable onConfirmar, String... campos) {
        this.campos = campos;
        datosFormulario.clear();

        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("  📝 " + titulo);
        System.out.println("  Completa los campos por cada artículo");
        System.out.println("══════════════════════════════════════════════╝\n");

        for (String campo : campos) {
            System.out.println("  ── " + campo + " ──────────────────────────");

            // Nombre
            System.out.print("  Nombre   : ");
            datosFormulario.put(campo + "_nombre", scanner.nextLine().trim());

            // Precio — solo decimales
            while (true) {
                System.out.print("  Precio   : ");
                String precio = scanner.nextLine().trim();
                if (precio.matches("\\d+(\\.\\d+)?")) {
                    datosFormulario.put(campo + "_precio", precio);
                    break;
                }
                System.out.println("  ⚠ Solo números (ej: 5000 o 5000.50)\n");
            }

            // Cantidad — solo enteros
            while (true) {
                System.out.print("  Cantidad : ");
                String cantidad = scanner.nextLine().trim();
                if (cantidad.matches("\\d+")) {
                    datosFormulario.put(campo + "_cantidad", cantidad);
                    break;
                }
                System.out.println("  ⚠ Solo números enteros.\n");
            }

            System.out.println();
        }

        // Resumen y confirmación
        System.out.println("  ¿Confirmar datos? [S/N]");
        printResumen();
        System.out.print("  → ");
        String confirmar = scanner.nextLine().trim();

        if (confirmar.equalsIgnoreCase("S")) {
            onConfirmar.run();
        } else {
            System.out.println("\n  ❌ Formulario cancelado.\n");
            datosFormulario.clear();
        }
    }

    private void printResumen() {
        System.out.println("  ┌──────────────────────────────────────────────────┐");
        System.out.printf("  │  %-15s %-20s %-10s %-8s │%n", "Artículo", "Nombre", "Precio", "Cantidad");
        System.out.println("  │  " + "─".repeat(56) + "  │");
        for (String campo : campos) {
            System.out.printf("  │  %-15s %-20s %-10s %-8s │%n",
                    campo,
                    datosFormulario.get(campo + "_nombre"),
                    datosFormulario.get(campo + "_precio"),
                    datosFormulario.get(campo + "_cantidad")
            );
        }
        System.out.println("  └──────────────────────────────────────────────────┘");
    }

    @Override
    public List<ArticuloRequestDTO> obtenerDatos() {
        List<ArticuloRequestDTO> datos = new ArrayList<>();
        for (String campo : campos) {
            datos.add(new ArticuloRequestDTO(
                    datosFormulario.get(campo + "_nombre"),
                    datosFormulario.get(campo + "_precio"),
                    datosFormulario.get(campo + "_cantidad")
            ));
        }
        return datos;
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
        System.out.println("  🛒  Sistema Comisionista                     ");
        System.out.println("      Selecciona una opción para continuar     ");
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