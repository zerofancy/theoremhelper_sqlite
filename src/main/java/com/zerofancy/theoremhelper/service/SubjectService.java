package com.zerofancy.theoremhelper.service;

import java.util.List;
import java.util.Map;

public interface SubjectService {
	List<Map<String, Object>> getAll();

	boolean Edit(Long id,String title);
	
	boolean Del(Long id);
	
	boolean Add(String subject);
	
	String getById(Long id);
}
