package Caw_Studios_Assignment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DynamicTableTest_with_json {
    private WebDriver driver;

    @BeforeClass
    public void setup() {
        // Initializing the Chrome browser for testing
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void Dynamic_Table_Test() throws InterruptedException, IOException, ParseException {
        String url = "https://testpages.herokuapp.com/styled/tag/dynamic-table.html";
        driver.get(url);

        WebElement tb = driver.findElement(By.xpath("//summary[contains(text(),'Table Data')]"));
        tb.click();
        Thread.sleep(3000);

        WebElement jsonDataElement = driver.findElement(By.xpath("//textarea[@id='jsondata']"));
        jsonDataElement.clear();

        // Parse JSON data from the file
        Object parsedObject = new JSONParser().parse(new FileReader("test.json"));

        // Cast the parsed object to a JSONArray
        JSONArray jsonArray = (JSONArray) parsedObject;

        // Construct the JSON string for the entire data
        String jsonString = jsonArray.toJSONString();

        // Send JSON data to the text area
        jsonDataElement.sendKeys(jsonString);

        WebElement refreshButton = driver.findElement(By.xpath("//button[@id='refreshtable']"));
        refreshButton.click();

        Thread.sleep(5000);

        // Replace 'table_id' with the actual ID of your table element
        String tableId = "dynamictable";

        // Locate the table element by its ID
        WebElement table = driver.findElement(By.id(tableId));

        // Get all rows of the table
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        // Iterate through rows and print data
        for (WebElement row : rows) {
            // Get all columns of the row
            List<WebElement> columns = row.findElements(By.tagName("td"));

            // Iterate through columns and print data
            for (WebElement column : columns) {
                System.out.print(column.getText() + "\t");
            }
            System.out.println(); // Move to the next line after printing a row
        }
    }


    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
