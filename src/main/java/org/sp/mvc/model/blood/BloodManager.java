package org.sp.mvc.model.blood;

public class BloodManager {
	
	public String getAdvice(String blood) {
		String msg="";
		
		switch(blood){
		case "A": msg="세심하고 꼼꼼함";break;
		case "B": msg="주관이 뚜렷함";break;
		case "O": msg="마당발";break;
		case "AB": msg="다양함";break;
		}
		
		return msg; 
		
	}
	
}
