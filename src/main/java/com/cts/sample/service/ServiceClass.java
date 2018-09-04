package com.cts.sample.service;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.cts.sample.Database.DatabaseLayerCodeClass;




@Service
public class ServiceClass {
	
	
	private long startTime;
	private long endTime;
	private static String SourceInfoReportSharedFolderPath = "C:/icgs/fim_ms/sourceReport/"; //"/home/pandab8/reports/"; //"C:/icgs/fim_ms/sourcereport/";   //"/home/pandab8/reports/"; ; /**"/home/pandab8/reports/"; //null; //; **/
	private static String MAIL_HOST = null;
	private static String FROM_ADDRESS = null;
	
	@Autowired
	DatabaseLayerCodeClass db;
	

	
	public String getSum(int o1, int o2)
	{
		return  String.valueOf(o1+o2); 
	}
	
	public JSONArray getData()
	{
		
		JSONObject result = null;
		JSONArray ja = new JSONArray();
		
		SqlRowSet srs = db.getdata();

		if(srs != null )
		{
			while(srs.next())
			{
				result = new JSONObject();
				
				result.append("name", srs.getString("name"));
				result.append("code", srs.getString("countrycode"));
				result.append("district", srs.getString("district"));
				
				ja.put(result);
			}
		}
		
		return ja;
	}
	
	
	/**
	 * Check File exists or not for a given City
	 * 
	 * @param req
	 * @param city
	 * @return
	 * @throws IOException
	 */
	public String checkFileExistance(String jur, String wc) throws IOException 
	{
		

		final String fileNamePrefix = jur.toUpperCase().trim() + "_" + wc.toUpperCase() + "_";
		
		String isFileFound = "";

		try 
		{
			

			File root = new File(SourceInfoReportSharedFolderPath);

			//logger.info("Root >> " + root);

			if (root.exists() && root.isDirectory()) {
				//logger.info("Directory Path >>" + root.getAbsolutePath());

				FilenameFilter fileFilter = new FilenameFilter() {
					@Override
					public boolean accept(File directory, String fileName) {
						return fileName.startsWith(fileNamePrefix);
					}
				};

				File[] files = root.listFiles(fileFilter);

				if (files.length > 0) {
					//logger.info("File Existence Path >> " + files[0].getAbsolutePath());

					isFileFound = files[0].getAbsolutePath();

					//logger.info("isFileFound? >> " + isFileFound);
				} else {
					isFileFound = "File Creation is in Progress...";

					//logger.info("isFileFound? >> " + isFileFound);

					/**
					 * Kickoff CSV Generator indirectly Method and send mail to
					 * User.
					 */
					/**TODO**/
				}
			}
		} catch (Exception e) {
			//logger.stackTraceToString(e);
			//logger.info(e.getMessage());
		}

		//logger.endMethod("checkFileExistance");

		return isFileFound;
	}
	
	
	/**
	 * 
	 * @param jur
	 * @param wc
	 * @throws Exception
	 */
	public void deleteFile(String jur, String wc) throws Exception 
	{
		final String fileNamePrefix = jur.toUpperCase().trim() + "_" + wc.toUpperCase() + "_";

		if (SourceInfoReportSharedFolderPath == null) /** GET the Source Report Path Folder if null **/
		{
			/*logger.info("SourceInfoReportSharedFolderPath Before Fetch >> " + SourceInfoReportSharedFolderPath);
				SourceInfoReportSharedFolderPath = dao.getValue("sourceInfoReportPath");
			logger.info("SourceInfoReportSharedFolderPath After Fetch  >> " + SourceInfoReportSharedFolderPath);*/
		}

		File root = new File(SourceInfoReportSharedFolderPath);

		//logger.info("Root >> " + root);

		if (root.exists() && root.isDirectory()) {
			//logger.info("Directory Path >> " + root.getAbsolutePath());

			FilenameFilter fileFilter = new FilenameFilter() {

				@Override
				public boolean accept(File directory, String fileName) {
					return fileName.startsWith(fileNamePrefix.toUpperCase().trim());
				}
			};

			File[] files = root.listFiles(fileFilter);

			if (files.length > 0) {
				for (File file : files) {
					boolean deleted = file.delete();
					//if (deleted)
						//logger.info("Sourcing Info File Deleted for Region >> " + jur + " : " + wc);
				}

				/**
				 * boolean result = Files.deleteIfExists(file.toPath());
				 * surround it in try catch block logger.info("isFileFound >>" +
				 * isFileFound);
				 */
			}
		}
	}

	
	public void runExe()
	{
		try
		{
			File file = null; 
			
			DesktopApi dkp = new DesktopApi();
			
			for(int i=0; i<6 ; i++)
	        {
	        	file = new File("D:\\icgs\\ngicgs\\"+i+".txt");
		        
	        	if(file.exists()) 
		        	dkp.open(file);
	        }
			
		
			
			 file = new File("C:\\icgs\\fim_ms\\sourceReport\\TEST.csv"); 
			
			 dkp.open(file);
	        
	        //let's try to open PDF file
	        
			
			//Runtime.getRuntime().exec("C:\\Windows\\notepad.exe", null, new File("C:\\Windows\\"));
			
			//Runtime run = Runtime.getRuntime();
			
			//Process p = run.exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
			
			
			
			//System.out.println(p.getErrorStream().toString());
			
			//Runtime.getRuntime().exec();
			
			
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Generate CSV File
	 * 
	 * @param list
	 * @param sendPriority
	 * @param req
	 * @throws IOException
	 */
	public void makeCsvReport(String userId, String jur, String wc) throws IOException 
	{
		//logger.startMethod("makeCsvReport");

		//EmailNotifier emailNotifier = null;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy_H-m-s-z");
		try 
		{
			Date dt = new Date();

			//logger.info(dateFormat.format(dt.getTime()));

			/*if (SourceInfoReportSharedFolderPath == null) *//**GET the Shared Path Folder if null**//*
			{
				logger.info("SourceInfoReportSharedFolderPath Before >> " + SourceInfoReportSharedFolderPath);
					SourceInfoReportSharedFolderPath = dao.getValue("sourceInfoReportPath");
				logger.info("SourceInfoReportSharedFolderPath After Fetch >> " + SourceInfoReportSharedFolderPath);
			}*/
								
			/**   1:  2:   3:   4:  5:   6:    7: 8: 9 : 10**/
			/**AJUR:AWC:LOCA:ZJUR:ZWC:LOCZ:CABLE:LP:HP : true/False**/
			
			String fileName = SourceInfoReportSharedFolderPath  + userId + jur + wc  + dateFormat.format(dt.getTime()) + ".csv";

			//logger.info("CSV File Generated @ path >> " + fileName);

			BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName), StandardCharsets.UTF_8, StandardOpenOption.CREATE);

			CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("TEST"));			

			
			
			
								
				csvPrinter.printRecord("a");
				csvPrinter.printRecord("b");		
			
			
			Date dt1 = new Date();
			
			endTime = dt1.getTime();
			
			long diffMs = endTime - startTime;
			long diffSec = diffMs / 1000;
			long min = diffSec / 60;
			long sec = diffSec % 60;
			//logger.info("Time Taken : " + min + " minutes and " + sec + " seconds.");
			
			csvPrinter.printRecord("", "", "", "", "", "", "");
			csvPrinter.printRecord("<<Time", "taken", min, "minutes", "and", sec, "seconds>>");
			csvPrinter.printRecord("", "", "End", "of", "report", "", "");
			
			csvPrinter.close();

			
		} catch (Exception ex) {
			//logger.stackTraceToString(ex);
		} finally {
			//emailNotifier = null;
		}

		//logger.endMethod("makeCsvReport");
	}
	

	
	
	
}
