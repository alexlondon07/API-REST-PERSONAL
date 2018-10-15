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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import github.io.alexlondon07.api.models.Order;
import github.io.alexlondon07.api.services.order.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import util.Constants;
import util.CustomErrorType;

@RestController
@RequestMapping(Constants.API_VERSION)
@Api(value="ClientControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
	
	//Variables Globales
	public static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	private CustomErrorType customErrorType;
	
	@Autowired
	OrderService orderService;
	
	// ------------------- GET Client----------------------------------------------------------------------------------
	
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@ApiOperation("Gets the orders with specific idOrder or idClient")
		@ApiResponses(value= { @ApiResponse(code =200, message = "OK", response = Order.class) })
		@RequestMapping(value="/orders", method = RequestMethod.GET, headers = Constants.JSON)
		public @ResponseBody ResponseEntity<List<Order>> getOrders(@RequestParam(value="ide_client", required=false) Long ideClient, @RequestParam(value = "ide_order", required = false) Long idOrder){

			//Search for  ide_order
			if(idOrder !=null && idOrder > 0){
				Order order =  orderService.findById(idOrder);
				if(order == null){
					return new ResponseEntity(new CustomErrorType("Order with idOrder " + idOrder + " not found ", MessageType.ERROR ), HttpStatus.NOT_FOUND);
				}else {
					return new ResponseEntity(order, HttpStatus.OK);
				}
			}
			
			//Search for ide_client
			if(ideClient !=null){
				List<Order> orders = orderService.findByIdeClient(ideClient);
				if(orders == null){
					return new ResponseEntity(new CustomErrorType("Orders wiht ideClient name " + ideClient + " not found ", MessageType.ERROR ), HttpStatus.NOT_FOUND);
				}else {
					return new ResponseEntity(orders, HttpStatus.OK);
				}
			}
			
			//If ide_client and ide_order are null, Get all Orders in database
			List<Order> orders = new ArrayList<>();
			if(idOrder == null && ideClient == null){
				orders = orderService.findAllOrders();
				if(orders.isEmpty()){				
					return new ResponseEntity(new CustomErrorType(Constants.NO_RESULTS, MessageType.INFO ), HttpStatus.NOT_FOUND);
				}
			}
			
			return new ResponseEntity(orders, HttpStatus.OK);
		}
}
