package com.jjla2425.da.unit5.sellerappspring.controllers;

import com.jjla2425.da.unit5.sellerappspring.model.DTOS.SellerExamenDTO;
import com.jjla2425.da.unit5.sellerappspring.model.entities.SellersEntity;
import com.jjla2425.da.unit5.sellerappspring.services.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-rest/Sellers")
public class SellerController
{
    @Autowired
    private SellerService sellerService;
    @GetMapping("/{cif}")
    public ResponseEntity<SellersEntity> findEmployeeByCIF(@PathVariable(value = "cif") String CIF)
    {
        return sellerService.findSellerBycif(CIF);
    }
    @PutMapping("/{cif}")
    public ResponseEntity<?> updateSeller(@RequestBody SellersEntity sellersEntity,@Valid
                                            @PathVariable(value = "cif")String CIF)
    {
        return sellerService.updateSeller(sellersEntity,CIF);
    }
    @GetMapping("/{idProduct}")
    public List<SellerExamenDTO> findAllSellerProductsByIdSeller(@PathVariable(value = "idProduct")int idProduct)
    {
        return sellerService.findSellersByProductId(idProduct);
    }
}
