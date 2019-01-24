package cn.itheima.springmvc.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class CustomExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse resp, Object obj,
			Exception e) {
		// obj 指异常发生的地方，  包名+类名+方法名（形参） 拼接的字符串
		ModelAndView mav = new ModelAndView();
		if(e instanceof MessageException) {
			// 预知异常
			MessageException me = (MessageException) e;
			mav.addObject("error", me.getMsg());
		}else {
			mav.addObject("error", "未知异常");			
		}
		
		mav.setViewName("error");
		
		return mav;
	}

}
