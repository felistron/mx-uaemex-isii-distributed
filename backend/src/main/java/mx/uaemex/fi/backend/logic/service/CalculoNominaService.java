package mx.uaemex.fi.backend.logic.service;

public interface CalculoNominaService {
    Double calcularCuotaFija(Double salarioBruto);
    Double calcularExcedente(Double salarioBruto);
    Double calcularPorcentaje(Double salarioBruto);
}
