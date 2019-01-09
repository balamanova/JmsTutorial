package queue;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {
    public static String receiveMessage(String queueName) throws JMSException{
        // TODO JMS add implmentation
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61618"); 
        Connection connection = connectionFactory.createConnection(); 
        connection.start(); 
      //  connection.setExceptionListener(this); 
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE); 
        Destination destination = session.createQueue("DemoQueue"); 
        MessageConsumer consumer = session.createConsumer(destination); 
        Message message = consumer.receive(1000); 
        String text = null;
        if (message != null) {            
            TextMessage textMessage = (TextMessage) message;            
            text = textMessage.getText();
        }
        consumer.close(); 
        session.close(); 
        connection.close();
        return text;
    }
}
