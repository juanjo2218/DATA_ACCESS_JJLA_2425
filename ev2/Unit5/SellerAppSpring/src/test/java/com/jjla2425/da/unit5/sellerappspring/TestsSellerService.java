package com.jjla2425.da.unit5.sellerappspring;

import com.jjla2425.da.unit5.sellerappspring.model.daos.ISellersDAO;
import com.jjla2425.da.unit5.sellerappspring.model.entities.SellersEntity;
import com.jjla2425.da.unit5.sellerappspring.services.SellerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = SellerAppSpringApplication.class)
class TestsSellerService {
    @Mock
    private ISellersDAO sellersDAO;
    @InjectMocks
    private SellerService sellerService;

    @Test
    void findSellerByCIF_Service() {
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
        when(sellersDAO.findByCif(CIF)).thenReturn(Optional.of(seller));
        Optional<SellersEntity> result = sellerService.findSellerBycif(CIF);
        assertTrue(result.isPresent());
        assertEquals(seller, result.get());
    }

    @Test
    void findSellerByCIFNotExists_Service() {
        String CIF = "A87654321";

        when(sellersDAO.findByCif(CIF)).thenReturn(Optional.empty());
        Optional<SellersEntity> result = sellerService.findSellerBycif(CIF);
        assertTrue(result.isEmpty());
    }
    @Test
    void updateSellerById_Service() {
        String CIF = "A12345678";
        SellersEntity existingSeller = new SellersEntity(
                1, CIF, "hola", "adsad", null, null, "1234",
                "810C9DBB52D04C20036DB08313ED055", null, false
        );

        SellersEntity updatedSeller = new SellersEntity(
                1, CIF, "nuevo nombre", "nueva empresa", "987654321", "email@test.com",
                "nuevaClave", "NEW_ENCRYPTED_PASSWORD", "http://nueva-url.com", true
        );

        when(sellersDAO.findByCif(CIF)).thenReturn(Optional.of(existingSeller));
        when(sellersDAO.save(any(SellersEntity.class))).thenReturn(updatedSeller);

        Optional<SellersEntity> result = sellerService.updateSeller(updatedSeller,CIF);
        assertTrue(result.isPresent());
        assertEquals(updatedSeller.getName(), result.get().getName());
        assertEquals(updatedSeller.getBusinessName(), result.get().getBusinessName());
        assertEquals(updatedSeller.getPhone(), result.get().getPhone());
        assertEquals(updatedSeller.getEmail(), result.get().getEmail());
        assertEquals(updatedSeller.getUrl(), result.get().getUrl());
        assertEquals(updatedSeller.isPro(), result.get().isPro());
    }
    @Test
    void updateSellerByIdNotExists_Service() {
        String CIF = "A87654321";
        SellersEntity updatedSeller = new SellersEntity(
                1099, "A87654321", "nuevo nombre", "nueva empresa", "987654321", "email@test.com",
                "nuevaClave", "NEW_ENCRYPTED_PASSWORD", "http://nueva-url.com", true
        );
        when(sellersDAO.findByCif(CIF)).thenReturn(Optional.empty());
        Optional<SellersEntity> result = sellerService.updateSeller(updatedSeller,CIF);
        assertTrue(result.isEmpty());
    }
    @Test
    void updateSellerInvalidData_Service() {
        String CIF = "A12345678";
        SellersEntity existingSeller = new SellersEntity(
                1, CIF, "hola", "adsad", null, null, "1234",
                "810C9DBB52D04C20036DB08313ED055", null, false
        );

        SellersEntity invalidSeller = new SellersEntity();

        when(sellersDAO.findByCif(CIF)).thenReturn(Optional.of(existingSeller));
        when(sellersDAO.save(any(SellersEntity.class))).thenReturn(invalidSeller);

        Optional<SellersEntity> result = sellerService.updateSeller(invalidSeller, CIF);
        assertTrue(result.isEmpty());
    }
}
