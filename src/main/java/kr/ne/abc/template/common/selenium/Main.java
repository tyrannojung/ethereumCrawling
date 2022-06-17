package kr.ne.abc.template.common.selenium;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
	
	private static WebElement element;
	
	public static void main(String[] args) {
		WebDriver driver = null;
		List<WebElement> webElements = null;
		
		System.setProperty("webdriver.chrome.driver", "C:\\selenium\\chromedriver/chromedriver.exe");// WebDriver 압축 해제 경로 입력
		driver = new ChromeDriver();// WebDriver 객체 생성
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);// 로드 웹페이지에서 특정 요소를 찾을 때까지 기다리는 시간 설정
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);// 페이지로드가 완료 될 때까지 기다리는 시간 설정
		driver.manage().window().maximize();// 브라우저 창 최대화
		driver.get("https://etherscan.io/blocks");// 웹 자동화 작업을 할 접속 사이트 명시
		element = driver.findElement(By.cssSelector(".pagination-sm .page-item:last-child .page-link"));//상단 메뉴바의 링크 추출
		element.click();
		element = driver.findElement(By.cssSelector("#ContentPlaceHolder1_topPageDiv > nav > ul > li:nth-child(2)"));

		int numcheck;
		while(true) {
			webElements = driver.findElements(By.cssSelector("#content > div.container.space-bottom-2 > div.card > div > div.table-responsive.mb-2.mb-md-0 > table > tbody > tr"));
			Collections.reverse(webElements);
			
			for(WebElement webElement : webElements) {
				List<WebElement> webElement2 = webElement.findElements(By.cssSelector("td"));
				
				//리스트를 DTO에 담아서 한번에 출력한다.
				for(WebElement webElement3 : webElement2){
					System.out.println("value =" + webElement3.getText());
				}
				
			}

			element = driver.findElement(By.cssSelector("#ContentPlaceHolder1_topPageDiv > nav > ul > li:nth-child(2)"));
			
			if(element.getAttribute("class").contains("disabled")) {
				System.out.println("완료했습니다.");
				break;
			}
			
			element.click();
			
		}
	}
}
