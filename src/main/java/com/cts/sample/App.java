package com.cts.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import com.cts.sample.Database.DatabaseLayerCodeClass;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@PropertySource("file:${DB_CONNECT}")
public class App implements CommandLineRunner
{
	
	@Autowired
	DatabaseLayerCodeClass db;
	
    public static void main( String[] args )
    {
       
        
        SpringApplication.run(App.class, args);
        
        System.out.println( "Application Started!" );
    }

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		
		 System.out.println( "Please wait making DB conection before initialization...." );
		//db.testConnection();
		 System.out.println( "Connection Successfull" );
		 
		
	}
	
	
	

 
	
}
