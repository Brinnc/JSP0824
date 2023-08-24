package org.sp.mvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//이 객체의 선언 이유?
//1) 모든 컨트롤러들을 같은 자료형으로 묶음
//2) 모든 컨트롤러들이 반드시 정의할 메서드에 대한 기준을 세움
public interface Controller {
	
	public void excute (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	public String getViewKey();
}
