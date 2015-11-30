package com.ws.androidWSNewClient;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SOAPMethod {
	
	public SOAPMethod(){
		
	}
	
	/*
	 * SOAPObject rpc: the input soapobject
	 * 
	 * */
	
	public static SoapObject callWebservice(String methodName, SoapObject rpc){
		String nameSpace = "http://128.238.38.77:8080/axis/CloudComputingProject.jws";
    	// EndPoint
    	String endPoint = "http://128.238.38.77:8080/axis/CloudComputingProject.jws";
    	// SOAP Action
    	String soapAction = endPoint + "/" + methodName;
    	SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    	envelope.dotNet = true;
    	envelope.bodyOut = rpc;
    	envelope.setOutputSoapObject(rpc);
    	HttpTransportSE transport = new HttpTransportSE(endPoint);
    	try {
    		// call WebService
    		transport.call(soapAction, envelope);
    	} 
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	// return result
    	return (SoapObject) envelope.bodyIn;
	}
}
