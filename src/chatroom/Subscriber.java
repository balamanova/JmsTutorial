package chatroom;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Subscriber implements MessageListener {

    Callback callback;
    Session session;
    Connection connection;
    MessageConsumer consumer;

    public void create(String topicName, Callback callback) throws JMSException {
        // TODO JMS add implementation
        // TODO JMS add implmentation
       ActiveMQConnectionFactory connectionFactory = new
ActiveMQConnectionFactory("tcp://localhost:61616");
Connection connection = connectionFactory.createConnection();
connection.start(); // not really needed
Session session = connection.createSession(false,
Session.AUTO_ACKNOWLEDGE);
Destination destination = session.createQueue("DemoQueue");
MessageProducer producer = session.createProducer(destination);
producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

    }

    public void close() throws JMSException {
        try {
            // TODO JMS add implementation

            consumer.close();
        } catch (JMSException ex) {
            Logger.getLogger(Subscriber.class.getName()).log(Level.SEVERE, null, ex);
        }
        session.close();
        connection.close();
        System.out.println("Connection closed");
    }

    @Override
    public void onMessage(Message msg) {
        try {
            Message message = consumer.receive(1000);
            String text = null;
            if (message != null) {
                TextMessage textMessage = (TextMessage) message;
                text = textMessage.getText();
            }
            callback.addMessage(text);
        } catch (JMSException ex) {
            Logger.getLogger(Subscriber.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
