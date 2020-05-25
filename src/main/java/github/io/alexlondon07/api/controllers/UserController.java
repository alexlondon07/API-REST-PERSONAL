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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import github.io.alexlondon07.api.models.User;
import github.io.alexlondon07.api.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import util.Constants;
import util.CustomResponse;
import util.DataValidator;

@RestController
@RequestMapping(Constants.API_VERSION)
@Api(value = "UserControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	public UserController() {
	}

	// Variables Globals
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	DataValidator validator;

	@ApiOperation("Gets the users with specific id or name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = User.class) })
	@GetMapping(value = "/users", headers = Constants.JSON)
	public @ResponseBody CustomResponse<List<User>> getUsers(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "id", required = false) Long id) {

		// Search for user id
		if (id != null && id > 0) {
			User user = userService.findById(id);
			if (user == null) {
				return new CustomResponse<>(Boolean.FALSE, "User with id " + user + " not found ");
			} else {
				return new CustomResponse(user);
			}
		}

		// Search for user name
		if (name != null) {
			User user = userService.findByUserName(name);
			if (user == null) {
				return new CustomResponse<>(Boolean.FALSE, "user name " + name + " not found ");
			} else {
				return new CustomResponse(user);
			}
		}

		List<User> users = new ArrayList<>();
		if (name == null && id == null) {
			users = userService.findAllUser();
			if (users.isEmpty()) {
				return new CustomResponse<>(Boolean.FALSE, Constants.NO_RESULTS);
			}
		}
		return new CustomResponse<>(Boolean.TRUE, "success", 200, users);
	}

	@ApiOperation("Create User")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OK", response = User.class) })
	@PostMapping(value = "/users", headers = Constants.JSON)
	public CustomResponse<?> createUser(@Validated @RequestBody User user, UriComponentsBuilder uriBuilder,
			BindingResult bindingResult) {

		logger.info("Creating User : {}", user.getName());

		if (bindingResult.hasErrors()) {

			@SuppressWarnings("unchecked")
			List<String> message = (List<String>) CustomResponse.getFielErrorResponse(bindingResult);
			return new CustomResponse<>(Boolean.FALSE, message.toString(), 409);

		} else {

			// Validate if the user exist in the DB
			User userExist = userService.findByUserName(user.getUser());
			if (userExist != null) {
				return new CustomResponse<>(Boolean.FALSE,
						"Unable to Create. A user with username " + user.getUser() + " already exist.", 409);
			}

			// Create User
			userService.saveUser(user);
		}
		return new CustomResponse<>(Boolean.TRUE, "success", 200, user);

	}

	@ApiOperation("Update user with specific id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = User.class) })
	@PostMapping(value = "/users/{id}", headers = Constants.JSON)
	public CustomResponse<User> updateuser(@RequestBody User user, BindingResult bindingResult) {

		logger.info("Updating User id {} ", user.getId());

		if (user.getId() <= 0) {
			return new CustomResponse<>(Boolean.FALSE, "Id User is required", 409);
		}

		if (bindingResult.hasErrors()) {

			@SuppressWarnings("unchecked")
			List<String> message = (List<String>) CustomResponse.getFielErrorResponse(bindingResult);
			return new CustomResponse<>(Boolean.FALSE, message.toString(), 409);

		} else {

			// Validate if user exist in the database
			if (userService.isUserExist(user)) {
				return new CustomResponse<>(Boolean.FALSE,
						"Unable to update. A user with username \" + user.getUser() + \" already exist. ", 409);
			}

			User currentuser = userService.findById(user.getId());
			if (currentuser == null) {
				return new CustomResponse<>(Boolean.FALSE, "Unable to update. A user with id \" + id + \" not found.",
						409);
			}
			userService.updateUser(user);
			return new CustomResponse<>(Boolean.TRUE, "success", 200, currentuser);

		}

	}

	@ApiOperation("Delete user with specific id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = User.class) })
	@DeleteMapping(value = "/user/{id}")
	public CustomResponse<?> deleteUser(@PathVariable("id") Long id) {

		logger.info("fetching % Deleting User with id {} ", id);

		if (id == null || id <= 0) {
			return new CustomResponse<>(Boolean.FALSE, "Ide user is required", 409);
		}

		User user = userService.findById(id);
		if (user == null) {
			return new CustomResponse<>(Boolean.FALSE, "Unable to delete. User with id \" + id + \" not found.", 404);
		}

		userService.deleteUser(id);
		return new CustomResponse<>(Boolean.TRUE, "success", 200, id);
	}
}
