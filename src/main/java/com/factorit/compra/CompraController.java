package com.factorit.compra;

import com.factorit.compra.dto.CompraResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compras")
public class CompraController {

    @GetMapping
    public List<CompraResponse> findAll() {
        return List.of();
    }

    @GetMapping("/{dni}")
    public List<CompraResponse> findByDni(@PathVariable String dni) {
        return List.of();
    }

    @GetMapping("/{dni}/from")
    public List<CompraResponse> findByDniDesde(
            @PathVariable String dni,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from) {
        return List.of();
    }

    // Compras por dni en un período (From-To)
    @GetMapping("/{dni}/periodo")
    public List<CompraResponse> findByDniPeriodo(
            @PathVariable String dni,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return List.of();
    }
}
