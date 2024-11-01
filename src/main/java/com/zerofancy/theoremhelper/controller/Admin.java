package com.zerofancy.theoremhelper.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zerofancy.theoremhelper.UsAdmin;
import com.zerofancy.theoremhelper.UsData;
import com.zerofancy.theoremhelper.service.AdminService;
import com.zerofancy.theoremhelper.service.SubjectService;
import com.zerofancy.theoremhelper.service.TheoremService;

@Controller
@RequestMapping(path = "/admin")

public class Admin {
	@Autowired
	private AdminService adminSerivce;
	@Autowired
	private TheoremService theoremService;
	@Autowired
	private SubjectService subjectService;

	/**
	 * 登录页面
	 */
	@RequestMapping("/login")
	public String loginPage() {
		return "adm_login";
	}

	/**
	 * 登录提交信息的处理
	 * 
	 * @param usr 用户名
	 * @param pwd 密码
	 * @param rnd 随机字符串
	 */
	@RequestMapping("/login/handle")
	@ResponseBody
	UsAdmin loginHandle(HttpServletRequest request, HttpServletResponse response,
						@RequestParam(name = "usr", required = false) String usr,
						@RequestParam(name = "pwd", required = false) String pwd,
						@RequestParam(name = "rnd", required = false) String rnd) {
		HttpSession session = request.getSession();
		UsAdmin tmpUs = adminSerivce.admLogin(usr, rnd, pwd);
		if (tmpUs == null) {
			return new UsAdmin();
		} else {
			session.setAttribute("user", tmpUs);
			return tmpUs;
		}
	}

//	@RequestMapping("/index")
//	String indexPage() {
//		return "frame";
//	}
	
	@RequestMapping("/index")
	String indexPage() {
		return "adm_index";
	}
	@RequestMapping("/logout")
	void logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.setAttribute("user", null);
		try {
			response.sendRedirect("/admin/login");
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}
	}

	@RequestMapping("/manage")
	String manage(Model model, @RequestParam(name = "page", defaultValue = "1") Integer page) {
		List<Map<String, Object>> tmpDatas = theoremService.doSearchInAllSub("", 20, page);
		List<UsData> disDatas = new LinkedList<UsData>();
		for (Map<String, Object> i : tmpDatas) {
			UsData tmpData = new UsData();
			if(i.get("id")==null) {//不知道为什么SQLite最后一页会一直执行到null
				break;				//好吧，是数据库id自动递增没设置好
			}
			tmpData.setId(Long.parseLong(i.get("id").toString()));
			tmpData.setAbs((String) i.get("th_abs"));
			tmpData.setContent((String) i.get("th_content"));
			tmpData.setSub(Long.parseLong(i.get("th_sub").toString()));
			tmpData.setSub_str(subjectService.getById(Long.parseLong(i.get("th_sub").toString())));
			disDatas.add(tmpData);
		}
		model.addAttribute("content", disDatas);
		model.addAttribute("pn", page);
		model.addAttribute("pgs",theoremService.doSearchInAllSubAndGetNum("")/20+(theoremService.doSearchInAllSubAndGetNum("")%20==0?0:1));
		return "adm_manage";
	}

	@RequestMapping("/edit")
	String edit(Model model, @RequestParam("id") Long id,@RequestParam(name = "page", defaultValue = "1") Integer page) {
		model.addAttribute("id", id);
		model.addAttribute("data", theoremService.getById(id));
		model.addAttribute("subject", subjectService.getAll());
		model.addAttribute("page", page);
		return "adm_edit";
	}

	@RequestMapping("/edit/Handle")
	String editHandle(Model model,@RequestParam("id") Long id,@RequestParam("abs") String abs,@RequestParam("content") String content,@RequestParam("sub") Long sub,@RequestParam(name = "page", defaultValue = "1") Integer page) {
		theoremService.Edit(id, abs, content, sub);
		model.addAttribute("edited", 1);
		return edit(model,id,page);
	}
	
	@RequestMapping("/del")
	@ResponseBody
	Boolean delHandle(@RequestParam("id") Long id) {
		return theoremService.Del(id);
	}
	@RequestMapping("/add")
	String add(Model model) {
		model.addAttribute("subject", subjectService.getAll());
		return "adm_add";
	}
	@RequestMapping("/add/Handle")
	String addHandle(@RequestParam("abs") String abs,@RequestParam("content") String content,@RequestParam("sub") Long sub) {
		if(theoremService.Add(abs, content, sub)) {
			return "adm_add_success";
		}
		return "adm_add_fail";
	}
	@RequestMapping("/subject")
	String subjectManage(Model model) {
		model.addAttribute("subject", subjectService.getAll());
		return "adm_subject";
	}
	@RequestMapping("/subject/edit")
	@ResponseBody
	boolean editSubject(Model model,@RequestParam("id") Long id,@RequestParam("subject") String subject) {
		return subjectService.Edit(id, subject);
	}
	@RequestMapping("/subject/add")
	void addSubject(HttpServletRequest request, HttpServletResponse response,@RequestParam("subject") String subject) {
		subjectService.Add(subject);
		try {
			response.sendRedirect("/admin/subject");
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}
	}
	@RequestMapping("/subject/del")
	@ResponseBody
	Boolean delSubject(@RequestParam("id") Long id) {
		return subjectService.Del(id);
	}
	
	/**
	 * 用户管理页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/user")
	String userManagePage(Model model) {
		List<Map<String,Object>> tmpUsers= adminSerivce.getAllUsers();
		List<UsAdmin> disUsers=new LinkedList<>();
		for(Map<String,Object> i:tmpUsers) {
			UsAdmin tmpUser=new UsAdmin();
			tmpUser.setId((int)i.get("id"));
			tmpUser.setName((String) i.get("ad_name"));
			disUsers.add(tmpUser);
		}
		model.addAttribute("users", disUsers);
		return "adm_user";
	}
	
	@RequestMapping("/adduser")
	@ResponseBody
	Map<String,Integer> userAdd(@RequestParam("name") String name,@RequestParam("pwd") String pwd) {
		Map<String,Integer> tmpReturn=new HashMap<String,Integer>();
		tmpReturn.put("status", adminSerivce.addUser(name, pwd)?1:0);
		return tmpReturn;
	}
	@RequestMapping("/setPassword")
	@ResponseBody
	boolean rndPassword(@RequestParam("id") Long id,@RequestParam("pwd") String pwd) {
		return adminSerivce.setPwd(id, pwd);
	}
	@RequestMapping("/deluser")
	@ResponseBody
	boolean userDel(@RequestParam("id") Long id) {
		return adminSerivce.delUser(id);
	}
}
