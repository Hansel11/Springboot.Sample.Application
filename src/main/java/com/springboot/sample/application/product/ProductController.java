package com.springboot.sample.application.product;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProduct(){
        return new ResponseEntity<>(productService.getAllProduct(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ProductDto> getProductById(@RequestParam @NotNull Long productId){
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestBody ProductDto product){
        return ResponseEntity.ok(productService.addProduct(product, userDetails.getUsername()));
    }

    @PutMapping
    public ResponseEntity<ProductDto> editProduct(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestParam @NotNull Long productId,
                                            @RequestBody ProductDto product){
        return ResponseEntity.ok(productService.editProduct(productId, product, userDetails.getUsername()));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProduct(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestParam @NotNull Long productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId, userDetails.getUsername()));
    }
}
