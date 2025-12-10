package mx.uaemex.fi.backend.logic.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mx.uaemex.fi.backend.persistence.model.Nomina;
import mx.uaemex.fi.backend.presentation.dto.NominaRequest;
import mx.uaemex.fi.backend.logic.exception.NotFoundException;
import mx.uaemex.fi.backend.persistence.repository.EmpleadoRepository;
import mx.uaemex.fi.backend.persistence.repository.NominaRepository;
import mx.uaemex.fi.backend.presentation.dto.NominaResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NominaServiceImp implements NominaService {
    private final NominaRepository nominaRepository;
    private final EmpleadoRepository empleadoRepository;
    private final CalculoNominaService calculoNominaService;

    @Transactional
    @Override
    public NominaResponse generarNomina(NominaRequest request) throws NotFoundException {
        var empleado = empleadoRepository.findByRfc(request.rfc())
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Empleado no encontrado"));

        var salario = request.salario();

        var nomina = new Nomina();
        nomina.setEmpleado(empleado);
        nomina.setSalarioBruto(request.salario());
        nomina.setExcedente(calculoNominaService.calcularExcedente(salario));
        nomina.setPorcentaje(calculoNominaService.calcularPorcentaje(salario));
        nomina.setCuotaFija(calculoNominaService.calcularCuotaFija(salario));
        nomina.setPeriodoInicio(request.fechaInicio());
        nomina.setPeriodoFin(request.fechaFin());

        var res = nominaRepository.save(nomina);

        return new NominaResponse(
                res.getId(),
                res.getSalarioBruto(),
                res.getExcedente(),
                res.getCuotaFija(),
                res.getPorcentaje(),
                res.getPeriodoInicio(),
                res.getPeriodoFin()
        );
    }

    @Override
    public void eliminarNomina(Integer id) throws NotFoundException {
        if (!nominaRepository.existsById(id))
            throw new NotFoundException("Nómina no encontrada");
        nominaRepository.deleteById(id);
    }

    @Override
    public List<NominaResponse> obtenerTodas(String rfcEmpleado) {
        return nominaRepository
                .findAllByEmpleado_RfcOrderByPeriodoInicio(rfcEmpleado)
                .stream()
                .map((nomina -> new NominaResponse(
                        nomina.getId(),
                        nomina.getSalarioBruto(),
                        nomina.getExcedente(),
                        nomina.getCuotaFija(),
                        nomina.getPorcentaje(),
                        nomina.getPeriodoInicio(),
                        nomina.getPeriodoFin()
                )))
                .toList();
    }
}
