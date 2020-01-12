package com.meep.challenge.exceptions;

public class VehicleManagerException extends Exception {
	
	private static final long serialVersionUID = 7950616028861537451L;

	public VehicleManagerException(String message, Throwable  e) {
		super(message,e);
	}
	
	public VehicleManagerException() {
		super();
	}

}
