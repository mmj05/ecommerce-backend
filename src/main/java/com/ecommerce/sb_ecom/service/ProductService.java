package com.ecommerce.sb_ecom.service;

import com.ecommerce.sb_ecom.payload.ProductDTO;
import com.ecommerce.sb_ecom.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO, Long categoryId);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse getProductsByKeyWord(String keyWord, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProduct(ProductDTO productDTO, Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
