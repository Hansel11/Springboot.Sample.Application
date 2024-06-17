package com.springboot.sample.application.product;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper mapper = new ModelMapper();

    public Product findProductById(Long id){
        return productRepository.findByIdAndDataStatusNot(id, 'D').orElseThrow(
                () -> new RuntimeException("Product not found!")
        );
    }

    public List<ProductDto> getAllProduct(){
        List<Product> products = productRepository.findByDataStatusNot('D');
        return products.stream().map(
                product -> mapper.map(product, ProductDto.class)
        ).toList();
    }

    public ProductDto getProductById(Long id){
        return mapper.map(findProductById(id), ProductDto.class);
    }

    public ProductDto addProduct(ProductDto request, String createdBy){
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .tax(request.getTax())
                .createdAt(LocalDate.now())
                .createdBy(createdBy)
                .dataStatus('A')
                .build();
        return mapper.map(productRepository.save(product), ProductDto.class);
    }

    public ProductDto editProduct(Long productId, ProductDto request, String updatedBy){
        Product product = findProductById(productId);
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setTax(request.getTax());
        product.setUpdatedAt(LocalDate.now());
        product.setUpdatedBy(updatedBy);
        return mapper.map(productRepository.save(product), ProductDto.class);
    }

    public String deleteProduct(Long productId, String deletedBy){
        Product product = findProductById(productId);
        product.setUpdatedBy(deletedBy);
        product.setUpdatedAt(LocalDate.now());
        product.setDataStatus('D');
        productRepository.save(product);
        return "Product successfully deleted!";
    }
}
