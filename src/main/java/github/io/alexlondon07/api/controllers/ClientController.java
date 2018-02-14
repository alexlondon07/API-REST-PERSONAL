package github.io.alexlondon07.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import github.io.alexlondon07.api.models.Client;
import github.io.alexlondon07.api.services.ClientService;
import util.CustomErrorType;

@Controller
@RequestMapping("/v1")
public class ClientController {
	
	public static final Logger logger = LoggerFactory.getLogger(ClientController.class);
	
	@Autowired
	ClientService clientService;
	
	/**
	 * Metodo para obtener el listado de los clientes Ingresados al sistemas.
	 * @param name
	 * @param idClient
	 * @return
	 */
	@RequestMapping(value="/clients", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<List<Client>> getClients(@RequestParam(value="name", required=false) String name, @RequestParam(value = "id_client", required = false) Long idClient){

		List<Client> clients = new ArrayList<>();
		
		
		if(idClient !=null){
			clients = (List<Client>) clientService.findById(idClient);
			if(clients.isEmpty()){
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		}
		
		if(name!=null){
			Client client = (Client) clientService.findByName(name);
			if(client == null){
				return new ResponseEntity(new CustomErrorType("Client name " + name + " not found "), HttpStatus.NOT_FOUND);
			}
		}
		
		if(name == null && idClient == null){
			clients = clientService.findAllClients();
			if(clients.isEmpty()){
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		}
		
		return new ResponseEntity(clients, HttpStatus.OK);
		
	}

}
