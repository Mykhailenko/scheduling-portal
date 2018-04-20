/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package automaticuitests;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


/**
 * Chrome driver adapted to work with GWT
 */
public class GwtChromeDriver extends ChromeDriver {

    private static int REPEAT_LIMIT = 5;


    public static final int aBit = 2000; // 2s

    public static GwtChromeDriver createHeadless() {
        // chromium-browser --headless --disable-gpu --dump-dom https://trydev.activeeon.com
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--disable-gpu");
        return new GwtChromeDriver(chromeOptions);
    }

    public static GwtChromeDriver create() {
        // chromium-browser --headless --disable-gpu --dump-dom https://trydev.activeeon.com
        return new GwtChromeDriver();
    }

    private GwtChromeDriver() {
        super();
    }

    private GwtChromeDriver(ChromeOptions chromeOptions) {
        super(chromeOptions);
    }

    public void click(String tooltip) {
        repeatUtillGetOptional(() -> findBy(tooltip)).get().click();
    }

    public void openAndSelect(String comboboxName, String itemName){
        openCombobox(comboboxName);
        selectInOpenedCombobox(itemName);
    }

    public void typeInInput(String name, String text) {
        final WebElement webElement = repeatUtillGet(() -> findElementByName(name));
        webElement.clear();
        webElement.sendKeys(text);
    }



    public WebElement findElementByTagAndText(String tag, String text) {
        return repeatUtillGetOptional(() -> findElementsByTagName(tag).stream()
                .filter(webElement -> {
                    try{
                        return webElement.getText().contains(text);
                    }catch(Exception e){
                        return false;
                    }
                }).findFirst()).get();
    }

    protected void waitABit() {
        try {
            Thread.sleep(aBit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<WebElement> findBy(String tooltip) {
        return findElementsByTagName("div").stream()
                                           .filter(webElement -> tooltip.equals(webElement.getAttribute("aria-label")))
                                           .findFirst();
    }

    private void openCombobox(String name){
        repeatUtillGet(() -> findElementByXPath("//*[@name=\""+ name + "\"]/following-sibling::table")).click();
    }

    private WebElement repeatUtillGet(Callable<WebElement> callable) {
        WebElement result = null;
        for(int attempt = 0; attempt < REPEAT_LIMIT && result == null; ++attempt){
            try {
                result = callable.call();
            } catch (Exception e) {
            }
            if(result == null){
                waitABit();
            }

        }
        return result;
    }


    private Optional<WebElement> repeatUtillGetOptional(Callable<Optional<WebElement>> callable) {
        Optional result = Optional.empty();
        for(int attempt = 0; attempt < REPEAT_LIMIT && !result.isPresent(); ++attempt){
            try {
                result = callable.call();
            } catch (Exception e) {
            }
            if(!result.isPresent()){
                waitABit();
            }
        }
        return result;
    }

    private void selectInOpenedCombobox(String name){
        repeatUtillGetOptional(() -> findBy("presentation", name)).get().click();
    }

    private Optional<WebElement> findBy(String role, String text){
        return findElementsByTagName("div").stream()
                .filter(webElement -> {
                    try{
                        return role.equals(webElement.getAttribute("role"))
                                && text.equals(webElement.getText());
                    } catch (Exception e){
                        return false;
                    }
                })
                .findFirst();
    }

}
