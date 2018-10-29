package github.io.alexlondon07.api.dao.category;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import github.io.alexlondon07.api.dao.AbstractSession;
import github.io.alexlondon07.api.models.Category;
import github.io.alexlondon07.api.models.Client;

@Repository
@Transactional
public class CategoryDaoImpl extends AbstractSession implements CategoryDao {

	public CategoryDaoImpl() {
		
	}
	
	@Override
	public void saveCategory(Category category) {
		getSession().persist(category);		
	}

	@Override
	public void updateCategory(Category category) {
		getSession().update(category);
	}

	@Override
	public void deleteCategory(Long id) {
		if(id!=null && id > 0){
			Category category = findById(id);
			getSession().delete(category);
		}
	}

	@Override
	public Category findById(Long id) {
		return getSession().get(Category.class, id);
	}

	@Override
	public Category findName(String name) {
		
		return (Category) getSession().createQuery(
				"from Category where name = :name")
			.setParameter("name", name)
			.uniqueResult();
	}

	@Override
	public List<Category> findAllCategories() {
		return getSession().createQuery("from Category Order by ide_category DESC").list();
	}

	@Override
	public boolean isCategoryExist(Category category) {
		
		Category categoryResponse = findName(category.getName());
		boolean vBalid = false;
		if(categoryResponse !=null) {
			if(category.getIdCategory() != categoryResponse.getIdCategory()) {
				vBalid =  true;	
			}
		}
		return vBalid;
	}
	
}
