package mx.uaemex.fi.backend.presentation.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import mx.uaemex.fi.backend.logic.exception.NotFoundException;
import mx.uaemex.fi.backend.logic.service.EmpleadoService;
import mx.uaemex.fi.backend.presentation.dto.EmpleadoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/empleado")
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    @GetMapping("/")
    public ResponseEntity<@NonNull List<EmpleadoResponse>> getAllEmpleados() {
        return ResponseEntity.ok(empleadoService.obtenerTodos());
    }

    @GetMapping("/{rfc}")
    public ResponseEntity<@NonNull EmpleadoResponse> getEmpleado(@PathVariable String rfc) {
        return ResponseEntity.ok(empleadoService.buscarPorRFC(rfc));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<@NonNull Void> handleNotFound() {
        return ResponseEntity.notFound().build();
    }
}
