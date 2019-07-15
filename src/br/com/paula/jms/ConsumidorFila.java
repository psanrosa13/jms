package br.com.paula.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class ConsumidorFila {

	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection(); 
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Queue queue = (Queue) context.lookup("fila");
		
		MessageConsumer consumer = session.createConsumer(queue);
		
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {

				TextMessage textMessage = (TextMessage)message;
				
				try {
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			
		});
				
		session.close();
		connection.close();
		context.close();
	}
}
