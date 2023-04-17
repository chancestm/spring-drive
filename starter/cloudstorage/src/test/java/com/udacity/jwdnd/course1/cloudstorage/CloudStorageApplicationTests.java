package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doMockSignUp(String firstName, String lastName, String userName, String password) {
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys(lastName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

        /* Check that the sign up was successful.
        // You may have to modify the element "success-msg" and the sign-up
        // success message below depening on the rest of your code.
        */
        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
    }


    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doLogIn(String userName, String password) {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling redirecting users
     * back to the login page after a succesful sign up.
     * Read more about the requirement in the rubric:
     * https://review.udacity.com/#!/rubrics/2724/view
     */
    @Test
    public void testRedirection() {
        // Create a test account
        doMockSignUp("Redirection", "Test", "RT", "123");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginForm")));
        // Check if we have been redirected to the log in page.
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling bad URLs
     * gracefully, for example with a custom error page.
     * <p>
     * Read more about custom error pages at:
     * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
     */
    @Test
    public void testBadUrl() {
        // Create a test account
        doMockSignUp("URL", "Test", "testBadUrl", "123");
        doLogIn("testBadUrl", "123");

        // Try to access a random made-up URL.
        driver.get("http://localhost:" + this.port + "/some-random-page");
        Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
    }


    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling uploading large files (>1MB),
     * gracefully in your code.
     * <p>
     * Read more about file size limits here:
     * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
     */
    @Test
    public void testLargeUpload() {
        // Create a test account
        doMockSignUp("Large File", "Test", "LFT", "123");
        doLogIn("LFT", "123");

        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        String fileName = "upload5m.zip";

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
        WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
        fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

        WebElement uploadButton = driver.findElement(By.id("uploadButton"));
        uploadButton.click();
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Large File upload failed");
        }
        Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

    }

    @Test
    public void testAccessRestrictionForUnauthorizedUser() {
        //When unauthorized user try to access home page will be redirected to login page
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }

    @Test
    public void testHomePageAccess() {
        // Create a test account
        doMockSignUp("URL", "Test", "homePageTest", "123");
        doLogIn("homePageTest", "123");

        //check home page access
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Home", driver.getTitle());

        //logout
        driver.get("http://localhost:" + this.port + "/logout");

        //restrict home page access
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertNotEquals("Home", driver.getTitle());
    }

    @Test
    public void testCreateNote() {
        String noteTitleStr = "Test Note Title";
        String noteDescStr = "Test Note Desc";

        createNote(noteTitleStr, noteDescStr, "createNote");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
        List<WebElement> rows = driver.findElements(By.cssSelector("#userTable > tbody > tr"));

        boolean foundTitle = false;
        boolean foundDesc = false;

        for (WebElement row : rows) {
            if (row.findElement(By.cssSelector("td:nth-of-type(2)")).getText().equals(noteTitleStr)) {
                foundTitle = true;
                foundDesc = row.findElement(By.cssSelector("td:nth-of-type(3)")).getText().equals(noteDescStr);
                break;
            }
        }

        Assertions.assertTrue(foundTitle);
        Assertions.assertTrue(foundDesc);
    }

    @Test
    public void testEditNote() {
        String noteTitleStr = "Test Note Title";
        String noteDescStr = "Test Note Desc";

        createNote(noteTitleStr, noteDescStr, "testEditNote");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
        List<WebElement> rows = driver.findElements(By.cssSelector("#userTable > tbody > tr"));

        int id = Integer.parseInt(rows.get(0).findElement(By.cssSelector("td:nth-of-type(1) > a")).getAttribute("data"));

        WebElement noteEditBtn = rows.get(0).findElement(By.cssSelector("td:nth-of-type(1) > a"));
        noteEditBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement noteDesc = driver.findElement(By.id("note-description"));
         noteDesc.click();
        noteDesc.sendKeys(" Update");

        WebElement noteSaveBtn = driver.findElement(By.className("noteSaveBtn"));
        noteSaveBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        noteTab.click();

        boolean foundUpdate = false;

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
        rows = driver.findElements(By.cssSelector("#userTable > tbody > tr"));

        for (WebElement row : rows) {
            int dataId = Integer.parseInt(row.findElement(By.cssSelector("td:nth-of-type(1) > a")).getAttribute("data"));

            if(id == dataId) {
                foundUpdate = row.findElement(By.cssSelector("td:nth-of-type(3)")).getText().equals("Test Note Desc Update");
            }
        }

        Assertions.assertTrue(foundUpdate);
    }

    @Test
    public void deleteNoteTest() {
        String noteTitleStr = "Test Note Title";
        String noteDescStr = "Test Note Desc";

        createNote(noteTitleStr, noteDescStr, "deleteNoteTest");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
        List<WebElement> rows = driver.findElements(By.cssSelector("#userTable > tbody > tr"));

        int id = Integer.parseInt(rows.get(0).findElement(By.cssSelector("td:nth-of-type(1) > a")).getAttribute("data"));

        WebElement noteDeleteBtn = rows.get(0).findElement(By.className("noteDeleteBtn"));
        noteDeleteBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        noteTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
        rows = driver.findElements(By.cssSelector("#userTable > tbody > tr"));

        boolean deletedNoteExist = false;

        for (WebElement row : rows) {
            int dataId = Integer.parseInt(row.findElement(By.cssSelector("td:nth-of-type(1) > a")).getAttribute("data"));

            if(id == dataId) {
                deletedNoteExist = true;
            }
        }

        Assertions.assertFalse(deletedNoteExist);
    }

    @Test
    public void testCreateCredential() {
        String url = "http://localhost:8080/home";
        String username = "test";
        String password = "test1234";

        createCredential(url, username, password, "testCreateCredential");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
        List<WebElement> rows = driver.findElements(By.cssSelector("#credentialTable > tbody > tr"));

        boolean foundUrl = false;
        boolean foundUsername = false;
        boolean passwordEncrypted = false;

        for (WebElement row : rows) {
            if (row.findElement(By.cssSelector("td:nth-of-type(2)")).getText().equals(url)) {
                foundUrl = true;
                foundUsername = row.findElement(By.cssSelector("td:nth-of-type(3)")).getText().equals(username);
                passwordEncrypted = !row.findElement(By.cssSelector("td:nth-of-type(4)")).getText().equals(password);
                break;
            }
        }
        Assertions.assertTrue(foundUrl);
        Assertions.assertTrue(foundUsername);
        Assertions.assertTrue(passwordEncrypted);
    }

    @Test
    public void testEditCredential() {
        String url = "http://localhost:8080/home";
        String username = "test";
        String password = "test1234";

        createCredential(url, username, password, "testEditCredential");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
        List<WebElement> rows = driver.findElements(By.cssSelector("#credentialTable > tbody > tr"));

        int id = Integer.parseInt(rows.get(0).findElement(By.cssSelector("td:nth-of-type(1) > a")).getAttribute("data"));
        String oldEncyptedPassword = rows.get(0).findElement(By.cssSelector("td:nth-of-type(4)")).getText();

        WebElement editBtn = rows.get(0).findElement(By.className("credentialEditBtn"));
        editBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement passwordElem = driver.findElement(By.id("credential-password"));

        //test showing decrypted password
        Assertions.assertEquals(password, passwordElem.getAttribute("value"));

        passwordElem.click();
        passwordElem.sendKeys("567");

        WebElement credentialSaveBtn = driver.findElement(By.className("credentialSaveBtn"));
        credentialSaveBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement credentialTabElem = driver.findElement(By.id("nav-credentials-tab"));
        credentialTabElem.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
        rows = driver.findElements(By.cssSelector("#credentialTable > tbody > tr"));

        boolean passwordUpdated = false;

        for (WebElement row : rows) {
            int dataId = Integer.parseInt(rows.get(0).findElement(By.cssSelector("td:nth-of-type(1) > a")).getAttribute("data"));

            if (dataId == id) {
                String newEncryptedPassword = row.findElement(By.cssSelector("td:nth-of-type(4)")).getText();
                passwordUpdated = !newEncryptedPassword.equals(oldEncyptedPassword);
                break;
            }
        }
        Assertions.assertTrue(passwordUpdated);
    }

    @Test
    public void testDeleteCredential() {
        String url = "http://localhost:8080/home";
        String username = "test";
        String password = "test1234";

        createCredential(url, username, password, "testDeleteCredential");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
        List<WebElement> rows = driver.findElements(By.cssSelector("#credentialTable > tbody > tr"));

        int credentailToBeDeletedId = Integer.parseInt(rows.get(0).findElement(By.cssSelector("td:nth-of-type(1) > a")).getAttribute("data"));

        WebElement deleteBtn = rows.get(0).findElement(By.className("credentialDeleteBtn"));
        deleteBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
        credentialTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
        rows = driver.findElements(By.cssSelector("#credentialTable > tbody > tr"));

        boolean deletedCredentialExist = false;

        for (WebElement row : rows) {
            int dataId = Integer.parseInt(row.findElement(By.cssSelector("td:nth-of-type(1) > a")).getAttribute("data"));

            if(credentailToBeDeletedId == dataId) {
                deletedCredentialExist = true;
            }
        }

        Assertions.assertFalse(deletedCredentialExist);
    }
    private void createNote(String noteTileStr, String noteDescStr, String loginName) {
        doMockSignUp("URL", "Test", loginName, "123");
        doLogIn(loginName, "123");

        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

        driver.get("http://localhost:" + this.port + "/home");
        WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        noteTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNewNoteBtn")));
        WebElement newNoteBtn = driver.findElement(By.id("addNewNoteBtn"));
        newNoteBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitle = driver.findElement(By.id("note-title"));
        noteTitle.click();
        noteTitle.sendKeys(noteTileStr);

        WebElement noteDesc = driver.findElement(By.id("note-description"));
        noteDesc.click();
        noteDesc.sendKeys(noteDescStr);

        WebElement noteSaveBtn = driver.findElement(By.className("noteSaveBtn"));
        noteSaveBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        noteTab = driver.findElement(By.id("nav-notes-tab"));
        noteTab.click();
    }

    private void createCredential(String url, String username, String password, String loginName) {
        doMockSignUp("URL", "Test", loginName, "123");
        doLogIn(loginName, "123");

        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

        driver.get("http://localhost:" + this.port + "/home");
        WebElement noteTab = driver.findElement(By.id("nav-credentials-tab"));
        noteTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("addCredentialNoteBtn")));
        WebElement newNoteBtn = driver.findElement(By.className("addCredentialNoteBtn"));
        newNoteBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement urlElem = driver.findElement(By.id("credential-url"));
        urlElem.click();
        urlElem.sendKeys(url);

        WebElement usernameElem = driver.findElement(By.id("credential-username"));
        usernameElem.click();
        usernameElem.sendKeys(username);

        WebElement passwordElem = driver.findElement(By.id("credential-password"));
        passwordElem.click();
        passwordElem.sendKeys(password);

        WebElement credentialSaveBtn = driver.findElement(By.className("credentialSaveBtn"));
        credentialSaveBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        noteTab = driver.findElement(By.id("nav-credentials-tab"));
        noteTab.click();
    }



}
