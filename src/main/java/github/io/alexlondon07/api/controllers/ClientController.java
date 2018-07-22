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

import github.io.alexlondon07.api.models.Client;
import github.io.alexlondon07.api.services.client.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import util.CustomErrorType;
import util.DataValidator;

@Controller
@RequestMapping("/api/v1")
@Api(value="ClientControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {
	
	//Variables Globales
	public static final Logger logger = LoggerFactory.getLogger(ClientController.class);
	private static final String JSON = "Accept=application/json";
	private CustomErrorType customErrorType;
	
	@Autowired
	ClientService clientService;
	
	DataValidator validator;
	
	
	// ------------------- GET Client----------------------------------------------------------------------------------
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Gets the clients with specific id_client or name")
	@ApiResponses(value= { @ApiResponse(code =200, message = "OK", response = Client.class) })
	@RequestMapping(value="/clients", method = RequestMethod.GET, headers = JSON)
	public @ResponseBody ResponseEntity<List<Client>> getClients(@RequestParam(value="name", required=false) String name, @RequestParam(value = "ide_client", required = false) Long idClient){

		List<Client> clients = new ArrayList<>();
		
		//Search for client id_client 
		if(idClient !=null && idClient > 0){
			Client client =  clientService.findById(idClient);
			if(client == null){
				return new ResponseEntity(new CustomErrorType("Client with ide_client " + idClient + " not found ", MessageType.ERROR ), HttpStatus.NOT_FOUND);
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
		
		//If id_client and name are null, Get all Clients in database
		if(name == null && idClient == null){
			clients = clientService.findAllClients();
			if(clients == null){
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		}
		
		return new ResponseEntity(clients, HttpStatus.OK);
	}
	

	// ------------------- POST Client----------------------------------------------------------------------------------
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Create client")
	@ApiResponses(value= { @ApiResponse(code =201, message = "OK", response = Client.class) })
	@RequestMapping(value="/clients", method = RequestMethod.POST, headers = JSON)
	public ResponseEntity<?> createclient(@Validated @RequestBody Client client, UriComponentsBuilder uriBuilder, BindingResult bindingResult){
		
		logger.info("Creating Client : {}", client.getName());
        
		if (bindingResult.hasErrors()) {
			
			List<String> message = customErrorType.processValidationError(bindingResult);
			return new ResponseEntity(new CustomErrorType(message.toString(), MessageType.ERROR),HttpStatus.BAD_REQUEST);
        
		} else {
				    		
	    		if(clientService.isClientExist(client)){
	    			return new ResponseEntity(new CustomErrorType("Unable to create. A Client with cellphone " + client.getCellphone() + " already exist.", MessageType.ERROR),HttpStatus.CONFLICT);
	    		}
	    		
	    		//Create Client
	    		clientService.saveClient(client);
	    		
	    		HttpHeaders headers = new HttpHeaders();
	    		headers.setLocation(uriBuilder.path("/v1/clients/{id}").buildAndExpand(client.getIdClient()).toUri());
	    		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        }
	} 
	

	// ------------------- UPDATE Client----------------------------------------------------------------------------------
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Update client with specific id")
	@ApiResponses(value= { @ApiResponse(code =200, message = "OK", response = Client.class) })
	@RequestMapping(value="/clients/{id}", method = RequestMethod.PATCH, headers = JSON)
	public ResponseEntity<Client> updateClient(@Validated @PathVariable("id") Long id, @RequestBody Client client, BindingResult bindingResult){
		
		logger.info("Updating Client id {} ", id);
		
		if (validator.isEmptyLong(id)) {
			return new ResponseEntity(new CustomErrorType("idClient is required", MessageType.ERROR), HttpStatus.CONFLICT);
		}
		
		if (bindingResult.hasErrors()) {
			
			List<String> message = customErrorType.processValidationError(bindingResult);
			return new ResponseEntity(new CustomErrorType(message.toString(), MessageType.ERROR),HttpStatus.BAD_REQUEST);
		
		} else {
			
			client.setIdClient(id);	
			
			//Validate if Client exist in the database
			if(clientService.isClientExist(client)){
				return new ResponseEntity(
						new CustomErrorType("Unable to update. A Client with cellphone " 
								+ client.getCellphone() + " already exist,  her name is " 
								+ client.getName(), MessageType.INFO ),HttpStatus.CONFLICT);
			}
			
			Client currentClient = clientService.findById(id);
			if(currentClient == null ){
				return new ResponseEntity(
						new CustomErrorType("Unable to update. A Client with id " + id + " already exist.", MessageType.INFO ),
						HttpStatus.CONFLICT);
			}
			currentClient.setName(client.getName());
			currentClient.setLastName(client.getLastName());
			currentClient.setIdentification(client.getIdentification());
			currentClient.setCellphone(client.getCellphone());
			currentClient.setEnable(client.getEnable());
			
			clientService.updateClient(currentClient);
			return new ResponseEntity<Client>(currentClient, HttpStatus.OK);
		}

	}
	
	
	// ------------------- DELETE Client----------------------------------------------------------------------------------
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Delete client with specific id")
	@ApiResponses(value= { @ApiResponse(code =200, message = "OK", response = Client.class) })
	@RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCourse(@PathVariable("id") Long id){
		
		logger.info("fetching % Deleting Course with id {} ", id);
	
		if (validator.isEmptyLong(id)) {
			return new ResponseEntity(new CustomErrorType("idClient is required", MessageType.ERROR ), HttpStatus.CONFLICT);
		}
		
		Client client = clientService.findById(id);
		
		if(client == null){
			return new ResponseEntity(
					new CustomErrorType("Unable to delete. A Client with id " + id + " not found.", MessageType.INFO ),
					HttpStatus.NOT_FOUND);
		}
		
		clientService.deleteClient(id);
		return new ResponseEntity<Client>(HttpStatus.OK);
		
	}
	
}
