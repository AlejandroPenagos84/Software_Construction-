package org.example.modelo;

import java.util.List;

public class Venta {
    List<Articulo> articulos;

    public Venta() {
    }

    public Venta(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public Double calcularComision() {
        Double comisionTotal = 0.0;
        for(Articulo articulo : articulos) {
            if(articulo.getPrecio() < 100000) comisionTotal+= articulo.getPrecio()*articulo.getCantidad() * 0.1;
            else comisionTotal+= articulo.getPrecio()*articulo.getCantidad() * 0.07;
        }
        return comisionTotal;
    }
}
