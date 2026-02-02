package com.example.sport_notification_service;

import org.apache.activemq.broker.BrokerService;

public class MessageBroker {
    public static void main(String[] args) throws Exception {
        BrokerService broker = new BrokerService();
        broker.setPersistent(false);
        broker.addConnector("tcp://localhost:61616");
        broker.start();
    }
}
