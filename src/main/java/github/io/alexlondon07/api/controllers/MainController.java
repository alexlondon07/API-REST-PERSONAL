package github.io.alexlondon07.api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	@RequestMapping("/")
	@ResponseBody
	public String index(){
		String response = "Welcome to our REST API with Spring - EmiStore";
		return response;
	}

}
