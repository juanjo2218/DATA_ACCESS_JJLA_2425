package com.jjla2425.da.unit5.sellerappspring;

import com.jjla2425.da.unit5.sellerappspring.controllers.SellerController;
import com.jjla2425.da.unit5.sellerappspring.model.daos.ISellersDAO;
import com.jjla2425.da.unit5.sellerappspring.model.entities.SellersEntity;
import com.jjla2425.da.unit5.sellerappspring.services.SellerService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest()
class TestsSellerController {
    @Mock
    private SellerService sellerService;
    @InjectMocks
    private SellerController sellerController;

    @Test
    void findSellerByCIF_Controller() {
        String CIF = "A12345678";
        SellersEntity seller = new SellersEntity(
                1,  // seller_id
                CIF,  // cif
                "hola",  // name
                "adsad",  // business_name
                null,  // phone
                null,  // email
                "1234",  // plain_password
                "810C9DBB52D04C20036DB08313ED055",  // password
                null,  // url
                false  // pro
        );

        when(sellerService.findSellerBycif(CIF)).thenReturn(Optional.of(seller));

        ResponseEntity<SellersEntity> result =
                sellerController.findEmployeeByCIF(CIF);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(seller, result.getBody());
    }
    @Test
    void findSellerByCIFNotExists_Controller() {
        String CIF = "A87654321";

        when(sellerService.findSellerBycif(CIF)).thenReturn(Optional.empty());

        // Request
        ResponseEntity<SellersEntity> result = sellerController.findEmployeeByCIF(CIF);

        // Assert
        assertEquals(404, result.getStatusCodeValue());
    }
    @Test
    void updateSellerById_Controller() {
        String CIF = "A12345678";
        SellersEntity existingSeller = new SellersEntity(
                1, CIF, "hola", "adsad", null, null, "1234",
                "810C9DBB52D04C20036DB08313ED055", null, false
        );

        SellersEntity updatedSeller = new SellersEntity(
                1, CIF, "nuevo nombre", "nueva empresa", "987654321", "email@test.com",
                "nuevaClave", "NEW_ENCRYPTED_PASSWORD", "http://nueva-url.com", true
        );

        when(sellerService.findSellerBycif(CIF)).thenReturn(Optional.of(existingSeller));
        when(sellerService.updateSeller(updatedSeller,CIF)).thenReturn(Optional.of(updatedSeller));

        ResponseEntity<?> result = sellerController.updateSeller(updatedSeller,CIF);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Update", result.getBody());
    }
    @Test
    void updateSellerByIdNotExists_Controller() {
        String CIF = "A87654321";
        SellersEntity updatedSeller = new SellersEntity(
                1099, "A87654321", "nuevo nombre", "nueva empresa", "987654321", "email@test.com",
                "nuevaClave", "NEW_ENCRYPTED_PASSWORD", "http://nueva-url.com", true
        );
        when(sellerService.findSellerBycif(CIF)).thenReturn(Optional.empty());
        ResponseEntity<?> result = sellerController.updateSeller(updatedSeller,CIF);
        assertEquals(404, result.getStatusCodeValue());
    }


    @Test
    void updateSellerInvalidData_Controller() {
        String CIF = "A12345678";
        SellersEntity invalidSeller = new SellersEntity();
        invalidSeller.setCif(CIF);

        when(sellerService.updateSeller(invalidSeller,CIF)).thenReturn(Optional.empty());

        ResponseEntity<?> result = sellerController.updateSeller(invalidSeller, CIF);
        assertEquals(404, result.getStatusCodeValue());
    }

}
