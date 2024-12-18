package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;
import dev.failsafe.internal.util.Assert;

public class TestCases extends ExcelDataProvider{ // Lets us read the data
        ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */
        @Test
        public void testCase01() throws InterruptedException{
                String getCurrentUrl = driver.getCurrentUrl();
                org.testng.Assert.assertEquals("https://www.youtube.com/", getCurrentUrl);
                JavascriptExecutor js = (JavascriptExecutor)driver;                
                WebElement aboutEl = driver.findElement(By.xpath("(//div[@id='guide-links-primary']/a)[1]"));
                js.executeScript("arguments[0].scrollIntoView();", aboutEl);
                Wrappers.abouttab(aboutEl);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.urlToBe("https://about.youtube/"));
                String expectedUrl ="https://about.youtube/";
                String currentUrl = driver.getCurrentUrl();
                org.testng.Assert.assertEquals(currentUrl, expectedUrl,"Navigation to about page unsucessfull");
                System.out.println("Navigation sucessfull");
                WebElement abouHeadingEl = driver.findElement(By.xpath("//section[@class='ytabout__content']/h1"));
                System.out.println(abouHeadingEl.getText());
                List<WebElement> aboutUsMsg = driver.findElements(By.xpath("(//section[@class='ytabout__content']/p)"));
                for(WebElement about :aboutUsMsg){
                        js.executeScript("arguments[0].scrollIntoView()", about);
                        System.out.println(about.getText());
                }
                
                
        }

        @Test
        public void testCase02() throws InterruptedException{
                driver.navigate().back();
                Thread.sleep(1000);
                SoftAssert sa = new SoftAssert();
                JavascriptExecutor js = (JavascriptExecutor)driver; 
                WebElement moviesEl = driver.findElement(By.xpath("(//a[@id='endpoint'])[9]"));
                js.executeScript("arguments[0].scrollIntoView();", moviesEl);
                Wrappers.tab(moviesEl);
                Thread.sleep(1000);
                WebElement topsellingScrollBtn = driver.findElement(By.xpath("//button[@aria-label='Next']"));
                WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOf(topsellingScrollBtn));
                js.executeScript("arguments[0].scrollIntoView();", moviesEl);
                Boolean horizontalscrollBtn = topsellingScrollBtn.isDisplayed();
                //System.out.println(horizontalscrollBtn);
                while (horizontalscrollBtn) {
                        topsellingScrollBtn.click();      
                        Thread.sleep(1000);
                        if(topsellingScrollBtn.isDisplayed()){
                                continue;
                        }else{
                                Thread.sleep(1000);
                                WebElement contentTypeEl = driver.findElement(By.xpath("(//div[@aria-label='U'])[6]/p"));
                                
                                boolean ismature = "U" .equals(contentTypeEl.getText());
                                sa.assertTrue(ismature,"The move is a Mature Movie");
                                sa.assertFalse(ismature,"Not a mature movie");
                                break;
                        }                  
                }
                Thread.sleep(1000);
                WebElement movieCategoryEl = driver.findElement(By.xpath("(//h3[@class='style-scope ytd-grid-movie-renderer']//following-sibling::span)[32]"));
                wait.until(ExpectedConditions.visibilityOf(movieCategoryEl));
                String movieCategory = movieCategoryEl.getText().replaceAll("\\d", "");
                sa.assertEquals(movieCategory,"Animation");                
                
        }

        @Test
        public void testCase03() throws InterruptedException{
                driver.navigate().back();
                Thread.sleep(1000);
                driver.navigate().refresh();
                Thread.sleep(1000);
                SoftAssert sa = new SoftAssert();
                JavascriptExecutor js = (JavascriptExecutor)driver; 
                WebElement musicEl = driver.findElement(By.xpath("(//a[@id='endpoint'])[8]"));
                WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOf(musicEl));
                Wrappers.tab(musicEl);
                Thread.sleep(1000);
                WebElement firstTitleEl = driver.findElement(By.xpath("(//div[@id='title-text'])[1]"));
                WebElement musicScrollBtn = driver.findElement(By.xpath("(//button[@aria-label='Next'])[1]"));
                js.executeScript("arguments[0].scrollIntoView()", firstTitleEl);                
                Boolean horizontalscrollBtn = musicScrollBtn.isDisplayed();
                while (horizontalscrollBtn) {
                        musicScrollBtn.click();      
                        Thread.sleep(1000);
                        if(musicScrollBtn.isDisplayed()){
                                continue;
                        }else{
                                Thread.sleep(1000);
                               
                                break;
                         }

                } 
                Thread.sleep(2000);
                WebElement nameOfPlaylistEl = driver.findElement(By.xpath("(//h3[@class='yt-lockup-metadata-view-model-wiz__heading-reset'])[12]"));  
                Thread.sleep(2000);                       
                WebElement noOfSongsEl = driver.findElement(By.xpath("(//div[@class='badge-shape-wiz__text'])[12]"));                                
                String noOfSongs = noOfSongsEl.getText();
                StringBuilder sb = new StringBuilder();
                for(char i : noOfSongs.toCharArray()){
                        if(Character.isDigit(i)){
                                sb.append(i);
                        }
                }
                int songsCount = Integer.parseInt(sb.toString());
                //System.out.println(songs);   
                sa.assertTrue(songsCount<=50,"The Song count is less than or Equal to 50");
                sa.assertFalse(songsCount<=50,"The Song count is not less than or Equal to 50");
                System.out.println(nameOfPlaylistEl.getText());    


        }

        @Test
        public void testCase04() throws InterruptedException{
                driver.navigate().back();
                Thread.sleep(1000);
                driver.navigate().refresh();
                Thread.sleep(1000);
                //SoftAssert sa = new SoftAssert();
                //JavascriptExecutor js = (JavascriptExecutor)driver; 
                WebElement newsEl = driver.findElement(By.xpath("(//a[@id='endpoint'])[12]"));
                Wrappers.tab(newsEl);
                Thread.sleep(1000);
                //WebElement firstTitleEl = driver.findElement(By.xpath("(//div[@id='title-text'])[2]"));
                //js.executeScript("arguments[0].scrollIntoView()", firstTitleEl); 
                List<WebElement> latestNewsEl = driver.findElements(By.xpath("//div[@id='content']//ytd-post-renderer"));
                        for(int i=0;i<3;i++){
                                WebElement newsChannelheadingEl = latestNewsEl.get(i).findElement(By.xpath(".//div[@id='author']"));
                                System.out.println(newsChannelheadingEl.getText()+" is the news channel name");

                                WebElement newsChannelcontentEl = latestNewsEl.get(i).findElement(By.xpath(".//yt-formatted-string[@id='home-content-text']/span[1]"));
                                System.out.println(newsChannelcontentEl.getText()+" is the news content");

                                WebElement noOfLikes = latestNewsEl.get(i).findElement(By.xpath(".//span[@id='vote-count-middle']"));
                                System.out.println(noOfLikes.getText()+" is the number of Likes");
                        }

                        

        }
        // @Test(dataProvider = "excelData")
        // public void testCase05() throws InterruptedException{
        //         driver.navigate().back();
        //         Thread.sleep(1000);
        //         driver.navigate().refresh();
        //         Thread.sleep(1000);

        // }
                               
               
        


        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);
                driver.get("https://www.youtube.com/");
                driver.manage().window().maximize();
        }

        @AfterTest
        public void endTest() {
                 driver.close();
                 driver.quit();

        }
}