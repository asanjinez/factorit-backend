package com.factorit.compra;

import com.factorit.compra.dto.CompraResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private ICompraService compraService;

    @GetMapping
    public List<CompraResponse> findAll() {
        return compraService.findAll();
    }

    @GetMapping("/{dni}")
    public List<CompraResponse> findByDni(@PathVariable String dni) {
        return compraService.findByDni(dni);
    }

    @GetMapping("/{dni}/from")
    public List<CompraResponse> findByDniFrom(
            @PathVariable String dni,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from) {
        return compraService.findByDniFrom(dni, from);
    }

    @GetMapping("/{dni}/periodo")
    public List<CompraResponse> findByPeriod(
            @PathVariable String dni,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return compraService.findByDniPeriod(dni, from, to);
    }
}
