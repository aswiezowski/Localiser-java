package pl.swiezowski.adam.localiser.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateResponseDTO extends ResponseDTO {

	private String code;

	@Builder
	public CreateResponseDTO(String code, String status) {
		super(status);
		this.code = code;
	}

	public CreateResponseDTO(String status) {
		super(status);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
