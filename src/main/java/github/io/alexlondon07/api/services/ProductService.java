package github.io.alexlondon07.api.services;

import java.util.List;

import github.io.alexlondon07.api.models.Product;

public interface ProductService {

	void saveProduct(Product product);
	
	void updateProduct(Product product);
	
	void deleteProduct(long id);
	
	Product findById(long id);
	
	Product findByName(String name);
	
	List<Product> findByIdCategory(long id);
	
	List<Product> findAllProducts();
	
	boolean isProductExist(Product product);
}
