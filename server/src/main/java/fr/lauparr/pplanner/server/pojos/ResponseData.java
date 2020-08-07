package fr.lauparr.pplanner.server.pojos;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseData extends ResponseBase {

	private final Object data;
	private final String type;

	@Builder
	public ResponseData(boolean success, Object data, String type) {
		super(success);
		this.data = data;
		this.type = type;
	}

}