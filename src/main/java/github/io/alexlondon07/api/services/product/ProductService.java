package github.io.alexlondon07.api.services.product;

import java.util.List;

import github.io.alexlondon07.api.models.Product;

public interface ProductService {

	void saveProduct(Product product);
	
	void updateProduct(Product product);
	
	void deleteProduct(Long id);
	
	Product findById(Long id);
	
	Product findByName(String name);
	
	List<Product> findByIdCategory(Long id);
	
	List<Product> findAllProducts();
	
	boolean isProductExist(Product product);
}
