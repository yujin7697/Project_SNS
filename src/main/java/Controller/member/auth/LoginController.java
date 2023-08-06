package Controller.member.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Controller.SubController;
import Domain.Common.Dao.ConnectionPool;
import Domain.Common.Service.MemberService;
import Domain.Common.Service.MemberServiceImpl;

public class LoginController  implements SubController{

	private MemberService service= MemberServiceImpl.getInstance();
	public ConnectionPool connectionPool = new ConnectionPool();

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("LoginController execute");
		
		//GET 요청 처리
		if(req.getMethod().equals("GET"))
		{
			try {
				req.getRequestDispatcher("/WEB-INF/view/member/Auth/login.jsp").forward(req, resp);
				return ;
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ;
		}
		
		//POST 요청 처리
		//1 파라미터 추출
		String id = (String) req.getParameter("id");
		String pw = (String) req.getParameter("pw");
		System.out.println("LoginController paramtes : " + id + " " + pw);
		
		try {
			//2 입력값 검증
			if (id.isEmpty() || pw.isEmpty()) {
				System.out.println("[ERROR] Data Validation Check Error!");
				req.setAttribute("msg", "[ERROR] ID나 PW가 공백입니다.");
				req.getRequestDispatcher("/WEB-INF/view/member/Auth/login.jsp").forward(req, resp);
				return ;
			}
			//3 서비스 실행
			boolean isLogin=false;
			isLogin=service.login(req);
			System.out.println("isLogin 실행: " + isLogin );
		
		
			//4 View로 전달 
			if(isLogin)
			{
				// 세션에 사용자 아이디 저장
                HttpSession session = req.getSession();
                session.setAttribute("userId", id);
                
				//list.do 이동 - Redirect 
				resp.sendRedirect(req.getContextPath()+"/list.do");
			}
			else
			{
				//login.do 이동 - Forward
				req.getRequestDispatcher("/WEB-INF/view/member/Auth/login.jsp").forward(req, resp);
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}
	

}
