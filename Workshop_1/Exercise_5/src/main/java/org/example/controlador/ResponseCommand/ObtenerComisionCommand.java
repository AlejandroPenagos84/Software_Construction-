package org.example.controlador.ResponseCommand;

import org.example.controlador.DTO.ArticuloRequestDTO;
import org.example.controlador.DTO.ArticuloResponseDTO;
import org.example.controlador.service.VentaService;

import java.util.ArrayList;
import java.util.List;

public class ObtenerComisionCommand implements ResponseCommand<Double> {
    private final VentaService ventaService;
    public ObtenerComisionCommand(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @Override
    public String name() {
        return "Obtener Comision";
    }

    @Override
    public CommandResult<Double> execute(List<ArticuloRequestDTO> datos) {
        List<ArticuloResponseDTO> articuloDTOS = new ArrayList<>();
        for (ArticuloRequestDTO p : datos) {
            articuloDTOS.add(new ArticuloResponseDTO(
                    p.nombre(),
                    Double.parseDouble(p.precio()),
                    Integer.parseInt(p.cantidad())));
        }
        Double comision = this.ventaService.calcularComision(articuloDTOS);

        return new CommandResult<>("Comisión obtenida", comision, false);
    }
}
