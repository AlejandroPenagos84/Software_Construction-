package org.example.vista;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.controlador.DTO.EmpleadoDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VistaMenuJavaFX implements VistaMenu {

    // ── Paleta ───────────────────────────────────────────────────────────────
    private static final String BG_DARK      = "#0D0F1A";
    private static final String BG_SIDEBAR   = "#10121F";
    private static final String BG_CARD      = "#141627";
    private static final String TEXT_PRIMARY = "#EAEAEA";
    private static final String TEXT_SUB     = "#7B7FA6";
    private static final String BORDER_COLOR = "#1E2140";
    private static final String ACCENT_EXIT  = "#FF4757";

    private static final String[] BUTTON_COLORS = {
            "#6C63FF", "#48CFAD", "#FC5C7D", "#F7971E", "#43CEA2",
            "#A78BFA", "#38BDF8", "#FB7185", "#FBBF24", "#34D399"
    };

    // ── State ────────────────────────────────────────────────────────────────
    private final Stage stage;
    private VBox menuLayout;
    private final Map<Integer, Button> buttons  = new HashMap<>();
    private final Map<Integer, Runnable> actions = new HashMap<>();
    private TableView<EmpleadoDTO> tableView;
    private Label tableTitle;
    private Label tableSubtitle;
    private int buttonCount = 0;
    private int totalButtons = 0;

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

        Scene scene = new Scene(root, 1100, 680);
        scene.getStylesheets().add(getClass().getResource("/dark-table.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Sistema de Liquidación de Empleados");
        stage.setMinWidth(900);
        stage.setMinHeight(550);
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

        // Header
        VBox header = new VBox(6);
        header.setPadding(new Insets(30, 24, 20, 24));
        header.setStyle("-fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 0 0 1 0;");

        Label icon = new Label("💼");
        icon.setStyle("-fx-font-size: 28px;");

        Label title = new Label("Liquidación");
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

        // Botones en scroll
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
        VBox content = new VBox(16);
        content.setPadding(new Insets(30, 30, 30, 30));
        content.setStyle("-fx-background-color: " + BG_DARK + ";");

        // Header tabla
        VBox tableHeader = new VBox(4);
        tableTitle = new Label("Liquidación de Empleados");
        tableTitle.setStyle(
                "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                        "-fx-font-family: 'Segoe UI Semibold', 'Segoe UI', sans-serif;"
        );
        tableSubtitle = new Label("Los resultados aparecerán aquí");
        tableSubtitle.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-text-fill: " + TEXT_SUB + ";" +
                        "-fx-font-family: 'Segoe UI', sans-serif;"
        );
        tableHeader.getChildren().addAll(tableTitle, tableSubtitle);

        // Separador
        Region sep = new Region();
        sep.setPrefHeight(1);
        sep.setMaxWidth(Double.MAX_VALUE);
        sep.setStyle("-fx-background-color: " + BORDER_COLOR + ";");

        tableView = createTableView();
        VBox.setVgrow(tableView, Priority.ALWAYS);

        content.getChildren().addAll(tableHeader, sep, tableView);
        return content;
    }

    private TableView<EmpleadoDTO> createTableView() {
        TableView<EmpleadoDTO> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web("#000000", 0.4));
        shadow.setRadius(20);
        table.setEffect(shadow);

        table.getColumns().addAll(
                makeCol("Código",      d -> d.getCodigo()),
                makeCol("Nombre",      d -> d.getNombre()),
                makeCol("Devengado",   d -> String.format("$ %,.2f", d.getDevengado())),
                makeCol("Retención",   d -> String.format("%.2f %%", d.getRetencion())),
                makeCol("Subsidio",    d -> "$ " + d.getSubsidio()),
                makeCol("Total a Pagar", d -> String.format("$ %,.2f", d.getTotalAPagar()))
        );

        Label placeholder = new Label("Selecciona una opción para ver los resultados");
        placeholder.setStyle("-fx-text-fill: " + TEXT_SUB + "; -fx-font-size: 14px;");
        table.setPlaceholder(placeholder);

        return table;
    }

    private TableColumn<EmpleadoDTO, String> makeCol(String title,
                                                     java.util.function.Function<EmpleadoDTO, String> extractor) {
        TableColumn<EmpleadoDTO, String> col = new TableColumn<>(title);
        col.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(extractor.apply(data.getValue()))
        );
        return col;
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
                String color   = isLast ? ACCENT_EXIT : BUTTON_COLORS[buttonCount % BUTTON_COLORS.length];

                Button btn = buildButton(label, color, isLast);
                btn.setOnAction(e -> {
                    animateClick(btn);
                    Runnable action = actions.get(option);
                    if (action != null) action.run();
                });

                // Entrada escalonada
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

            if (message instanceof List) {
                List<?> lista = (List<?>) message;
                if (!lista.isEmpty() && lista.get(0) instanceof EmpleadoDTO) {
                    @SuppressWarnings("unchecked")
                    List<EmpleadoDTO> empleados = (List<EmpleadoDTO>) lista;
                    tableSubtitle.setText(empleados.size() + " empleado(s) encontrado(s)");
                    tableView.setItems(FXCollections.observableArrayList(empleados));

                    FadeTransition ft = new FadeTransition(Duration.millis(300), tableView);
                    ft.setFromValue(0.4);
                    ft.setToValue(1.0);
                    ft.play();
                } else {
                    tableSubtitle.setText("Sin resultados");
                    tableView.setItems(FXCollections.observableArrayList());
                }
            } else {
                tableSubtitle.setText("Resultado calculado");
                tableView.setItems(FXCollections.observableArrayList());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(title);
                alert.setHeaderText(null);
                alert.setContentText(message.toString());
                alert.getDialogPane().setStyle(
                        "-fx-background-color: " + BG_CARD + ";" +
                                "-fx-border-color: #6C63FF;" +
                                "-fx-border-width: 1.5px;" +
                                "-fx-border-radius: 12px;" +
                                "-fx-background-radius: 12px;"
                );
                alert.getDialogPane().lookup(".content.label").setStyle(
                        "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                                "-fx-font-size: 15px;" +
                                "-fx-font-family: 'Segoe UI', sans-serif;"
                );
                alert.showAndWait();
            }
        });
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

    // ── Helpers ──────────────────────────────────────────────────────────────

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
        btn.setOnMouseExited(e  -> btn.setStyle(normalStyle));

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