package com.comet.orderserviceclient.client;

import com.comet.orderserviceclient.soap.bindings.GetOrderRequest;
import com.comet.orderserviceclient.soap.bindings.GetOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;


@Service
public class OrderClient {

    @Autowired
    private Jaxb2Marshaller jaxb2Marshaller;

    private WebServiceTemplate webServiceTemplate;

    public GetOrderResponse showRequestDetails(GetOrderRequest request){
        webServiceTemplate = new WebServiceTemplate(jaxb2Marshaller);
        return (GetOrderResponse) webServiceTemplate.marshalSendAndReceive("http://192.168.128.54:8080/ws",request);
    }
}
