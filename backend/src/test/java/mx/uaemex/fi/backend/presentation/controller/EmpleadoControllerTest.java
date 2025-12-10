package mx.uaemex.fi.backend.presentation.controller;

import mx.uaemex.fi.backend.logic.exception.NotFoundException;
import mx.uaemex.fi.backend.logic.service.CustomUserDetailsService;
import mx.uaemex.fi.backend.logic.service.EmpleadoService;
import mx.uaemex.fi.backend.logic.service.JwtServiceImp;
import mx.uaemex.fi.backend.persistence.repository.AccesoRepository;
import mx.uaemex.fi.backend.persistence.repository.EmpleadoRepository;
import mx.uaemex.fi.backend.presentation.dto.EmpleadoResponse;
import mx.uaemex.fi.backend.util.TestDataBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static mx.uaemex.fi.backend.util.TestDataBuilder.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmpleadoController.class)
class EmpleadoControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private EmpleadoService empleadoService;

    @MockitoBean
    private JwtServiceImp jwtService;

    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    @MockitoBean
    private EmpleadoRepository empleadoRepository;

    @MockitoBean
    private AccesoRepository accesoRepository;

    @Test
    @DisplayName("obtenerEmpleados - Retorna todos los empleados")
    @WithMockUser(username = "AAAA123456XXX", roles = {"ADMIN"})
    void obtenerEmpleados_retornaEmpleados() throws Exception {
        List<EmpleadoResponse> empleados = TestDataBuilder.crearListaEmpleadosResponse();

        when(empleadoService.obtenerTodos()).thenReturn(empleados);

        mvc.perform(get("/empleado/"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    // Verificar que la respuesta contiene los empleados esperados
                    for (EmpleadoResponse emp : empleados) {
                        assert json.contains(emp.rfc());
                        assert json.contains(emp.nombre());
                        assert json.contains(emp.apellidos());
                        assert json.contains(emp.correo());
                    }
                });
    }

    @Test
    @DisplayName("obtenerEmpleados - Retorna 401")
    void obtenerEmpleados_retorna401() throws Exception {
        mvc.perform(get("/empleado/"))
                .andExpect(status().isUnauthorized());

        verify(empleadoService, never()).obtenerTodos();
    }

    @Test
    @DisplayName("obtenerEmpleado - RFC existente retorna empleado")
    @WithMockUser(username = "AAAA123456XXX", roles = {"ADMIN"})
    void obtenerEmpleado_rfcExistente_retornaEmpleado() throws Exception {
        EmpleadoResponse empleado = TestDataBuilder.crearEmpleadoResponse();

        when(empleadoService.buscarPorRFC(TEST_RFC)).thenReturn(empleado);

        mvc.perform(get("/empleado/" + TEST_RFC))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assert json.contains(empleado.rfc());
                    assert json.contains(empleado.nombre());
                    assert json.contains(empleado.apellidos());
                    assert json.contains(empleado.correo());
                });

        verify(empleadoService).buscarPorRFC(TEST_RFC);
    }

    @Test
    @DisplayName("obtenerEmpleado - RFC inexistente retorna 404")
    @WithMockUser(username = "AAAA123456XXX", roles = {"ADMIN"})
    void obtenerEmpleado_rfcInexistente_retornaEmpleado() throws Exception {
        when(empleadoService.buscarPorRFC(TEST_RFC)).thenThrow(new NotFoundException("Empleado no encontrado"));

        mvc.perform(get("/empleado/" + TEST_RFC))
                .andExpect(status().isNotFound());

        verify(empleadoService).buscarPorRFC(TEST_RFC);
    }

    @Test
    @DisplayName("obtenerEmpleado - Retorna 401")
    void obtenerEmpleado_retorna401() throws Exception {
        mvc.perform(get("/empleado/AAAA123456XXX"))
                .andExpect(status().isUnauthorized());

        verify(empleadoService, never()).buscarPorRFC(TEST_RFC);
    }
}