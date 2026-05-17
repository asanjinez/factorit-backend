package com.factorit.compra;

import com.factorit.compra.dto.CompraResponse;
import java.time.LocalDateTime;
import java.util.List;

public interface ICompraService {

    List<CompraResponse> findAll();

    List<CompraResponse> findByDni(String dni);

    List<CompraResponse> findByDniFrom(String dni, LocalDateTime from);

    List<CompraResponse> findByDniPeriod(String dni, LocalDateTime from, LocalDateTime to);
}
