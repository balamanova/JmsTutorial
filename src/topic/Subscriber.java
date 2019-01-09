package topic;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Subscriber {

    Session session;
    Connection connection;
    MessageConsumer consumer;
    Topic topic;

    public void create(String topicName) throws JMSException {
        // TODO JMS add implementation
        ActiveMQConnectionFactory connectionFactory = new
        ActiveMQConnectionFactory("tcp://localhost:61616");
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false,
        Session.AUTO_ACKNOWLEDGE);
       topic= session.createTopic(topicName);
        consumer = session.createConsumer(topic, topicName);

    }

    public void close() throws JMSException {
        try {
            consumer.close();
        } catch (JMSException ex) {
            Logger.getLogger(chatroom.Subscriber.class.getName()).log(Level.SEVERE, null, ex);
        }
        session.close();
        connection.close();
        System.out.println("Connection closed");
    }

    public String receive() {
        // TODO JMS add implementation
        String text = null;
        try {
            Message message = consumer.receive(1000);

            if (message != null) {
                TextMessage textMessage = (TextMessage) message;
                text = textMessage.getText();
            }
        } catch (JMSException ex) {
            Logger.getLogger(chatroom.Subscriber.class.getName()).log(Level.SEVERE, null, ex);
        }
        return text;
    }

}
