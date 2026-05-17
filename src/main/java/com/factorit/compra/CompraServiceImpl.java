package com.factorit.compra;

import com.factorit.compra.dto.CompraItemResponse;
import com.factorit.compra.dto.CompraResponse;
import com.factorit.descuento.DescuentoResponse;
import java.time.LocalDateTime;
import java.util.List;

import com.factorit.exception.DniInvalidException;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CompraServiceImpl implements ICompraService {

    @Autowired
    private ICompraRepository compraRepository;

    @Override
    public List<CompraResponse> findAll(Sort sort) {
        return compraRepository.findAll(sort).stream().map(this::toResponse).toList();
    }

    @Override
    public List<CompraResponse> findByDni(String dni, Sort sort) {
        vlidateDni(dni);

        return compraRepository.findByClienteDni(dni, sort).stream().map(this::toResponse).toList();
    }

    private static void vlidateDni(String dni) {
        if (dni == null || !dni.matches("\\d{8}")) {
            throw new DniInvalidException();
        }
    }

    @Override
    public List<CompraResponse> findByDniFrom(String dni, LocalDateTime from, Sort sort) {
        vlidateDni(dni);

        return compraRepository.findByClienteDniAndFechaGreaterThanEqual(dni, from, sort)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public List<CompraResponse> findByDniPeriod(String dni, LocalDateTime from, LocalDateTime to, Sort sort) {
        vlidateDni(dni);

        return compraRepository.findByClienteDniAndFechaBetween(dni, from, to, sort)
                .stream().map(this::toResponse).toList();
    }

    private CompraResponse toResponse(Compra compra) {
        List<CompraItemResponse> items = compra.getItems().stream()
                .map(i -> new CompraItemResponse(i.getProductoNombre(), i.getPrecioUnitario(), i.getCantidad()))
                .toList();
        List<DescuentoResponse> descuentos = compra.getDescuentos().stream()
                .map(d -> new DescuentoResponse(d.getTipo(), d.getNivel(), d.getDescripcion(),
                        d.getMonto(), d.getProductoNombre()))
                .toList();
        return new CompraResponse(compra.getId(), compra.getClienteDni(), compra.getFecha(),
                compra.getSubtotal(), compra.getDescuentoTotal(), compra.getTotal(),
                descuentos, items);
    }
}
