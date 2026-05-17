package com.factorit.compra;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findByClienteDni(String clienteDni, Sort sort);

    List<Compra> findByClienteDniAndFechaGreaterThanEqual(String clienteDni, LocalDateTime from, Sort sort);

    List<Compra> findByClienteDniAndFechaBetween(String clienteDni, LocalDateTime from, LocalDateTime to, Sort sort);

    @Query(""" 
            select coalesce(sum(c.total), 0)
            from Compra c
            where c.clienteDni = :clienteDni
              and c.fecha between :from and :to
            """)
    BigDecimal sumarTotalByClienteDniAndFechaBetween(
            @Param("clienteDni") String clienteDni,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);
}
