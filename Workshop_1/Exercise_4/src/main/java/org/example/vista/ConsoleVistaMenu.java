package org.example.vista;

import org.example.controlador.DTO.EmpleadoDTO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleVistaMenu implements VistaMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final Map<Integer, Runnable> actions = new LinkedHashMap<>();
    private final Map<Integer, String> menuLabels = new LinkedHashMap<>();

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
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("  💼 " + title);
        System.out.println("══════════════════════════════════════════════════════════════════");

        if (message instanceof List<?> lista && !lista.isEmpty() && lista.get(0) instanceof EmpleadoDTO) {
            @SuppressWarnings("unchecked")
            List<EmpleadoDTO> empleados = (List<EmpleadoDTO>) lista;

            System.out.printf("  %-10s %-20s %-15s %-12s %-12s %-15s%n",
                    "Código", "Nombre", "Devengado", "Retención", "Subsidio", "Total a Pagar");
            System.out.println("  " + "─".repeat(86));

            empleados.forEach(e -> System.out.printf(
                    "  %-10s %-20s %-15s %-12s %-12s %-15s%n",
                    e.getCodigo(),
                    e.getNombre(),
                    String.format("$ %,.2f", e.getDevengado()),
                    String.format("%.2f %%", e.getRetencion()),
                    "$ " + e.getSubsidio(),
                    String.format("$ %,.2f", e.getTotalAPagar())
            ));

            System.out.println("\n  " + empleados.size() + " empleado(s) encontrado(s)");
        } else {
            System.out.println("  " + message);
        }

        System.out.println("╚══════════════════════════════════════════════════════════════════╝\n");
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
        System.out.println("  💼  Sistema de Liquidación de Empleados      ");
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