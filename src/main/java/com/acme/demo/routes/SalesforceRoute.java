package com.acme.demo.routes;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


import org.apache.camel.Exchange;
import org.apache.camel.PropertyInject;

import org.apache.camel.component.http.HttpMethods;
import org.apache.camel.model.rest.RestBindingMode;

@Component
public class SalesforceRoute extends RouteBuilder {

    @PropertyInject("{{nsroute.urll}}")
    private String nsrouteUrl;

    @Override
    public void configure() throws Exception {

        onException(CamelExecutionException.class)
                .handled(true)
                .redeliveryDelay(2000)
                .maximumRedeliveries(5)
                // asynchronous redelivery
                .asyncDelayedRedelivery()
                // log the exception details
                .to("log:exceptionLogger");

        // call the embedded rest service from the RestController
        /*from("salesforce:{{salesforce.contact-topic}}")
                .unmarshal().json()
                .choice()
                    .when(header("CamelSalesforceEventType").isEqualTo("created"))
                        .log("New Salesforce contact was created: " +
                                "[ID:${body[Id]}, Name:${body[Name]}, Email:${body[Email]}, Phone: ${body[Phone]}]")
                    .when(header("CamelSalesforceEventType").isEqualTo("updated"))
                        .log("A Salesforce contact was updated: " +
                                "[ID:${body[Id]}, Name:${body[Name]}, Email:${body[Email]}, Phone: ${body[Phone]}]")
                    .when(header("CamelSalesforceEventType").isEqualTo("undeleted"))
                        .log("A Salesforce contact was undeleted: " +
                                "[ID:${body[Id]}, Name:${body[Name]}, Email:${body[Email]}, Phone: ${body[Phone]}]")
                    .when(header("CamelSalesforceEventType").isEqualTo("deleted"))
                        .log("A Salesforce contact was deleted: [ID:${body[Id]}]");*/

        from("salesforce:{{salesforce.account-topic}}")
                .unmarshal().json()
                .choice()
                .when(header("CamelSalesforceEventType").isEqualTo("created"))
                .log("DEMO 123456013 -New Salesforce Account was created: " +
                        "[ID:${body[Id]}, Name:${body[Name]}, AccountNumber:${body[AccountNumber]}, NumberOfEmployees:${body[NumberOfEmployees]}, BillingAddress: ${body[BillingAddress]}]")
                         
                .when(header("CamelSalesforceEventType").isEqualTo("updated"))
                .log("A Salesforce Account was updated: " +
                        "[ID:${body[Id]}, Name:${body[Name]}, AccountNumber:${body[AccountNumber]}, NumberOfEmployees:${body[NumberOfEmployees]}, BillingAddress: ${body[BillingAddress]}]")
                .when(header("CamelSalesforceEventType").isEqualTo("undeleted"))
                .log("A Salesforce Account was undeleted: " +
                        "[ID:${body[Id]}, Name:${body[Name]}, AccountNumber:${body[AccountNumber]}, NumberOfEmployees:${body[NumberOfEmployees]}, BillingAddress: ${body[BillingAddress]}]")
                .when(header("CamelSalesforceEventType").isEqualTo("deleted"))
                .log("Demo 12982 A Salesforce Account was deleted: [ID:${body[Id]}]");

               
                // from("direct:netsuite-create-customer-route").id("direct-customer-master")
                //         .log("request received for netsuite..\n ${body}")
                //         .setHeader(Exchange.HTTP_QUERY, header("details"))
                //         .setHeader(Exchange.HTTP_URI, constant(nsrouteUrl))
                //         .setHeader(Exchange.HTTP_METHOD, HttpMethods.POST)
                      
                //         .to("https://netsuite-post").id("netsuite-post")
                //         .setBody(simple("{\"recordId\" : ${body} }"))
                //         .log("content received from https invocation is:\n${body}");
    }
}
