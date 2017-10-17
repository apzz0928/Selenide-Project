import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
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


public class authMng_Selenide {
	private static String baseUrl, nodeUrl;
	private static String TestBrowser;
	
	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://rct-d-p.astorm.com";
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
	public void Login() {
        open(baseUrl + "/login/form.ct");
        $(By.name("j_username")).setValue("apzz0928");
        $(By.name("j_password")).setValue("qordlf!@34");
        $(".uid_ldap_login_submit_btn").click();
        open(baseUrl + "/common/locale/ko");
        System.out.println(TestBrowser + " Login : Pass");
    }
	@Test(priority = 1)
	public void authorityMenu_groupOrder() {
        open(baseUrl + "/authority/menugroup.ct");
        $(".menu-title").waitUntil(text("전체 메뉴"), 5000);
        $(".uid_group_order_btn", 1).waitUntil(exist, 5000);
        $(".uid_group_order_btn", 1).click();
        $(".uid_ok_btn").click();
        $(".uid_group_order_btn", 0).waitUntil(exist, 5000);
        $(".uid_group_order_btn", 0).click();
        $(".uid_ok_btn").click();
        System.out.println(TestBrowser + " authorityMenu_groupOrder : Pass");
    }
	@Test(priority = 2)
	public void authorityMenu_keywordSearch() {
        $(".ac_input", 0).waitUntil(exist, 5000);
		$(".ac_input").setValue("test menu");
		$(".uid_menu_search").click();
        $(".ac_input").setValue("");
		$(".uid_menu_search").click();
		System.out.println(TestBrowser + " authorityMenu_keywordSearch : Pass");
    }
	@Test(priority = 3)
	public void authorityMenu_detailMenu_add() {
		Date date = new Date();
		SimpleDateFormat todayTime = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
		Object Time = todayTime.format(date);
		$(By.linkText("test menu")).click();
		$(".uid_show_layer_menu_add_btn").waitUntil(exist, 5000);
		$(".uid_show_layer_menu_add_btn").click();
		$(".uid_layer_menu_add_btn").waitUntil(exist, 5000);
		$(By.name("menuName")).setValue("Test Menu Name!");
		$(By.name("menuNameKo")).setValue("Test Menu Ko!");
		$(By.name("menuNameEn")).setValue("Test Menu En!");
		$(By.name("menuNameJa")).setValue("Test Menu Ja!");
		$(By.name("menuNameZh")).setValue("Test Menu Zn!");
		$(By.name("menuDesc")).setValue("Test Menu Desc!");
		js("$('input[name=menuURI]').val('/" + Time + ".ct');");
        js("$('input[name=menuHide]').val('0');");
        js("$('input[name=menuOrder]').val('0');");
		$(".uid_layer_menu_add_btn").click();
		$(".uid_ok_btn").waitUntil(exist, 5000);
		$(".uid_ok_btn").click();		
		System.out.println(TestBrowser + " authorityMenu_detailMenu_add : Pass");
    }
	@Test(priority = 4)
	public void authorityMenu_detailMenu_menuOrder() {
		$(".uid_menu_order_btn", 1).waitUntil(exist, 5000);
		$(".uid_menu_order_btn", 1).click();
		$(".uid_ok_btn").waitUntil(exist, 5000);
		$(".uid_ok_btn").click();
		$(".uid_menu_order_btn", 2).waitUntil(exist, 5000);
        $(".uid_menu_order_btn", 2).click();
		$(".uid_ok_btn").waitUntil(exist, 5000);
        $(".uid_ok_btn").click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 5000);
		System.out.println(TestBrowser + " authorityMenu_detailMenu_menuOrder : Pass");
    }
	@Test(priority = 5)
	public void authorityMenu_detailMenu_edit() {
		$(".uid_menu_edit_btn", 0).click();
		$(By.name("menuName")).setValue("Menu Name!!!!!");
		$(By.name("menuNameKo")).setValue("Menu Ko!!!!!");
		$(By.name("menuNameEn")).setValue("Menu En!!!!!");
		$(By.name("menuNameJa")).setValue("Menu Ja!!!!!");
		$(By.name("menuNameZh")).setValue("Menu Zn!!!!!");
		$(By.name("menuDesc")).setValue("Test Menu Desc!");
		$(".uid_layer_menu_edit_btn").click();
        $(".uid_ok_btn").click();
        $(".menu-title").waitUntil(text("전체 메뉴"), 5000);
		System.out.println(TestBrowser + " authorityMenu_detailMenu_edit : Pass");
    }
	@Test(priority = 6)
	public void authorityMenu_detailMenu_del() {
		$(".uid_menu_del_btn", 0).click();
        $(".uid_ok_btn").click();
        $(".uid_ok_btn").click();
        System.out.println(TestBrowser + " authorityMenu_detailMenu_del : Pass");
    }
	@Test(priority = 7)
	public void authorityGroupMng_Menu() {
		open(baseUrl + "/authority/authGroupList.ct");
		$(".menu-title").waitUntil(text("전체 메뉴"), 5000);
		js("$('tbody > tr:last-child > td:eq(1) > a')[0].click();");
		$(".ac_toggle_btn", 0).click();
		$(".uid_authgroup_menu_add_btn").click();
        $(".uid_ok_btn").waitUntil(exist, 5000);
        $(".uid_ok_btn").click();
		$(".ac_toggle_btn", 1).click();
		$(".uid_authgroup_menu_delete_btn").click();
        $(".uid_ok_btn").waitUntil(exist, 5000);
        $(".uid_ok_btn").click();
        System.out.println(TestBrowser + " authorityGroupMng_Menu : Pass");
	}
	@Test(priority = 8)
	public void authorityGroupMng_Member() {
        $(".ac_toggle_btn", 2).waitUntil(exist, 5000);
		$(".ac_toggle_btn", 2).click();
		$(".uid_authgroup_member_add_btn").click();
        $(".uid_ok_btn").waitUntil(exist, 5000);
        $(".uid_ok_btn").click();
        $(".ac_toggle_btn", 3).waitUntil(exist, 5000);
		$(".ac_toggle_btn", 3).click();
		$(".uid_authgroup_member_delete_btn").click();
        $(".uid_ok_btn").waitUntil(exist, 5000);
        $(".uid_ok_btn").click();
        System.out.println(TestBrowser + " authorityGroupMng_Member : Pass");
	}
	@Test(priority = 9)
	public void groupListByAdmin() {
		open(baseUrl + "/authority/groupListByAdmin.ct");
		$(".menu-title").waitUntil(text("전체 메뉴"), 5000);
		$(".fa-chevron-down").click();
		$("li[data-key='apzz0928(INTERNAL)']").click();
		open(baseUrl + "/authority/groupListByAdmin.ct?loginMode=LDAP");
        $(".fa-chevron-down").click();
		$("li[data-key='apzz0928(LDAP)']").click();
        System.out.println(TestBrowser + " groupListByAdmin : Pass");
	}
	@Test(priority = 10)
	public void ctActionLog() {
		open(baseUrl + "/authority/ctActionLogList.ct");
		$(".uid_search_btn").waitUntil(exist, 5000);
		$(".uid_search_btn").shouldBe(visible).click();
		$(".uid_ctactionlog_parameter", 0).waitUntil(appear, 60000).click();
		if(TestBrowser.equals("chrome")){	
			windowTitle("Control Tower @ Cockpit");
			$(".uid_confirm").click();
			windowTitle("Control Tower @ reboot");
		}
        $(".uid_ctactionlog_result", 0).click();
		if(TestBrowser.equals("chrome")){
        	windowTitle("Control Tower @ Cockpit");
			$(".uid_confirm").click();
			windowTitle("Control Tower @ reboot");
		}
        System.out.println(TestBrowser + " ctActionLog : Pass");
	}
	@Test(priority = 11)
	public void ctMember_addMember_add() {
		open(baseUrl + "/authority/ctMember/list.ct");
		$(".uid_ctmember_add_btn").waitUntil(exist, 5000);
		$(".uid_ctmember_add_btn").click();
		$(".uid_ctmember_save_btn").waitUntil(exist, 5000);
		$(By.name("id")).setValue("TestUser-id");
		$(By.name("name")).setValue("TestUser-name");
		$(By.name("department")).setValue("TestUser-department");
		$(By.name("tel")).setValue("TestUser-tel");
		$(By.name("email")).setValue("TestUser-email@Test.com");
		$(By.name("password")).setValue("TestUser-password");
		$(By.name("passwordRe")).setValue("TestUser-password");
		$(".uid_ctmember_save_btn").click();
		$(".uid_ok_btn").waitUntil(exist, 5000).click();
		$(".uid_ok_btn").waitUntil(exist, 5000).click();
        System.out.println(TestBrowser + " ctMember_addMember_add : Pass");
	}
	@Test(priority = 12)
	public void ctMember_addMember_edit() {
		$(".uid_ctmember_edit_btn", 0).click();
		$(By.name("name")).setValue("TestUser-name!!!!");
		$(By.name("department")).setValue("TestUser-department!!!!");
		$(By.name("tel")).setValue("TestUser-tel!!!!");
		$(By.name("email")).setValue("TestUser-email@Test.com!!!!");
		$(".uid_ctmember_save_btn").click();
        $(".uid_ok_btn").waitUntil(exist, 5000).click();
        $(".uid_ok_btn").waitUntil(exist, 5000).click();
        System.out.println(TestBrowser + " ctMember_addMember_edit : Pass");
	}
	@Test(priority = 13)
	public void ctMember_addMember_resetPW_del() {
		$(".uid_ctmember_initpassword_btn", 0).click();
        $(".uid_ok_btn").waitUntil(exist, 5000).click();
        $(".uid_ok_btn").waitUntil(exist, 5000).click();
		$(".uid_ctmember_delete_btn", 0).click();
        $(".uid_ok_btn").waitUntil(exist, 5000).click();
        $(".uid_ok_btn").waitUntil(exist, 5000).click();
        System.out.println(TestBrowser + " ctMember_addMember_resetPW_del : Pass");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}
