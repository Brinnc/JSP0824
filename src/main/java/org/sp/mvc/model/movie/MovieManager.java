package org.sp.mvc.model.movie;

//아 클래스는 웹이건, 스윙이건 모든 플랫폼에서 재사용이 가능한 순수로직
//즉, MVC중 model영역의 클래스
public class MovieManager {
	public String getAdvice(String movie) {
		
		String msg=null;
		switch(movie) {
			case "슬램덩크":msg="★★★★★";break;
			case "오펜하이머":msg="★★★★";break;
			case "해리포터":msg="★★★★";break;
			case "스파이더맨":msg="★★★★★";break;
		
		}
		
		return msg;
	}
	
}
