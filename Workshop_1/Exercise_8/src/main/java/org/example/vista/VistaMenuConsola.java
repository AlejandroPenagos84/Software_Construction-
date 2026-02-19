package org.example.vista;

import org.example.controlador.DTO.ClienteDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class VistaMenuConsola implements VistaMenu {

    private final Scanner scanner;
    private Map<Integer, Runnable> actions;
    private Map<Integer, String> labels;
    private boolean running;

    public VistaMenuConsola() {
        this.scanner = new Scanner(System.in);
        this.actions = new HashMap<>();
        this.labels = new HashMap<>();
        this.running = false;
    }

    @Override
    public void renderMenu(Map<Integer, String> labels) {
        this.labels = labels;
    }

    @Override
    public void bindOption(int option, Runnable action) {
        this.actions.put(option, action);
    }

    @Override
    public void showMessage(String title, Object message) {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("  " + title.toUpperCase());
        System.out.println("═".repeat(60));

        if (message instanceof List) {
            List<?> lista = (List<?>) message;

            if (lista.isEmpty()) {
                System.out.println("\n  ⚠ No se encontraron resultados.\n");
            } else if (lista.get(0) instanceof ClienteDTO) {
                @SuppressWarnings("unchecked")
                List<ClienteDTO> clientes = (List<ClienteDTO>) lista;
                mostrarTablaClientes(clientes);
            } else {
                lista.forEach(item -> System.out.println("  • " + item));
            }
        } else {
            System.out.println("\n  " + message.toString());
        }

        System.out.println("═".repeat(60) + "\n");
        esperarEnter();
    }

    private void mostrarTablaClientes(List<ClienteDTO> clientes) {
        // Encabezados
        System.out.println();
        System.out.printf("  %-20s %-6s %-8s %-8s %-15s %-15s%n",
                "NOMBRE", "EDAD", "ALTURA", "PESO", "OJOS", "CABELLO");
        System.out.println("  " + "-".repeat(80));

        // Datos
        for (ClienteDTO cliente : clientes) {
            System.out.printf("  %-20s %-6d %-8.2f %-8.2f %-15s %-15s%n",
                    cliente.getNombre(),
                    cliente.getEdad(),
                    cliente.getAltura(),
                    cliente.getPeso(),
                    cliente.getColorOjos(),
                    cliente.getColorCabello()
            );
        }

        System.out.println("\n  Total de registros: " + clientes.size());
        System.out.println();
    }

    @Override
    public void show() {
        this.running = true;

        while (running) {
            mostrarMenuPrincipal();
            System.out.print("\nSeleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());

                Runnable action = actions.get(opcion);
                if (action != null) {
                    System.out.println(); // Línea en blanco
                    action.run();
                } else {
                    System.out.println("\n❌ Opción inválida. Por favor intente de nuevo.\n");
                    esperarEnter();
                }

            } catch (NumberFormatException e) {
                System.out.println("\n❌ Por favor ingrese un número válido.\n");
                esperarEnter();
            }
        }
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          SISTEMA DE REPORTES DE CLIENTES                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        labels.forEach((opcion, label) -> {
            System.out.printf("[%d] %s%n", opcion, label);
        });

        System.out.println("════════════════════════════════════════════════════════════");
    }

    @Override
    public void close() {
        System.out.println("\n👋 ¡Gracias por usar el sistema!\n");
        scanner.close();
        this.running = false;
    }

    private void esperarEnter() {
        System.out.print("Presione ENTER para continuar...");
        scanner.nextLine();
    }
}