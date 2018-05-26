package com.ocularminds.expad.error;

public class XChainedRuntimeException extends Exception{

	Throwable e;
	String error;

	public XChainedRuntimeException(){}

	    public XChainedRuntimeException(Throwable e) {
	        this.e = e;
	    }

	    public XChainedRuntimeException(String str) {
	       this.error = str;
	    }

	    public XChainedRuntimeException(String str, Throwable cause) {
	       this.e = cause;
	       this.error = str;
	    }
}