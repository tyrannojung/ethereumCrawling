package kr.ne.abc.template.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component // 스프링의 기타 bean
@Aspect // aop bean (Aspect 역할을 할 클래스라고 명시)
public class LogAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(LogAdvice.class);
	
	// 포인트컷 - 실행 시점, Around - 실행 전후
	// Before, After, Around
	// 컨트롤러,서비스,맵퍼의 모든 method 실행 전후에 logPrint method가 호출됨
	// execution( 리턴자료형 class.method(매개변수) )	
	
	/**
	 * Pointcut이라고 명시된 메서드가 필요
	 * @Pointcut의 속성에 핵심코드의 어느 부분까지 공통기능을 사용하겠다고 명시
	 * @Pointcut을 사용하지 않으셔도 됩니다. 
	 * 아래애 @Before, @After 어노테이션을 보면 속성에 바로 범위를 지정을 해놨는데요. 
	 * 같은 방법으로 @Around에 속성을 지정해주어도 되고, Pointcut을 따로 지정해주는 메서드를 생성하셔도 됩니다.(내용은 없어도 돼요)
	 */
	@Pointcut("execution(* kr.ne.abc.template..*Controller.*(..))"
			+ " or execution(* kr.ne.abc.template..*Impl.*(..))"
			+ " or execution(* kr.ne.abc.template..*Mapper.*(..))")
	private void pointcutMethod() {
		
	}	
	
	@Before("pointcutMethod()") //before가 적용될 포인트컷을 명시 : pointcutMethod()
	public void beforeMethod() {
		//System.out.println("==============================================");
		//System.out.println("AOP beforeMethod() 실행");
	}
	
	@After("pointcutMethod()") //before가 적용될 포인트컷을 명시 : pointcutMethod()
	public void afterMethod() {
		//System.out.println("AOP afterMethod() 실행");
	}
	
	@Around("execution(* kr.ne.abc.template..*Controller.*(..))"
			+ " or execution(* kr.ne.abc.template..*Impl.*(..))"
			+ " or execution(* kr.ne.abc.template..*Mapper.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		Object result = joinPoint.proceed();		
		
		//공통 기능이 적용되는 메서드가 어떤 메서드인지 출력하기 위해 메서드명을 얻어옴
        String signatureStr = joinPoint.getSignature().toShortString();
        //System.out.println(signatureStr + " START"); //메서드 실행
        
        //공통기능
        //System.out.println("AOP 핵심 기능 전에 실행 할 공통 기능입니다. " + System.currentTimeMillis());
        
        try {            
            // class name(공통 기능이 적용되는 메서드가 어떤 메서드인지 출력하기 위해 메서드명을 얻어옴)
    		String type = joinPoint.getSignature().getDeclaringTypeName();
    		
    		String name = "";
    		if (type.indexOf("Controller") > -1) {
    			name = "Controller \t: ";
    		} else if (type.indexOf("Service") > -1) {
    			name = "ServiceImpl \t: ";
    		} else if (type.indexOf("Mapper") > -1) {
    			name = "Mapper \t: ";
    		}
    		
    		// method name
    		//logger.info(name + type + "." + joinPoint.getSignature().getName() + "()");
    		
    		//매개변수
    		//logger.info(Arrays.toString(joinPoint.getArgs()));
    		
    		//핵심기능
    		long end = System.currentTimeMillis();
    		long time = end-start;
    		//logger.info("실행시간 : " + time);
			
		} finally {
            //공통기능
            //System.out.println("AOP 핵심 기능 후에 실행 할 공통 기능입니다. " + System.currentTimeMillis());
            
            //System.out.println(signatureStr + " END");
            //System.out.println("==============================================");
		}
		
		return result;
	}

}
