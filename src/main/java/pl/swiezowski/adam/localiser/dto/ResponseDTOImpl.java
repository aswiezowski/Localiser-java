package pl.swiezowski.adam.localiser.dto;

public class ResponseDTOImpl implements ResponseDTO {

	private Long id;
	private String answer;

	public Long getId() {
		return id;
	}

	public String getStatus() {
		return answer;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStatus(String answer) {
		this.answer = answer;
	}

}
