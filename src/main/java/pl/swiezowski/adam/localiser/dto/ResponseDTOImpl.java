package pl.swiezowski.adam.localiser.dto;

public class ResponseDTOImpl implements ResponseDTO {

	private String status;

	public ResponseDTOImpl(){
	}
	
	public ResponseDTOImpl(String status){
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
