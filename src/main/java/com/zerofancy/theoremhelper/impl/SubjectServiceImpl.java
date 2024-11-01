package com.zerofancy.theoremhelper.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.zerofancy.theoremhelper.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> getAll() {
		// 
		return jdbcTemplate.queryForList("select * from th_sub");
	}

	@Override
	public boolean Edit(Long id, String title) {
		// 
		return jdbcTemplate.update("update th_sub set sub_title=? where id=?", title, id) > 0;
	}

	@Override
	public boolean Del(Long id) {
		// 
		jdbcTemplate.update("delete from theorem where th_sub=?", id);
		return jdbcTemplate.update("delete from th_sub where id=?", id) > 0;
	}

	@Override
	public boolean Add(String subject) {
		// 
		return jdbcTemplate.update("insert into th_sub(sub_title) values(?)", subject) > 0;
	}

	@Override
	public String getById(Long id) {
		// 
		return jdbcTemplate.queryForObject("select sub_title from th_sub where id=?", new Object[]{id},String.class);
	}

}
