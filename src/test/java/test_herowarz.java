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
		baseUrl = "http://www.d.p.herowarz.com";
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
  			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true); //IE 실행을 위한 보안 관련 설정 임시 변경
  			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); //ie text 입력 속도 향상을 위한 부분
  			cap.setCapability("requireWindowFocus", true); //ie text 입력 속도 향상을 위한 부분
  			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); //ie 캐시 삭제를 위한 부분
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
	@Test(priority = 0)
	public void login() {
        open(baseUrl);
        js("");
        $(By.linkText("액션중독! - 최강의 군단")).click();
        $(".uid_login_login").waitUntil(appear, 5000);
        $(".uid_cookie_checkbox").click();
        $(".uid_test_btn_ok").click();
        $(".uid_login_login").click();
        dismiss("아이디를 입력해주세요");
        $(".uid_login_id").setValue("영권1232");
        $(".uid_login_login").click();
        dismiss("비밀번호를 입력해주세요.");
        $(".uid_login_password").setValue("qordlf13");
        $(".uid_login_login").click();
        confirm("아이디 또는 비밀번호가 일치하지 않습니다.");
        $(".uid_login_password").setValue("qordlf12");
        $(".uid_login_login").click();
    }
	//@Test(priority = 1)
	public void recent() {
        $(".recent").hover();
        $(By.linkText("새소식")).click();
        $(".subject", 0).click();
        $(".btn-white").click();
        System.out.println(TestBrowser + "Recent Notice Pass");
        $(".l-sub-update", 0).click();
        $(".view-h-txt", 0).click();
        $(".btn-white").click();
        System.out.println(TestBrowser + "Recent Update Pass");
        $(".l-sub-secret", 0).click();
        $(".thumb", 1).click();
        $(".btn-white").click();
        System.out.println(TestBrowser + "Recent Secret Pass");
        $(".l-sub-pink", 0).click();
        $(".btn-white").click();
        $(".thumb", 0).click();
        $(".btn-white").click();
        System.out.println(TestBrowser + "Recent Pink Pass");
        $(".l-sub-cospre", 0).click();
        $(".thumb", 0).click();
        $(".btn-white").click();
        System.out.println(TestBrowser + "Recent cospre Pass");
        $(".l-sub-tv", 0).click();
        $(".subject", 0).click();
        $(".btn-white").click();
        System.out.println(TestBrowser + "Recent Tv Pass");
    }
	//@Test(priority = 2)
	public void guide() {
        open(baseUrl + "/about/herowarz.hero");
        System.out.println(TestBrowser + "Guide about Pass");
        $(".character > a", 0).click();
        $(".item-rpg > a", 0).click();
        $(".btn-highlight").click();
        $(".btn-close").click();
        System.out.println(TestBrowser + "Guide character highlight Pass");
        $(By.linkText("스킬")).click();
        js("$('.list > ul > li > a')[0].click();");
        //$(".list > ul > li > a", 0).click();
        $(".mask", 2).click();
        System.out.println(TestBrowser + "Guide character skill Pass");
        $(By.linkText("프로필")).click();
        $(".edge", 2).click();
        $(".edge", 3).click();
        $(".edge", 4).click();
        $(".btn-skill > span").click();
        $(".btn-close").click();
        System.out.println(TestBrowser + "Guide character profile Pass");
        $(By.linkText("소셜")).click();
        $(By.linkText("춤")).click();
        $(By.linkText("터치")).click();
        $(By.linkText("목마")).click();
        $(By.linkText("앉기")).click();
        $(By.linkText("하이파이브")).click();
        $(By.linkText("기쁨")).click();
        $(By.linkText("슬픔")).click();
        $(By.linkText("인사")).click();
        $(By.linkText("포옹")).click();
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}
