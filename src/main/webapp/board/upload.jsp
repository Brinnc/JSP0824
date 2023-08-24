<%@page import="java.io.IOException"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//클라이언트가 전송한 텍스트데이터 + 바이너리 파일을 전송받아 정보를 출력
	//원하는 위치에 저장 (업로드 컴포넌트가 처리)
	
	//클라이언트의 데이터 전송이 텍스트 데이터뿐만 아니라, 바이너리 형식의 데이터도 포함되어 있을 경우
	//request.getParameter()메서드로는 텍스트만 받을 수 있고, 바이너리 형식의 데이터는 
	//별도의 스트림 처리가 필요함. 하지만 개발의 효율성이 떨어지므로, 이미 잘 작성된 외부 컴포넌트를 이용
	
	//어플리케이션에 자체 정보를 개발자가 직접 얻지말고, 프로그래밍적으로 얻을 수 있는 방법을 이용
	//jsp의 내장객체 9개 중, application 내장객체: 웹어플리케이션과 관련된 컨텍스트
	
	//현재의 jsp 즉 서블릿이 실행중인 웹어플리케이션의 루트의 하위에 있는 data디렉토리의 하드디스크 상 풀 경로 얻기
	//현재 개발 중인 이클립스가 아니라, 고양이 서버에 배포된 어플리케이션의 위치를 기준느오 함
	String path=application.getRealPath("/data"); //서버 컴퓨터의 저장 위치
	out.print(path);
	int maxSize=1024*1024*1; //제한할 파일의 용량 : 1MB
	
	//생성자 호출만해도, 지정한 경로에 파일이 자동으로 업로드
	//생성자 메서드에는 IOException이 throws되어 있으므로, 
	//현재 스크립틀릿에서 그 예외를 처리하도록 함
	
	try{
		MultipartRequest multi=new MultipartRequest(request, path, maxSize, "utf-8");
	}catch(IOException e){
		out.print("용량은 1M를 넘어설 수 없음");
	}
	
%>