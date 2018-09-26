package github.io.alexlondon07.api.services.product;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.io.alexlondon07.api.dao.product.ProductDao;
import github.io.alexlondon07.api.models.Product;

@Service("productService")
@Transactional
public class ProductServiceImp implements ProductService {
	
	@Autowired
	private ProductDao productDao;

	@Override
	public void saveProduct(Product product) {
		productDao.saveProduct(product);
	}

	@Override
	public void updateProduct(Product product) {
		productDao.updateProduct(product);
	}

	@Override
	public void deleteProduct(Long id) {
		productDao.deleteProduct(id);
	}

	@Override
	public Product findById(Long id) {
		return productDao.findById(id);
	}

	@Override
	public Product findByName(String name) {
		return productDao.findByName(name);
	}

	@Override
	public List<Product> findByIdCategory(Long id) {
		return productDao.findByIdCategory(id);
	}

	@Override
	public List<Product> findAllProducts() {
		return productDao.findAllProducts();
	}

	@Override
	public boolean isProductExist(Product product) {
		return productDao.isProductExist(product);
	}

}
