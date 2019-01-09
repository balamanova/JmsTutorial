package topic;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Publisher {

    Session session;
    Topic topic;
    Connection connection;
    MessageProducer producer;

    public void create(String topicName) throws JMSException {
        // TODO JMS add implementation
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start(); // not really needed
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        topic = session.createTopic(topicName);
        producer = session.createProducer(topic);

        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
         MessageConsumer consumer1 = session.createDurableSubscriber(topic, topicName, "", false); 
        System.out.println("Connection created");

    }

    public void send(String message) throws JMSException {
        // TODO JMS add implementation
        String text = "Message: " + message;
        TextMessage textMessage = session.createTextMessage(text);
        producer.send(textMessage);

    }

    public void close() throws JMSException {
        // TODO JMS add implementation
        session.close();
        connection.close();
        System.out.println("Connection closed");
    }
}
