package com.zerofancy.theoremhelper.interceptor;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zerofancy.theoremhelper.UsAdmin;

/**
 * 自定义拦截器1
 */
public class AdmInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// System.out.println(">>>MyInterceptor1>>>>>>>在请求处理之前进行调用（Controller方法调用之前）");
		// System.out.println(request.getServletPath());
		// System.out.println(request.getRequestURI());

		Map<String, Integer> ris = new HashMap<String, Integer>();
		ris.put("/admin/index", 1);
		//管理首页权限值
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("user") != null) {
			for (String k : ris.keySet()) {
				if (request.getServletPath().equalsIgnoreCase(k))//.toLowerCase()实现不区分大小写
					if ((int) ris.get(k) > ((UsAdmin) session.getAttribute("user")).getLevel()) {
						PrintWriter printWriter = response.getWriter();
						printWriter.write("{code:1,message:\"You are not permitted to this page!\"}");
						return false;
					}
			}
			return true;
		} else {

			PrintWriter printWriter = response.getWriter();
			printWriter.write("{code:0,message:\"not login!\"}");
			response.sendRedirect("/admin/login");
			return false;
		}
		// return true;// 只有返回true才会继续向下执行，返回false取消当前请求
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		// System.out.println(">>>MyInterceptor1>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		// System.out.println(">>>MyInterceptor1>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet
		// 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
	}

}