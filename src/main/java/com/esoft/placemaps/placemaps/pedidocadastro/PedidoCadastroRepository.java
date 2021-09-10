package com.esoft.placemaps.placemaps.pedidocadastro;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PedidoCadastroRepository extends JpaRepository<PedidoCadastro, String> {

  Optional<PedidoCadastro> findFirstByEmail(String email);

}
