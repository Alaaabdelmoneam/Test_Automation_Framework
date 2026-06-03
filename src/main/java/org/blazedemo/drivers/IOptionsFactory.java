package org.blazedemo.drivers;

import org.openqa.selenium.remote.AbstractDriverOptions;

public interface IOptionsFactory <T extends AbstractDriverOptions<?>>{
    T createOptions();
}
