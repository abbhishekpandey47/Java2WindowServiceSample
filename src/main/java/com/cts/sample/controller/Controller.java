package com.cts.sample.controller;

import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.sample.RequestFormat.ReqFormat;
import com.cts.sample.service.ServiceClass;
import com.cts.sample.utils.ApplicationUtils;

import io.swagger.annotations.ApiParam;

@EnableScheduling
@RestController
@RequestMapping(value="/greet")
public class Controller {
	
	@Autowired
	ServiceClass srvc;

	@RequestMapping(value="/getGreetByP", method= RequestMethod.POST)
	public String getGreetingsByPost(@ApiParam @RequestParam String name)
	{	
		return "Hello " + name + "Welcome To MS Training";
		 
	}
	
	
	@RequestMapping(value="/getGreetByG", method= RequestMethod.GET)
	public String getGreetingsByGet()
	{
		
		return "Hello Guest Welcome To MS Training";
		 
	}
	
	
	@RequestMapping(value="/makeCsvReport" , method= RequestMethod.GET)
	//@Scheduled(fixedRate = 5000)
	public String getGreetings1()
	{
		try
		{
			System.out.println("Executing....");
			//srvc.makeCsvReport("TEST", "Service", "Test");
			
			System.out.println("Executing RUNEXE...");
			//srvc.runExe();
			System.out.println("Executed RUNEXE...");
		}
		catch(Exception e)		
		{
			System.out.println(e.getMessage());
		}
		
		
		return "HI Trainees!! Welcome";
		 
	}
	
	@RequestMapping(value="/getCityData" , method= RequestMethod.GET)
	public String getGreetings2()
	{
		
		
		JSONArray ja = srvc.getData();
		
		return ApplicationUtils.createJsonFormat(ja);
		 
	}
	
	@RequestMapping(value="/getSum" , method= RequestMethod.POST)
	public String getSum(@RequestBody ReqFormat req)
	{
		String message = "";
		
		if(req.getOper1() == 0)
		{
			message = "OPerand 1 cant be 0"; 
		}
		else if(req.getOper2() == 0)
		{
			message = "OPerand 2 cant be 0"; 
		}
		else
		{
			message =   srvc.getSum(req.getOper1(), req.getOper2());
		}
		
		return message;
		 
	}
}
