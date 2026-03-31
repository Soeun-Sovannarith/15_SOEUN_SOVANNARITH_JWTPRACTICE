package com.rith.jwtsecurity.Controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/v1/products")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    @GetMapping()
    public String getProducts(){
        return "This is products endpoint";
    }
}
