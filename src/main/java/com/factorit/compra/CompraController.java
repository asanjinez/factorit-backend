package com.factorit.compra;

import com.factorit.compra.dto.CompraResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    public List<CompraResponse> findAll(
            @RequestParam(defaultValue = "fecha") String sort,
            @RequestParam(defaultValue = "desc") String direction) {
        return compraService.findAll(toSort(sort, direction));
    }

    @GetMapping("/{dni}")
    public List<CompraResponse> findByDni(
            @PathVariable String dni,
            @RequestParam(defaultValue = "fecha") String sort,
            @RequestParam(defaultValue = "desc") String direction) {
        return compraService.findByDni(dni, toSort(sort, direction));
    }

    @GetMapping("/{dni}/from")
    public List<CompraResponse> findByDniFrom(
            @PathVariable String dni,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(defaultValue = "fecha") String sort,
            @RequestParam(defaultValue = "desc") String direction) {
        return compraService.findByDniFrom(dni, from.atStartOfDay(), toSort(sort, direction));
    }

    @GetMapping("/{dni}/periodo")
    public List<CompraResponse> findByPeriod(
            @PathVariable String dni,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "fecha") String sort,
            @RequestParam(defaultValue = "desc") String direction) {
        return compraService.findByDniPeriod(dni, from.atStartOfDay(), to.atTime(LocalTime.MAX),
                toSort(sort, direction));
    }

    private Sort toSort(String sort, String direction) {
        String property = "monto".equalsIgnoreCase(sort) ? "total" : "fecha";
        Sort.Direction sortDirection = "asc".equalsIgnoreCase(direction)
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        return Sort.by(sortDirection, property);
    }
}
