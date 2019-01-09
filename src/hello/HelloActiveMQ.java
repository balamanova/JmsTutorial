package hello;
 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

public class HelloActiveMQ {
 
    public static void main(String[] args){
 
        try {
            String queueName = "Hello";
            ActiveMQConnectionFactory connectionFactory 
                    = new ActiveMQConnectionFactory("tcp://localhost:61618");
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            TextMessage message = session.createTextMessage("Hello world!");
            producer.send(message);
            session.close();
            connection.close();

            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(queueName);
            MessageConsumer consumer = session.createConsumer(destination);
            message = (TextMessage)consumer.receive(1000);
            System.out.println(message.getText());
            consumer.close();
            session.close();
            connection.close();
        } catch (JMSException ex) {
            Logger.getLogger(HelloActiveMQ.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }
}

