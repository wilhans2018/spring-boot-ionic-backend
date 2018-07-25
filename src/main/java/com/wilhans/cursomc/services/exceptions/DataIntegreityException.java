package com.wilhans.cursomc.services.exceptions;

public class DataIntegreityException extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	public DataIntegreityException(String msg) {
		super(msg);
	}
	
	public DataIntegreityException(String msg, Throwable cause) {
		super(msg, cause);		
	}

}
