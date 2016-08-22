package pl.swiezowski.adam.localiser.logic;

import pl.swiezowski.adam.localiser.entities.Localisation;
import pl.swiezowski.adam.localiser.launch.Main;

public class LocalisationPreparator {
	
	static private LinkGenerator linkGenerator = new LinkGenerator();
	
	public static Localisation prepareLocation(Localisation location){
		location.setCode(linkGenerator.generateLink());
		return location;
	}
}
