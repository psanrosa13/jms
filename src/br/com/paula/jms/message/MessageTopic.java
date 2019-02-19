package br.com.paula.jms.message;

import java.util.Scanner;

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

public class MessageTopic {

	private InitialContext context ;
	
	private ConnectionFactory factory;
	
	private Session session ;
	
	private Connection connection;
		
	public MessageTopic() throws NamingException, JMSException {
		context = new InitialContext();
		factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		connection = factory.createConnection(); 
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	public void getMessage() throws NamingException, JMSException {
		Destination topico = (Destination) context.lookup("loja");
		MessageConsumer consumer = session.createConsumer(topico);
		
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
				
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
	}
}
