package com.factorit.compra;

import com.factorit.compra.dto.CompraResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface ICompraService {
    List<CompraResponse> findAll(Sort sort);
    List<CompraResponse> findByDni(String dni, Sort sort);
    List<CompraResponse> findByDniFrom(String dni, LocalDateTime from, Sort sort);

    List<CompraResponse> findByDniPeriod(String dni, LocalDateTime from, LocalDateTime to, Sort sort);
}
