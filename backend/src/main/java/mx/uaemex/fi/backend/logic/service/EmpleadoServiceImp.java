package mx.uaemex.fi.backend.logic.service;

import lombok.RequiredArgsConstructor;
import mx.uaemex.fi.backend.logic.exception.NotFoundException;
import mx.uaemex.fi.backend.persistence.repository.EmpleadoRepository;
import mx.uaemex.fi.backend.presentation.dto.EmpleadoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImp implements EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    @Override
    public EmpleadoResponse buscarPorRFC(String rfc) throws NotFoundException {
        var empleado = empleadoRepository
                .findByRfc(rfc)
                .orElseThrow(() -> new NotFoundException("Empleado no encontrado"));

        return new EmpleadoResponse(
                empleado.getId(),
                empleado.getRfc(),
                empleado.getNombres(),
                empleado.getApellidos(),
                empleado.getCorreo()
        );
    }

    @Override
    public List<EmpleadoResponse> obtenerTodos() {
        return empleadoRepository
                .findAll()
                .stream()
                .map((empleado -> new EmpleadoResponse(
                        empleado.getId(),
                        empleado.getRfc(),
                        empleado.getNombres(),
                        empleado.getApellidos(),
                        empleado.getCorreo()
                )))
                .toList();
    }
}
