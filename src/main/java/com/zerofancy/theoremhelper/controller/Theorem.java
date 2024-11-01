package com.zerofancy.theoremhelper.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zerofancy.theoremhelper.service.SubjectService;
import com.zerofancy.theoremhelper.service.TheoremService;

@Controller
@RequestMapping("/")
public class Theorem {
	@Autowired
	private TheoremService theoremService;
	@Autowired
	private SubjectService subjectService;

	@RequestMapping("/")
	String IndexPage(HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		model.addAttribute("subject", subjectService.getAll());
		return "search";
	}
	
	@RequestMapping("/searchHandle")
	@ResponseBody
	List<Map<String, Object>> searchHandle(@RequestParam("sub") Long sub,@RequestParam("kwd") String keyWord,@RequestParam("limit") Integer limit,@RequestParam("page") Integer page) {
		return theoremService.doSearch(keyWord, sub, limit, page);
	}
	
	@RequestMapping("/searchNumHandle")
	@ResponseBody
	Long searchNumHandle(@RequestParam("sub") Long sub,@RequestParam("kwd") String keyWord) {
		return theoremService.doSearchAndGetNum(keyWord, sub);
	}
}
