package com.boot.contentful.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Map;

public class ResponseObject implements Serializable {
	
	private String ReturnType="";
	private String ReturnCode="";
	private String ReturnMessage="";
	@JsonProperty("returnData")
	private Map<?,?> ReturnData;
	private String queryTimeInMilli = "";
	public String getReturnType() {
		return ReturnType;
	}
	public void setReturnType(String returnType) {
		ReturnType = returnType;
	}
	public String getReturnMessage() {
		return ReturnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		ReturnMessage = returnMessage;
	}
	public Map<?, ?> getReturnData() {
		return ReturnData;
	}
	public void setReturnData(Map<?, ?> returnData) {
		ReturnData = returnData;
	}
	public String getQueryTimeInMilli() {
		return queryTimeInMilli;
	}
	public void setQueryTimeInMilli(String queryTimeInMilli) {
		this.queryTimeInMilli = queryTimeInMilli;
	}
	public String getReturnCode() {
		return ReturnCode;
	}
	public void setReturnCode(String returnCode) {
		ReturnCode = returnCode;
	}
	
	

}
