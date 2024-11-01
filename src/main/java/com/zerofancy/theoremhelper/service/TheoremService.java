package com.zerofancy.theoremhelper.service;

import java.util.List;
import java.util.Map;

public interface TheoremService {
	boolean Add(String abs,String content,Long sub);
	
	boolean Del(Long id);
	
	boolean Edit(Long id,String abs,String content,Long sub);
	
	List<Map<String, Object>> doSearch(String keyWord,Long sub,Integer limit,Integer page);
	
	Long doSearchAndGetNum(String keyWord,Long sub);
	
	List<Map<String, Object>> doSearchInAllSub(String keyWord,Integer limit,Integer page);
	
	Long doSearchInAllSubAndGetNum(String keyWord);
	
	Map<String,Object> getById(Long id);
}
