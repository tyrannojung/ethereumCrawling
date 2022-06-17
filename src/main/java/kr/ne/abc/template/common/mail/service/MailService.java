package kr.ne.abc.template.common.mail.service;

import kr.ne.abc.template.common.mail.dto.EmailDTO;

public interface MailService {
	
	public void sendToHtml(String from, String to, String title, String content) throws Exception;
	
	public void sendToHtml(EmailDTO email) throws Exception;
	
	public boolean sendTestMail(EmailDTO email) throws Exception;
	
	public void sendToHtml(String from, String[] to, String title, String content) throws Exception;
	
	public void sendToHtml(EmailDTO email, String[] to) throws Exception;

}
