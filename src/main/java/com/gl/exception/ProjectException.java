package com.gl.exception;

public class ProjectException extends Exception {

	private static final long serialVersionUID = -4804260092667286863L;

	private String msg;

	public ProjectException(String msg) {
		this.msg = msg;
	}

	@Override
	public String getMessage() {
		return this.msg;
	}

}
