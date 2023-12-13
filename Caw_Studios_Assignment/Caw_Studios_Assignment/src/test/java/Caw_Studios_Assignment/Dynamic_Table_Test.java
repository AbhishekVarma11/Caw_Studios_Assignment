package Caw_Studios_Assignment;



import java.sql.Time;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Dynamic_Table_Test {
	 private WebDriver driver;
	@SuppressWarnings("deprecation")
	@BeforeClass
	    public void setup() {
	        // Initializing the Chrome browser for testing
	        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
	        driver = new ChromeDriver();
	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    }

    @Test
    public void Dynamic_Table_Test() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");      
        String url = "https://testpages.herokuapp.com/styled/tag/dynamic-table.html";

        // Navigate to the specified URL
        driver.get(url);

        WebElement tb = driver.findElement(By.xpath("//summary[contains(text(),'Table Data')]"));
        tb.click();
        Thread.sleep(3000);
        //locating text area
        WebElement jsonDataElement = driver.findElement(By.xpath("//textarea[@id='jsondata']"));
        jsonDataElement.clear();
        String originalJsonData = "[{\"name\":\"Bob\",\"age\":20,\"gender\":\"male\"},{\"name\":\"George\",\"age\":42,\"gender\":\"male\"},{\"name\":\"Sara\",\"age\":42,\"gender\":\"female\"},{\"name\":\"Conor\",\"age\":40,\"gender\":\"male\"},{\"name\":\"Jennifer\",\"age\":42,\"gender\":\"female\"}]";
        jsonDataElement.sendKeys(originalJsonData);
        
        //clicking the refresh button
        WebElement refreshButton = driver.findElement(By.xpath("//button[@id='refreshtable']"));

        refreshButton.click();

      
        String tableId = "dynamictable";

        // Locate the table element by its ID
        WebElement table = driver.findElement(By.id(tableId));

        // Get all rows of the table
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        // Convert table data to JSON
        JSONArray tableDataJsonArray = convertTableDataToJson(rows);

        // Convert original JSON data to JSONArray
        JSONArray originalJsonArray = new JSONArray(originalJsonData);
        System.out.println(originalJsonArray);
        

        // Check if the two JSON arrays are equal
        if (originalJsonArray.similar(tableDataJsonArray)) {
        	Assert.assertTrue(true);
            System.out.println("JSON data and Table data match!");
        } else {
        	Assert.assertFalse(false);
            System.out.println("JSON data and Table data do not match!");
        }

       Thread.sleep(5000);
 
    }

    private JSONArray convertTableDataToJson(List<WebElement> rows) {
        JSONArray jsonArray = new JSONArray();

        for (int i = 1; i < rows.size(); i++) { // Start from index 1 to skip the header row
            WebElement row = rows.get(i);
            List<WebElement> columns = row.findElements(By.tagName("td"));

            JSONObject rowData = new JSONObject();
            rowData.put("name", columns.get(0).getText());
            rowData.put("age", Integer.parseInt(columns.get(1).getText()));
            rowData.put("gender", columns.get(2).getText());

            jsonArray.put(rowData);
        }
        System.out.println(jsonArray);
        return jsonArray;
    }
    
    @AfterClass
    public void tearDown() {
    	//closing all the browser windows
        driver.quit();
    }
    
}


