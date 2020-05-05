package github.io.alexlondon07.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import github.io.alexlondon07.api.models.Client;
import github.io.alexlondon07.api.services.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import util.Constants;
import util.CustomResponse;
import util.DataValidator;

@RestController
@RequestMapping(Constants.API_VERSION)
@Api(value = "ClientControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

	// Variables Globals
	public static final Logger logger = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	ClientService clientService;

	DataValidator validator;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Gets the clients with specific idClient or name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Client.class) })
	@GetMapping(value = "/clients", headers = Constants.JSON)
	public @ResponseBody CustomResponse<List<Client>> getClients(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "ide_client", required = false) Long idClient) {

		// Search for client id_client
		if (idClient != null && idClient > 0) {
			Client client = clientService.findById(idClient);
			if (client == null) {
				return new CustomResponse<>(Boolean.FALSE, "Client with idClient " + idClient + " not found ");
			} else {
				return new CustomResponse(client);
			}
		}

		// Search for client name
		if (name != null) {
			Client client = clientService.findByCellphone(name);
			if (client == null) {
				return new CustomResponse<>(Boolean.FALSE, "Client name " + name + " not found ");
			} else {
				return new CustomResponse(client);
			}
		}

		List<Client> clients = new ArrayList<>();
		if (name == null && idClient == null) {
			clients = clientService.findAllClients();
			if (clients.isEmpty()) {
				return new CustomResponse<>(Boolean.FALSE, Constants.NO_RESULTS);
			}
		}
		return new CustomResponse<>(Boolean.TRUE, "success", 200, clients);
	}

	@ApiOperation("Create client")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OK", response = Client.class) })
	@PostMapping(value = "/clients", headers = Constants.JSON)
	public CustomResponse<Client> createClient(@Validated @RequestBody Client client, UriComponentsBuilder uriBuilder,
			BindingResult bindingResult) {

		logger.info("Creating Client : {}", client.getName());

		if (bindingResult.hasErrors()) {

			@SuppressWarnings("unchecked")
			List<String> message = (List<String>) CustomResponse.getFielErrorResponse(bindingResult);
			return new CustomResponse<>(Boolean.FALSE, message.toString(), 409);

		} else {

			// Validate if Client exist in the database
			if (clientService.isClientExist(client)) {
				return new CustomResponse<>(Boolean.FALSE,
						"Unable to Create. A Client with cellphone " + client.getCellphone() + " already exist.", 409);
			}

			// Create Client
			clientService.saveClient(client);
		}
		return new CustomResponse<>(Boolean.TRUE, "success", 200, client);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Update client with specific id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Client.class) })
	@PatchMapping(value = "/client/{id}", headers = Constants.JSON)
	public CustomResponse<Client> updateClient(@Validated @PathVariable("id") Long id, @RequestBody Client client,
			BindingResult bindingResult) {

		logger.info("Updating Client id {} ", id);

		if (id == null || id <= 0) {
			return new CustomResponse<>(Boolean.FALSE, "Ide Client is required", 409);
		}

		if (bindingResult.hasErrors()) {

			@SuppressWarnings("unchecked")
			List<String> message = (List<String>) CustomResponse.getFielErrorResponse(bindingResult);
			return new CustomResponse<>(Boolean.FALSE, message.toString(), 409);

		} else {

			client.setIdClient(id);

			// Validate if Client exist in the database
			if (clientService.isClientExist(client)) {
				return new CustomResponse<>(Boolean.FALSE,
						"Unable to update. A Client with cellphone \" + client.getCellphone() + \" already exist. ",
						409);
			}

			Client currentClient = clientService.findById(id);
			if (currentClient == null) {
				return new CustomResponse<>(Boolean.FALSE, "Unable to update. A Client with id \" + id + \" not found.",
						409);
			}
			currentClient.setName(client.getName());
			currentClient.setLastName(client.getLastName());
			currentClient.setIdentification(client.getIdentification());
			currentClient.setCellphone(client.getCellphone());
			currentClient.setEnable(client.getEnable());
			currentClient.setCity(client.getCity());
			currentClient.setAddress(client.getAddress());

			clientService.updateClient(currentClient);
			return new CustomResponse<>(Boolean.TRUE, "success", 200, currentClient);

		}

	}

	@ApiOperation("Delete client with specific id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Client.class) })
	@DeleteMapping(value = "/client/{id}")
	public CustomResponse<?> deleteClient(@PathVariable("id") Long id) {

		logger.info("fetching % Deleting Client with id {} ", id);

		if (id == null || id <= 0) {
			return new CustomResponse<>(Boolean.FALSE, "Ide client is required", 409);
		}

		Client client = clientService.findById(id);

		if (client == null) {
			return new CustomResponse<>(Boolean.FALSE, "Unable to delete. Client with id \" + id + \" not found.", 404);
		}

		clientService.deleteClient(id);
		return new CustomResponse<>(Boolean.TRUE, "success", 200, id);
	}

}
