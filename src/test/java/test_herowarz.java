import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
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
	
	Date now = new Date();
    String nowTime = now.toString();
    
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
	public static void js(String javaScriptSource) {
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
  	public static void url(String URL) throws Exception {
  		HttpURLConnection huc = null;
  		huc = (HttpURLConnection)(new URL(URL).openConnection());
  		huc.setRequestMethod("HEAD");
  		huc.connect();
  		int respCode = huc.getResponseCode ();
  		if(respCode >= 400){
  	        System.out.println(URL +" is a broken link " + respCode);
  		} else {
  	        System.out.println(URL +" is a valid link " + respCode);
  		}
  	}
  	public void write(String board){
  		$(".ua_title").setValue(nowTime);
        js("$('.uid_smarteditor_area iframe').contents().find('iframe').contents().find('body').text('" + TestBrowser + " / " + nowTime + "')");
        $(".btn-blue").click();
        confirm("등록이 완료되었습니다.");
        System.out.println(TestBrowser + "Community " + board +" write Pass");
  	}
  	public void  writeImg(String board){
  		js("$('.uid_smarteditor_area iframe').contents().find('iframe').contents().find('body').text('" + TestBrowser + " / " + nowTime + "')");
        js("$('.uid_smarteditor_area iframe').contents().find('.se2_photo').click();");
        windowTitle("이미지 업로드 :: SmartEditor2");
        $("#uploadInputBox").setValue("C:\\Users\\Administrator\\Downloads\\black.jpg");
        $("#btn_confirm").click();
        windowTitle(" :: 액션중독! - 최강의군단");
        $("label", 0).click();
        $("label", 2).click();
        $(".ua_title").setValue(nowTime);
        $(".btn-blue").click();
        confirm("등록이 완료되었습니다.");
        System.out.println(TestBrowser + "Community " + board +" write Pass");
  	}
  	public void comment(String board){
  		$(".uid_comment_content").setValue(TestBrowser);
        $(".uid_comment_writesave").click();
        $(".uid_comment_like").click();
        $(".uid_comment_like").click();
        confirm("최고군은 한번만 할 수 있습니다.");
        $(".uid_comment_reply").click();
        $(".uid_comment_content", 1).setValue(nowTime);
        $(".uid_comment_writesave", 1).click();
        $(".uid_comment_like", 1).click();
        $(".uid_comment_like", 1).click();
        confirm("최고군은 한번만 할 수 있습니다.");
        $(".uid_comment_delete", 1).click();
        dismiss("댓글을 삭제 하시겠습니까?");
        $(".uid_comment_delete", 1).click();
        confirm("댓글을 삭제 하시겠습니까?");
        $(".uid_comment_delete").click();
        dismiss("댓글을 삭제 하시겠습니까?");
        $(".uid_comment_delete").click();
        confirm("댓글을 삭제 하시겠습니까?");
        System.out.println(TestBrowser + "Community " + board +" comment Pass");
        $(".btn-white").click();
  	}

	@Test(priority = 0)
	public void login() {
        open(baseUrl);
        js("");
        $(By.linkText("액션중독! - 최강의 군단")).click();
        $(".uid_login_login").waitUntil(appear, 5000);
        $(".uid_cookie_checkbox").click();
        $(".uid_test_btn_ok").click();
//        $(".uid_login_login").click();
//        dismiss("아이디를 입력해주세요");
        $(".uid_login_id").setValue("영권1232");
//        $(".uid_login_login").click();
//        dismiss("비밀번호를 입력해주세요.");
//        $(".uid_login_password").setValue("qordlf13");
//        $(".uid_login_login").click();
//        confirm("아이디 또는 비밀번호가 일치하지 않습니다.");
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
	public void guide() throws Exception {
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
        js("$('.mask')[2].click();");
        //$(".mask", 2).click();
        System.out.println(TestBrowser + "Guide character skill Pass");
        $(By.linkText("프로필")).click();
        $(".edge", 2).click();
        $(".edge", 3).click();
        $(".edge", 4).click();
        $(".btn-skill > span").click();
        $(".btn > a").click();
        System.out.println(TestBrowser + "Guide character profile Pass");
        $(By.linkText("소셜")).click();
        url("http://www.youtube.com/embed/k-FsurQIm-E");
        $(By.linkText("춤")).click();
        url("http://www.youtube.com/embed/SG3SZouMMs8");
        $(By.linkText("터치")).click();
        url("http://www.youtube.com/embed/S6oSDEX0COU");
        $(By.linkText("목마")).click();
        url("http://www.youtube.com/embed/ikEQTgelnG0");
        $(By.linkText("앉기")).click();
        url("http://www.youtube.com/embed/ReOIRmunGMg");
        $(By.linkText("하이파이브")).click();
        url("http://www.youtube.com/embed/qQ-oAkwixWQ");
        $(By.linkText("기쁨")).click();
        url("http://www.youtube.com/embed/s0r8Ob2jXgw");
        $(By.linkText("슬픔")).click();
        url("http://www.youtube.com/embed/DTmDEClwhL8");
        $(By.linkText("인사")).click();
        url("http://www.youtube.com/embed/1LAuHywpQd0");
        $(By.linkText("포옹")).click();
        url("http://www.youtube.com/embed/s91yeO73KFI");
        System.out.println(TestBrowser + "Guide character social Pass");
        $(By.linkText("주메뉴 열기")).click();
        $(By.linkText("여행자 지도")).click();
        System.out.println(TestBrowser + "Guide map Pass");
        $(By.linkText("군단 연대기")).click();
        $("#lAboutFooter").waitUntil(visible, 5000);
        System.out.println(TestBrowser + "Guide chronicle Pass");
        $(By.linkText("최강의군단")).click();
	}
	//@Test(priority = 3)
	public void community() {
		$(By.linkText("커뮤니티")).hover();
        $(By.linkText("자유 게시판")).click();
        js("window.scrollBy(0,999);");
        $(".btn-blue").click();
        $("#cateDepth1").selectOptionContainingText("수다");
        write("freeboard");
        $(".btn-white").click();
        $(".comment-num", 3).click();
        comment("board");
        $(".l-sub-manual ").click();
        js("window.scrollBy(0,999)");
        $(".btn-blue").click();
        $("#cateDepth1").selectOptionContainingText("PVE 공략");
        $("#cateDepth2").selectOptionContainingText("톰");
        write("manual");
        comment("manual");
        /*
        $(".l-sub-boast").click();
        js("window.scrollBy(0,1999);");
        $(".btn-blue").click();
		open(baseUrl + "/community/write.hero?code=GetItem");
        writeImg("GetItem");
        comment("GetItem");
        $(".l-sub-collection").click();
        js("window.scrollBy(0,1999);");
        $(".btn-blue").click();
        writeImg("collection");
        comment("collection");
        $(".l-sub-fan").click();
        js("window.scrollBy(0,1999);");
        $(".btn-blue").click();
        writeImg("fan");
        comment("fan");
        */
	}
	@Test(priority = 4)
	public void ranking() throws InterruptedException {
		if(TestBrowser == "internetExplorer"){
			$(By.linkText("랭킹")).hover();
			$(By.linkText("군단 랭킹")).click();
		} else {
			open(baseUrl + "/ranking/corpsRanking.hero");
		}
		for(int i=0;i<2;++i){
			$("#keyword").setValue("영권1232화란");
			$(".btn-srch").click();
			if(i==0){
				$(".ico-server-hyperion").click();
			} else if (i==1){
		        System.out.println(TestBrowser + " Ranking corps Pass");
		        $(".l-sub-character").click();
			}
		}
		for(int i=0;i<2;++i){
			$(By.linkText("통합능력치")).click();
			$("#keyword").setValue("영권1232화란");
			$(".btn-srch").click();
			$(By.linkText("전투력")).click();
			$("#keyword").setValue("영권1232화란");
			$(".btn-srch").click();
			$(By.linkText("생존력")).click();
			$("#keyword").setValue("영권1232화란");
			$(".btn-srch").click();
			if(i==0){
				$(".ico-server-hyperion").click();
			} else if (i==1){
		        System.out.println(TestBrowser + " Ranking character Pass");
		        $(".l-sub-pvp").click();				
			}
		}
        for(int i=0;i<2;++i){
    		$(By.linkText("시즌랭킹")).click();
            $("#keyword").setValue("영권1232화란");
    		$(".btn-srch").click();
    		$(By.linkText("주간랭킹")).click();
    		$("#keyword").setValue("영권1232화란");
    		$(".btn-srch").click();
    		$(By.linkText("월간랭킹")).click();
    		$("#keyword").setValue("영권1232화란");
    		$(".btn-srch").click();
    		if(i==0){
    			$(".ico-server-hyperion").click();
    		} else if (i==1){
    	        System.out.println(TestBrowser + " Ranking mflMaster Pass");
    	        $(".l-sub-pvp-all").click();
    		}
        }
        for(int i=0;i<2;++i){
    		$("#keyword").setValue("영권1232화란");
    		$(".btn-srch").click();
    		if (i==0){
    			$(".ico-server-hyperion").click();
    		} else if (i==1){
    			System.out.println(TestBrowser + " Ranking mfl Pass");
    	        $(".l-sub-liberation").click();
    		}
        }
		int a = 0;
		for(int i=1;i<5;++i){
			$(By.linkText(i + "인")).click();
			$("#keyword").setValue("영권1232화란");
			$(".btn-search").click();
			if(i==4 && a==0){
				$(".ico-server-hyperion").click();
				$("#keyword").setValue("영권1232화란");
				$(".btn-search").click();
				i = 1;
				a = 1;
			} else if (i==4 && a==1) {
				System.out.println(TestBrowser + " Ranking libaration Pass");
				$(".l-sub-backdraft").click();
			}
		}
		for(int i=0;i<2;++i){
			$("#keyword").setValue("영권1232화란");
			$(".btn-search").click();
			if(i==0){
				$(".ico-server-hyperion").click();
			} else if (i==1) {
				System.out.println(TestBrowser + " Ranking backdraft Pass");
				$(".l-sub-pantheon-time").click();
			}
		}
		for(int i=0;i<6;){
			switch(i){
				case 0: 
					$("#uid_stage_select").selectOptionContainingText("사마귀 부화장");
					break;
				case 1: 
					$("#uid_stage_select").selectOptionContainingText("바리데기 침실");
					break;
				case 2:
					$("#uid_stage_select").selectOptionContainingText("음속의 공간");
					break;
				case 3:
					$("#uid_stage_select").selectOptionContainingText("이글거리는 주차장");
					break;
				case 4:
					$("#uid_stage_select").selectOptionContainingText("비트 오페라하우스");
					break;
			}
			if(i<5){
				for(int z=1;z<5;z++){
					$(By.linkText(z + "인")).click();
					$("#keyword").setValue("영권1232화란");
					$(".btn-search").click();
				}				
			}
			i++;
			if(i == 5 && a == 1){
				$(".ico-server-hyperion").click();
				$("#uid_stage_select").selectOptionContainingText("사마귀 부화장");
				i = 0;
				a = 2;
			} else if (i == 5 && a == 2){
				System.out.println(TestBrowser + " Ranking pantheonTime Pass");
				$(".l-sub-raid").click();
			}
		}
		for(int i=0;i<2;++i){
			$("#keyword").setValue("영권1232화란");
			$(".btn-search").click();
			if(i==0){
				$(".ico-server-hyperion").click();
			} else if (i==1) {
				System.out.println(TestBrowser + " Ranking raid Pass");
				$(".l-sub-try-raid").click();
			}
		}
		for(int i=0;i<2;++i){
			$("#keyword").setValue("영권1232화란");
			$(".btn-search").click();
			if(i==0){
				$(".ico-server-hyperion").click();
			} else if (i==1) {
				System.out.println(TestBrowser + " Ranking try raid Pass");
			}
		}
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}
