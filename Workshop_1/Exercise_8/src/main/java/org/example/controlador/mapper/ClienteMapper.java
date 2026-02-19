package org.example.controlador.mapper;
import org.example.controlador.DTO.ClienteDTO;
import org.example.modelo.Cliente;

public class ClienteMapper {

    public static ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        return new ClienteDTO(
                cliente.getNombre(),
                cliente.getEdad(),
                cliente.getAltura(),
                cliente.getPeso(),
                cliente.getColorOjos() != null ? cliente.getColorOjos().getDescripcion() : null,
                cliente.getColorCabello() != null ? cliente.getColorCabello().getDescripcion() : null
        );
    }
}