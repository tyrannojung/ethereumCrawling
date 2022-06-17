package kr.ne.abc.template.config;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.catalina.connector.Connector;
import org.apache.commons.httpclient.contrib.ssl.EasySSLProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectorConfig {
    
	@Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        //tomcat.addAdditionalTomcatConnectors(createSslConnector());
        setEasyProtocol(443); // default https setEasyProtocol startup
        return tomcat;
    }

    private Connector createSslConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setSecure(false);
        connector.setPort(80); // default http protocol Port Setting
        return connector;
    }
	
	//-------------------------------------------------------------
	// Https Settings
	//-------------------------------------------------------------
	@SuppressWarnings("deprecation")
	public static boolean setEasyProtocol(int defaultPort) {
		try {
			Protocol.registerProtocol("https", new Protocol("https", new EasySSLProtocolSocketFactory(), defaultPort));
			return true;
			
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		return false;
	}
    
}
