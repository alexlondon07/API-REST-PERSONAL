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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import github.io.alexlondon07.api.models.Category;
import github.io.alexlondon07.api.models.State;
import github.io.alexlondon07.api.services.state.StateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import util.Constants;
import util.CustomErrorType;

@Controller
@RequestMapping(Constants.API_VERSION)
@Api(value="StateControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)

public class StateController {
	
	public static final Logger logger =  LoggerFactory.getLogger(StateController.class);

	@Autowired
	StateService stateService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Gets the states with specific ide_state or name")
	@ApiResponses(value= { @ApiResponse(code =200, message = "OK", response = State.class) })
	@RequestMapping(value="/states", method = RequestMethod.GET, headers = Constants.JSON)
	public @ResponseBody ResponseEntity<List<Category>> getSates(@RequestParam(value="name", required=false) String name, @RequestParam(value = "ide_state", required = false) Long ideState){
		
		//Search for category ide_category 
		if(ideState !=null && ideState > 0){
			State state = stateService.findById(ideState);
			if(state == null){
				return new ResponseEntity(new CustomErrorType("State with ide_state " + ideState + " not found ", MessageType.ERROR ), HttpStatus.NOT_FOUND);
			}else {
				return new ResponseEntity(state, HttpStatus.OK);
			}
		}
		
		//Search for category name
		if(name !=null){
			State state = stateService.findByName(name);
			if(state == null){
				return new ResponseEntity(new CustomErrorType("State with name " + name + " not found ", MessageType.ERROR ), HttpStatus.NOT_FOUND);
			}else {
				return new ResponseEntity(state, HttpStatus.OK);
			}
		}
		
		List<State> states = new ArrayList<>();
		//If ide_category and name are null, Get all Categories in database
		if(name == null && ideState == null){
			states = stateService.findAllStates();
			if(states.isEmpty() ){
				return new ResponseEntity(new CustomErrorType(Constants.NO_RESULTS, MessageType.INFO ), HttpStatus.NOT_FOUND);
			}
		}
		
		return new ResponseEntity(states, HttpStatus.OK);
	}
	
}
