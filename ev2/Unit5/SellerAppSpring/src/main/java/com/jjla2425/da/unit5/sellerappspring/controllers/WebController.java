package com.jjla2425.da.unit5.sellerappspring.controllers;

import com.jjla2425.da.unit5.sellerappspring.MyUtils;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

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
    public String showLogin(@AuthenticationPrincipal UserDetails user) {
        return "login";
    }

    @GetMapping("/viewseller")
    public String viewSeller(@AuthenticationPrincipal UserDetails user, Model model) {
        SellerDTO seller = SellerDTO.toDTO(sellerService.findSellerBycif(user.getUsername()).get());

        model.addAttribute("seller", seller);
        return "viewseller";
    }

    @PostMapping("/viewseller")
    public String updateSeller(@AuthenticationPrincipal UserDetails user,
                               @Valid @ModelAttribute("seller") SellerDTO sellerDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            model.addAttribute("seller", sellerDTO);
            return "viewseller";
        }

        if (sellerDTO.equals(SellerDTO.toDTO(sellerService.findSellerBycif(user.getUsername()).get()))) {
            redirectAttributes.addFlashAttribute("successMessage", "No changes detected.");
            return "redirect:/viewseller";
        }
        sellerService.updateSeller(sellerDTO, user.getUsername());
        redirectAttributes.addFlashAttribute("successMessage", "Seller data updated successfully!");
        return "redirect:/viewseller";
    }


    @GetMapping("/addproduct")
    public String addProduct(@AuthenticationPrincipal UserDetails user, @RequestParam(value = "categoryId", required = false) Integer categoryId, Model model) {
        SellersEntity seller = sellerService.findSellerBycif(user.getUsername()).get();
        List<CategoriesEntity> categories = categoriesService.findAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("sellerproduct", new SellerProductsEntity(seller.getSellerId()));
        List<ProductsEntity> products = null;
        if (categoryId != null && categoryId != 0) {
            products = productsService.getProductsSellerRemaining(categoryId, seller.getSellerId());
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
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            model.addAttribute("categories", categoriesService.findAllCategories());
            return "addproduct";
        }
        sellerProductService.saveSellerProduct(sellerProduct);
        redirectAttributes.addFlashAttribute("successMessage", "SellerProduct added successfully!");
        return "redirect:/addproduct";
    }

    @GetMapping("/addoffer")
    public String addOffer(@AuthenticationPrincipal UserDetails user,
                           @RequestParam(value = "productId", required = false) Integer productId,
                           Model model) {
        SellersEntity seller = sellerService.findSellerBycif(user.getUsername()).get();
        SellerProductsEntity sellerProductsEntity = new SellerProductsEntity(seller.getSellerId());
        if (productId != null && productId != 0) {
            Optional<SellerProductsEntity> optionalSellerProduct = sellerProductService.findBySellerIdAndProductId(seller.getSellerId(), productId);
            if (optionalSellerProduct.isPresent()) {
                sellerProductsEntity = optionalSellerProduct.get();
            }
        }
        model.addAttribute("sellerproduct", sellerProductsEntity);
        model.addAttribute("sellerproducts", productsService.getProductsBySellerID(seller.getSellerId()));
        model.addAttribute("sellerproductSelected", sellerProductsEntity);
        return "addoffer";
    }

    @PostMapping("/addoffer")
    public String addOffer(@AuthenticationPrincipal UserDetails user,
                           RedirectAttributes redirectAttributes,
                           @Valid @ModelAttribute("sellerproduct") SellerProductsEntity sellerProduct,
                           BindingResult bindingResult,
                           Model model) {


        SellersEntity seller = sellerService.findSellerBycif(user.getUsername()).get();
        if (bindingResult.hasErrors()) {
            model.addAttribute("sellerproducts", productsService.getProductsBySellerID(seller.getSellerId()));
            return "addoffer";
        }
        LocalDate offerStartDate = sellerProduct.getOfferStartDate();
        LocalDate offerEndDate = sellerProduct.getOfferEndDate();
        LocalDate today = LocalDate.now();
        if (offerStartDate.isBefore(today)) {
            bindingResult.rejectValue("offerStartDate", "offerStartDate.invalid", "Offer start date cannot be in the past.");
        }
        if (offerEndDate.isBefore(offerStartDate)) {
            bindingResult.rejectValue("offerEndDate", "offerEndDate.invalid", "Offer end date cannot be before the start date.");
        }
        if (offerStartDate.isAfter(offerEndDate)) {
            bindingResult.rejectValue("offerStartDate", "offerStartDate.invalid", "Offer start date cannot be after the end date.");
        }
        if (MyUtils.checkOfferDateOverlap(seller.getSellerId(), offerStartDate, offerEndDate, sellerProduct.getProductId())) {
            bindingResult.rejectValue("offerStartDate", "offerDates.conflict", "An existing offer already overlaps with the selected dates.");
        }
        int difference = (int)ChronoUnit.DAYS.between(offerStartDate, offerEndDate);
        double maxdiscount = MyUtils.getMaxDiscount(difference);
        if (maxdiscount < sellerProduct.getDiscount())
            bindingResult.rejectValue("discount", "offerPrice.conflict", "For "+ difference +  " days the max discount is " + maxdiscount + ".");
        if (bindingResult.hasErrors()) {
            model.addAttribute("sellerproducts", productsService.getProductsBySellerID(seller.getSellerId()));
            return "addoffer";
        }
        sellerProductService.updateSellerProduct(sellerProduct);
        redirectAttributes.addFlashAttribute("successMessage", "SellerProductOffer updated successfully!");
        return "redirect:/addoffer";
    }
    @GetMapping("/viewAllProducts")
    public String viewAllProducts(@AuthenticationPrincipal UserDetails user,Model model, @RequestParam(value = "check", required = false) boolean check)
    {
        SellersEntity seller = sellerService.findSellerBycif(user.getUsername()).get();
        List<SellerProductsEntity> sellerProductsEntities = sellerProductService.findAllSellerProductsByIdSellerAndActive(seller.getSellerId());
        List<ProductsEntity> productsEntities = productsService.getProductsBySellerID(seller.getSellerId());
        if (check)
        {
            sellerProductsEntities = sellerProductService.findAllSellerProductsByIdSellerAndActiveExamen(seller.getSellerId(), true);
        }
        for (int i = 0; i <sellerProductsEntities.size() ; i++) {
            for (int j = 0; j <productsEntities.size() ; j++) {
                if (sellerProductsEntities.get(i).getProductId() == productsEntities.get(j).getProductId())
                {
                    sellerProductsEntities.get(i).setProductName(productsEntities.get(j).getProductName());
                    sellerProductsEntities.get(i).setCategory(categoriesService.findCategoryById(productsEntities.get(j).getCategoryId()).getBody().getCategoryName());
                }
            }
        }
        model.addAttribute("checkSelected", check);
        model.addAttribute("sellerproducts",sellerProductsEntities);
        return "viewAllProducts";
    }
}



