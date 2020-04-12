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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import github.io.alexlondon07.api.models.Category;
import github.io.alexlondon07.api.models.Product;
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
@Api(value = "StateControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)

public class StateController {

	public static final Logger logger = LoggerFactory.getLogger(StateController.class);

	@Autowired
	StateService stateService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Gets the states with specific ide_state or name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = State.class) })
	@GetMapping(value = "/states", headers = Constants.JSON)
	public @ResponseBody ResponseEntity<List<Category>> getSates(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "ide_state", required = false) Long ideState) {

		// Search for category ide_category
		if (ideState != null && ideState > 0) {
			State state = stateService.findById(ideState);
			if (state == null) {
				return new ResponseEntity(
						new CustomErrorType("State with ide_state " + ideState + " not found ", MessageType.ERROR),
						HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity(state, HttpStatus.OK);
			}
		}

		// Search for category name
		if (name != null) {
			State state = stateService.findByName(name);
			if (state == null) {
				return new ResponseEntity(
						new CustomErrorType("State with name " + name + " not found ", MessageType.ERROR),
						HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity(state, HttpStatus.OK);
			}
		}

		List<State> states = new ArrayList<>();
		states = stateService.findAllStates();
		if (states.isEmpty()) {
			return new ResponseEntity(new CustomErrorType(Constants.NO_RESULTS, MessageType.INFO),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(states, HttpStatus.OK);
	}

	// ------------------- POST
	// State----------------------------------------------------------------------------------
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Create state")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OK", response = Product.class) })
	@PostMapping(value = "/states", headers = Constants.JSON)
	public ResponseEntity<Product> createState(@Validated @RequestBody State state, UriComponentsBuilder uriBuilder,
			BindingResult bindingResult) {

		logger.info("Creating State : {}", state.toString());

		if (bindingResult.hasErrors()) {
			return new ResponseEntity(new CustomErrorType(bindingResult.getAllErrors().toString(), MessageType.ERROR),
					HttpStatus.BAD_REQUEST);
		} else {

			// Validate if State exist in the database
			if (stateService.isStateExist(state)) {
				return new ResponseEntity(new CustomErrorType(
						"Unable to Create. A State with name " + state.getName() + " already exist.", MessageType.INFO),
						HttpStatus.CONFLICT);
			}

			// Create State
			stateService.saveState(state);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(uriBuilder.path(Constants.API_VERSION + Constants.STATES + "{id}")
					.buildAndExpand(state.getIdeState()).toUri());
			return new ResponseEntity(state, headers, HttpStatus.CREATED);
		}
	}

}
