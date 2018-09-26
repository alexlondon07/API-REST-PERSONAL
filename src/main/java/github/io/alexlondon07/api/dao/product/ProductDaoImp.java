package github.io.alexlondon07.api.dao.product;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import github.io.alexlondon07.api.dao.AbstractSession;
import github.io.alexlondon07.api.models.Product;

@Repository
@Transactional
public class ProductDaoImp  extends AbstractSession implements ProductDao{

	public ProductDaoImp() {
	}
	
	@Override
	public void saveProduct(Product product) {
		getSession().persist(product);
	}

	@Override
	public void updateProduct(Product product) {
		getSession().update(product);
	}

	@Override
	public void deleteProduct(Long id) {
		if(id!=null && id > 0){
			Product product = findById(id);
			getSession().delete(product);
		}
	}

	@Override
	public Product findById(Long id) {
		return getSession().get(Product.class, id);
	}

	@Override
	public Product findByName(String name) {
		return (Product) getSession().createQuery(
				" from Product where name = :name")
				.setParameter("name", name)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findAllProducts() {
		return getSession().createQuery("from Product").list();
	}

	@Override
	public boolean isProductExist(Product product) {
		Product productResponse = findByName(product.getName());
		boolean vBalid = false;
		
		if(productResponse !=null) {
			if(product.getIdeProduct() != productResponse.getIdeProduct()) {
				vBalid = true;
			}
		}
		return vBalid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findByIdCategory(Long id) {
		return getSession().createQuery("from Product where ide_category = :ide_category")
				.setParameter("ide_category", id)
				.list();
	}
}
