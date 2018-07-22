package github.io.alexlondon07.api.dao.category;

import java.util.List;

import github.io.alexlondon07.api.models.Category;

public interface CategoryDao {

	void saveCategory(Category category);
	
	void updateCategory(Category category);
	
	void deleteCategory( Long idCategory);
	
	Category findById(Long idCategory);
	
	Category findName(String name);
	
	List<Category> findAllCategories();
	
	boolean isCategoryExist(Category category);
}
