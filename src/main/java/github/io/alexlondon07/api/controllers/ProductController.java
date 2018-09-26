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

import github.io.alexlondon07.api.models.Product;
import github.io.alexlondon07.api.services.product.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import util.Constants;
import util.CustomErrorType;

@Controller
@RequestMapping(Constants.API_VERSION)
@Api(value="ProductControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

	//Variables Globales
	public static final Logger logger =  LoggerFactory.getLogger(ProductController.class);
	private CustomErrorType customErrorType;
	
	@Autowired
	ProductService productService;
	
	// ------------------- GET Products----------------------------------------------------------------------------------
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Gets the products with specific ide_product or name")
	@ApiResponses(value= { @ApiResponse(code =200, message = "OK", response = Product.class) })
	@RequestMapping(value="/products", method = RequestMethod.GET, headers = Constants.JSON)
	public @ResponseBody ResponseEntity<List<Product>> getProducts(@RequestParam(value="name", required=false) String name, @RequestParam(value = "ide_product", required = false) Long ideProduct){
		
		//Search for product ide_product 
		if(ideProduct !=null && ideProduct > 0){
			Product product = productService.findById(ideProduct);
			if(product == null){
				return new ResponseEntity(new CustomErrorType("Product with ide_product " + ideProduct + " not found ", MessageType.ERROR ), HttpStatus.NOT_FOUND);
			}else {
				return new ResponseEntity(product, HttpStatus.OK);
			}
		}
		
		//Search for Product name
		if(name !=null){
			Product product = productService.findByName(name);
			if(product == null){
				return new ResponseEntity(new CustomErrorType("Product with name " + name + " not found ", MessageType.ERROR ), HttpStatus.NOT_FOUND);
			}else {
				return new ResponseEntity(product, HttpStatus.OK);
			}
		}
		
		List<Product> products = new ArrayList<>();
		//If ide_product and name are null, Get all Products in database
		if(name == null && ideProduct == null){
			products = productService.findAllProducts();
			if(products.isEmpty() ){
				return new ResponseEntity(new CustomErrorType(Constants.NO_RESULTS, MessageType.INFO ), HttpStatus.NOT_FOUND);
			}
		}
		
		return new ResponseEntity(products, HttpStatus.OK);
	}
	
	// ------------------- POST Product----------------------------------------------------------------------------------
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Create product")
	@ApiResponses(value= { @ApiResponse(code =201, message = "OK", response = Product.class) })
	@RequestMapping(value="/products", method = RequestMethod.POST, headers = Constants.JSON)
	public ResponseEntity<Product> createProduct(@Validated @RequestBody Product product, UriComponentsBuilder uriBuilder, BindingResult bindingResult){
		
		logger.info("Creating Product : {}", product.getName());
	        
		if (bindingResult.hasErrors()) {
			
			List<String> message = customErrorType.processValidationError(bindingResult);
			return new ResponseEntity(new CustomErrorType(message.toString(), MessageType.ERROR),HttpStatus.BAD_REQUEST);
			
		}else {
			
			//Validate if Product exist in the database
			if(productService.isProductExist(product)){
				return new ResponseEntity(
						new CustomErrorType("Unable to Create. A Product with name " 
								+ product.getName() + " already exist." 
								, MessageType.INFO ),HttpStatus.CONFLICT);
			}
			
			//Create Product
			productService.saveProduct(product);	
			
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(uriBuilder.path(Constants.API_VERSION+ Constants.PRODUCTS + "{id}").buildAndExpand(product.getIdeProduct()).toUri());
			return new ResponseEntity(product, headers, HttpStatus.CREATED);
        }
	} 
	
	
	// ------------------- UPDATE Product----------------------------------------------------------------------------------
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Update product with specific id")
	@ApiResponses(value= { @ApiResponse(code =200, message = "OK", response = Product.class) })
	@RequestMapping(value="/product/{id}", method = RequestMethod.PATCH, headers = Constants.JSON)
	public ResponseEntity<Product> updateUpdate(@Validated @PathVariable("id") Long id, @RequestBody Product product, BindingResult bindingResult) {
		
		logger.info("Updating Product id {} ", id);
		
		if(id == null || id <= 0) {
			return new ResponseEntity(new CustomErrorType("idProduct is required", MessageType.ERROR), HttpStatus.CONFLICT);
		}
	
		if (bindingResult.hasErrors()) {
			return new ResponseEntity(new CustomErrorType(bindingResult.getAllErrors().toString(), MessageType.ERROR),HttpStatus.BAD_REQUEST);
		} else {
			
			product.setIdeProduct(id);	
			
			//Validate if Product exist in the database
			if(productService.isProductExist(product)){
				return new ResponseEntity(
						new CustomErrorType("Unable to Create. A Product with name " 
								+ product.getName() + " already exist." 
								, MessageType.INFO ),HttpStatus.CONFLICT);
			}
			
			Product currentProduct = productService.findById(id);
			if(currentProduct == null ){
				return new ResponseEntity(
						new CustomErrorType("Unable to update. A Product with id " + id + " not found.", MessageType.INFO ),
						HttpStatus.CONFLICT);
			}
			currentProduct.setName(product.getName());
			currentProduct.setDescription(product.getDescription());
			currentProduct.setCost(product.getCost());
			currentProduct.setPrice(product.getPrice());
			currentProduct.setEnable(product.getEnable());
			currentProduct.setCategory(product.getCategory());
			
			productService.updateProduct(currentProduct);
			return new ResponseEntity(currentProduct, HttpStatus.OK);
		}
	}
	
	// ------------------- DELETE Product----------------------------------------------------------------------------------

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Delete product with specific id")
	@ApiResponses(value= { @ApiResponse(code =200, message = "OK", response = Product.class) })
	@RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
	public ResponseEntity deleteProduct(@PathVariable("id") Long id){
		
		logger.info("fetching % Deleting Product with id {} ", id);
	
		if(id == null || id <= 0) {
			return new ResponseEntity(new CustomErrorType("idProduct is required", MessageType.ERROR ), HttpStatus.CONFLICT);
		}

		Product product = productService.findById(id);
		
		if(product == null){
			return new ResponseEntity(
					new CustomErrorType("Unable to delete. Product with id " + id + " not found.", MessageType.INFO ),
					HttpStatus.NOT_FOUND);
		}
		
		productService.deleteProduct(id);
		return new ResponseEntity(HttpStatus.OK);
	}
}
