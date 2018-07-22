package github.io.alexlondon07.api.controllers;

import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import github.io.alexlondon07.api.models.Category;
import github.io.alexlondon07.api.services.category.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import util.CustomErrorType;
import util.DataValidator;

@Controller
@RequestMapping("/api/v1")
@Api(value="CategoryControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

	//Variables Globales
	public static final Logger logger =  LoggerFactory.getLogger(CategoryController.class);
	private static final String JSON = "Accept=application/json";
	
	@Autowired
	CategoryService categoryService;
	
	DataValidator validator;
	
	
	// ------------------- GET Category----------------------------------------------------------------------------------
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Gets the categories with specific ide_category or name")
	@ApiResponses(value= { @ApiResponse(code =200, message = "OK", response = Category.class) })
	@RequestMapping(value="/categories", method = RequestMethod.GET, headers = JSON)
	public @ResponseBody ResponseEntity<List<Category>> getCategories(@RequestParam(value="name", required=false) String name, @RequestParam(value = "ide_category", required = false) Long ideCategory){

		List<Category> categories = new ArrayList<>();
		
		//Search for client id_client 
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
		
		//If ide_category and name are null, Get all Categories in database
		if(name == null && ideCategory == null){
			categories = categoryService.findAllCategories();
			if(categories == null){
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		}
		
		return new ResponseEntity(categories, HttpStatus.OK);
	}
}
