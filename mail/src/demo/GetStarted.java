package demo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

public class GetStarted extends Authenticator {
	private Session session;
	private PasswordAuthentication authentication;

	public GetStarted(String username, String password) {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imap");
		props.setProperty("mail.imap.host", "imap.sina.com");
		props.setProperty("mail.imap.port", "143");
		props.setProperty("mail.imap.auth.login.disable", "true");

		authentication = new PasswordAuthentication(username, password);
		session = Session.getInstance(props, this);
		session.setDebug(true);
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return this.authentication;
	}

	public void connect() throws IOException {
		try {
			Store store = session.getStore();
			store.connect();
			Folder root = store.getDefaultFolder();
			Folder inbox = root.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);
			System.out.println(inbox.getMessageCount());
			
//			Message[] msgs = inbox.getMessages();
//			for (Message msg : msgs) {
//				System.out.println(msg.getSubject() + "("
//						+ msg.getReceivedDate() + ")");
//			}
		} catch (MessagingException e) {
			try {
				byte[] buf = e.getMessage().getBytes("ISO-8859-1");
				System.out.println(new String(buf, "GBK"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException("登录失败", e);
		}
	}

	public static void main(String[] args) throws IOException {
		GetStarted gs = new GetStarted("nicktrey@sina.com", "baishuihu");
		gs.connect();
	}
}
