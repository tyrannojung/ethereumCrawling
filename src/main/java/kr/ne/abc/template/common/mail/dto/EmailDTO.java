package kr.ne.abc.template.common.mail.dto;

public class EmailDTO {
	
	String subject;
	String content;
	
	String from;
	String to;
	
	public EmailDTO(){
		
	}
	
	@Override
	public String toString() {
		return "{from:"+from+", to:"+to+", subject:"+subject+", content:"+content+"}";
	}
	
	public EmailDTO(String from, String to, String subject, String content) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.content = content;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setSubject(Object subject) {
		this.subject = (String) subject;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setContent(Object content) {
		this.content = (String) content;
	}
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public void setFrom(Object from) {
		this.from = (String) from;
	}
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public void setTo(Object to) {
		this.to = (String) to;
	}

}
