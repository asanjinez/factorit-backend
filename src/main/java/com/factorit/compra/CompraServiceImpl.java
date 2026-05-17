package com.factorit.compra;

import com.factorit.compra.dto.CompraResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompraServiceImpl implements ICompraService {

    @Autowired
    private ICompraRepository compraRepository;

    @Override
    public List<CompraResponse> findAll() {
        return List.of(); 
    }

    @Override
    public List<CompraResponse> findByDni(String dni) {
        return List.of(); 
    }

    @Override
    public List<CompraResponse> findByDniFrom(String dni, LocalDateTime from) {
        return List.of(); 
    }

    @Override
    public List<CompraResponse> findByDniPeriod(String dni, LocalDateTime from, LocalDateTime to) {
        return List.of(); 
    }
}
