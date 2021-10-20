package com.esoft.placemaps.placemaps.pedidocadastro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PedidoCadastroRepository extends JpaRepository<PedidoCadastro, String> {

  Optional<PedidoCadastro> findFirstByEmail(String email);

  @Query(nativeQuery = true,
          value = "SELECT " +
                  "   p.* " +
                  "FROM " +
                  "   pedido_cadastro p " +
                  "ORDER BY data ASC",
          countQuery = "SELECT " +
                       "  COUNT(p.*) " +
                       "FROM " +
                       "   pedido_cadastro p")
  Page<PedidoCadastro> obterPedidosCadastrados(Pageable pageable);
}
