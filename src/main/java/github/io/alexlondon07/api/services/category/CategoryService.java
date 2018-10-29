package github.io.alexlondon07.api.services.category;

import java.util.List;

import github.io.alexlondon07.api.models.Category;

public interface CategoryService {

	void saveCategory(Category category);
	
	void updateCategory(Category category);
	
	void deleteCategory( long idCategory);
	
	Category findById(long idCategory);
	
	Category findName(String name);
	
	List<Category> findAllCategories();
	
	boolean isCategoryExist(Category category);
}
