package br.com.paula.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class ProdutorFila {

	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection(); 
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination destino = (Destination) context.lookup("fila");
		
		MessageProducer producer = session.createProducer(destino);
		
		Message message = session.createTextMessage("Mensagem 1");
		producer.send(message);
				
		session.close();
		connection.close();
		context.close();
	}
}
