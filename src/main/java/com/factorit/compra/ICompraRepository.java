package com.factorit.compra;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findByClienteDni(String clienteDni);

    List<Compra> findByClienteDniAndFechaGreaterThanEqual(String clienteDni, LocalDateTime from);

    List<Compra> findByClienteDniAndFechaBetween(String clienteDni, LocalDateTime from, LocalDateTime to);
}
