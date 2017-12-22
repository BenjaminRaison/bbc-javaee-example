package ch.bbcag.javaee.util;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.archive.importer.MavenImporter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.net.URL;

import static org.junit.Assert.assertNotEquals;

@RunWith(Arquillian.class)
public class LocaleTest {

    @Drone
    private WebDriver browser;
    @ArquillianResource
    private URL       deploymentURL;

    @FindBy(id = "dropdown-lang-button")
    private WebElement btnDropdown;

    @FindBy(id = "dropdown-lang")
    private WebElement dropdown;

    @FindBy(id = "dropdown-lang-mobile-button")
    private WebElement btnDropdownMobile;

    @FindBy(id = "dropdown-lang-mobile")
    private WebElement dropdownMobile;

    @FindBy(id = "signin")
    private WebElement linkSignin;

    @FindBy(id = "signin-mobile")
    private WebElement linkSigninMobile;

    @FindBy(className = "button-collapse")
    private WebElement hamburger;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(MavenImporter.class)
                         .loadPomFromFile("pom.xml")
                         .importBuildOutput()
                         .as(WebArchive.class);
    }

    @Test
    public void test_locale_changes_main() throws Exception {
        browser.manage().window().fullscreen();
        browser.navigate().to(deploymentURL.toExternalForm() + "index.jsf");

        String signinText = linkSignin.getText();
        int elementNum = 0;
        if (signinText.equals("Sign in")) {
            elementNum = 1;
        }
        btnDropdown.click();
        Thread.sleep(1000);
        dropdown.findElement(By.cssSelector("a:nth-child(" + elementNum + ")")).click();
        assertNotEquals(signinText, linkSignin.getText());
    }

    @Test
    public void test_locale_changes_mobile() throws Exception {
        browser.manage().window().setSize(new Dimension(412, 732));
        browser.navigate().to(deploymentURL.toExternalForm() + "index.jsf");

        String signinText = linkSigninMobile.getText();
        int elementNum = 0;
        if (signinText.equals("Sign in")) {
            elementNum = 1;
        }
        hamburger.click();
        Thread.sleep(1000);
        btnDropdownMobile.click();
        dropdownMobile.findElements(By.tagName("a")).get(elementNum).click();
        assertNotEquals(signinText, linkSigninMobile.getText());
    }

}
