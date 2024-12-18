package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

	public static WebDriver driver;
	public Logger logger;
	public Properties prop;

	// setup()
	@BeforeClass(groups = { "Master", "Regression", "Ddt", "Sanity" })
	@Parameters({ "OS", "browser" })
	public void setup(String od, String br) throws IOException, URISyntaxException {

		// loading config properties file
		FileReader file = new FileReader("./src/test/resources/config.properties");
		prop = new Properties();
		prop.load(file);
		logger = LogManager.getLogger(this.getClass());
		
        // Select execution environment
		if (prop.getProperty("Execution_env").equalsIgnoreCase("remote")) {

			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			// OS
			if (od.equalsIgnoreCase("Windows")) {
				capabilities.setPlatform(Platform.WIN10);
			} else if (od.equalsIgnoreCase("Mac")) {
				capabilities.setPlatform(Platform.MAC);
			} else if (od.equalsIgnoreCase("Linux")) {
				capabilities.setPlatform(Platform.LINUX);
			} else {
				System.out.println("No matching OS");
			}
			
			// browser
			switch (br.toLowerCase()) {
			case "chrome":
				capabilities.setBrowserName("chrome");
				break;
			case "edge":
				capabilities.setBrowserName("MicrosoftEdge");
				break;
			case "firefox":
				capabilities.setBrowserName("firefox");
				break;
			default:
				System.out.println("Invalid browser name..!");
				return;
			}

			driver = new RemoteWebDriver(new URI("http://localhost:4444/wd/hub").toURL(), capabilities);

		}
		if (prop.getProperty("Execution_env").equalsIgnoreCase("local")) {

			switch (br.toLowerCase()) {
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "firfox":
				driver = new FirefoxDriver();
				break;
			case "edge":
				driver = new EdgeDriver();
				break;
			default:
				System.out.println("This browser is not supported");
				return;
			}
		}
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		logger.info("*** Launching application ***");
		driver.get(prop.getProperty("appURL"));

	}

	// tearDown()
	@AfterClass(groups = { "Master", "Regression", "Ddt", "Sanity" })
	public void tearDown() {

		driver.quit();
	}

	// create random Alphabetic
	public String randomString() {
		return RandomStringUtils.randomAlphabetic(5);

	}

	// create random telephone number
	public String randomNumber() {
		return RandomStringUtils.randomNumeric(10);
	}

	// create random email
	public String randomAlphaNumeric() {
		String str = RandomStringUtils.randomAlphabetic(3);
		String num = RandomStringUtils.randomNumeric(3);

		return (str + "_" + num);
	}

	public String captureScreenshot(String name) {

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		// TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String targetFilepath = System.getProperty("user.dir") + "/screenshots/" + name + "_" + timeStamp + ".png";
		File targetFile = new File(targetFilepath);
		src.renameTo(targetFile);

		return targetFilepath;
	}

}
