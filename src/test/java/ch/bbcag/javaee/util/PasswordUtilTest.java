package ch.bbcag.javaee.util;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class PasswordUtilTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                         .addAsManifestResource(
                                 EmptyAsset.INSTANCE,
                                 "beans.xml").addAsResource(new File("WebContent/WEB-INF/config.properties"))
                         .addClasses
                                 (ConfigHandler.class, LogHelper.class, EnumConfig.class, PasswordUtil.class);
    }

    @Test
    public void should_hash() {
        String password = "Welcome$17";
        assertNotNull("Token should not be null", PasswordUtil.hash(password));
        assertNotNull("Token should not be null", PasswordUtil.hash(password.toCharArray()));
    }

    @Test
    public void should_validate() {
        String password = "Welcome$17";
        String token = PasswordUtil.hash(password);
        assertTrue("Token should be valid", PasswordUtil.isValid(password, token));
    }

}
