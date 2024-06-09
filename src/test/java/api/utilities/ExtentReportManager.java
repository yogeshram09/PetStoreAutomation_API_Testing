package api.utilities;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {

	private ExtentSparkReporter sparkReporter;
	private ExtentTest test;
	private ExtentReports extent;
	private String reportName;

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);

		String timeStamp = new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss").format(new Date());
		reportName = "Test-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\reports\\" + reportName);
		sparkReporter.config().setDocumentTitle("RestAssured Automation Project");
		sparkReporter.config().setReportName("Pet Store Users API");
		sparkReporter.config().setTheme(Theme.STANDARD);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

		extent.setSystemInfo("Application", "Pet Store Users API");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");

//		String os = context.getCurrentXmlTest().getParameter("os");
//		extent.setSystemInfo("Operating System", os);

//		String browser = context.getCurrentXmlTest().getParameter("browser");
//		extent.setSystemInfo("Browser", browser);
//
//		List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
//		if (!includedGroups.isEmpty()) {
//			extent.setSystemInfo("Groups", includedGroups.toString());
	}

	@Override
	public void onTestStart(ITestResult result) {

		ITestListener.super.onTestStart(result);

		test = extent.createTest(result.getTestClass().getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		ITestListener.super.onTestSuccess(result);

		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.createNode(result.getName());
		test.log(Status.PASS, result.getName().toUpperCase() + " got successfully executed..!!");
	}

	@Override
	public void onTestFailure(ITestResult result) {

		ITestListener.super.onTestFailure(result);

		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.FAIL, result.getName().toUpperCase() + " got failed..!!");
		test.log(Status.INFO, result.getThrowable().getMessage());

	}

	@Override
	public void onTestSkipped(ITestResult result) {

		ITestListener.super.onTestSkipped(result);

		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.SKIP, result.getName() + " got skipped..!!");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onFinish(ITestContext context) {

		ITestListener.super.onFinish(context);

		extent.flush();

		// complete all the test and report will be automatically open in browser

		String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + reportName;
		File extentReport = new File(pathOfExtentReport);

		try {

			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
