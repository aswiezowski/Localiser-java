package pl.swiezowski.adam.localiser.dto;

import lombok.Data;

@Data
public class ResponseDTO {

	private String status;

	public ResponseDTO() {
	}

	public ResponseDTO(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
