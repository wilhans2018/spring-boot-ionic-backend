package com.wilhans.cursomc.services.exceptions;

public class AuthorizantionException extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	public AuthorizantionException(String msg) {
		super(msg);
	}
	
	public AuthorizantionException(String msg, Throwable cause) {
		super(msg, cause);		
	}

}
