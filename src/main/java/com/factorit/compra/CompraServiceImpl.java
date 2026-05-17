package com.factorit.compra;

import com.factorit.compra.dto.CompraItemResponse;
import com.factorit.compra.dto.CompraResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CompraServiceImpl implements ICompraService {

    @Autowired
    private ICompraRepository compraRepository;

    @Override
    public List<CompraResponse> findAll() {
        return compraRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<CompraResponse> findByDni(String dni) {
        return compraRepository.findByClienteDni(dni).stream().map(this::toResponse).toList();
    }

    @Override
    public List<CompraResponse> findByDniFrom(String dni, LocalDateTime from) {
        return compraRepository.findByClienteDniAndFechaGreaterThanEqual(dni, from)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public List<CompraResponse> findByDniPeriod(String dni, LocalDateTime from, LocalDateTime to) {
        return compraRepository.findByClienteDniAndFechaBetween(dni, from, to)
                .stream().map(this::toResponse).toList();
    }

    private CompraResponse toResponse(Compra compra) {
        List<CompraItemResponse> items = compra.getItems().stream()
                .map(i -> new CompraItemResponse(i.getProductoNombre(), i.getPrecioUnitario(), i.getCantidad()))
                .toList();
        return new CompraResponse(compra.getId(), compra.getClienteDni(), compra.getFecha(),
                compra.getTotal(), items);
    }
}
