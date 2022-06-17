package kr.ne.abc.template.common.pg;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

@Component
public class Iamport {
	
	/** IamportClient **/
	public IamportClient getBillingTestClient() {
		String test_api_key = "2674411053831369";
		String test_api_secret = "0rIGMLl2p4tQbCRK1F7c3NaIFrRPcsvcUeSGZeIpicfuP6eQMm3P9eyPFqFRSa3MpWtpbcOup4Yebd4Y";

		return new IamportClient(test_api_key, test_api_secret);
	}
	
	/**
	 * testCancelPaymentChecksumByImpUid
	 * @param imp_uid
	 * @return
	 */
	public boolean testCancelPaymentChecksumByImpUid(
			String imp_uid){
		
		if (imp_uid != null) {
			// ============================ BEGIN PAYMENT CANCEL
			IamportClient billingClient = getBillingTestClient();
			
			String test_already_cancelled_imp_uid = imp_uid;			
			CancelData cancel_data = new CancelData(test_already_cancelled_imp_uid, true); //imp_uid를 통한 전액취소
			
			// 검증 데이터
			try {
				IamportResponse<Payment> payment_response = billingClient.cancelPaymentByImpUid(cancel_data);
				
				//assertNull(payment_response.getResponse()); // 이미 취소된 거래는 response가 null이다
				//System.out.println(payment_response.getResponse());
				//System.out.println(payment_response.getMessage());
				if (payment_response.getResponse() != null) {
					//System.out.println("결제취소 성공");
					return true;
					
				}
				
			} catch (IamportResponseException e1) {
				System.out.println(e1.getMessage());
				
				switch(e1.getHttpStatusCode()) {
				case 401 :
					//TODO
					break;
				case 500 :
					//TODO
					break;
				}
				
			} catch (IOException e1) {
				e1.printStackTrace();
				
			}
			// ============================ END PAYMENT CANCEL
			
		}
		
		return false;
		
    }
	
	/**
	 * testPartialCancelPaymentAlreadyCancelledImpUid
	 * @param imp_uid
	 * @param cancel_price
	 * @return
	 */
	public boolean testPartialCancelPaymentAlreadyCancelledImpUid(
			String imp_uid
			, Double cancel_price){
		
		if (imp_uid != null) {
			// ============================ BEGIN PAYMENT CANCEL
			IamportClient billingClient = getBillingTestClient();
			
			String test_already_cancelled_imp_uid = imp_uid;			
			CancelData cancel_data = new CancelData(test_already_cancelled_imp_uid, true, BigDecimal.valueOf(cancel_price)); //imp_uid를 통한 500원 부분취소
			
			// 검증 데이터
			try {
				IamportResponse<Payment> payment_response = billingClient.cancelPaymentByImpUid(cancel_data);
				
				//assertNull(payment_response.getResponse()); // 이미 취소된 거래는 response가 null이다
				//System.out.println(payment_response.getResponse());
				//System.out.println(payment_response.getMessage());
				if (payment_response.getResponse() != null) {
					//System.out.println("결제부분취소 성공");
					return true;
					
				}
				
			} catch (IamportResponseException e) {
				System.out.println(e.getMessage());
				
				switch(e.getHttpStatusCode()) {
				case 401 :
					//TODO
					break;
				case 500 :
					//TODO
					break;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				
			}
			// ============================ END PAYMENT CANCEL
			
		}
		
		return false;
		
	}
	
}
