package github.io.alexlondon07.api.controllers;

import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import github.io.alexlondon07.api.models.Category;

import github.io.alexlondon07.api.services.category.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import util.Constants;
import util.CustomErrorType;
import util.DataValidator;

@Controller
@RequestMapping(Constants.API_VERSION)
@Api(value="CategoryControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

	//Variables Globales
	public static final Logger logger =  LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	CategoryService categoryService;
	
	DataValidator validator;
	
	
	// ------------------- GET Category----------------------------------------------------------------------------------
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Gets the categories with specific ide_category or name")
	@ApiResponses(value= { @ApiResponse(code =200, message = "OK", response = Category.class) })
	@RequestMapping(value="/categories", method = RequestMethod.GET, headers = Constants.JSON)
	public @ResponseBody ResponseEntity<List<Category>> getCategories(@RequestParam(value="name", required=false) String name, @RequestParam(value = "ide_category", required = false) Long ideCategory){
		
		//Search for category ide_category 
		if(ideCategory !=null && ideCategory > 0){
			Category category = categoryService.findById(ideCategory);
			if(category == null){
				return new ResponseEntity(new CustomErrorType("Category with ide_category " + ideCategory + " not found ", MessageType.ERROR ), HttpStatus.NOT_FOUND);
			}else {
				return new ResponseEntity(category, HttpStatus.OK);
			}
		}
		
		//Search for category name
		if(name !=null){
			Category category = categoryService.findName(name);
			if(category == null){
				return new ResponseEntity(new CustomErrorType("Category with name " + name + " not found ", MessageType.ERROR ), HttpStatus.NOT_FOUND);
			}else {
				return new ResponseEntity(category, HttpStatus.OK);
			}
		}
		
		List<Category> categories = new ArrayList<>();
		//If ide_category and name are null, Get all Categories in database
		if(name == null && ideCategory == null){
			categories = categoryService.findAllCategories();
			if(categories.isEmpty() ){
				return new ResponseEntity(new CustomErrorType(Constants.NO_RESULTS, MessageType.INFO ), HttpStatus.NOT_FOUND);
			}
		}
		
		return new ResponseEntity(categories, HttpStatus.OK);
	}
	
	// ------------------- POST Category----------------------------------------------------------------------------------
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Create category")
	@ApiResponses(value= { @ApiResponse(code =201, message = "OK", response = Category.class) })
	@RequestMapping(value="/categories", method = RequestMethod.POST, headers = Constants.JSON)
	public ResponseEntity<Category> createCategory(@Validated @RequestBody Category category, UriComponentsBuilder uriBuilder, BindingResult bindingResult){
		logger.info("Creating Category : {}", category.getName());
        
		if (bindingResult.hasErrors()) {
			return new ResponseEntity(new CustomErrorType(bindingResult.getAllErrors().toString(), MessageType.ERROR),HttpStatus.BAD_REQUEST);
		} else {
			
			//Validate if Category already exist.
			if(categoryService.isCategoryExist(category)){
				return new ResponseEntity(
						new CustomErrorType("Unable to Create. A Category with name " 
								+ category.getName() + " already exist." 
								, MessageType.INFO ),HttpStatus.CONFLICT);
			}
			
			//Create Category
			categoryService.saveCategory(category);	
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(uriBuilder.path(Constants.API_VERSION+ Constants.CATEGORIES + "{id}").buildAndExpand(category.getIdCategory()).toUri());		
			return new ResponseEntity(category, headers, HttpStatus.CREATED);
        }
	}
	
	// ------------------- UPDATE Category----------------------------------------------------------------------------------
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Update category with specific id")
	@ApiResponses(value= { @ApiResponse(code =200, message = "OK", response = Category.class) })
	@RequestMapping(value="/category/{id}", method = RequestMethod.PATCH, headers = Constants.JSON)
	public ResponseEntity<Category> updateCategory(@Validated @PathVariable("id") Long id, @RequestBody Category category, BindingResult bindingResult) {
		
		logger.info("Updating Category id {} ", id);
		
		if(id == null || id <= 0) {
			return new ResponseEntity(new CustomErrorType("idCategory is required", MessageType.ERROR), HttpStatus.CONFLICT);
		}
	
		if (bindingResult.hasErrors()) {
			return new ResponseEntity(new CustomErrorType(bindingResult.getAllErrors().toString(), MessageType.ERROR),HttpStatus.BAD_REQUEST);
		} else {
			
			category.setIdCategory(id);	
			
			//Validate if Category already exist.
			if(categoryService.isCategoryExist(category)){
				return new ResponseEntity(
						new CustomErrorType("Unable to Create. A Category with name " 
								+ category.getName() + " already exist." 
								, MessageType.INFO ),HttpStatus.CONFLICT);
			}
			
			Category currentCategory = categoryService.findById(id);
			if(currentCategory == null ){
				return new ResponseEntity(
						new CustomErrorType("Unable to update. A Category with id " + id + " not found.", MessageType.INFO ),
						HttpStatus.CONFLICT);
			}
			currentCategory.setName(category.getName());
			currentCategory.setDescription(category.getDescription());
			currentCategory.setEnable(category.getEnable());
			
			categoryService.updateCategory(currentCategory);
			return new ResponseEntity(currentCategory, HttpStatus.OK);
		}

	}
	
	
	// ------------------- DELETE Category----------------------------------------------------------------------------------
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Delete category with specific id")
	@ApiResponses(value= { @ApiResponse(code =200, message = "OK", response = Category.class) })
	@RequestMapping(value = "/category/{id}", method = RequestMethod.DELETE)
	public ResponseEntity deleteCategory(@PathVariable("id") Long id){
		
		logger.info("fetching % Deleting Course with id {} ", id);
	
		if(id == null || id <= 0) {
			return new ResponseEntity(new CustomErrorType("idCategory is required", MessageType.ERROR ), HttpStatus.CONFLICT);
		}

		Category category = categoryService.findById(id);
		
		if(category == null){
			return new ResponseEntity(
					new CustomErrorType("Unable to delete. Category with id " + id + " not found.", MessageType.INFO ),
					HttpStatus.NOT_FOUND);
		}
		
		categoryService.deleteCategory(id);
		return new ResponseEntity(HttpStatus.OK);
	}	
	
}

