package controler;

import lombok.AllArgsConstructor;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Controller;

import javax.jms.*;

@Controller
@AllArgsConstructor
public class MqController {
    String brokerUrl;
    String queueName;
    String topicName;

    public void sendTransaction(String transaction) {
        if (!brokerUrl.equals("")) {
            try {
                if (!queueName.equals("")) sendQueue(transaction);
                if (!topicName.equals("")) sendTopic(transaction);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendQueue(String transaction) throws JMSException {
        Connection connection = null;
        ActiveMQConnectionFactory mqConnectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        try {
            connection = mqConnectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);
            Message message = session.createTextMessage(transaction);
            MessageProducer sender = session.createProducer(queue);
            sender.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
            if (connection != null) connection.close();
        }
    }

    private void sendTopic(String transaction) throws JMSException {
        Connection connection = null;
        try {
            ActiveMQConnectionFactory mqConnectionFactory = new ActiveMQConnectionFactory(brokerUrl);

            connection = mqConnectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(topicName);
            connection.start();
            Message message = session.createTextMessage(transaction);
            MessageProducer producer = session.createProducer(topic);
            producer.send(message);
            Thread.sleep(800);
            session.close();
        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
            if (connection != null) connection.close();
        }
    }
}
