package github.io.alexlondon07.api.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import github.io.alexlondon07.api.models.Users;
import io.swagger.annotations.Api;
import util.Constants;
import util.CustomResponse;

@Controller
@RequestMapping(Constants.API_VERSION)
@Api(value = "UsuariosController", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuariosController {
	
	public  CustomResponse createUser(Users user) {
		
		return new CustomResponse(true, "El usurio se ha creado correctamente");
		
	}
	
	public CustomResponse editUser(Users user) {
		return new CustomResponse(true, "El usurio se ha creado correctamente");
		
	}
	
	
	public CustomResponse deleteUser(Users user) {
		return new CustomResponse(true, "El usurio se ha creado correctamente");
		
	}
	public CustomResponse getUser(Users user) {
		return new CustomResponse(true, "El usurio se ha creado correctamente");
		
	}


}
