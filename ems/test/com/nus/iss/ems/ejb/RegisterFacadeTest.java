package com.nus.iss.ems.ejb;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Milan
 */
public class RegisterFacadeTest {

    private static EJBContainer container;

    public RegisterFacadeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(EJBContainer.MODULES, new File("build/jar"));
        container = EJBContainer.createEJBContainer(properties);
        
        System.out.println("Opening the container");
    }

    @AfterClass
    public static void tearDownClass() {
        container.close();
        System.out.println("Closing the container");
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of registerUser method, of class RegisterFacade.
     */
    @Test
    public void testRegisterUser() throws Exception {
        System.out.println("registerUser");
        String userID = "";
        String password = "";
        RegisterFacade instance = (RegisterFacade) container.getContext().lookup("java:global/classes/RegisterFacade");
        String expResult = "User ID not found";
        String result = instance.registerUser(userID, password);
        assertEquals(expResult, result);
        
       
    }

}
