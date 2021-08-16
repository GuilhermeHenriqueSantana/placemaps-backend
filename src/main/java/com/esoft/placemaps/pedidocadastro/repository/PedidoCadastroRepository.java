package com.esoft.placemaps.pedidocadastro.repository;

import com.esoft.placemaps.pedidocadastro.PedidoCadastro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PedidoCadastroRepository extends JpaRepository<PedidoCadastro, String> {
}
