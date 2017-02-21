package pl.swiezowski.adam.localiser.logic;

import java.security.SecureRandom;
import java.util.Base64;

public class CodeGenerator {

	SecureRandom random;
	
	public CodeGenerator(){
		random = new SecureRandom();
	}
	
	public String generateCode() {
		byte bytes[] = new byte[6];
		random.nextBytes(bytes);
		return Base64.getEncoder().encodeToString(bytes).replace("+", "a").replace("/", "z");
	}
}
