package ch.bbcag.javaee.util;

import ch.bbcag.javaee.model.User;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.archive.importer.MavenImporter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.net.URL;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class LoginTest {

    @Drone
    private WebDriver browser;

    @ArquillianResource
    private URL deploymentURL;

    @FindBy(css = "input[type=email]")
    private WebElement txtEmail;

    @FindBy(css = "input[type=password]")
    private WebElement txtPassword;

    @FindBy(css = "input[type=submit]")
    private WebElement btnSignIn;

    private User user;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(MavenImporter.class)
                         .loadPomFromFile("pom.xml")
                         .importBuildOutput()
                         .as(WebArchive.class);
    }

    @Before
    public void setup() {
        // This user needs to be registered manually before the tests are run
        // Can't be automated at the moment
        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("Bbc");
        user.setName("Max Mustermann");
    }

    @Test
    public void test_signin_success() throws Exception {
        browser.navigate().to(deploymentURL + "signin.jsf");
        txtEmail.sendKeys(user.getEmail());
        txtPassword.sendKeys(user.getPassword());
        btnSignIn.click();
        Thread.sleep(1000); // Wait for page to load
        assertTrue(browser.getPageSource().contains("You're swimming") || browser.getPageSource().contains("Du " +
                                                                                                           "schwimmst"));
    }

    @Test
    public void test_signin_fail() throws Exception {
        browser.navigate().to(deploymentURL + "signin.jsf");
        txtEmail.sendKeys(user.getEmail());
        txtPassword.sendKeys(user.getPassword());
        btnSignIn.click();
        Thread.sleep(1000); // Wait for page to load
        assertTrue(browser.getCurrentUrl().contains("signin.jsf"));
    }


}
