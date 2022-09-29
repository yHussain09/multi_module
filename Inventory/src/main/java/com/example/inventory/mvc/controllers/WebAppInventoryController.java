package com.example.inventory.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/app")
public class WebAppInventoryController {

    @GetMapping(value = "/inventory")
    public String getInventoryDashboard() {
        return "inventory-dashboard-view";
    }

    @GetMapping(value = "/category")
    public String getCategoryView() {
        return "category-view";
    }

    @GetMapping(value = "/product")
    public String getProductView() {
        return "product-view";
    }
}
