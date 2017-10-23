import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.WebDriverRunner.*;

import com.codeborne.selenide.testng.ScreenShooter;


public class test_herowarz {
	private static String baseUrl, nodeUrl;
	private static String TestBrowser;
	
	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "http://www.d.p.herowarz.com/index.main?c=n";
		nodeUrl = "http://10.10.105.228:4444/wd/hub";
  		
		
  		String urlToRemoteWD = nodeUrl;
  		DesiredCapabilities cap;
  		ScreenShooter.captureSuccessfulTests = false;
  		if(browser.equalsIgnoreCase("chrome")){
  			TestBrowser = "chrome";
  			cap = DesiredCapabilities.chrome();
	        RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD),cap);
	        WebDriverRunner.setWebDriver(driver);
	  		driver.manage().window().setSize(new Dimension(1600, 1400));
  		} else if(browser.equals("firefox")) {
  			TestBrowser = "firefox";
  			cap = DesiredCapabilities.firefox();
	        RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD),cap);
	        WebDriverRunner.setWebDriver(driver);
	  		driver.manage().window().setSize(new Dimension(1600, 1400));
  		} else if(browser.equals("internetExplorer")) {
  			TestBrowser = "internetExplorer";
  			cap = DesiredCapabilities.internetExplorer();
  			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true); //IE ������ ���� ���� ���� ���� �ӽ� ����
  			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); //ie text �Է� �ӵ� ����� ���� �κ�
  			cap.setCapability("requireWindowFocus", true); //ie text �Է� �ӵ� ����� ���� �κ�
	        RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD),cap);
	        WebDriverRunner.setWebDriver(driver);
	  		driver.manage().window().setSize(new Dimension(1600, 1400));
  		} 
    }
	private static void js(String javaScriptSource) {
	    executeJavaScript(javaScriptSource);
	}
	public void sw(String name) {
		switchTo().window(name);
	} 
  	public static void windowTitle(String windowTitle) { 
  		WebDriverRunner.getWebDriver();
  		Set<String> windows = getWebDriver().getWindowHandles();
  		for (String window : windows) { 
  			switchTo().window(window); 
  			if (getWebDriver().getTitle().contains(windowTitle)) { 
  				return; 
			} 
		} 
	}
	@Test(priority = 0)
	public void login() {
        open(baseUrl);
        js("");
        $(".uid_login_login").waitUntil(appear, 5000);
        $(".uid_cookie_checkbox").click();
        $(".uid_test_btn_ok").click();
        $(".uid_login_login").click();
        dismiss("���̵� �Է����ּ���");
        $(".uid_login_id").setValue("����1232");
        $(".uid_login_login").click();
        dismiss("��й�ȣ�� �Է����ּ���.");
        $(".uid_login_password").setValue("qordlf13");
        $(".uid_login_login").click();
        confirm("���̵� �Ǵ� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
        $(".uid_login_password").setValue("qordlf12");
        $(".uid_login_login").click();
    }
	@Test(priority = 1)
	public void recentNotice() {
        $(".recent").hover();
        $(By.linkText("���ҽ�")).click();
        $(".uid_gameserver_btn").waitUntil(appear, 5000);
        $(".cell-subject > a", 0).click();
        $(".uid_gameserver_btn").waitUntil(appear, 5000);
        $(".btn-white").click();
        $(".uid_gameserver_btn").waitUntil(appear, 5000);
    }
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}