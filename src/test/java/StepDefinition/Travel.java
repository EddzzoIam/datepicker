package StepDefinition;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Travel {

	WebDriver driver;
	WebDriverWait wait;

	@Before
	public void setup() {
		// launching Chrome browser and maximising it
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		// global wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

	}

	// url
	@Given("I navigate to skyscanner web site")
	public void i_navigate_to_skyscanner_web_site() {

		driver.get("https://www.virginatlantic.com/");

	}

	// selecting depature location from autofill dropdown with keyboard assimilation
	// while loop to iterate to the desire location
	@When("I enter From as {string}")
	public void i_enter_from_as(String depatureLocation) {
		driver.findElement(By.xpath("//span[normalize-space()='From']")).click();
		driver.findElement(By.xpath("//input[@id='search_input']")).sendKeys(depatureLocation);
		int i = 0;
		while (i < 4) {

			driver.findElement(By.xpath("//input[@id='search_input']")).sendKeys(Keys.DOWN);

			i++;
		}

		driver.findElement(By.xpath("//input[@id='search_input']")).sendKeys(Keys.ENTER);
	}

	// selecting arrival location from autofill dropdown with keyboard assimilation
	// while loop to iterate to the desire arrival location
	@And("I enter To as {string}")
	public void i_enter_to_as(String arrivalLocation) throws InterruptedException {

		driver.findElement(By.xpath("//span[contains(text(),'To')]")).click();

		Thread.sleep(3000);

		driver.findElement(By.xpath("//input[@id='search_input']")).sendKeys(arrivalLocation);
		int i = 0;
		while (i < 3) {

			driver.findElement(By.xpath("//input[@id='search_input']")).sendKeys(Keys.DOWN);

			i++;
		}
		// explicit wait for the webElement to be available before performing action
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='search_input']")))
				.sendKeys(Keys.ENTER);

	}
	// choosing date from the calendar

	@And("I enter depature date>")
	public void i_enter_depature_date() throws InterruptedException {
		driver.findElement(By.xpath("//span[@class='calenderDepartSpan']")).click();

		while (!driver.findElement(By.xpath("//span[@class='dl-datepicker-month-1']")).getText().contains("April")) {

			driver.findElement(By.xpath("//span[normalize-space()='Next']")).click();

		}
		// logic to iterate through the calendar and picking the desire date
		List<WebElement> days = driver.findElements(By.xpath("//a[@aria-label='15 April 2023, Saturday']"));

		for (int i = 0; i < days.size(); i++) {

			if (days.get(i).getText().equals("15")) {

				days.get(i).click();
				break;

			}

		}

	}

	// logic to iterate through the calendar and picking the desire date
	@And("I enter return arrival date>")
	public void i_enter_return_arrival_date() {

		List<WebElement> day2 = driver.findElements(By.xpath("//a[@aria-label='27 April 2023, Thursday']"));

		for (int i = 0; i < day2.size(); i++) {

			if (day2.get(i).getText().equals("27")) {

				day2.get(i).click();
				break;

			}

		}

	}

	// adding number of passenger for adult
	@And("I enter adults as {string}")
	public void i_enter_adults_as(String numbAdult) {

		driver.findElement(By.xpath("//div[@class='paxCountDesc']")).click();
		int i = 0;
		while (i < 2) {
			driver.findElement(By.xpath(
					"/html[1]/body[1]/app-root[1]/app-home[1]/ngc-global-nav[1]/header[1]/div[1]/div[1]/ngc-book[1]/div[1]/div[1]/form[1]/div[2]/div[1]/div[1]/div[1]/div[4]/ngc-childpassenger[1]/div[1]/ul[1]/li[1]/div[2]/div[3]/a[1]"))
					.click();

			i++;
		}

	}

	// adding number of passenger for children
	@And("I enter children as {string}")
	public void i_enter_children_as(String numbChildren) {
		int j = 0;

		while (j < 2) {
			driver.findElement(By.xpath(
					"/html[1]/body[1]/app-root[1]/app-home[1]/ngc-global-nav[1]/header[1]/div[1]/div[1]/ngc-book[1]/div[1]/div[1]/form[1]/div[2]/div[1]/div[1]/div[1]/div[4]/ngc-childpassenger[1]/div[1]/ul[1]/li[3]/div[2]/div[3]/a[1]"))
					.click();

			j++;

		}
		driver.findElement(By.xpath("//a[@aria-label='passenger dropdown close']")).click();

	}

	// selecting cabinclass
	@And("I select cabin as {string}")
	public void i_select_cabin_as(String cabinClass) {

		driver.findElement(By.xpath("//a[@id='adv-search']")).click();

		driver.findElement(By.xpath(
				"//div[@class='col-lg-4 offset-lg-0 col-md-8 offset-md-2 meeting-code-wrapper best-fares-wrapper select-container select-container-down-md adv-search-item']//span[@role='combobox']"))
				.sendKeys(cabinClass);

	}

	@And("I click search")
	public void i_click_search() {

		driver.findElement(By.xpath("//button[@id='btnSubmit']")).click();

	}

	// assertion to verify the search page
	@Then("A search page is displayed")
	public void a_search_page_is_displayed() {

		WebElement bookAFlight = null;

		try {
			bookAFlight = driver.findElement(By.xpath("//h1[@class='advanced-search-heading']"));

		} catch (Exception exc) {

		}

		org.junit.Assert.assertNotEquals(bookAFlight, "the test got failed");

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='advanced-search-heading']")));
	}

	@After
	public void tearDown(Scenario scenario) {
		// before closing the browser take screenshot of the result page
		TakesScreenshot ts = (TakesScreenshot) driver;
		byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
		scenario.attach(screenshot, "image/png", "Screenshot attached");

		driver.close();

	}
}
