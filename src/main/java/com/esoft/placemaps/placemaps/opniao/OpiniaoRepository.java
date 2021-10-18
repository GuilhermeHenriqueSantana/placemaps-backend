package com.esoft.placemaps.placemaps.opniao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OpiniaoRepository extends JpaRepository<Opiniao, String> {

  @Query(nativeQuery = true,
          value = "SELECT " +
                  "   * " +
                  "FROM " +
                  "   opiniao p " +
                  "ORDER BY " +
                  "   p.data DESC ",
          countQuery = "SELECT " +
                  "   * " +
                  "FROM " +
                  "   opiniao p ")
  Page<Opiniao> getPageOrderByDateDesc(Pageable pageable);

}
