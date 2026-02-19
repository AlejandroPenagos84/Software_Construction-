package org.example.vista;

import org.example.controlador.DTO.GranjeroDTO;

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
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("  🌾 " + title);
        System.out.println("══════════════════════════════════════════════");

        if (message instanceof List<?> lista && !lista.isEmpty() && lista.get(0) instanceof GranjeroDTO) {
            @SuppressWarnings("unchecked")
            List<GranjeroDTO> granjeros = (List<GranjeroDTO>) lista;

            System.out.printf("  %-30s %s%n", "Nombre del Granjero", "Valor a Pagar ($)");
            System.out.println("  " + "─".repeat(48));
            granjeros.forEach(g ->
                    System.out.printf("  %-30s $ %.2f%n", g.getNombre(), g.getValorAPagar())
            );
            System.out.println("\n  " + granjeros.size() + " granjero(s) procesado(s)");
        } else {
            System.out.println("  " + message);
        }

        System.out.println("╚══════════════════════════════════════════════╝\n");
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
        System.out.println("  🌾  Sistema de Fumigación de Cosechas        ");
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