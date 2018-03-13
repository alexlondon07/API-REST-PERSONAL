package github.io.alexlondon07.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
	
	@Autowired
	ClientService clientService;
	
	// ------------------- GET Client----------------------------------------------------------------------------------
	/**
	 * Metodo para obtener el listado de los clientes Ingresados al sistema.
	 * @param name
	 * @param idClient
	 * @return
	 */
	@RequestMapping(value="/clients", method = RequestMethod.GET, headers = "Accept=application/json")
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
				return new ResponseEntity(new CustomErrorType("Client name " + name + " not found "), HttpStatus.NOT_FOUND);
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
	/**
	 * Metodo  que se encar se crear un cliente
	 * @param client
	 * @param uriBuilder
	 * @return
	 */
	@RequestMapping(value="/clients", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> createclient(@RequestBody Client client, UriComponentsBuilder uriBuilder){
		
		logger.info("Creating Client : {}", client);
		
		CustomErrorType response = validateDataClient(client);
		if(!response.isValidationOk()){
			return new ResponseEntity(new CustomErrorType(response.getErrorMessage()),HttpStatus.CONFLICT);
		}
		
		//Create Client
		clientService.saveClient(client);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriBuilder.path("/v1/clients/{id}").buildAndExpand(client.getIdCliente()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		
	}
	
	/**
	 * Funcion que se encarga de validar los datos obligatorios de la creación de un Cliente
	 * @param client
	 * @return
	 */
	public CustomErrorType validateDataClient(Client client){
		
		CustomErrorType response = new CustomErrorType("Information registered correctly");
		response.setValidationOk(true);
		
		if(client.getName() == null || client.getName().isEmpty()){
			response.setErrorMessage("Client name is required");
			response.setValidationOk(false);
			return response;
		}
		
		if(client.getCellphone() == null || client.getCellphone().isEmpty()){
			response.setErrorMessage("Client cellphone is required.");
			response.setValidationOk(false);
			return response;
		}
		
		if(isClientExist(client)){
			response.setErrorMessage("Unable to create. A Client with cellphone " + client.getCellphone() + " already exist.");
			response.setValidationOk(false);
			return response;
		}
		
		return response;
	}

	// ------------------- UPDATE Client----------------------------------------------------------------------------------
	/**
	 * Metodo que sirve para actualizar la Información de Client
	 * @param id
	 * @param client
	 * @return
	 */
	public ResponseEntity<Client> updateClient(@PathVariable("id") Long id, @RequestBody Client client){
		
		logger.info("Updating Client id {} ", id);
		if(id == null || id <= 0){
			return new ResponseEntity(new CustomErrorType("idClient is required"), HttpStatus.CONFLICT);
		}
		
		validateDataClient(client);
		
		if(isClientExist(client)){
			return new ResponseEntity(
					new CustomErrorType("Unable to create. A Client with name " + client.getName() + " already exist."),
					HttpStatus.CONFLICT);
		}
		
		Client currentClient = clientService.findById(id);
		if(currentClient == null ){
			return new ResponseEntity(
					new CustomErrorType("Unable to create. A Client with id " + id + " already exist."),
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
	
	
	/**
	 * Metodo que valida si un cliente ya existe en el sistema registrado con el mismo nùmero de Telefono
	 * @param client
	 * @return
	 */
	private boolean isClientExist(Client client) {
		if(clientService.findByCellphone(client.getCellphone()) !=null){
			logger.error("Unable to create. A Client with Cellphone " + client.getCellphone() +  " already exist");
			return true;
		}
		return false;
	}
	
}
