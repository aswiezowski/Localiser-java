package pl.swiezowski.adam.localiser.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@Column
	private Double latitude;
	@Column
	private Double longitude;
	@Column
	private String code;
	@Column
	private String description;

	@JsonIgnore
	public boolean isValid() {
		if (latitude >= -90.0 && latitude <= 90.0 && longitude <= 180.0 && longitude >= -180 && code != null) {
			return true;
		}
		return false;
	}

	public Location(double latitude, double longitude, String code) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.code = code;
	}

	public double distance(Location localisation) {
		final int R = 6371;
		Double latDistance = Math.toRadians(localisation.latitude - latitude);
		Double lonDistance = Math.toRadians(localisation.longitude - longitude);
		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(localisation.latitude))
						* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return R * c * 1000;
	}

}
