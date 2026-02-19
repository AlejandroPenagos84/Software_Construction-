package org.example.vista;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedHashMap;
import java.util.Map;

public class JavaFXVistaMenu implements VistaMenu {

    private final Stage stage;
    private final Map<Integer, Runnable> bindings = new LinkedHashMap<>();
    private final Map<Integer, String> menuLabels = new LinkedHashMap<>();
    private VBox buttonContainer;

    // ── Paleta ──────────────────────────────────────────────────────────────
    private static final String BG_DARK      = "#0D0F1A";
    private static final String BG_CARD      = "#141627";
    private static final String ACCENT_1     = "#6C63FF"; // violeta
    private static final String ACCENT_2     = "#FF6584"; // rosa
    private static final String ACCENT_EXIT  = "#FF4757"; // rojo salir
    private static final String TEXT_PRIMARY = "#EAEAEA";
    private static final String TEXT_SUB     = "#7B7FA6";

    // Colores rotativos para los botones (excepto el último que es "Salir")
    private static final String[] BUTTON_COLORS = {
            "#6C63FF", "#48CFAD", "#FC5C7D", "#F7971E", "#43CEA2"
    };

    public JavaFXVistaMenu(Stage stage) {
        this.stage = stage;
    }

    // ────────────────────────────────────────────────────────────────────────
    @Override
    public void renderMenu(Map<Integer, String> labels) {
        this.menuLabels.putAll(labels);
    }

    @Override
    public void bindOption(int option, Runnable action) {
        bindings.put(option, action);
    }

    @Override
    public void showMessage(String title, Object message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(String.valueOf(message));

            // Estilizar el diálogo
            alert.getDialogPane().setStyle(
                    "-fx-background-color: " + BG_CARD + ";" +
                            "-fx-border-color: " + ACCENT_1 + ";" +
                            "-fx-border-width: 1.5px;" +
                            "-fx-border-radius: 12px;" +
                            "-fx-background-radius: 12px;"
            );
            alert.getDialogPane().lookup(".content.label").setStyle(
                    "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                            "-fx-font-size: 15px;" +
                            "-fx-font-family: 'Segoe UI';"
            );
            alert.showAndWait();
        });
    }

    @Override
    public void show() {
        Platform.runLater(() -> {
            // ── Root ────────────────────────────────────────────────────────
            StackPane root = new StackPane();
            root.setStyle("-fx-background-color: " + BG_DARK + ";");

            // Fondo con gradiente sutil
            Region bgGradient = new Region();
            bgGradient.setStyle(
                    "-fx-background-color: radial-gradient(center 30% 20%, radius 60%," +
                            " #1a1040 0%, " + BG_DARK + " 100%);"
            );
            root.getChildren().add(bgGradient);

            // ── Tarjeta central ─────────────────────────────────────────────
            VBox card = new VBox(16);
            card.setAlignment(Pos.CENTER);
            card.setPadding(new Insets(40, 50, 40, 50));
            card.setMaxWidth(480);
            card.setStyle(
                    "-fx-background-color: " + BG_CARD + ";" +
                            "-fx-background-radius: 20px;" +
                            "-fx-border-color: #2a2d4a;" +
                            "-fx-border-width: 1.5px;" +
                            "-fx-border-radius: 20px;"
            );

            // Sombra de la tarjeta
            DropShadow cardShadow = new DropShadow();
            cardShadow.setColor(Color.web("#6C63FF", 0.25));
            cardShadow.setRadius(40);
            cardShadow.setOffsetY(10);
            card.setEffect(cardShadow);

            // ── Header ──────────────────────────────────────────────────────
            Label icon = new Label("📊");
            icon.setStyle("-fx-font-size: 38px;");

            Label title = new Label("Panel de Estadísticas");
            title.setStyle(
                    "-fx-font-size: 22px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                            "-fx-font-family: 'Segoe UI Semibold', 'Segoe UI', sans-serif;"
            );

            Label subtitle = new Label("Selecciona una opción para continuar");
            subtitle.setStyle(
                    "-fx-font-size: 13px;" +
                            "-fx-text-fill: " + TEXT_SUB + ";" +
                            "-fx-font-family: 'Segoe UI', sans-serif;"
            );

            VBox header = new VBox(6, icon, title, subtitle);
            header.setAlignment(Pos.CENTER);
            header.setPadding(new Insets(0, 0, 12, 0));

            card.getChildren().add(header);

            // Separador decorativo
            Region sep = new Region();
            sep.setPrefHeight(1.5);
            sep.setMaxWidth(Double.MAX_VALUE);
            sep.setStyle("-fx-background-color: linear-gradient(to right, transparent, " + ACCENT_1 + ", transparent);");
            card.getChildren().add(sep);

            // ── Botones ─────────────────────────────────────────────────────
            buttonContainer = new VBox(10);
            buttonContainer.setAlignment(Pos.CENTER);

            int[] colorIdx = {0};
            int total = menuLabels.size();
            int[] i = {0};

            menuLabels.forEach((key, label) -> {
                boolean isLast = (i[0] == total - 1);
                String color = isLast ? ACCENT_EXIT : BUTTON_COLORS[colorIdx[0] % BUTTON_COLORS.length];

                Button btn = buildButton(label, color, isLast);

                Runnable action = bindings.get(key);
                if (action != null) {
                    btn.setOnAction(e -> {
                        animateClick(btn);
                        action.run();
                    });
                }

                // Animación de entrada escalonada
                btn.setOpacity(0);
                btn.setTranslateX(-20);
                FadeTransition ft = new FadeTransition(Duration.millis(350), btn);
                ft.setToValue(1);
                ft.setDelay(Duration.millis(80L * i[0]));

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), btn);
                tt.setToX(0);
                tt.setDelay(Duration.millis(80L * i[0]));

                ft.play();
                tt.play();

                buttonContainer.getChildren().add(btn);
                colorIdx[0]++;
                i[0]++;
            });

            card.getChildren().add(buttonContainer);

            // ── Footer ──────────────────────────────────────────────────────
            Label footer = new Label("Sistema de Estadísticas Estudiantiles");
            footer.setStyle(
                    "-fx-font-size: 11px;" +
                            "-fx-text-fill: #3d4065;" +
                            "-fx-font-family: 'Segoe UI', sans-serif;"
            );
            footer.setPadding(new Insets(10, 0, 0, 0));
            card.getChildren().add(footer);

            root.getChildren().add(card);

            // ── Escena ──────────────────────────────────────────────────────
            Scene scene = new Scene(root, 580, 680);
            stage.setScene(scene);
            stage.setTitle("Panel de Estadísticas");
            stage.setResizable(false);

            // Fade in general
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();

            stage.show();
        });
    }

    @Override
    public void close() {
        Platform.runLater(stage::close);
    }

    // ── Helpers ─────────────────────────────────────────────────────────────

    private Button buildButton(String label, String accentColor, boolean isExit) {
        Button btn = new Button(label);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(48);

        String baseStyle =
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: 'Segoe UI Semibold', 'Segoe UI', sans-serif;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-radius: 10px;";

        if (isExit) {
            // Botón salir: outline rojo
            btn.setStyle(baseStyle +
                    "-fx-background-color: transparent;" +
                    "-fx-border-color: " + accentColor + ";" +
                    "-fx-border-width: 1.5px;" +
                    "-fx-text-fill: " + accentColor + ";"
            );
            btn.setOnMouseEntered(e -> btn.setStyle(baseStyle +
                    "-fx-background-color: " + accentColor + ";" +
                    "-fx-border-color: " + accentColor + ";" +
                    "-fx-border-width: 1.5px;" +
                    "-fx-text-fill: white;"
            ));
            btn.setOnMouseExited(e -> btn.setStyle(baseStyle +
                    "-fx-background-color: transparent;" +
                    "-fx-border-color: " + accentColor + ";" +
                    "-fx-border-width: 1.5px;" +
                    "-fx-text-fill: " + accentColor + ";"
            ));
        } else {
            // Botón normal: fondo sólido con color acento
            btn.setStyle(baseStyle +
                    "-fx-background-color: " + accentColor + "22;" +   // muy transparente
                    "-fx-border-color: " + accentColor + ";" +
                    "-fx-border-width: 1.5px;" +
                    "-fx-text-fill: " + TEXT_PRIMARY + ";"
            );
            btn.setOnMouseEntered(e -> btn.setStyle(baseStyle +
                    "-fx-background-color: " + accentColor + ";" +
                    "-fx-border-color: " + accentColor + ";" +
                    "-fx-border-width: 1.5px;" +
                    "-fx-text-fill: white;"
            ));
            btn.setOnMouseExited(e -> btn.setStyle(baseStyle +
                    "-fx-background-color: " + accentColor + "22;" +
                    "-fx-border-color: " + accentColor + ";" +
                    "-fx-border-width: 1.5px;" +
                    "-fx-text-fill: " + TEXT_PRIMARY + ";"
            ));
        }

        // Sombra de color al hover ya se maneja con el estilo; agrego drop shadow base
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web(accentColor, 0.3));
        shadow.setRadius(8);
        btn.setEffect(shadow);

        return btn;
    }

    private void animateClick(Button btn) {
        ScaleTransition st = new ScaleTransition(Duration.millis(100), btn);
        st.setToX(0.95);
        st.setToY(0.95);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
    }
}