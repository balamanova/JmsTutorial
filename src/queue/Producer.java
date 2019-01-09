package queue;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
    public static void sendMessage(String queueName, String message) throws JMSException{
        // TODO JMS add implementation
        
        ActiveMQConnectionFactory connectionFactory = new  ActiveMQConnectionFactory("tcp://localhost:61618"); 
        Connection connection = connectionFactory.createConnection(); 
        connection.start(); 
        // not really needed 
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE); 
        Destination destination = session.createQueue("DemoQueue"); 
        MessageProducer producer = session.createProducer(destination); 
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT); 
        String text = "Queue: "+ queueName + "\nMessage: " + message;
       
        TextMessage message1; 
        message1 = session.createTextMessage(text);
        producer.send(message1); 
        session.close(); 
        connection.close();
        
        System.out.println("Queue: "+ queueName );
        System.out.println("Message: " + message);
    }
}
