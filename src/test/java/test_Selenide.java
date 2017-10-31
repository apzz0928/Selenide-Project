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


public class test_Selenide {
	private static String baseUrl, hubUrl;
	private static String TestBrowser;
	
	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "http://www.d.p.herowarz.com/index.main?c=n";
		hubUrl = "http://10.10.105.228:4444/wd/hub";
  		
		
  		String urlToRemoteWD = hubUrl;
  		DesiredCapabilities cap;
  		ScreenShooter.captureSuccessfulTests = false;
  		if(browser.equals("chrome")){
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
  			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); //ie ĳ�� ������ ���� �κ�
	        RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD),cap);
	        WebDriverRunner.setWebDriver(driver);
	  		driver.manage().window().setSize(new Dimension(1600, 1400));
  		} 
    }
	private static void js(String javaScriptSource) {
	    executeJavaScript(javaScriptSource);
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
	//@Test(priority = 0)
	public void alertSample() {
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
        $(".btn-logout").click();
    }
	//@Test(priority = 1)
	public void ajaxSample() {
        open(baseUrl);
        refresh();
        js("");
        $(".uid_login_login").waitUntil(appear, 5000);
        $(".uid_cookie_checkbox").click();
        $(".uid_test_btn_ok").click();
        $(".uid_login_id").setValue("webqa01");
        $(".uid_login_password").setValue("qordlf12");
        $(".uid_login_login").click();
        $(By.linkText("Ŀ�´�Ƽ")).click();
        $("#txtBoardSearchKeyword").setValue("1234");
        $("#btnBoardSearchSearch").click();
        $("#txtBoardSearchKeyword").shouldHave(value("1234"));
        System.out.println(TestBrowser + "ajaxSample --- end ---");
    }
	//@Test(priority = 2)
	public void getPageUrlTitleSource() {
        open(baseUrl);
        System.out.println(url());
        System.out.println(title());
        //System.out.println(source());
        System.out.println("getPageUrlTitleSource --- end ---");
    }
	//@Test(priority = 3)
	public void promptTest() throws InterruptedException {
        open("file:///C:/Users/Administrator/Desktop/Untitled-1.html");
        $(".alert").click();
        confirm("���â�Դϴ�.");
        //prompt("���� �Է����ּ���", "1234"); // 4.7���� prompt(); �޼ҵ� �߰���
        Thread.sleep(5000);
    }
	//@Test(priority = 99)
	public void jqueryTest() throws InterruptedException {
        open("http://naver.com");
        String sel = "sel = document.createElement('SCRIPT');";
        String sel2 = "sel.setAttribute('src', 'https://code.jquery.com/jquery-1.12.4.js');";
        String sel3 = "document.querySelector('body').appendChild(sel);";
        //js("var sel = document.createElement('SCRIPT');");
        js(sel);
        Thread.sleep(2000);
        js(sel2);
        Thread.sleep(2000);
        js(sel3);
        Thread.sleep(2000);
        js("alert($('.ico_search_submit'));");
        Thread.sleep(10000);
    }
	@Test(priority = 99)
	public void Chrome() throws InterruptedException {
        open("http://www.d.p.herowarz.com");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}
