package mx.uaemex.fi.backend.logic.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import mx.uaemex.fi.backend.persistence.repository.AccesoRepository;
import mx.uaemex.fi.backend.persistence.repository.EmpleadoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final EmpleadoRepository empleadoRepository;
    private final AccesoRepository accesoRepository;


    @NonNull
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        var empleado = empleadoRepository.findByRfc(username)
                .orElseThrow(() -> new UsernameNotFoundException("Empleado no encontrado"));

        var acceso = accesoRepository.findByIdEmpleado(empleado.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Acceso no encontrado"));

        return new UserDetailsAdapter(empleado, acceso.getHashedPassword());
    }
}
