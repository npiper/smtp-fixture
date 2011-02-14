package com.smsmt.smtpfixture;

import javax.mail.MessagingException;

import org.junit.Assert;
import org.junit.Test;

public class EmailFixtureTest {

	/** Test of a real Dumbster email send.
	 * Starts the Dumbster local server on port 25 during the test
	 * Sends an email.
	 * It is then confirmed that the email was received by
	 * the dumbster mock SMTP server using it's API.
	 * 
	 */
	@Test
	public void testEmailFixtureSubjectIsCorrect()
	{
		EmailFixture fixture = new EmailFixture();

		// Send an email message
		try {
			fixture.postLocalMail(new String[] { "n_piper@hotmail.com" },
					"subject", "message", "from");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Assert.assertEquals("Subject did not match.","subject", fixture.getSubject());

		fixture.stopTestEmailServer();
	}
	
	@Test
	public void testPostLocalMail()
	{
		
	}
	
}
