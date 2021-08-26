package com.esoft.placemaps.placemaps.controleponto;

import com.esoft.placemaps.placemaps.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ControlePontoRepository extends JpaRepository<ControlePonto, String> {

    ControlePonto findFirstByUsuario(Usuario usuario);

}
