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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import github.io.alexlondon07.api.models.Client;
import github.io.alexlondon07.api.services.client.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import util.Constants;
import util.CustomErrorType;
import util.DataValidator;

@RestController
@RequestMapping(Constants.API_VERSION)
@Api(value="ClientControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {
	
	//Variables Globales
	public static final Logger logger = LoggerFactory.getLogger(ClientController.class);
	private CustomErrorType customErrorType;
	
	@Autowired
	ClientService clientService;
	
	DataValidator validator;
	
	
	// ------------------- GET Client----------------------------------------------------------------------------------
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Gets the clients with specific idClient or name")
	@ApiResponses(value= { @ApiResponse(code =200, message = "OK", response = Client.class) })
	@RequestMapping(value="/clients", method = RequestMethod.GET, headers = Constants.JSON)
	public @ResponseBody ResponseEntity<List<Client>> getClients(@RequestParam(value="name", required=false) String name, @RequestParam(value = "ide_client", required = false) Long idClient){

		
		//Search for client id_client
		if(idClient !=null && idClient > 0){
			Client client =  clientService.findById(idClient);
			if(client == null){
				return new ResponseEntity(new CustomErrorType("Client with idClient " + idClient + " not found ", MessageType.ERROR ), HttpStatus.NOT_FOUND);
			}else {
				return new ResponseEntity(client, HttpStatus.OK);
			}
		}
		
		//Search for client name
		if(name !=null){
			Client client = clientService.findByCellphone(name);
			if(client == null){
				return new ResponseEntity(new CustomErrorType("Client name " + name + " not found ", MessageType.ERROR ), HttpStatus.NOT_FOUND);
			}else {
				return new ResponseEntity(client, HttpStatus.OK);
			}
		}
		
		List<Client> clients = new ArrayList<>();
		//If id_client and name are null, Get all Clients in database
		if(name == null && idClient == null){
			clients = clientService.findAllClients();
			if(clients.isEmpty()){				
				return new ResponseEntity(new CustomErrorType(Constants.NO_RESULTS, MessageType.INFO ), HttpStatus.NOT_FOUND);
			}
		}
		
		return new ResponseEntity(clients, HttpStatus.OK);
	}
	

	// ------------------- POST Client----------------------------------------------------------------------------------
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Create client")
	@ApiResponses(value= { @ApiResponse(code =201, message = "OK", response = Client.class) })
	@RequestMapping(value="/clients", method = RequestMethod.POST, headers = Constants.JSON)
	public ResponseEntity<Client> createClient(@Validated @RequestBody Client client, UriComponentsBuilder uriBuilder, BindingResult bindingResult){
		
		logger.info("Creating Client : {}", client.getName());
	        
		if (bindingResult.hasErrors()) {
			
			List<String> message = customErrorType.processValidationError(bindingResult);
			return new ResponseEntity(new CustomErrorType(message.toString(), MessageType.ERROR),HttpStatus.BAD_REQUEST);
			
		}else {
			
			//Validate if Client exist in the database
			if(clientService.isClientExist(client)){
				return new ResponseEntity(
						new CustomErrorType("Unable to Create. A Client with cellphone " 
								+ client.getCellphone() + " already exist." 
								, MessageType.INFO ),HttpStatus.CONFLICT);
			}
			
			//Create Client
			clientService.saveClient(client);	
			
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(uriBuilder.path(Constants.API_VERSION+ Constants.CLIENTS + "{id}").buildAndExpand(client.getIdClient()).toUri());
			return new ResponseEntity(client, headers, HttpStatus.CREATED);
        }
	} 
		
	// ------------------- UPDATE Client----------------------------------------------------------------------------------
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Update client with specific id")
	@ApiResponses(value= { @ApiResponse(code =200, message = "OK", response = Client.class) })
	@RequestMapping(value="/client/{id}", method = RequestMethod.PATCH, headers = Constants.JSON)
	public ResponseEntity<Client> updateClient(@Validated @PathVariable("id") Long id, @RequestBody Client client, BindingResult bindingResult) {
		
		logger.info("Updating Client id {} ", id);
		
		if(id == null || id <= 0) {
			return new ResponseEntity(new CustomErrorType("idClient is required", MessageType.ERROR), HttpStatus.CONFLICT);
		}
	
		if (bindingResult.hasErrors()) {
			return new ResponseEntity(new CustomErrorType(bindingResult.getAllErrors().toString(), MessageType.ERROR),HttpStatus.BAD_REQUEST);
		} else {
			
			client.setIdClient(id);	
			
			//Validate if Client exist in the database
			if(clientService.isClientExist(client)){
				return new ResponseEntity(
						new CustomErrorType("Unable to update. A Client with cellphone " 
								+ client.getCellphone() + " already exist. ",
								MessageType.INFO ),HttpStatus.CONFLICT);
			}
			
			Client currentClient = clientService.findById(id);
			if(currentClient == null ){
				return new ResponseEntity(
						new CustomErrorType("Unable to update. A Client with id " + id + " not found.", MessageType.INFO ),
						HttpStatus.CONFLICT);
			}
			currentClient.setName(client.getName());
			currentClient.setLastName(client.getLastName());
			currentClient.setIdentification(client.getIdentification());
			currentClient.setCellphone(client.getCellphone());
			currentClient.setEnable(client.getEnable());
			currentClient.setCity(client.getCity());
			currentClient.setAddress(client.getAddress());
			
			clientService.updateClient(currentClient);
			return new ResponseEntity(currentClient, HttpStatus.OK);
		}

	}
	
	
	// ------------------- DELETE Client----------------------------------------------------------------------------------
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Delete client with specific id")
	@ApiResponses(value= { @ApiResponse(code =200, message = "OK", response = Client.class) })
	@RequestMapping(value = "/client/{id}", method = RequestMethod.DELETE)
	public ResponseEntity deleteClient(@PathVariable("id") Long id){
		
		logger.info("fetching % Deleting Client with id {} ", id);
	
		if(id == null || id <= 0) {
			return new ResponseEntity(new CustomErrorType("idClient is required", MessageType.ERROR ), HttpStatus.CONFLICT);
		}

		Client client = clientService.findById(id);
		
		if(client == null){
			return new ResponseEntity(
					new CustomErrorType("Unable to delete. Client with id " + id + " not found.", MessageType.INFO ),
					HttpStatus.NOT_FOUND);
		}
		
		clientService.deleteClient(id);
		return new ResponseEntity(HttpStatus.OK);
	}	
	
}
