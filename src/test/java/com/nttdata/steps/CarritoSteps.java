package com.nttdata.steps;

import com.nttdata.page.CarritoPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CarritoSteps {
    private WebDriver driver;

    public CarritoSteps(WebDriver driver) {
        this.driver = driver;
    }
    public int getItemProductSize(){
        List<WebElement> items = this.driver.findElements(CarritoPage.itemsProduct);
        return items.size();
    }

    public String getNameProduct(){
        return this.driver.findElement(CarritoPage.nameProduct).getText();
    }
}
