package com.esoft.placemaps.placemaps.mensagem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MensagemRepository extends JpaRepository<Mensagem, String> {

  @Query(nativeQuery = true,
          value = "SELECT " +
                  "   * " +
                  "FROM " +
                  "   mensagem p " +
                  "ORDER BY " +
                  "   p.data DESC ",
          countQuery = "SELECT " +
                  "   COUNT(*) " +
                  "FROM " +
                  "   mensagem p ")
  Page<Mensagem> getPageOrderByDateDesc(Pageable pageable);

}
