package com.jjla2425.da.unit5.sellerappspring.controllers;

import com.jjla2425.da.unit5.sellerappspring.model.entities.SellersEntity;
import com.jjla2425.da.unit5.sellerappspring.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class WebController {
    @Autowired
    private SellerService sellerService;


    @GetMapping("/")
    public String showLogin(Model model)
    {
        return "login";
    }
    @GetMapping("/viewseller/{cif}")
    public String viewSeller(@PathVariable(value = "cif") String CIF, Model model)
    {
        SellersEntity seller = sellerService.findSellerBycif(CIF).getBody();

        // Si el seller no se encuentra, puedes redirigir a una página de error o mostrar un mensaje
        if (seller == null) {
            model.addAttribute("error", "Seller not found");
            return "error";  // Redirigir a una página de error
        }

        model.addAttribute("seller", seller);  // Pasar el objeto seller a la plantilla
        return "viewseller";  // Nombre de
    }
    @GetMapping("/addproduct")
    public String addProduct(String CIF,Model model)
    {
        model.addAttribute("seller",sellerService.findSellerBycif(CIF));
        return "addproduct";
    }
    @GetMapping("/addoffer")
    public String addOffer(String CIF,Model model)
    {
        model.addAttribute("seller",sellerService.findSellerBycif(CIF));
        return "addoffer";
    }
}
