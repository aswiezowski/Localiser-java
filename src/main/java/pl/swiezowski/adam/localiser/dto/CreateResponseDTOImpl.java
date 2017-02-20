package pl.swiezowski.adam.localiser.dto;

public class CreateResponseDTOImpl extends ResponseDTOImpl implements CreateResponseDTO {

	private String code;

	public CreateResponseDTOImpl(){
		
	}
	
	public CreateResponseDTOImpl(String status) {
		super(status);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
