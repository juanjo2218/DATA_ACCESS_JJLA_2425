package com.jjla2425.da.unit5.sellerappspring.controllers;

import com.jjla2425.da.unit5.sellerappspring.model.DTOS.SellerDTO;
import com.jjla2425.da.unit5.sellerappspring.model.entities.CategoriesEntity;
import com.jjla2425.da.unit5.sellerappspring.model.entities.ProductsEntity;
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
    public String addOffer(@AuthenticationPrincipal UserDetails user,Model model)
    {
        SellersEntity sellersEntity = sellerService.findSellerBycif(user.getUsername()).getBody();
        model.addAttribute("sellerproducts", sellerProductService.findAllSellerProductsByIdSellerAndActive(sellersEntity.getSellerId()));
        model.addAttribute("seller",sellerService.findSellerBycif(sellersEntity.getCif()));
        return "addoffer";
    }
}



