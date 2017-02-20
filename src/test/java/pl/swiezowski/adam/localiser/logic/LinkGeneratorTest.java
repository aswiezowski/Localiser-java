package pl.swiezowski.adam.localiser.logic;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class LinkGeneratorTest {
	
	private static final int NUMBER_OF_TESTED_LINKS = 100;
	private static final String LINK_PATTERN = "^[a-zA-Z0-9]{8}$";

	@Test
	public void testRandomLinkUniquess(){
		CodeGenerator linkGenerator = new CodeGenerator();
		HashSet<String> links = new HashSet<String>(100);
		for(int i=0; i<NUMBER_OF_TESTED_LINKS; i++){
			String link = linkGenerator.generateLink();
			assertTrue(links.add(link));
		}
	}
	
	@Test
	public void testLinkValueCorectness(){
		CodeGenerator linkGenerator = new CodeGenerator();
		Pattern pattern = Pattern.compile(LINK_PATTERN);
		for(int i=0; i<NUMBER_OF_TESTED_LINKS; i++){
			String link = linkGenerator.generateLink();
			Matcher matcher = pattern.matcher(link);
			assertTrue(matcher.matches());
		}
	}
	
}
