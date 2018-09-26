package util;

public class Constants {
	
	public static final String NO_RESULTS = "No results";
	public static final String API_VERSION = "/api/v1/";
	public static final String JSON = "Accept=application/json";
	
	//Category
	public static final String CATEGORIES = "categories";
	public static final String H = "?";
	public static final String IDE_CATEGORY = "ide_category=";
	public static final String URI_GET_CATEGORY_ID = Constants.API_VERSION + Constants.CATEGORIES +  Constants.H  + Constants.IDE_CATEGORY;
	
	
	//Clients
	public static final String CLIENTS = "clients/";
	
	//Products
	public static final String PRODUCTS = "products/";
	
	
	//Controllers
}


//http://localhost:8080/api/v1/categories%3Fide_category=4 