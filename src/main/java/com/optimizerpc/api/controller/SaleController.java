package com.optimizerpc.api.controller;

import com.optimizerpc.api.entity.AppUser;
import com.optimizerpc.api.entity.Sale;
import com.optimizerpc.api.repository.AppUserRepository;
import com.optimizerpc.api.repository.SaleRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class SaleController {

    private final SaleRepository saleRepository;
    private final AppUserRepository userRepository;

    public SaleController(SaleRepository saleRepository, AppUserRepository userRepository) {
        this.saleRepository = saleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/v0/s/sale")
    public List<Sale> findSales() {
        return saleRepository.findAllByOrderByDateAsc();
    }

    @PostMapping("/v0/sale")
    @ResponseStatus(HttpStatus.CREATED)
    public Sale createSale(
            @RequestParam Double price,
            @RequestParam(required = false) UUID userId
    ) {
        if (price == null || price <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Precio invalido");
        }

        AppUser user = null;
        if (userId != null) {
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        }

        Sale sale = new Sale();
        sale.setPrice(price);
        sale.setUser(user);

        return saleRepository.save(sale);
    }
}