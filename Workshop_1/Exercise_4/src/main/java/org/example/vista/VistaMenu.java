package org.example.vista;

import java.util.Map;

public interface VistaMenu{
    void renderMenu(Map<Integer, String> labels);
    void bindOption(int option, Runnable action);
    void showMessage(String title, Object message);
    void show();
    void close();
}
