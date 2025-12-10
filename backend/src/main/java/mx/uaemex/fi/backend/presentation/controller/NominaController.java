package mx.uaemex.fi.backend.presentation.controller;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import mx.uaemex.fi.backend.logic.service.NominaService;
import mx.uaemex.fi.backend.presentation.dto.NominaRequest;
import mx.uaemex.fi.backend.presentation.dto.NominaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/nomina")
public class NominaController {
    private final NominaService nominaService;

    @GetMapping("/")
    public ResponseEntity<@NonNull List<NominaResponse>> getAllNominas(@RequestParam String rfc) {
        return ResponseEntity.ok(nominaService.obtenerTodas(rfc));
    }

    @PostMapping("/")
    public ResponseEntity<@NonNull NominaResponse> createNomina(@Valid @RequestBody NominaRequest request) {
        return ResponseEntity.ok(nominaService.generarNomina(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<@NonNull Void> deleteNomina(@PathVariable Integer id) {
        nominaService.eliminarNomina(id);
        return ResponseEntity.noContent().build();
    }
}
