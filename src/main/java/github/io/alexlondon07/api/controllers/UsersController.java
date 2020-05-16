package github.io.alexlondon07.api.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import github.io.alexlondon07.api.models.Client;
import github.io.alexlondon07.api.models.Users;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import util.Constants;
import util.CustomResponse;

@Controller
@RequestMapping(Constants.API_VERSION)
@Api(value = "UsersController", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersController {
	
	@ApiOperation("Permite crear usuarios")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OK", response = Client.class) })
	@PostMapping(value = "/createUser", headers = Constants.JSON)
	public  CustomResponse createUser(@RequestBody Users user) {
		
		return new CustomResponse(true, "El usurio se ha creado correctamente");
		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Edit user with specific id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Client.class) })
	@PatchMapping(value = "/editUser/{id}", headers = Constants.JSON)
	public CustomResponse editUser(@Validated @PathVariable("id") Long id,
			BindingResult bindingResult, @RequestBody Users user) {
		return new CustomResponse(true, "El usurio se ha creado correctamente");
		
	}
	
	@ApiOperation("Delete user with specific id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Client.class) })
	@DeleteMapping(value = "/deleteUser/{id}")
	public CustomResponse deleteUser(@Validated @PathVariable("id") Long id) {
		return new CustomResponse(true, "El usurio se ha creado correctamente");
		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Gets the all user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Client.class) })
	@GetMapping(value = "/clients", headers = Constants.JSON)
	public CustomResponse getUsers() {
		return new CustomResponse(true, "El usurio se ha creado correctamente");
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Get the user with specific username and password")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Client.class) })
	@PostMapping(value = "/getUserLogin", headers = Constants.JSON)
	public CustomResponse getUserLogin() {
		return new CustomResponse(true, "El usurio se ha creado correctamente");
		
	}


}
