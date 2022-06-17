package kr.ne.abc.template.common.util;

import java.io.*;
import java.net.*;

public class SMS {
	
	protected Socket socket;
	   int port = 7296;
	   
	   String destination = "210.109.111.38";
	   DataOutputStream out;
	   DataInputStream in;
	   private String ID=null;
	   private String PW=null;

	   public SMS(){
		   ID="localhost";//업체 ID 추가 하세요( 10자리 미만 )
		   PW="localhost";//업체 PW 추가 하세요( 10자리 미만 ) 
	   }
	   
	   public void connect() throws Exception{
	       socket = new Socket(destination,port);
		   socket.setSoTimeout (5000);
		   out = new DataOutputStream(socket.getOutputStream()) ;
		   in = new DataInputStream(socket.getInputStream());
	   }
	   
	   public void disconnect() throws Exception{
	       in.close();
		   out.close();
		   socket.close();

	    
	   }
	   
	   //전화번호와 회신번호는 숫자만가능 
	   public String SendMsg(String callNo, String callBack,String caller, String msg) throws IOException
	   {
	  
	       String result = "",msgid = "";
	       int i = 0;
		   out.writeBytes("01"); //Msg Type
		   out.writeBytes("144 "); //Msg Len
		   out.writeBytes(fillSpace(ID,10)); 
		   out.writeBytes(fillSpace(PW,10)); 
		   out.writeBytes(fillSpace(callNo,11));	   
		   out.writeBytes(fillSpace(callBack,11));
	       out.writeBytes(fillSpace(caller,10));
		   out.writeBytes(fillSpace(" ",12)); //즉시전송시 날짜와 시간은 모두 space
		   //예약 전송시 12자리를 맞춰주시면 됩니다. 형식은 YYYYMMDDHH
	       out.writeBytes(fillSpace(msg,80)); // msg
	       out.flush();

		   boolean inputExist = true;
		   
		   do{
		       try{									
			        byte buffer[] = new byte[2];
			        for(i=0 ; i < 2; i++)buffer[i] = in.readByte();
			        String msgType = new String(buffer);
	                byte temp[] = new byte[4];
			        for(i = 0; i < 4 ; i++) temp[i] = in.readByte();
	                String sLen = new String(temp);
					sLen = sLen.trim();

			        int nLen = Integer.valueOf(sLen).intValue();
	                buffer = new byte[nLen];

			        if(msgType.equals("02")){
			           inputExist = false;

				       for(i=0 ; i < nLen; i++)buffer[i] = in.readByte();
				          result = new String(buffer);
						  
	                   //result 의 구조는 성공/실패 2 char, 전화번호 11 char , msgid = 10 char
	                   if(result.substring(0,2).equals("00"))
			              msgid = result.substring(13);
					   else
				          msgid = "";
			        }else if(msgType.equals("03")){
					   
	                   for(i=0 ; i < nLen; i++) buffer[i] = in.readByte();
				          result = new String(buffer);
	                   out.writeBytes("04");
					   out.writeBytes("12  ");
					   out.writeBytes("00");
					   out.writeBytes(result.substring(2));
					   out.flush();
	     			}

				
			   }catch (EOFException e){
				 	inputExist = false;
			   
			   }catch (InterruptedIOException e){
				 	
			   }
	       }while(inputExist);
		   //System.out.println("msgid : " + msgid);
		   //return msgid.trim();
		   return msgid;
	   
	   }
	    
		public String fillSpace(String text, int size){
	    	int diff = size - text.length();
	    	if (diff > 0 ){
	    		for (int i = 0 ; i < diff ; i++)
	    			text += " ";
	    	}else{
			   StringBuffer sb = new StringBuffer(text);
			   sb.setLength(size);
			   text = sb.toString();
			}

			return text;
	    }
		
		
		public String en(String ko){
	        /**/
	        String new_str = null;
	        try{        
	            new_str = new String(ko.getBytes("KSC5601"), "8859_1");
	        } catch(UnsupportedEncodingException ex) {
	        	ex.printStackTrace(); 
	        }
	        return new_str;
	        /**/
	        //return ko;
		}
		
		public String ko(String en){
		        /**/
		        String new_str = null;
		        try{              
			        try{
			            new_str = new String(en.getBytes("8859_1"), "KSC5601");                
			        } catch(UnsupportedEncodingException ex) {
			        	ex.printStackTrace();
		        	}
		        }catch(NullPointerException e)
				{	//System.out.println("Null  " + en);
					new_str = en;
				}
		        return new_str;
		        /**/
		        //return en;
		}

}
