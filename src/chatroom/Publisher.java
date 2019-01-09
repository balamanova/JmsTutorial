package chatroom;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Publisher {

    Session session;
    Destination destination;
    Connection connection;

    public void create() throws JMSException {

        // TODO JMS add implementation
        System.out.println("Topic created");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61618");
        connection = connectionFactory.createConnection();
        connection.start();
        // not really needed 
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue("ChatRoom");

    }

    public void send(String message) throws JMSException {
        // TODO JMS add implementation
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        String text = "Message: " + message;

        TextMessage message1 = session.createTextMessage(text);
        producer.send(message1);
        System.out.println("Message: " + message);
    }

    public void close() throws JMSException {
        // TODO JMS add implementation

        session.close();
        connection.close();
        System.out.println("Connection closed");
    }
}
