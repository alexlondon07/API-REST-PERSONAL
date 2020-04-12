package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class CustomResponse<T> {

	private boolean success;
	private String message;
	private int statusCode;
	private T data;

	public CustomResponse() {

	}

	public CustomResponse(boolean success, String message) {
		this(success, message, 200, null);
	}

	public CustomResponse(T data) {
		this(true, "sucsess", 200, data);
	}

	public CustomResponse(boolean success, String message, int statusCode, T data) {
		super();
		this.success = success;
		this.message = message;
		this.statusCode = statusCode;
		this.data = data;
	}

	public CustomResponse(boolean success, String message, int statusCode) {
		super();
		this.success = success;
		this.message = message;
		this.statusCode = statusCode;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static List<String> processValidationError(BindingResult bindingResult) {
		List<FieldError> errors = bindingResult.getFieldErrors();
		List<String> message = new ArrayList<>();
		for (FieldError e : errors) {
			if (e.getDefaultMessage() != null) {
				message.add("@" + e.getField().toUpperCase() + ":" + e.getDefaultMessage());
			}
		}
		return message;
	}

	public static Map<String, Object> getFielErrorResponse(BindingResult bindingResult) {
		Map<String, Object> fieldError = new HashMap<String, Object>();
		List<FieldError> errors = bindingResult.getFieldErrors();
		for (FieldError e : errors) {
			fieldError.put(e.getField(), e.getDefaultMessage());
		}
		return fieldError;
	}

}
