package kr.ne.abc.template.agent.controller;

import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgentController {
	
	private static final Logger logger = LoggerFactory.getLogger(AgentController.class);

	@PostMapping("/api")
	public String nhnft(
			Locale locale

			
			
			, @RequestBody HashMap<String, Object> map
			, HttpSession session) {
		
		String strResult = "{ \"result\":\"FAIL\", \"value\":\"[]\" }";
		
		/**
	     * ============================= NHNFT DB AGENT =============================
	     */
		
		if (map.get("cmd").equals("keyword_create")) {		
			if (1 == 1)
				strResult = "{ \"result\":\"OK\" }";
				
			else {
				strResult = "{ \"result\":\"FAIL\" }";
				
			}
		}
		return strResult;
		
	}
	
}
