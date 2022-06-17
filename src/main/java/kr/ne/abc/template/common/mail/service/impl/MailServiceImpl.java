package kr.ne.abc.template.common.mail.service.impl;

import org.springframework.stereotype.Service;

import kr.ne.abc.template.common.mail.dto.EmailDTO;
import kr.ne.abc.template.common.mail.service.MailService;
import kr.ne.abc.template.common.mail.util.Mail;

@Service("mailService")
public class MailServiceImpl implements MailService {
	
	//@Autowired
	//private JavaMailSenderImpl mailSender;
	
	//private Logger logger = LoggerFactory.getLogger(this.getClass());
	//private MyLogger log = new MyLogger(getClass(), 0);
	
	@Override
	public void sendToHtml(String from, String to, String title, String content) throws Exception {
		Mail.getInstance().sendMail(from, to, title, content);
	}
	
	@Override
	public void sendToHtml(EmailDTO email) throws Exception {
		Mail.getInstance().sendMail(email);
	}
	
	@Override
	public boolean sendTestMail(EmailDTO email) throws Exception {
		return Mail.getInstance().testMailSend(email);
	}

	@Override
	public void sendToHtml(String from, String[] toArray, String title, String content) throws Exception {
		for (String to2 : toArray) {
			Mail.getInstance().sendMail(from, to2, title, content);
		}
	}
	
	@Override
	public void sendToHtml(EmailDTO email, String [] to) throws Exception {
		this.sendToHtml(email.getFrom(), to, email.getSubject(), email.getContent());
	}
	
	/*
	public static void main(String[] args) throws Exception {
		kr.or.itkc.tps.cmm.service.MailService ms = new kr.or.itkc.tps.mail.service.impl.MailServiceImpl();
		ms.sendToHtml("shinil.kim@hotmail.com", "shinil.kim@hotmail.com", "title(타이틀)", "content(내용)");
	}
	*/

}
