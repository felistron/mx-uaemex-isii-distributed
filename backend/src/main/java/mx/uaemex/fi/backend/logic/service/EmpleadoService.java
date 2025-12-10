package mx.uaemex.fi.backend.logic.service;

import mx.uaemex.fi.backend.logic.exception.NotFoundException;
import mx.uaemex.fi.backend.presentation.dto.EmpleadoResponse;

import java.util.List;

public interface EmpleadoService {
    EmpleadoResponse buscarPorRFC(String rfc) throws NotFoundException;
    List<EmpleadoResponse> obtenerTodos();
}
