package com.boot.contentful.common;

import org.springframework.stereotype.Component;

public enum ReturnStatusEnum {

	SUCCESFUL( "200" ),
	FAILURE( "500" );

	
	private String	value;

	ReturnStatusEnum( String code ) {

		this.value = code;
	}

	public String getValue( ) {

		return value;
	}
}
