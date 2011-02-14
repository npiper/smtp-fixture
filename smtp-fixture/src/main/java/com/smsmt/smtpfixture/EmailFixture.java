package com.smsmt.smtpfixture;

import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;
import fit.ColumnFixture;

public class EmailFixture extends ColumnFixture {

	public EmailFixture() {
		emailServer = SimpleSmtpServer.start();

	}

	public void stopTestEmailServer() {
		emailServer.stop();
	}

	private SimpleSmtpServer emailServer;

	private String subject;

	private Set<String> toAddresses;

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {

		SmtpMessage email = (SmtpMessage) emailServer.getReceivedEmail().next();
		return email.getHeaderValue("Subject");
	}

	public void setToAddresses(Set<String> toAddresses) {
		this.toAddresses = toAddresses;
	}

	public Set<String> getToAddresses() {
		return toAddresses;
	}

	public static void main(String[] args) {
		EmailFixture fixture = new EmailFixture();

		// Send an email message
		try {
			fixture.postLocalMail(new String[] { "n_piper@hotmail.com" },
					"subject", "message", "from");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Throws assertion error if didn't work
		assert (fixture.getSubject().equals("subject"));

		fixture.stopTestEmailServer();
	}

	public void postLocalMail(String recipients[], String subject,
			String message, String from) throws MessagingException {
		boolean debug = false;

		// Set the host smtp address
		Properties props = new Properties();
		props.put("mail.smtp.host", "127.0.0.1");

		// create some properties and get the default Session
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(debug);

		// create a message
		Message msg = new MimeMessage(session);

		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);

		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			addressTo[i] = new InternetAddress(recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);

		// Optional : You can also set your custom headers in the Email if you
		// Want
		msg.addHeader("MyHeaderName", "myHeaderValue");

		// Setting the Subject and Content Type
		msg.setSubject(subject);
		msg.setContent(message, "text/plain");
		Transport.send(msg);
	}

}
