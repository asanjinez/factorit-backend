package com.factorit.compra;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;

public interface ICompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findByClienteDni(String clienteDni, Sort sort);

    List<Compra> findByClienteDniAndFechaGreaterThanEqual(String clienteDni, LocalDateTime from, Sort sort);

    List<Compra> findByClienteDniAndFechaBetween(String clienteDni, LocalDateTime from, LocalDateTime to, Sort sort);
}
