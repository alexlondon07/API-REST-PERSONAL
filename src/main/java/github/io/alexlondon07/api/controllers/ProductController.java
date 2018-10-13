package github.io.alexlondon07.api.controllers;

import java.awt.TrayIcon.MessageType;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;
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
	
	public static final String PRODUCT_UPLOADED_FOLDER ="images/products/";
	
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
			return new ResponseEntity(new CustomErrorType(bindingResult.getAllErrors().toString(), MessageType.ERROR),HttpStatus.BAD_REQUEST);
		} else {
			
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
	
	
	// ------------------- POST Product Create Product Image----------------------------------------------------------------------------------
		@ApiOperation("Upload Product Image")
		@ApiResponses(value= { @ApiResponse(code =201, message = "OK", response = Product.class) })
		@RequestMapping(value="/products/images", method = RequestMethod.POST, headers=("content-type=multipart/form-data"))
		public ResponseEntity<byte[]> uploadProductImage(@RequestParam("ide_product") Long idProduct,  @RequestParam("image") MultipartFile multipartFile,  UriComponentsBuilder componentsBuilder){
			
			if (idProduct == null) {
				return new ResponseEntity(new CustomErrorType("Please set id_product", MessageType.INFO), HttpStatus.NO_CONTENT);
			}
			
			if (multipartFile.isEmpty()) {
				return new ResponseEntity(new CustomErrorType("Please select a file to upload", MessageType.INFO), HttpStatus.NO_CONTENT);
			}
			
			//Buscamos la información del Producto
			Product product = productService.findById(idProduct);
			if (product == null) {
				return new ResponseEntity(new CustomErrorType("Product with id_product: " + idProduct + " not dfound", MessageType.ERROR), HttpStatus.NOT_FOUND);
			}
			
			//Validamos si la imagen no está vacio o no está null, Eliminamos la imagen
			if (product.getImage() != null) {
				
				String fileName = product.getImage();
				Path path = Paths.get(fileName);
				File f = path.toFile();
				
				if (f.exists()) {
					f.delete();
				}
			}
			
			//Se procede a Subir la imagen
			try {
				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				String dateName = dateFormat.format(date);
				
				String fileName = String.valueOf(idProduct) + "-pictureProduct-" + dateName + "." + multipartFile.getContentType().split("/")[1];
				product.setImage(PRODUCT_UPLOADED_FOLDER + fileName);
				
				byte[] bytes = multipartFile.getBytes();
				Path path = Paths.get(PRODUCT_UPLOADED_FOLDER + fileName);
				Files.write(path, bytes);
				
				//Se sube la imagen del Producto
				productService.updateProduct(product);
				return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity(new CustomErrorType("Error during upload: " + multipartFile.getOriginalFilename(), MessageType.ERROR),HttpStatus.CONFLICT);
			}
	}
	
		
		// ------------------- Get Image---------------------------------------------------------------------------------------------------------------
		@ApiOperation("Get Product Image")
		@ApiResponses(value= { @ApiResponse(code =201, message = "OK", response = Product.class) })
		@RequestMapping(value="/products/{ide_product}/images", method = RequestMethod.GET)
		public ResponseEntity<byte[]> getProductImage(@PathVariable("ide_product") Long idProduct){
			
			if (idProduct == null) {
				 return new ResponseEntity(new CustomErrorType("Please set id_product ", MessageType.INFO), HttpStatus.NO_CONTENT);
			}
			
			Product product = productService.findById(idProduct);
			if (product == null) {
				return new ResponseEntity(new CustomErrorType("Product with id_product: " + idProduct + " not found", MessageType.INFO), HttpStatus.NOT_FOUND);
			}
			
			try {
				
				String fileName = product.getImage();
				Path path = Paths.get(fileName);
				File f = path.toFile();
				if (!f.exists()) {
					return new ResponseEntity(new CustomErrorType("Image not found", MessageType.ERROR),HttpStatus.CONFLICT);
				}
				
				byte[] image = Files.readAllBytes(path);
				return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity(new CustomErrorType("Error to show image", MessageType.ERROR),HttpStatus.CONFLICT);
			}
		
	}
		
	// ------------------- Delete Image------------------------------------------------------------------------------------------------------
	@ApiOperation("Delete Product Image")
	@ApiResponses(value= { @ApiResponse(code =201, message = "OK", response = Product.class) })
	@RequestMapping(value="/products/{ide_product}/images", method = RequestMethod.DELETE,headers = "Accept=application/json")
	public ResponseEntity<?> deleteProductImage(@PathVariable("ide_product") Long idProduct){
		
		if (idProduct == null) {
			 return new ResponseEntity(new CustomErrorType("Please set ide_product ", MessageType.INFO), HttpStatus.NO_CONTENT);
		}
		
		Product product = productService.findById(idProduct);
		if (product == null) {
			return new ResponseEntity(new CustomErrorType("Product with ide_product: " + idProduct + " not found", MessageType.ERROR), HttpStatus.NOT_FOUND);
		}
		
		if (product.getImage() == null) {
			 return new ResponseEntity(new CustomErrorType("This Product dosen't have image assigned", MessageType.ERROR), HttpStatus.NO_CONTENT);
		}
		
		String fileName = product.getImage();
		Path path = Paths.get(fileName);
		File file = path.toFile();
		if (file.exists()) {
			file.delete();
		}
		
		product.setImage("");
		productService.updateProduct(product);
		
		return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
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
