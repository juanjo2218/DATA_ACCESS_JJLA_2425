package com.jjla2425.da.unit5.sellerappspring.controllers;

import com.jjla2425.da.unit5.sellerappspring.model.entities.CategoriesEntity;
import com.jjla2425.da.unit5.sellerappspring.model.entities.ProductsEntity;
import com.jjla2425.da.unit5.sellerappspring.model.entities.SellersEntity;
import com.jjla2425.da.unit5.sellerappspring.services.CategoriesService;
import com.jjla2425.da.unit5.sellerappspring.services.ProductsService;
import com.jjla2425.da.unit5.sellerappspring.services.SellerProductService;
import com.jjla2425.da.unit5.sellerappspring.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private SellerProductService sellerProductService;
    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private ProductsService productsService;
    @GetMapping("/")
    public String showLogin(Model model)
    {
        return "login";
    }
    @GetMapping("/viewseller")
    public String viewSeller(@RequestParam(value = "cif", required = true) String CIF, Model model) {
        SellersEntity seller = sellerService.findSellerBycif(CIF).getBody();

        if (seller == null) {
            model.addAttribute("error", "Seller not found");
            return "error";
        }
        model.addAttribute("seller", seller);
        return "viewseller";
    }
    @PostMapping("/viewseller")
    public String updateSeller(@RequestParam(value = "cif", required = true) String CIF,SellersEntity seller) {
        sellerService.updateSeller(seller,CIF);
        return "viewseller";
    }

    @GetMapping("/addproduct")
    public String addProduct(@RequestParam(value = "categoryId", required = false) Integer categoryId, Model model) {
        List<CategoriesEntity> categories = categoriesService.findAllCategories();
        model.addAttribute("categories", categories);

        if (categoryId != null && categoryId != 0) {
            List<ProductsEntity> products = productsService.findAllProductsByCategoryAndActive(categoryId);
            model.addAttribute("products", products);
            model.addAttribute("selectedCategoryId", categoryId);  // Pasa el ID de la categoría seleccionada
        }

        return "addproduct";
    }

    @GetMapping("/addoffer")
    public String addOffer(@RequestParam(value = "cif", required = true) String CIF,Model model)
    {
        SellersEntity sellersEntity = sellerService.findSellerBycif(CIF).getBody();
        model.addAttribute("sellerproducts", sellerProductService.findAllSellerProductsByIdSellerAndActive(sellersEntity.getSellerId()));
        model.addAttribute("seller",sellerService.findSellerBycif(sellersEntity.getCif()));
        return "addoffer";
    }
}
