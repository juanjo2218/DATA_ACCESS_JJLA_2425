package com.jjla2425.da.unit5.sellerappspring.controllers;

import com.jjla2425.da.unit5.sellerappspring.model.DTOS.SellerDTO;
import com.jjla2425.da.unit5.sellerappspring.model.entities.CategoriesEntity;
import com.jjla2425.da.unit5.sellerappspring.model.entities.ProductsEntity;
import com.jjla2425.da.unit5.sellerappspring.model.entities.SellerProductsEntity;
import com.jjla2425.da.unit5.sellerappspring.model.entities.SellersEntity;
import com.jjla2425.da.unit5.sellerappspring.services.CategoriesService;
import com.jjla2425.da.unit5.sellerappspring.services.ProductsService;
import com.jjla2425.da.unit5.sellerappspring.services.SellerProductService;
import com.jjla2425.da.unit5.sellerappspring.services.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller()
public class WebController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private SellerProductService sellerProductService;
    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private ProductsService productsService;
    @GetMapping("/login")
    public String showLogin(@AuthenticationPrincipal UserDetails user)
    {
        return "login";
    }
    @GetMapping("/viewseller")
    public String viewSeller(@AuthenticationPrincipal UserDetails user , Model model) {
        SellerDTO seller = SellerDTO.toDTO(sellerService.findSellerBycif(user.getUsername()).getBody()) ;

        model.addAttribute("seller", seller);
        return "viewseller";
    }
    @PostMapping("/viewseller")
    public String updateSeller(@AuthenticationPrincipal UserDetails user,
                               @Valid @ModelAttribute("seller") SellerDTO sellerDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Model model)
    {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            model.addAttribute("seller", sellerDTO);
            return "viewseller";
        }

        if (sellerDTO.equals(SellerDTO.toDTO(sellerService.findSellerBycif(user.getUsername()).getBody()))) {
            redirectAttributes.addFlashAttribute("successMessage", "No changes detected.");
            return "redirect:/viewseller";
        }
        sellerService.updateSeller(sellerDTO, user.getUsername());
        redirectAttributes.addFlashAttribute("successMessage", "Seller data updated successfully!");
        return "redirect:/viewseller";
    }



    @GetMapping("/addproduct")
    public String addProduct(@AuthenticationPrincipal UserDetails user,@RequestParam(value = "categoryId", required = false) Integer categoryId, Model model) {
        SellersEntity seller = sellerService.findSellerBycif(user.getUsername()).getBody();
        List<CategoriesEntity> categories = categoriesService.findAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("sellerproduct", new SellerProductsEntity(seller.getSellerId()));
        List<ProductsEntity> products = null;
        if (categoryId != null && categoryId != 0) {
            products = productsService.getProductsSellerRemaining(categoryId,seller.getSellerId());
            model.addAttribute("products", products);
            model.addAttribute("selectedCategoryId", categoryId);
        }
        model.addAttribute("products", products);
        return "addproduct";
    }
    @PostMapping("/addproduct")
    public String addProduct(@AuthenticationPrincipal UserDetails user,
                             RedirectAttributes redirectAttributes,
                             @Valid @ModelAttribute("sellerproduct") SellerProductsEntity sellerProduct,
                             BindingResult bindingResult,Model model)
    {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            model.addAttribute("categories",  categoriesService.findAllCategories());
            return "addproduct";
        }
        sellerProductService.saveSellerProduct(sellerProduct);
        redirectAttributes.addFlashAttribute("successMessage", "SellerProduct added successfully!");
        return "redirect:/addproduct";
    }
    @GetMapping("/addoffer")
    public String addOffer(@AuthenticationPrincipal UserDetails user,Model model)
    {
        SellersEntity seller = sellerService.findSellerBycif(user.getUsername()).getBody();
        model.addAttribute("sellerproduct", new SellerProductsEntity());
        model.addAttribute("sellerproducts", productsService.getProductsBySellerID(seller.getSellerId()));
        return "addoffer";
    }
    @PostMapping("/addoffer")
    public String addOffer(@AuthenticationPrincipal UserDetails user,
                             RedirectAttributes redirectAttributes,
                             @Valid @ModelAttribute("sellerproduct") SellerProductsEntity sellerProduct,
                             BindingResult bindingResult,Model model)
    {
        SellersEntity seller = sellerService.findSellerBycif(user.getUsername()).getBody();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            model.addAttribute("sellerproducts", productsService.getProductsBySellerID(seller.getSellerId()));
            return "addoffer";
        }
        sellerProduct.setSellerId(seller.getSellerId());
        sellerProductService.updateSellerProduct(sellerProduct);
        redirectAttributes.addFlashAttribute("successMessage", "SellerProductOffer updated successfully!");
        return "redirect:/addoffer";
    }
}



