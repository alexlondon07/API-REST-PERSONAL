package util;

public class CustomErrorType {
	private String errorMessage;
	private boolean validationOk;

	 
    public CustomErrorType(String errorMessage){
        this.errorMessage = errorMessage;
    }
 
    public String getErrorMessage() {
        return errorMessage;
    }
    
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isValidationOk() {
		return validationOk;
	}

	public void setValidationOk(boolean validationOk) {
		this.validationOk = validationOk;
	}
}
