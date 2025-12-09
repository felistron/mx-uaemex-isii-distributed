package mx.uaemex.fi.backend.presentation.controller;

import mx.uaemex.fi.backend.logic.exception.NotFoundException;
import mx.uaemex.fi.backend.logic.service.CalculoNominaService;
import mx.uaemex.fi.backend.logic.service.CustomUserDetailsService;
import mx.uaemex.fi.backend.logic.service.JwtServiceImp;
import mx.uaemex.fi.backend.logic.service.NominaService;
import mx.uaemex.fi.backend.persistence.repository.AccesoRepository;
import mx.uaemex.fi.backend.persistence.repository.EmpleadoRepository;
import mx.uaemex.fi.backend.persistence.repository.NominaRepository;
import mx.uaemex.fi.backend.presentation.dto.NominaRequest;
import mx.uaemex.fi.backend.presentation.dto.NominaResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static mx.uaemex.fi.backend.util.TestDataBuilder.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NominaController.class)
class NominaControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private NominaService nominaService;

    @MockitoBean
    private JwtServiceImp jwtService;

    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    @MockitoBean
    private EmpleadoRepository empleadoRepository;

    @MockitoBean
    private AccesoRepository accesoRepository;

    @MockitoBean
    private CalculoNominaService calculoNominaService;

    @MockitoBean
    private NominaRepository nominaRepository;

    @Test
    @DisplayName("obtenerNomina - RFC existente retorna nominas de un empleado")
    @WithMockUser(username = "AAAA123456XXX", roles = {"ADMIN"})
    void obtenerNomina_rfcExistente_retornaNominas() throws Exception {
        NominaResponse nominaResponse = new NominaResponse(
                1,
                500d,
                0d,
                100d,
                0.05d,
                TEST_FECHA_INICIO,
                TEST_FECHA_FIN
        );
        List<NominaResponse> nominas = List.of(nominaResponse);
        when(nominaService.obtenerTodas(TEST_RFC)).thenReturn(nominas);

        mvc.perform(get("/nomina/").queryParam("rfc", TEST_RFC))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$[0].id").value(nominaResponse.id()),
                        jsonPath("$[0].salario").value(nominaResponse.salario()),
                        jsonPath("$[0].excedente").value(nominaResponse.excedente()),
                        jsonPath("$[0].cuotaFija").value(nominaResponse.cuotaFija()),
                        jsonPath("$[0].porcentaje").value(nominaResponse.porcentaje()),
                        jsonPath("$[0].periodoInicio").value(nominaResponse.periodoInicio().toString()),
                        jsonPath("$[0].periodoFin").value(nominaResponse.periodoFin().toString())
                );

        verify(nominaService).obtenerTodas(TEST_RFC);
    }

    @Test
    @DisplayName("obtenerNomina - RFC null retorna 400 Bad Request")
    @WithMockUser(username = "AAAA123456XXX", roles = {"ADMIN"})
    void obtenerNomina_rfcNull_retorna400() throws Exception {

        mvc.perform(get("/nomina/"))
                .andExpect(status().isBadRequest());

        verify(nominaService, never()).obtenerTodas(TEST_RFC);
    }

    @Test
    @DisplayName("obtenerNomina - Usuario anónimo 403 Unauthorized")
    void obtenerNomina_usuarioAnonimo_retorna403() throws Exception {
        mvc.perform(get("/nomina/"))
                .andExpect(status().isUnauthorized());
        verify(nominaService, never()).obtenerTodas(TEST_RFC);
    }

    @Test
    @DisplayName("generarNomina - Request válido retorna NominaResponse")
    @WithMockUser(username = "AAAA123456XXX", roles = {"ADMIN"})
    void generarNomina_valido_retornaNominaResponse() throws Exception {
        NominaRequest request = new NominaRequest(TEST_RFC, TEST_SALARIO_MINIMO, TEST_FECHA_INICIO, TEST_FECHA_FIN);

        NominaResponse nominaResponse = new NominaResponse(
                1,
                TEST_SALARIO_MINIMO,
                100d,
                50d,
                0.10d,
                TEST_FECHA_INICIO,
                TEST_FECHA_FIN
        );

        when(empleadoRepository.existsByRfc(TEST_RFC)).thenReturn(true);

        when(nominaService.generarNomina(request)).thenReturn(nominaResponse);

        mvc.perform(post("/nomina/")
                        .with(csrf())
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(nominaResponse.id()),
                        jsonPath("$.salario").value(nominaResponse.salario()),
                        jsonPath("$.excedente").value(nominaResponse.excedente()),
                        jsonPath("$.cuotaFija").value(nominaResponse.cuotaFija()),
                        jsonPath("$.porcentaje").value(nominaResponse.porcentaje()),
                        jsonPath("$.periodoInicio").value(nominaResponse.periodoInicio().toString()),
                        jsonPath("$.periodoFin").value(nominaResponse.periodoFin().toString())
                );

        verify(nominaService).generarNomina(request);
    }

    @Test
    @DisplayName("generarNomina - RFC inexistente retorna 400 Bad request")
    @WithMockUser(username = "AAAA123456XXX", roles = {"ADMIN"})
    void generarNomina_rfcInexistente_retorna400() throws Exception {
        NominaRequest request = new NominaRequest(TEST_RFC, TEST_SALARIO_MINIMO, TEST_FECHA_INICIO, TEST_FECHA_FIN);

        when(empleadoRepository.existsByRfc(TEST_RFC)).thenReturn(false);

        mvc.perform(post("/nomina/")
                        .with(csrf())
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(nominaService, never()).generarNomina(request);
    }

    @Test
    @DisplayName("generarNomina - RFC nulo retorna 400 Bad request")
    @WithMockUser(username = "AAAA123456XXX", roles = {"ADMIN"})
    void generarNomina_rfcNull_retorna400() throws Exception {
        NominaRequest request = new NominaRequest(null, TEST_SALARIO_MINIMO, TEST_FECHA_INICIO, TEST_FECHA_FIN);

        mvc.perform(post("/nomina/")
                        .with(csrf())
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(nominaService, never()).generarNomina(request);
    }

    @Test
    @DisplayName("generarNomina - Salario inválido retorna 400 Bad request")
    @WithMockUser(username = "AAAA123456XXX", roles = {"ADMIN"})
    void generarNomina_salarioInvalido_retorna400() throws Exception {
        NominaRequest request = new NominaRequest(TEST_RFC, 0.0, TEST_FECHA_INICIO, TEST_FECHA_FIN);

        when(empleadoRepository.existsByRfc(TEST_RFC)).thenReturn(true);

        mvc.perform(post("/nomina/")
                        .with(csrf())
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(nominaService, never()).generarNomina(request);
    }

    @Test
    @DisplayName("generarNomina - Salario nulo retorna 400 Bad request")
    @WithMockUser(username = "AAAA123456XXX", roles = {"ADMIN"})
    void generarNomina_salarioNull_retorna400() throws Exception {
        NominaRequest request = new NominaRequest(TEST_RFC, null, TEST_FECHA_INICIO, TEST_FECHA_FIN);

        mvc.perform(post("/nomina/")
                        .with(csrf())
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(nominaService, never()).generarNomina(request);
    }

    @Test
    @DisplayName("generarNomina - Fecha inicio nula retorna 400 Bad request")
    @WithMockUser(username = "AAAA123456XXX", roles = {"ADMIN"})
    void generarNomina_fechaInicioNull_retorna400() throws Exception {
        NominaRequest request = new NominaRequest(TEST_RFC, TEST_SALARIO_MINIMO, null, TEST_FECHA_FIN);

        mvc.perform(post("/nomina/")
                        .with(csrf())
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(nominaService, never()).generarNomina(request);
    }

    @Test
    @DisplayName("generarNomina - Fecha fin nula retorna 400 Bad request")
    @WithMockUser(username = "AAAA123456XXX", roles = {"ADMIN"})
    void generarNomina_fechaFinNull_retorna400() throws Exception {
        NominaRequest request = new NominaRequest(TEST_RFC, TEST_SALARIO_MINIMO, TEST_FECHA_INICIO, null);

        mvc.perform(post("/nomina/")
                        .with(csrf())
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(nominaService, never()).generarNomina(request);
    }

    @Test
    @DisplayName("generarNomina - Periodo inválido retorna 400 Bad request")
    @WithMockUser(username = "AAAA123456XXX", roles = {"ADMIN"})
    void generarNomina_periodoInvalido_retorna400() throws Exception {
        NominaRequest request = new NominaRequest(TEST_RFC, TEST_SALARIO_MINIMO, LocalDate.of(2025, 1, 15), LocalDate.of(2025, 1, 1));

        mvc.perform(post("/nomina/")
                        .with(csrf())
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(nominaService, never()).generarNomina(request);
    }

    @Test
    @DisplayName("generarNomina - Usuario anónimo 403 Unauthorized")
    void generarNomina_usuarioAnonimo_retorna403() throws Exception {
        mvc.perform(post("/nomina/"))
                .andExpect(status().isForbidden());
        verify(nominaService, never()).generarNomina(any(NominaRequest.class));
    }

    @Test
    @DisplayName("eliminarNomina - ID existente elimina la nómina")
    @WithMockUser(username = "AAAA123456XXX", roles = {"ADMIN"})
    void eliminarNomina() throws Exception {
        doNothing().when(nominaService).eliminarNomina(1);

        mvc.perform(delete("/nomina/" + 1)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(nominaService).eliminarNomina(1);
    }

    @Test
    @DisplayName("eliminarNomina - ID inexistente lanza NotFoundException")
    @WithMockUser(username = "AAAA123456XXX", roles = {"ADMIN"})
    void eliminarNomina_idInexistente_lanzaNotFound() throws Exception {
        doThrow(new NotFoundException("Nomina no encontrada")).when(nominaService).eliminarNomina(1);

        mvc.perform(delete("/nomina/" + 1)
                        .with(csrf()))
                .andExpect(status().isNotFound());

        verify(nominaService).eliminarNomina(1);
    }

    @Test
    @DisplayName("eliminarNomina - Usuario anónimo 403 Unauthorized")
    void eliminarNomina_usuarioAnonimo_retorna403() throws Exception {
        mvc.perform(delete("/nomina/" + 1))
                .andExpect(status().isForbidden());
        verify(nominaService, never()).eliminarNomina(1);
    }
}