package github.io.alexlondon07.api.controllers;

import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import github.io.alexlondon07.api.models.Client;
import github.io.alexlondon07.api.services.ClientService;
import util.CustomErrorType;

@Controller
@RequestMapping("/v1")
public class ClientController {
	
	public static final Logger logger = LoggerFactory.getLogger(ClientController.class);
	private static final String JSON = "Accept=application/json";
	private CustomErrorType customErrorType;
	
	@Autowired
	ClientService clientService;
	
	
	// ------------------- GET Client----------------------------------------------------------------------------------
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/clients", method = RequestMethod.GET, headers = JSON)
	public @ResponseBody ResponseEntity<List<Client>> getClients(@RequestParam(value="name", required=false) String name, @RequestParam(value = "id_client", required = false) Long idClient){

		List<Client> clients = new ArrayList<>();
		
		//Search for client id_client 
		if(idClient !=null){
			clients = (List<Client>) clientService.findById(idClient);
			if(clients.isEmpty()){
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		}
		
		//Search for client name
		if(name!=null){
			Client client = (Client) clientService.findByCellphone(name);
			if(client == null){
				return new ResponseEntity(new CustomErrorType("Client name " + name + " not found ", MessageType.ERROR ), HttpStatus.NOT_FOUND);
			}
		}
		
		//If id_client and name are null, Get all Clients in database
		if(name == null && idClient == null){
			clients = clientService.findAllClients();
			if(clients.isEmpty()){
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		}
		
		return new ResponseEntity(clients, HttpStatus.OK);
	}
	

	// ------------------- POST Client----------------------------------------------------------------------------------
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
	@RequestMapping(value="/clients/{id}", method = RequestMethod.PATCH, headers = JSON)
	public ResponseEntity<Client> updateClient(@Validated @PathVariable("id") Long id, @RequestBody Client client, BindingResult bindingResult){
		
		logger.info("Updating Client id {} ", id);
		
		if(id == null || id <= 0){
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
	@RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCourse(@PathVariable("id") Long id){
		
		logger.info("fetching % Deleting Course with id {} ", id);
		
		if (id == null || id <= 0) {
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
