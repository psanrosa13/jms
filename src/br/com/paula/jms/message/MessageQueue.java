package br.com.paula.jms.message;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageQueue {
	
	private InitialContext context;
	
	private Session session;
	
	private Connection connection;
	
	public MessageQueue() throws NamingException, JMSException {
		context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		connection = factory.createConnection(); 
		connection.start();
		
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}



	public void getMessage() throws NamingException, JMSException {
		Destination fila = (Destination) context.lookup("financeiro");
		MessageConsumer consumer = session.createConsumer(fila );
		
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
