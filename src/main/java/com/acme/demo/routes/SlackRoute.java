package com.acme.demo.routes;


import org.apache.camel.CamelExecutionException;
import org.apache.camel.LoggingLevel;
import org.apache.camel.PropertyInject;
import org.apache.camel.builder.RouteBuilder;

import org.springframework.stereotype.Component;

@Component
public class SlackRoute extends RouteBuilder {

    @PropertyInject("{{application.connection.slack.webhook.url}}")
    private String slackWebHook;

  

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

        from("direct:slack-notification").id("direct-slack-notification")
                //.process(notificationProcessor)
                .to("slack:#notification?iconEmoji=:camel&webhookUrl=" + slackWebHook).id("slack-producer")
                .log(LoggingLevel.INFO, "Test Post posted to slack-${body}");
    }
}