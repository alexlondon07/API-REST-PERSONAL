package util;

public class DataValidator {
	
	public boolean isEmpty(String input) {
		return input == null || input.trim().length() == 0;
	}
	
	public boolean isNotEmpty(String input) {
		return input != null && !input.isEmpty();
	}

	public boolean isEmptyLong(Long input) {
		return input == null || input == 0;
	}
	
    public boolean checkInputString(String input) {
        return (input == null || input.trim().length() == 0);
    }
	
}
