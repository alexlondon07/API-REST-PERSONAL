package util;

public class DataValidator {
	
	public boolean isEmpty(String input) {
		return input == null;
	}
	
	public boolean isNotEmpty(String input) {
		return input !=null;
	}

	public boolean isEmptyLong(Long input) {
		return (input == null || input <= 0);
	}
	
	
}
