package com.qualitest.qualithon;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Hackathon {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException, URISyntaxException {
		 
		
        	WebDriverManager.chromedriver().setup();
        	DesiredCapabilities caps = DesiredCapabilities.chrome();
            LoggingPreferences logPrefs = new LoggingPreferences();
            logPrefs.enable(LogType.BROWSER, Level.ALL);
            caps.setCapability("goog:loggingPrefs", logPrefs);
            
            WebDriver driver = new ChromeDriver(caps);
            
            driver.manage().window().maximize();

            driver.get("http://54.80.137.197:5000/");
            //Click door and start button
            driver.findElement(By.xpath("//a/img[contains(@src,'door')]")).click();
            driver.findElement(By.xpath("//button[text()='Start']")).click();
            
            //Task 1 : Click the  proceed button until it goes to next level.
            List<WebElement> buttons =driver.findElements(By.xpath("//button[@class='btn deep-orange darken-4']"));
            for(int i=0;i<buttons.size();i++)
            {
            	buttons.get(i).click();
            	try {
            		if(driver.findElement(By.xpath("//span[@class='btn btn-small green black-text']")).isDisplayed())
                	{
                		break;
                	}
				} catch (Exception e) {
					// TODO: handle exception
				}
            	
            }
            //Task 2 : Play video ,wait and pause 
            driver.switchTo().frame(driver.findElement(By.id("aVideoPlayer")));
            driver.findElement(By.xpath("//button[@aria-label='Play']")).click();
            Thread.sleep(10000); 
          
            
            driver.findElement(By.xpath("//button[@aria-label='Mute (m)']")).click();
            driver.switchTo().defaultContent();
            driver.findElement(By.xpath("//button[@class='btn deep-orange darken-4']")).click();
            
            //Task 3 : Solve Crystal Maze
			int startX=-1;
			int startY=-1;
			boolean found =false;
            //Find startX and startY
			for(int i=0;i<10;i++)
            {
            	for (int j=0;j<12;j++) {
            		if(driver.findElement(By.xpath("//tr["+(i+2)+"]//td["+(j+1)+"]")).getAttribute("className").contains("deep-purple"))
            		{
            			startX=i+2;
            			startY=j+1;
            			found =true;
            			break;		
            		}
            	}
            	if (found)
            	{
            		break;
            	}
            }

            int CurrentX = startX;
            int CurrentY = startY;
            String visited="";
            String lastMove="";
            String wall="";
            driver.findElement(By.xpath("//a[contains(@onclick,'right')]")).click();
        	CurrentY +=1;
        	try {
				
			//Move until it reaches destination
            while(CurrentX>=0 && CurrentY>=0 && CurrentX<13 && CurrentY<13 && driver.findElement(By.xpath("//td[contains(@class,'green')]")).isDisplayed() && !visited.contains(driver.findElement(By.xpath("//tr["+(CurrentX)+"]//td["+(CurrentY)+"]")).getAttribute("class")))
            {
            	String v_text=driver.findElement(By.xpath("//tr["+(CurrentX)+"]//td["+(CurrentY)+"]")).getAttribute("class");
            	visited +=v_text.split(" ")[0]+" "+v_text.split(" ")[1]+":";
            	
            	String right_val=driver.findElement(By.xpath("//tr["+(CurrentX)+"]//td["+(CurrentY+1)+"]")).getAttribute("class");
            	String rightpoints=right_val.split(" ")[0]+" "+right_val.split(" ")[1];
            	String dow_val=driver.findElement(By.xpath("//tr["+(CurrentX+1)+"]//td["+(CurrentY)+"]")).getAttribute("class");            	
            	String downpoints=dow_val.split(" ")[0]+" "+dow_val.split(" ")[1];
            	String up_val=driver.findElement(By.xpath("//tr["+(CurrentX-1)+"]//td["+(CurrentY)+"]")).getAttribute("class");
            	String uppoints=up_val.split(" ")[0]+" "+up_val.split(" ")[1];
            	String Leftval=driver.findElement(By.xpath("//tr["+(CurrentX)+"]//td["+(CurrentY-1)+"]")).getAttribute("class");
            	String leftpoints=Leftval.split(" ")[0]+" "+Leftval.split(" ")[1];
            	
            	//Press Right arrow
            	if(CurrentY+1 <13 && !driver.findElement(By.xpath("//tr["+(CurrentX)+"]//td["+(CurrentY+1)+"]")).getAttribute("className").contains("black") && !visited.contains(driver.findElement(By.xpath("//tr["+(CurrentX)+"]//td["+(CurrentY+1)+"]")).getAttribute("class")) && !wall.contains(driver.findElement(By.xpath("//tr["+(CurrentX)+"]//td["+(CurrentY+1)+"]")).getAttribute("class"))  && !visited.contains(rightpoints) && !wall.contains(rightpoints))
            	{
            		
            		driver.findElement(By.xpath("//a[contains(@onclick,'right')]")).click();
            		
            		if(visited.contains(driver.findElement(By.xpath("//tr["+(CurrentX)+"]//td["+(CurrentY+1)+"]")).getAttribute("class")))
            		{
            			String test=driver.findElement(By.xpath("//tr["+(CurrentX)+"]//td["+(CurrentY+1)+"]")).getAttribute("class");
            			visited = visited.replace(test.split(" ")[0]+" "+test.split(" ")[1], "");
            			String wal_val=driver.findElement(By.xpath(lastMove)).getAttribute("class");
            			wall+=wal_val.split(" ")[0]+" "+wal_val.split(" ")[1];
            		}
            		lastMove="//tr["+(CurrentX)+"]//td["+(CurrentY+1)+"]";
            		CurrentY +=1;
            		
            	}
            	//Press Down arrow
            	else if(CurrentX+1 <13 && !driver.findElement(By.xpath("//tr["+(CurrentX+1)+"]//td["+(CurrentY)+"]")).getAttribute("className").contains("black") && !visited.contains(driver.findElement(By.xpath("//tr["+(CurrentX+1)+"]//td["+(CurrentY)+"]")).getAttribute("class")) && !wall.contains(driver.findElement(By.xpath("//tr["+(CurrentX+1)+"]//td["+(CurrentY)+"]")).getAttribute("class")) && !visited.contains(downpoints) && !wall.contains(downpoints))
            	{
            		driver.findElement(By.xpath("//a[contains(@onclick,'down')]")).click();
            		
            		if(visited.contains(driver.findElement(By.xpath("//tr["+(CurrentX+1)+"]//td["+(CurrentY)+"]")).getAttribute("class")))
            		{
            			String test=driver.findElement(By.xpath("//tr["+(CurrentX+1)+"]//td["+(CurrentY)+"]")).getAttribute("class");
            			visited = visited.replace(test.split(" ")[0]+" "+test.split(" ")[1], "");
            			String wal_val=driver.findElement(By.xpath(lastMove)).getAttribute("class");
            			wall+=wal_val.split(" ")[0]+" "+wal_val.split(" ")[1];
            		}
            		lastMove="//tr["+(CurrentX+1)+"]//td["+(CurrentY)+"]";
            		CurrentX +=1;
            	}
            	//Press UP arrow
            	else if(CurrentX-1 >=0 && !driver.findElement(By.xpath("//tr["+(CurrentX-1)+"]//td["+(CurrentY)+"]")).getAttribute("className").contains("black") && !visited.contains(driver.findElement(By.xpath("//tr["+(CurrentX-1)+"]//td["+(CurrentY)+"]")).getAttribute("class")) && !wall.contains(driver.findElement(By.xpath("//tr["+(CurrentX-1)+"]//td["+(CurrentY)+"]")).getAttribute("class")) && !visited.contains(uppoints) && !wall.contains(uppoints))
            	{
            		driver.findElement(By.xpath("//a[contains(@onclick,'up')]")).click();
            		
            		if(visited.contains(driver.findElement(By.xpath("//tr["+(CurrentX-1)+"]//td["+(CurrentY)+"]")).getAttribute("class")))
            		{
            			String test=driver.findElement(By.xpath("//tr["+(CurrentX-1)+"]//td["+(CurrentY)+"]")).getAttribute("class");
            			visited = visited.replace(test.split(" ")[0]+" "+test.split(" ")[1], "");
            			String wal_val=driver.findElement(By.xpath(lastMove)).getAttribute("class");
            			wall+=wal_val.split(" ")[0]+" "+wal_val.split(" ")[1];
            		}
            		lastMove="//tr["+(CurrentX-1)+"]//td["+(CurrentY)+"]";
            		CurrentX -=1;
            	}
            	//Press Left arrow
            	else if(CurrentY-1 >=0 && !driver.findElement(By.xpath("//tr["+(CurrentX)+"]//td["+(CurrentY-1)+"]")).getAttribute("className").contains("black") && !visited.contains(driver.findElement(By.xpath("//tr["+(CurrentX)+"]//td["+(CurrentY-1)+"]")).getAttribute("class")) && !wall.contains(driver.findElement(By.xpath("//tr["+(CurrentX)+"]//td["+(CurrentY-1)+"]")).getAttribute("class")) && !visited.contains(leftpoints) && !wall.contains(leftpoints))
            	{
            		driver.findElement(By.xpath("//a[contains(@onclick,'left')]")).click();
            		if(visited.contains(driver.findElement(By.xpath("//tr["+(CurrentX)+"]//td["+(CurrentY-1)+"]")).getAttribute("class")))
            		{
            			String test=driver.findElement(By.xpath("//tr["+(CurrentX)+"]//td["+(CurrentY-1)+"]")).getAttribute("class");
            			visited = visited.replace(test.split(" ")[0]+" "+test.split(" ")[1], "");
            			String wal_val=driver.findElement(By.xpath(lastMove)).getAttribute("class");
            			wall+=wal_val.split(" ")[0]+" "+wal_val.split(" ")[1];
            		}
            		lastMove="//tr["+(CurrentX)+"]//td["+(CurrentY-1)+"]";
            		CurrentY -=1;
            	}
            	//when it reaches wall and no further moves
            	else 
            	{
            		
            		String test=driver.findElement(By.xpath("//tr["+(CurrentX)+"]//td["+(CurrentY)+"]")).getAttribute("class");
        			visited = visited.replace(test.split(" ")[0]+" "+test.split(" ")[1], "");
        			String wal_val=driver.findElement(By.xpath(lastMove)).getAttribute("class");
        			wall+=wal_val.split(" ")[0]+" "+wal_val.split(" ")[1];
        			visited = visited.replace(visited.split(":")[visited.split(":").length-1],"");        			
            	}
            		
            	
            	
            }
        	} catch (Exception e) {
				// TODO: handle exception
			}

        	driver.findElement(By.xpath("//button[text()='Submit']")).click();
        	
        	//Task 4 : Move pointer using keyboard arrow keys to INDIA
        	WebElement webElement = driver.findElement(By.xpath("//div[@id='map']"));
        	webElement.click();
        	webElement.sendKeys("i");
        	for(int i=0;i<37;i++)
        	{
        	webElement.sendKeys(Keys.ARROW_RIGHT);
        	}
        	for(int i=0;i<9;i++)
        	{
        	webElement.sendKeys(Keys.ARROW_UP);
        	}
        	
        	driver.findElement(By.xpath("//button[text()='Proceed']")).click();
            
        	//Task 4 : Submit captcha to proceed

            List<LogEntry> logs = driver.manage().logs().get(LogType.BROWSER).getAll();

            String captcha=logs.get(logs.size()-1).toString().split(" ")[5-1].replace("\"", "");
            driver.findElement(By.xpath("//input[@id='notABotCaptchaResponse']")).sendKeys(captcha);
            driver.findElement(By.xpath("//button[@id='notABotCaptchaSubmit']")).click();

            //Task 5 : Socket Gate - Enter Access token and Submit
            
            String message=driver.findElement(By.xpath("//div[@class='yellow lighten-3']")).getText();

            	WSClient c = new WSClient(new URI(
                    "ws://54.80.137.197:5001")); 
                c.connect();
                Thread.sleep(2000);
                c.send(message);
                Thread.sleep(2000);
                c.close();
            
              
                driver.findElement(By.xpath("//input[@id='socketGateMessage']")).sendKeys(c.token);;
                driver.findElement(By.xpath("//button[text()='Submit']")).click();
                
                if(driver.findElement(By.xpath("//h3")).getText().equals("Congratulations!! You Found the Treasure"))
                {
                	 System.out.println("Congratulations!! You Found the Treasure");
                }
                else
                {
                	System.out.println("Something went wrong");
                }
                Thread.sleep(2000);
            driver.quit();
	}

}
