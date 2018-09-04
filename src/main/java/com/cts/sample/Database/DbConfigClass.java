package com.cts.sample.Database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DbConfigClass {
	
	@Autowired
	protected JdbcTemplate jdbcT;

	@Autowired
	protected NamedParameterJdbcTemplate namedJdbcTemplate;

}
