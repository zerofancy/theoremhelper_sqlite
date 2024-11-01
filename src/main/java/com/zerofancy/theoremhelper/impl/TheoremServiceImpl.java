package com.zerofancy.theoremhelper.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.zerofancy.theoremhelper.service.TheoremService;

@Service
public class TheoremServiceImpl implements TheoremService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean Del(Long id) {
		// 
		return jdbcTemplate.update("delete from theorem where id=?", id) > 0;
	}

	@Override
	public boolean Edit(Long id, String abs, String content, Long sub) {
		// 
		return jdbcTemplate.update("update theorem set th_abs=?,th_content=?,th_sub=? where id=?", abs, content,sub, id) > 0;
	}

	/**
	 * @param limit 每页记录条数
	 * @param page  当前页码，从1开始
	 */
	@Override
	public List<Map<String, Object>> doSearch(String keyWord, Long sub, Integer limit, Integer page) {
		// 
		return jdbcTemplate.queryForList("select * from theorem where th_abs like ? and th_sub=? limit ?,?",
				"%" + keyWord + "%", sub, (page - 1)*limit, limit);
	}

	@Override
	public Map<String, Object> getById(Long id) {
		// 
		return jdbcTemplate.queryForMap("select * from theorem where id=?",id);
	}

	@Override
	public boolean Add(String abs, String content, Long sub) {
		// 
		return jdbcTemplate.update("insert into theorem(th_abs,th_content,th_sub) values(?,?,?)", abs, content,sub) > 0;
	}

	@Override
	public List<Map<String, Object>> doSearchInAllSub(String keyWord, Integer limit, Integer page) {
		// 
		return jdbcTemplate.queryForList("select * from theorem where th_abs like ? limit ?,?",
				"%" + keyWord + "%", (page - 1)*limit, limit);
	}

	@Override
	public Long doSearchInAllSubAndGetNum(String keyWord) {
		// 
		return jdbcTemplate.queryForObject("select COUNT(*) from theorem where th_abs like ?",new Object[]{"%" + keyWord + "%"},Long.class);
	}

	@Override
	public Long doSearchAndGetNum(String keyWord, Long sub) {
		// 
		return jdbcTemplate.queryForObject("select COUNT(*) from theorem where th_abs like ? and th_sub=?",new Object[]{"%" + keyWord + "%",sub},Long.class);
	}

}
