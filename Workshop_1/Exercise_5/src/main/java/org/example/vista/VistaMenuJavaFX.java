package org.example.vista;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.controlador.DTO.ArticuloRequestDTO;
import java.util.*;

public class VistaMenuJavaFX implements VistaMenu {

    // ── Paleta ───────────────────────────────────────────────────────────────
    private static final String BG_DARK = "#0D0F1A";
    private static final String BG_SIDEBAR = "#10121F";
    private static final String BG_CARD = "#141627";
    private static final String TEXT_PRIMARY = "#EAEAEA";
    private static final String TEXT_SUB = "#7B7FA6";
    private static final String BORDER_COLOR = "#1E2140";
    private static final String ACCENT = "#6C63FF";
    private static final String ACCENT_EXIT = "#FF4757";

    private static final String[] BUTTON_COLORS = {
            "#6C63FF", "#48CFAD", "#FC5C7D", "#F7971E", "#43CEA2",
            "#A78BFA", "#38BDF8", "#FB7185", "#FBBF24", "#34D399"
    };

    // ── State ────────────────────────────────────────────────────────────────
    private final Stage stage;
    private VBox menuLayout;
    private final Map<Integer, Button> buttons = new HashMap<>();
    private final Map<Integer, Runnable> actions = new HashMap<>();
    private Label tableTitle;
    private Label tableSubtitle;
    private VBox contentArea;
    private int buttonCount = 0;
    private int totalButtons = 0;
    private String[] campos;
    // Form state — solo los inputs activos
    private final Map<String, TextField> formInputs = new LinkedHashMap<>();

    public VistaMenuJavaFX(Stage stage) {
        this.stage = stage;
        initializeUI();
    }

    // ── UI Construction ──────────────────────────────────────────────────────

    private void initializeUI() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + BG_DARK + ";");

        root.setLeft(buildSidebar());
        root.setCenter(buildContentArea());

        Scene scene = new Scene(root, 1000, 640);
        try {
            scene.getStylesheets().add(
                    Objects.requireNonNull(getClass().getResource("/dark-table.css")).toExternalForm()
            );
        } catch (Exception ignored) {
        }

        stage.setScene(scene);
        stage.setTitle("Sistema Comisionista");
        stage.setMinWidth(800);
        stage.setMinHeight(500);
        stage.setOnCloseRequest(e -> close());
    }

    private VBox buildSidebar() {
        VBox sidebar = new VBox(0);
        sidebar.setPrefWidth(300);
        sidebar.setStyle(
                "-fx-background-color: " + BG_SIDEBAR + ";" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-width: 0 1 0 0;"
        );

        VBox header = new VBox(6);
        header.setPadding(new Insets(30, 24, 20, 24));
        header.setStyle("-fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 0 0 1 0;");

        Label icon = new Label("🗳️");
        icon.setStyle("-fx-font-size: 28px;");

        Label title = new Label("Sistema Electoral");
        title.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                        "-fx-font-family: 'Segoe UI Semibold', 'Segoe UI', sans-serif;"
        );

        Label subtitle = new Label("Selecciona una opción");
        subtitle.setStyle(
                "-fx-font-size: 12px;" +
                        "-fx-text-fill: " + TEXT_SUB + ";" +
                        "-fx-font-family: 'Segoe UI', sans-serif;"
        );

        header.getChildren().addAll(icon, title, subtitle);

        menuLayout = new VBox(10);
        menuLayout.setPadding(new Insets(20, 16, 20, 16));
        menuLayout.setAlignment(Pos.TOP_CENTER);

        ScrollPane scroll = new ScrollPane(menuLayout);
        scroll.setFitToWidth(true);
        scroll.setStyle(
                "-fx-background: transparent;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: transparent;"
        );
        VBox.setVgrow(scroll, Priority.ALWAYS);

        sidebar.getChildren().addAll(header, scroll);
        return sidebar;
    }

    private VBox buildContentArea() {
        contentArea = new VBox(16);
        contentArea.setPadding(new Insets(30, 30, 30, 30));
        contentArea.setStyle("-fx-background-color: " + BG_DARK + ";");

        VBox headerBox = new VBox(4);
        headerBox.setUserData("static");

        tableTitle = new Label("Bienvenido");
        tableTitle.setStyle(
                "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                        "-fx-font-family: 'Segoe UI Semibold', 'Segoe UI', sans-serif;"
        );

        tableSubtitle = new Label("Selecciona una opción del menú para comenzar");
        tableSubtitle.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-text-fill: " + TEXT_SUB + ";" +
                        "-fx-font-family: 'Segoe UI', sans-serif;"
        );

        headerBox.getChildren().addAll(tableTitle, tableSubtitle);

        Region sep = new Region();
        sep.setPrefHeight(1);
        sep.setMaxWidth(Double.MAX_VALUE);
        sep.setStyle("-fx-background-color: " + BORDER_COLOR + ";");
        sep.setUserData("static");

        contentArea.getChildren().addAll(headerBox, sep);
        return contentArea;
    }

    // ── VistaMenu impl ───────────────────────────────────────────────────────

    @Override
    public void renderMenu(Map<Integer, String> labels) {
        totalButtons = labels.size();
        Platform.runLater(() -> {
            menuLayout.getChildren().removeIf(n -> n instanceof Button);
            buttons.clear();
            buttonCount = 0;

            labels.forEach((option, label) -> {
                boolean isLast = (buttonCount == totalButtons - 1);
                String color = isLast ? ACCENT_EXIT : BUTTON_COLORS[buttonCount % BUTTON_COLORS.length];

                Button btn = buildButton(label, color, isLast);
                btn.setOnAction(e -> {
                    animateClick(btn);
                    Runnable action = actions.get(option);
                    if (action != null) action.run();
                });

                btn.setOpacity(0);
                btn.setTranslateX(-15);

                FadeTransition ft = new FadeTransition(Duration.millis(300), btn);
                ft.setToValue(1);
                ft.setDelay(Duration.millis(60L * buttonCount));

                TranslateTransition tt = new TranslateTransition(Duration.millis(300), btn);
                tt.setToX(0);
                tt.setDelay(Duration.millis(60L * buttonCount));

                ft.play();
                tt.play();

                buttons.put(option, btn);
                menuLayout.getChildren().add(btn);
                buttonCount++;
            });
        });
    }

    @Override
    public void bindOption(int option, Runnable action) {
        actions.put(option, action);
    }

    @Override
    public void showMessage(String title, Object message) {
        Platform.runLater(() -> {
            tableTitle.setText(title);
            tableSubtitle.setText("Resultado obtenido");

            contentArea.getChildren().removeIf(n -> !"static".equals(n.getUserData()));

            VBox card = buildResultCard(message);
            card.setOpacity(0);

            FadeTransition ft = new FadeTransition(Duration.millis(300), card);
            ft.setToValue(1);
            ft.play();

            contentArea.getChildren().add(card);
        });
    }

    /**
     * Construye el formulario y ejecuta onConfirmar cuando el usuario confirma.
     * Los datos se obtienen con obtenerDatos() dentro del callback.
     *
     * @param titulo      Título del formulario
     * @param onConfirmar Callback que se ejecuta al confirmar
     * @param campos      Nombres de los campos
     */
    @Override
    public void crearFormulario(String titulo, Runnable onConfirmar, String... campos) {
        this.campos = campos;
        formInputs.clear();

        tableTitle.setText(titulo);
        tableSubtitle.setText("Completa los campos y confirma");
        contentArea.getChildren().removeIf(n -> !"static".equals(n.getUserData()));

        VBox formCard = new VBox(20);
        formCard.setPadding(new Insets(24));
        formCard.setMaxWidth(780); // un poco más ancho para los dos campos
        formCard.setStyle(
                "-fx-background-color: " + BG_CARD + ";" +
                        "-fx-border-color: " + ACCENT + ";" +
                        "-fx-border-width: 1.5px;" +
                        "-fx-border-radius: 12px;" +
                        "-fx-background-radius: 12px;"
        );

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web(ACCENT, 0.2));
        shadow.setRadius(15);
        formCard.setEffect(shadow);

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(14);

        for (int i = 0; i < campos.length; i++) {

            // --- Nombre ---
            Label labelNombre = new Label("Nombre de " + campos[i]);
            labelNombre.setStyle("-fx-text-fill: " + TEXT_SUB + ";-fx-font-size: 12px;-fx-font-family: 'Segoe UI', sans-serif;");

            TextField fieldNombre = new TextField();
            fieldNombre.setPromptText("Ingresa nombre de " + campos[i].toLowerCase());
            fieldNombre.setPrefWidth(160);
            fieldNombre.setStyle(buildFieldStyle(BORDER_COLOR));
            fieldNombre.focusedProperty().addListener((obs, wasF, isF) ->
                    fieldNombre.setStyle(buildFieldStyle(isF ? ACCENT : BORDER_COLOR)));

            // --- Precio ---
            Label labelPrecio = new Label("Precio de " + campos[i]);
            labelPrecio.setStyle("-fx-text-fill: " + TEXT_SUB + ";-fx-font-size: 12px;-fx-font-family: 'Segoe UI', sans-serif;");

            TextField fieldPrecio = new TextField();
            fieldPrecio.setPromptText("Ingresa precio");
            fieldPrecio.setPrefWidth(120);
            fieldPrecio.setStyle(buildFieldStyle(BORDER_COLOR));
            fieldPrecio.setTextFormatter(new TextFormatter<>(change -> {
                String newText = change.getControlNewText();
                if (newText.isEmpty() || newText.matches("\\d*(\\.\\d*)?")) return change;
                return null;
            }));
            fieldPrecio.focusedProperty().addListener((obs, wasF, isF) ->
                    fieldPrecio.setStyle(buildFieldStyle(isF ? ACCENT : BORDER_COLOR)));

            // --- Cantidad ---
            Label labelCantidad = new Label("Cantidad de " + campos[i]);
            labelCantidad.setStyle("-fx-text-fill: " + TEXT_SUB + ";-fx-font-size: 12px;-fx-font-family: 'Segoe UI', sans-serif;");

            TextField fieldCantidad = new TextField();
            fieldCantidad.setPromptText("Ingresa cantidad");
            fieldCantidad.setPrefWidth(100);
            fieldCantidad.setStyle(buildFieldStyle(BORDER_COLOR));
            fieldCantidad.setTextFormatter(new TextFormatter<>(change -> {
                String newText = change.getControlNewText();
                if (newText.isEmpty() || newText.matches("\\d*")) return change;
                return null;
            }));
            fieldCantidad.focusedProperty().addListener((obs, wasF, isF) ->
                    fieldCantidad.setStyle(buildFieldStyle(isF ? ACCENT : BORDER_COLOR)));

            grid.add(labelNombre,   0, i);
            grid.add(fieldNombre,   1, i);
            grid.add(labelPrecio,   2, i);
            grid.add(fieldPrecio,   3, i);
            grid.add(labelCantidad, 4, i);
            grid.add(fieldCantidad, 5, i);

            formInputs.put(campos[i] + "_nombre",   fieldNombre);
            formInputs.put(campos[i] + "_precio",   fieldPrecio);
            formInputs.put(campos[i] + "_cantidad", fieldCantidad);
        }

        Button cancelarBtn = new Button("Cancelar");
        cancelarBtn.setStyle(buildBtnStyle(BORDER_COLOR, false));
        cancelarBtn.setOnAction(e -> {
            formInputs.clear();
            contentArea.getChildren().removeIf(n -> !"static".equals(n.getUserData()));
            tableTitle.setText("Bienvenido");
            tableSubtitle.setText("Selecciona una opción del menú para comenzar");
        });

        Button confirmarBtn = new Button("Confirmar");
        confirmarBtn.setStyle(buildBtnStyle(ACCENT, true));
        confirmarBtn.setDisable(true);

        confirmarBtn.setOnAction(e -> onConfirmar.run());

        formInputs.values().forEach(f ->
                f.textProperty().addListener((obs, o, n) -> {
                    boolean vacio = formInputs.values().stream()
                            .anyMatch(tf -> tf.getText().trim().isEmpty());
                    confirmarBtn.setDisable(vacio);
                })
        );

        HBox btnRow = new HBox(12);
        btnRow.setAlignment(Pos.CENTER_RIGHT);
        btnRow.getChildren().addAll(cancelarBtn, confirmarBtn);

        formCard.getChildren().addAll(grid, btnRow);

        formCard.setOpacity(0);
        formCard.setTranslateY(10);
        FadeTransition ft = new FadeTransition(Duration.millis(300), formCard);
        ft.setToValue(1);
        TranslateTransition tt = new TranslateTransition(Duration.millis(300), formCard);
        tt.setToY(0);
        ft.play();
        tt.play();

        contentArea.getChildren().add(formCard);
        formInputs.values().iterator().next().requestFocus();
    }
    /**
     * Retorna los datos actuales del formulario.
     * Llamar DENTRO del callback onConfirmar de crearFormulario().
     */
    @Override
    public List<ArticuloRequestDTO> obtenerDatos() {
        List<ArticuloRequestDTO> datos = new ArrayList<>();

        for (String campo : campos) {
            String nombre   = formInputs.get(campo + "_nombre").getText().trim();
            String precio   = formInputs.get(campo + "_precio").getText().trim();
            String cantidad = formInputs.get(campo + "_cantidad").getText().trim();
            datos.add(new ArticuloRequestDTO(nombre, precio, cantidad));
        }

        return datos;
    }

    @Override
    public void show() {
        Platform.runLater(stage::show);
    }

    @Override
    public void close() {
        Platform.runLater(() -> {
            stage.close();
            Platform.exit();
        });
    }

    // ── Result Card ──────────────────────────────────────────────────────────

    private VBox buildResultCard(Object message) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setMaxWidth(Double.MAX_VALUE);
        card.setStyle(
                "-fx-background-color: " + BG_CARD + ";" +
                        "-fx-border-color: " + ACCENT + ";" +
                        "-fx-border-width: 1.5px;" +
                        "-fx-border-radius: 12px;" +
                        "-fx-background-radius: 12px;"
        );

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web(ACCENT, 0.2));
        shadow.setRadius(15);
        card.setEffect(shadow);

        Label msgLbl = new Label(message != null ? message.toString() : "Sin resultado");
        msgLbl.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-text-fill: " + TEXT_SUB + ";" +
                        "-fx-font-family: 'Segoe UI', sans-serif;"
        );
        msgLbl.setWrapText(true);

        card.getChildren().add(msgLbl);
        return card;
    }

    // ── Style Helpers ────────────────────────────────────────────────────────

    private String buildFieldStyle(String borderColor) {
        return "-fx-background-color: " + BG_DARK + ";" +
                "-fx-border-color: " + borderColor + ";" +
                "-fx-border-width: 1.5px;" +
                "-fx-border-radius: 8px;" +
                "-fx-background-radius: 8px;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                "-fx-font-size: 13px;" +
                "-fx-prompt-text-fill: " + TEXT_SUB + ";" +
                "-fx-padding: 8px 12px;";
    }

    private String buildBtnStyle(String color, boolean filled) {
        return "-fx-background-color: " + (filled ? color : "transparent") + ";" +
                "-fx-text-fill: " + (filled ? "white" : TEXT_SUB) + ";" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 13px;" +
                "-fx-background-radius: 8px;" +
                "-fx-border-color: " + color + ";" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: 8px;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 8px 20px;";
    }

    private Button buildButton(String label, String accentColor, boolean isExit) {
        Button btn = new Button(label);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(52);
        btn.setWrapText(true);

        String base =
                "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: 'Segoe UI Semibold', 'Segoe UI', sans-serif;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-alignment: center-left;" +
                        "-fx-padding: 0 16px;";

        String normalStyle = base +
                "-fx-background-color: " + accentColor + "18;" +
                "-fx-border-color: " + accentColor + ";" +
                "-fx-border-width: 1.5px;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";";

        String hoverStyle = base +
                "-fx-background-color: " + accentColor + ";" +
                "-fx-border-color: " + accentColor + ";" +
                "-fx-border-width: 1.5px;" +
                "-fx-text-fill: white;";

        btn.setStyle(normalStyle);
        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e -> btn.setStyle(normalStyle));

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web(accentColor, 0.25));
        shadow.setRadius(8);
        btn.setEffect(shadow);

        return btn;
    }

    private void animateClick(Button btn) {
        ScaleTransition st = new ScaleTransition(Duration.millis(90), btn);
        st.setToX(0.96);
        st.setToY(0.96);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
    }
}