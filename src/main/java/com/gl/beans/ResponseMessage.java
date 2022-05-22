package com.gl.beans;

import lombok.*;

@Data
@NoArgsConstructor
public class ResponseMessage {

	private String message;
	private int errorCode;

}
