package kr.ne.abc.template.common.mail.util;

import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import kr.ne.abc.template.common.mail.dto.EmailDTO;

public class Mail {
	private Queue<EmailDTO> qe = new LinkedList<EmailDTO>();
	private static Mail instance = null;
	private Properties props = null;
	private String password = null;
	private String username = null;
	private ScheduledMailJob job = new ScheduledMailJob();

	private Timer jobScheduler = null;
	private String classpath = this.getClass().getResource("/").getPath();
	//private DecimalFormat df = new DecimalFormat("0.#");

	private Mail() {
		props = new Properties();
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		username = "dev.4intel";
		password = "abc123ok**";
			
		job.setMailer(this);
	    jobScheduler = new Timer();
	    int delay = 1000;
	    int period = 1000;
		
		/*
		props = new Properties();
		props.put("mail.smtp.auth", config.getString("mail.smtp.auth"));
		props.put("mail.smtp.starttls.enable", config.getString("mail.smtp.starttls.enable"));
		props.put("mail.smtp.host", config.getString("mail.smtp.host"));
		props.put("mail.smtp.port", config.getString("mail.smtp.port"));

		password = config.getString("mail.password");
		username = config.getString("mail.username");
			
		job.setMailer(this);
	    jobScheduler = new Timer();
	    int delay = config.getInt("mail.scheduler.delay");
	    int period = config.getInt("mail.scheduler.period");
	    */
		
	    jobScheduler.scheduleAtFixedRate(job, delay, period);
	}

	public static Mail getInstance() {
		if (instance == null) {
			instance = new Mail();
		}
		return instance;
	}
	public static void newInstance() {
		if( instance != null ) {
			instance = new Mail();
			System.out.println("Mail instance update");
		}
	}
	
	public void sendMail(String from, String to, String title, String content) {
		this.sendMail(new EmailDTO(from, to, title, content));
	}
	
	public void sendMail(EmailDTO mail) {
		qe.add(mail);
	}
	
	public boolean testMailSend(EmailDTO m) {
		boolean isSendMail = true;
		try {
			Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
				
			Message message = null;
			
			try{
				message = new MimeMessage(session);
				message.setFrom(new InternetAddress(m.getFrom()));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(m.getTo()));
				message.setSubject(m.getSubject());
				message.setContent(m.getContent(), "text/html; charset=utf-8");
	
				Transport.send(message);
			} catch(MessagingException e1) {
				isSendMail = false;
				//System.out.println(e1.toString());
				//e1.printStackTrace();
			}

			//System.out.println("sendMail {isSendMail:"+isSendMail+", from:"+m.getFrom()+", to:"+m.getTo()+", title:"+m.getSubject()+"}");
		} catch (Exception e) {
			//System.out.println("Mail send error");
		}
		
		return isSendMail;
	}

	public void send() {
		
		try {
			Session session = null;
			int size = qe.size();
			if( size > 0 ) {
				session = Session.getInstance(props,
						new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								//System.out.println("username:"+ username + ", password:" + password);
								return new PasswordAuthentication(username, password);
							}
				});
				
				while (qe.size() > 0) {
					EmailDTO m = null;
					Message message = null;
					boolean isSendMail = true;
					
					try{
						m = qe.poll();
						message = new MimeMessage(session);
						message.setFrom(new InternetAddress(m.getFrom()));
						message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(m.getTo()));
						message.setSubject(m.getSubject());
						message.setText(m.getContent());
	
						Transport.send(message);
					} catch(MessagingException e1) {
						isSendMail = false;
						//System.out.println(e1.toString());
						//e1.printStackTrace();
					}

					//System.out.println("sendMail {isSendMail:"+isSendMail+", from:"+m.getFrom()+", to:"+m.getTo()+", title:"+m.getSubject()+"}");
				}// while
				
			}
		} catch (Exception e) {
			//System.out.println("Mail send error");
			// throw new RuntimeException(e);
		}
	}

	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Mail mailservice = Mail.getInstance();
		EmailDTO mail = new EmailDTO();
		mail.setFrom("rootguys@gmail.com");
		mail.setTo("shinil.kim@hotmail.com");
		mail.setSubject("Title!");
		mail.setContent("Test!!!");
		mailservice.getInstance().sendMail(mail);

		
		try {
			Thread.sleep(3000000);
			
		} catch (InterruptedException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage().toString());
			
		}
	}
	*/



}

class ScheduledMailJob extends TimerTask {
	private Mail mailer = null;
	
	public void run() {
		mailer.send();
	}
	
	public void setMailer(Mail instance) {
		this.mailer = instance;
	}
}