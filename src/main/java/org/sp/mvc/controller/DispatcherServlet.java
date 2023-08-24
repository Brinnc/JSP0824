package org.sp.mvc.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//웹 어플리케이션의 모든 요청을 이 클래스가 받을 예정
//만일 모든 요청마다 1대1 대응하는 서블릿을 매핑하게될 경우
//요청의 수가 늘어남에 따라 유지보수성이 떨어짐
public class DispatcherServlet extends HttpServlet{
	
	//모든 컨트롤러의 5대 업무 처리 프로세스
	//1) 요청을 받음 -DispatcherServlet
	//2) 요청을 분석함 (모든 요청을 하나의 전담 컨트롤러가 감당하기 때문) -DispatcherServlet
	//3) 하위 컨트롤러가 알맞은 로직 객체에 일을 시킴
	//4) view인 jsp로 가져갈 데이터가 있다면 결과를 request에 저장(포워딩이 요구됨) 
	//5) 결과 페이지로 전환, 보여주기
	
	FileReader reader;
	JSONParser jsonParser;
	JSONObject obj; //파싱한 결과 객체
	
	//서블릿이 톰캣에 의해 인스턴스화된 직후, 서블릿으로서 알아야할 정보를 init()을 통해 전달받을 수 있음(from tomcat)
	@Override
	public void init(ServletConfig config) throws ServletException {
		//스트림 생성
		URL url=this.getClass().getResource("/org/sp/mvc/controller/mapping.js"); //.은 오직 클래스 패키지에만
		try {
			reader=new FileReader(new File(url.toURI()));
			
			//파싱
			jsonParser=new JSONParser();
			//파싱 후 객체를 반환받음, 따라서 문자열이었던 json은 이 시점부터 객체가됨
			obj=(JSONObject)jsonParser.parse(reader); //해석(파싱)
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); //모든 하위 컨트롤러가 이 request 객체를 전달받으므로, 여기서 한번만 파라미터 처리를 함
		
		PrintWriter out=response.getWriter();
		out.print("요청을 받음");
		
		//2단계) 요청 분석하기
		String uri=request.getRequestURI();
		out.print(uri);
		
		//uri를 분석하여, 어떤 하위컨트롤러에게 요청을  전달할지를 결정
		//JSON을 파싱한 정보를 이용하여 하위 컨트롤러 메모리에 올리고 동작
		
		/*
		if(uri.equals("/blood.do")) { //혈액형을 원하면
			BloodController controller=new BloodController();
			controller.work(request, response); //하위컨트롤러 동작 시작
		}else if(uri.equals("/movie.do")) { //
			MovieController controller=new MovieController();
			controller.work(request, response); //하위컨트롤러 동작 시작
		}
		*/
		
		JSONObject json=(JSONObject)obj.get("controller");
		JSONObject viewJson=(JSONObject)obj.get("view");
		
		//하위 컨트롤러의 패키지+클래스 이름 반환
		String subName=(String)json.get(uri);
		
		out.print(uri+"요청을 처리할 "+subName);
		
		//매개변수로 전달한 클래스명을 이용하여, static영역으로 Load함
		try {
			Class subController=Class.forName(subName); //static 동적로드
			Controller controller=(Controller)subController.getConstructor().newInstance(); //인스턴스 생성
			controller.excute(request, response);
			
			
			String viewKey=controller.getViewKey();
			String viewPage=(String)viewJson.get(viewKey);
			//위의 msg는 여기서도 out.print()를 할 수는 있지만, 하는 순간부터 컨트롤러+뷰까지 담당하게됨
			RequestDispatcher dis=request.getRequestDispatcher(viewPage);
			//5단계) 결과 보여주기
			dis.forward(request, response);
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	//서블릿이 소멸될 때 호출되는 생명주기 메서드(:주로 닫을 자원이 있을 때 유용)
	@Override
	public void destroy() {
		
		if(reader!=null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
