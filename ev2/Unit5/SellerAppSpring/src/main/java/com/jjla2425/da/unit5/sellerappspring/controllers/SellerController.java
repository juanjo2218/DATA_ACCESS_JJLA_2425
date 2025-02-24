package com.jjla2425.da.unit5.sellerappspring.controllers;

import com.jjla2425.da.unit5.sellerappspring.model.DTOS.SellerExamenDTO;
import com.jjla2425.da.unit5.sellerappspring.model.entities.SellersEntity;
import com.jjla2425.da.unit5.sellerappspring.services.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-rest/Sellers")
public class SellerController
{
    @Autowired
    private SellerService sellerService;
    @GetMapping("/{cif}")
    public ResponseEntity<SellersEntity> findEmployeeByCIF(@PathVariable(value = "cif") String CIF)
    {
        Optional<SellersEntity> seller = sellerService.findSellerBycif(CIF);
        return seller.isPresent()  ? ResponseEntity.ok().body(seller.get()) : ResponseEntity.notFound().build();
    }
    @PutMapping("/{cif}")
    public ResponseEntity<?> updateSeller(@RequestBody SellersEntity sellersEntity,@Valid
                                            @PathVariable(value = "cif")String CIF)
    {
        Optional<SellersEntity> seller = sellerService.updateSeller(sellersEntity,CIF);
        return seller.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok().body("Update");
    }
    @GetMapping("/{idProduct}")
    public List<SellerExamenDTO> findAllSellerProductsByIdSeller(@PathVariable(value = "idProduct")int idProduct)
    {
        return sellerService.findSellersByProductId(idProduct);
    }
}
