package org.example.vista;

import org.example.controlador.DTO.EmpleadoRequestDTO;

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
        System.out.println("  👤 " + title);
        System.out.println("══════════════════════════════════════════════");
        System.out.println("  " + message);
        System.out.println("╚══════════════════════════════════════════════╝\n");
    }

    @Override
    public void crearFormulario(String titulo, Runnable onConfirmar) {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("  📝 " + titulo);
        System.out.println("  Completa los campos y confirma");
        System.out.println("══════════════════════════════════════════════╝\n");

        datosFormulario.clear();

        System.out.print("  Código        : ");
        datosFormulario.put("Código", scanner.nextLine().trim());

        System.out.print("  Nombre        : ");
        datosFormulario.put("Nombre", scanner.nextLine().trim());

        // Sexo con select
        String sexo = "";
        while (!sexo.equals("1") && !sexo.equals("2")) {
            System.out.println("  Sexo          : ");
            System.out.println("    [1] Hombre");
            System.out.println("    [2] Mujer");
            System.out.print("  → Opción: ");
            sexo = scanner.nextLine().trim();
            if (!sexo.equals("1") && !sexo.equals("2"))
                System.out.println("  ⚠ Opción no válida, ingresa 1 o 2.\n");
        }
        datosFormulario.put("Sexo", sexo.equals("1") ? "Hombre" : "Mujer");

        // Número de horas — solo enteros
        while (true) {
            System.out.print("  Núm. de Horas : ");
            String horas = scanner.nextLine().trim();
            if (horas.matches("\\d+")) {
                datosFormulario.put("Número de Horas", horas);
                break;
            }
            System.out.println("  ⚠ Solo se permiten números enteros.\n");
        }

        // Tarifa por hora — números decimales
        while (true) {
            System.out.print("  Tarifa/Hora   : ");
            String tarifa = scanner.nextLine().trim();
            if (tarifa.matches("\\d+(\\.\\d+)?")) {
                datosFormulario.put("Tarifa por Hora", tarifa);
                break;
            }
            System.out.println("  ⚠ Solo se permiten números (ej: 15000 o 15000.50).\n");
        }

        // Confirmación
        System.out.println("\n  ¿Confirmar datos? [S/N]: ");
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
        System.out.println("  ┌─────────────────────────────────────┐");
        datosFormulario.forEach((k, v) ->
                System.out.printf("  │  %-20s : %-12s │%n", k, v));
        System.out.println("  └─────────────────────────────────────┘");
    }

    // Almacén temporal de los datos del formulario

    @Override
    public EmpleadoRequestDTO obtenerDatos() {
        return new EmpleadoRequestDTO(
                datosFormulario.get("Código"),
                datosFormulario.get("Nombre"),
                datosFormulario.get("Sexo"),
                Integer.parseInt(datosFormulario.get("Número de Horas")),
                Double.parseDouble(datosFormulario.get("Tarifa por Hora"))
        );
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
        System.out.println("  👤  Sistema de Empleados                     ");
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