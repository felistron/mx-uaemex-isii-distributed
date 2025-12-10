package mx.uaemex.fi.backend.logic.service;

import mx.uaemex.fi.backend.presentation.dto.NominaRequest;
import mx.uaemex.fi.backend.logic.exception.NotFoundException;
import mx.uaemex.fi.backend.presentation.dto.NominaResponse;

import java.util.List;

public interface NominaService {
    NominaResponse generarNomina(NominaRequest request) throws NotFoundException;
    void eliminarNomina(Integer id) throws NotFoundException;
    List<NominaResponse> obtenerTodas(String rfcEmpleado);
}
