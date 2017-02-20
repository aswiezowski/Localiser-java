package pl.swiezowski.adam.localiser.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import lombok.Data;

@Table
@Entity
@Data
public class Localisation {

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

	@JsonIgnore
	public boolean isValid() {
		if (latitude >= -90.0 && latitude <= 90.0 && longitude <= 180.0 && longitude >= -180 && code != null) {
			return true;
		}
		return false;
	}

}
