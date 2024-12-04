package Utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail2.jakarta.DefaultAuthenticator;
import org.apache.commons.mail2.jakarta.ImageHtmlEmail;
import org.apache.commons.mail2.jakarta.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	public String reportName;

	public void onStart(ITestContext context) {

//		 // for dynamic report, Ex Test-Report-2024.09.29.10.34.25.html   
//		 SimpleDateFormat df = new SimpleDateFormat("yyy.MM.dd.HH.mm.ss");
//		 Date dt = new Date();
//		 df.format(dt);

		String timeStamp = new SimpleDateFormat("yyy.MM.dd.HH.mm.ss").format(new Date());
		reportName = "Test-Report-" + timeStamp + ".html";

		sparkReporter = new ExtentSparkReporter("./reports/" + reportName);
		sparkReporter.config().setDocumentTitle("Opencart Automation Report");
		sparkReporter.config().setReportName("Opencart Functional testing");
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "OpenCart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Enviroment", "QA");

		String os = context.getCurrentXmlTest().getParameter("OS");
		extent.setSystemInfo("Operating System", os);

		String browserName = context.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browserName);

		List<String> includeGroups = context.getCurrentXmlTest().getIncludedGroups();
		if (!includeGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includeGroups.toString());
		}

	}

	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS, result.getName() + "got passed !");

	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, result.getName() + "got failed !");
		test.log(Status.INFO, result.getThrowable().getMessage());
		try {
			String imagePath = new BaseClass().captureScreenshot(result.getName());
			test.addScreenCaptureFromPath(imagePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName() + "got skkiped !");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}

	public void onFinish(ITestContext context) {
		extent.flush();
		String pathOfExtentReport = System.getProperty("user.dir") + "/reports/" + reportName;
		File extentReport = new File(pathOfExtentReport);
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {

			e.printStackTrace();
		}

//		try {
//			URL url = new URL("file:///"+ System.getProperty("user.dir")+"/reports/"+reportName);
//			// Create the email message
//			ImageHtmlEmail email = new ImageHtmlEmail();
//			email.setDataSourceResolver(new DataSourceUrlResolver(url));
//			email.setHostName("smtp.googlemail.com");
//			email.setSmtpPort(456);
//			email.setAuthenticator(new DefaultAuthenticator("ashraful.Haq707@gmail.com", "password"));
//			email.setSSLOnConnect(true);
//			email.setFrom("ashraful.haq707@gamil.com");// Sender
//			email.setSubject("Test Result");
//			email.setMsg("Please find the attached report...");
//			email.addTo("ashraful.haq6188@gmail.com");//Receiver
//			email.attach(url, "extent report", "please check reoort...");
//			email.send();
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}

	}

}
