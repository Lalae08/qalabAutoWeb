package com.nttdata.stepsdefinitions;

import com.nttdata.steps.CarritoSteps;
import com.nttdata.steps.InventorySteps;
import com.nttdata.steps.LoginSteps;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginStepDefs {
    private WebDriver driver;
    private Scenario scenario;

    private InventorySteps inventorySteps(WebDriver driver){
        return new InventorySteps(driver);
    }

    private CarritoSteps carritoSteps(WebDriver driver){
        return new CarritoSteps(driver);
    }

    @Before
    public void setUp(Scenario scenario){
        //setUp
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        //crear el driver
        driver = new ChromeDriver();
        //max
        driver.manage().window().maximize();
        this.scenario = scenario;
    }

    //@Before(order = 1)
    //public void setScenario(Scenario scenario){
        //this.scenario = scenario;
    //}

    @After
    public void quitDriver(){
        driver.quit();
    }

    @Dado("que me encuentro en la página de login de Saucedemo")
    public void que_me_encuentro_en_la_página_de_login_de_sacedemo() {
        driver.get("https://www.saucedemo.com/");
        screenShot();
    }
    @Cuando("inicio sesión con las credenciales usuario: {string} y contraseña: {string}")
    public void inicio_sesión_con_las_credenciales_usuario_y_contraseña(String user, String password) {
        LoginSteps loginSteps = new LoginSteps(driver);
        loginSteps.typeUser(user);
        loginSteps.typePassword(password);
        loginSteps.login();
        screenShot();
    }
    @Entonces("valido que debería aparecer el título de {string}")
    public void valido_que_debería_aparecer_el_título_de(String expectedTitle) {
        String title =  inventorySteps(driver).getTitle();
        Assertions.assertEquals(expectedTitle, title);
    }
    @Y("también valido que al menos exista un item")
    public void también_valido_que_al_menos_exista_un_item() {
        int itemsListSize = inventorySteps(driver).getItemSize();
        screenShot();
        Assertions.assertTrue(itemsListSize > 0, "La cantidad de items es: " + itemsListSize);
    }

    @Y("agrego un item al carrito de compras")
    public void agrego_un_item_al_carrito_de_compras() {
        inventorySteps(driver).addCarrito();
        screenShot();
    }
    @Y("reviso que el carrito de compras tenga un item agregado")
    public void reviso_que_el_carrito_de_compras_tenga_un_item_agregado() {
        inventorySteps(driver).verCarrito();
        int itemsCarrito = carritoSteps(driver).getItemProductSize();
        screenShot();
        Assertions.assertTrue(itemsCarrito > 0, "La cantidad de productos agregados es:" + itemsCarrito);
    }
    @Y("valido que el item agregado se llame {string}")
    public void valido_que_el_item_agregado_se_llame(String expectedNameProduct) {
        String nameProduct = carritoSteps(driver).getNameProduct();
        Assertions.assertEquals(expectedNameProduct, nameProduct);
    }
    public void screenShot(){
        //Se castea y se toma el screenshot en bytes
        byte[] evidencia = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        //(screenshot, formato que se mostrará en el reporte, nombre de la imagen)
        this.scenario.attach(evidencia, "image/png", "evidencias");
    }
}
