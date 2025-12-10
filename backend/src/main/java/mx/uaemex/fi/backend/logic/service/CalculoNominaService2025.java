package mx.uaemex.fi.backend.logic.service;

import org.springframework.stereotype.Service;

/**
 * Datos obtenidos en la sección A.V de las tarifas aplicables a pagos provisionales, retenciones y cálculo del ISR, en el Anexo no. 8 de
 * la Resolución Miscelánea Fiscal 2025 que se puede consultar en la página <a href="https://www.sat.gob.mx/minisitio/NormatividadRMFyRGCE/documentos2025/rmf/anexos/Anexo8_RMF2025-30122024.pdf">oficial del SAT</a>.
 */
@Service
public class CalculoNominaService2025 implements CalculoNominaService {
    @Override
    public Double calcularCuotaFija(Double salarioBruto) {
        if (salarioBruto >= 0.01 && salarioBruto <= 746.04) {
            return 0.00;
        }
        else if (salarioBruto >= 746.05 && salarioBruto <= 6332.05) {
            return 14.32;
        }
        else if (salarioBruto >= 6332.06 && salarioBruto <= 11128.01) {
            return 371.83;
        }
        else if (salarioBruto >= 11128.02 && salarioBruto <= 12935.82) {
            return 893.63;
        }
        else if (salarioBruto >= 12935.83 && salarioBruto <= 15487.71) {
            return 1182.88;
        }
        else if (salarioBruto >= 15487.72 && salarioBruto <= 31236.49) {
            return 1640.18;
        }
        else if (salarioBruto >= 31236.50 && salarioBruto <= 49233.00) {
            return 5004.12;
        }
        else if (salarioBruto >= 49233.01 && salarioBruto <= 93993.90) {
            return 9236.89;
        }
        else if (salarioBruto >= 93993.91 && salarioBruto <= 125325.20) {
            return 22665.17;
        }
        else if (salarioBruto >= 125325.21 && salarioBruto <= 375975.61) {
            return 32691.18;
        }
        else {
            return 117912.32;
        }
    }

    @Override
    public Double calcularExcedente(Double salarioBruto) {
        if (salarioBruto >= 0.01 && salarioBruto <= 746.04) {
            return salarioBruto - 0.01;
        }
        else if (salarioBruto >= 746.05 && salarioBruto <= 6332.05) {
            return salarioBruto - 746.05;
        }
        else if (salarioBruto >= 6332.06 && salarioBruto <= 11128.01) {
            return salarioBruto - 6332.06;
        }
        else if (salarioBruto >= 11128.02 && salarioBruto <= 12935.82) {
            return salarioBruto - 11128.02;
        }
        else if (salarioBruto >= 12935.83 && salarioBruto <= 15487.71) {
            return salarioBruto - 12935.83;
        }
        else if (salarioBruto >= 15487.72 && salarioBruto <= 31236.49) {
            return salarioBruto - 15487.72;
        }
        else if (salarioBruto >= 31236.50 && salarioBruto <= 49233.00) {
            return salarioBruto - 31236.50;
        }
        else if (salarioBruto >= 49233.01 && salarioBruto <= 93993.90) {
            return salarioBruto - 49233.01;
        }
        else if (salarioBruto >= 93993.91 && salarioBruto <= 125325.20) {
            return salarioBruto - 93993.91;
        }
        else if (salarioBruto >= 125325.21 && salarioBruto <= 375975.61) {
            return salarioBruto - 125325.21;
        }
        else {
            return salarioBruto - 375975.62;
        }
    }

    @Override
    public Double calcularPorcentaje(Double salarioBruto) {
        if (salarioBruto >= 0.01 && salarioBruto <= 746.04) {
            return 0.0192;
        }
        else if (salarioBruto >= 746.05 && salarioBruto <= 6332.05) {
            return 0.0640;
        }
        else if (salarioBruto >= 6332.06 && salarioBruto <= 11128.01) {
            return 0.1088;
        }
        else if (salarioBruto >= 11128.02 && salarioBruto <= 12935.82) {
            return 0.1600;
        }
        else if (salarioBruto >= 12935.83 && salarioBruto <= 15487.71) {
            return 0.1792;
        }
        else if (salarioBruto >= 15487.72 && salarioBruto <= 31236.49) {
            return 0.2136;
        }
        else if (salarioBruto >= 31236.50 && salarioBruto <= 49233.00) {
            return 0.2352;
        }
        else if (salarioBruto >= 49233.01 && salarioBruto <= 93993.90) {
            return 0.3000;
        }
        else if (salarioBruto >= 93993.91 && salarioBruto <= 125325.20) {
            return 0.3200;
        }
        else if (salarioBruto >= 125325.21 && salarioBruto <= 375975.61) {
            return 0.3400;
        }
        else {
            return 0.3500;
        }
    }
}
