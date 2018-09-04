package com.cts.sample.Database;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseLayerCodeClass extends DbConfigClass{
	
	
	public SqlRowSet getdata()
	{
		
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("city", "");
		params.addValue("district", "");
		
		String sql = "select * from dummycity where cityname = :city and district = :district";
		
		SqlRowSet srs = namedJdbcTemplate.queryForRowSet(sql, params);
		
		
		return srs;
		
	}
	
	public void testConnection()
	{
		jdbcT.execute("select * from dual");
	}
	
	

}
