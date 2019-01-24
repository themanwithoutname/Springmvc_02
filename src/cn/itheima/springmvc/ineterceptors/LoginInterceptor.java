package cn.itheima.springmvc.ineterceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object obj) throws Exception {
		System.out.println("控制器方法执行前");
		String uri = req.getRequestURI();
		if(!uri.contains("login")) {
			String user = (String) req.getSession().getAttribute("user");
			if(null == user || "".equals(user)){
				resp.sendRedirect(req.getContextPath() + "/loginPage");
				return false;
			}
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		System.out.println("控制器方法执行后");
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("页面渲染后");
	}
	
}
