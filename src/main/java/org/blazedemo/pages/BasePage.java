package org.blazedemo.pages;

import org.blazedemo.pages.components.NavigationBar;

abstract public class BasePage {

    public final NavigationBar navigationBar = new NavigationBar();

    abstract public BasePage navigate();
    static public boolean isPageLoaded() {
        return false;
    }
}
