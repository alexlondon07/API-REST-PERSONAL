package github.io.alexlondon07.api.services.category;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.io.alexlondon07.api.dao.category.CategoryDao;
import github.io.alexlondon07.api.models.Category;

@Service("categoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryDao categoryDao;

	@Override
	public void saveCategory(Category category) {
		categoryDao.saveCategory(category);
	}

	@Override
	public void updateCategory(Category category) {
		categoryDao.updateCategory(category);
	}

	@Override
	public void deleteCategory(long idCategory) {
		categoryDao.deleteCategory(idCategory);
	}

	@Override
	public Category findById(long id) {
		return categoryDao.findById(id);
	}

	@Override
	public Category findName(String name) {
		return categoryDao.findName(name);
	}

	@Override
	public List<Category> findAllCategories() {
		return categoryDao.findAllCategories();
	}

	@Override
	public boolean isCategoryExist(Category category) {
		return categoryDao.isCategoryExist(category);
	}
}
