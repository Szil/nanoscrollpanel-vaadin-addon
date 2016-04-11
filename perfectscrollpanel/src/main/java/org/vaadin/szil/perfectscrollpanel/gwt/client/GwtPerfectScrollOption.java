package org.vaadin.szil.perfectscrollpanel.gwt.client;

public enum GwtPerfectScrollOption {
    /**
     * A classname for scrollbar track element. If you change this setting, 
     * you also have to change it in the plugin's CSS file.
     * Default: 'pane'
     */
    PANEL_CLASS("paneClass"),
    
    /**
     * A classname for scrollbar thumb element. If you change this setting, 
     * you also have to change it in the plugin's CSS file.
     * Default: 'slider'
     */
    SLIDER_CLASS("sliderClass"),
    
    /**
     * A classname for your content div. If you change this setting, you also 
     * have to change it in the plugin's CSS file.
     * Default: 'content'
     */
    CONTENT_CLASS("contentClass"),
    
    /**
     * Enable iOS native scrolling
     */
    IOS_NATIVE_SCROLLING("iOSNativeScrolling"),
    
    /**
     * Sets the minimum height of the slider element
     * Default: 20
     */
    SLIDER_MIN_HEIGHT("sliderMinHeight"),
    
    /**
     * Sets the maximum height of the slider element.
     * Default: null
     */
    SLIDER_MAX_HEIGHT("sliderMaxHeight"),
    
    /**
     * Set to true to prevent page scrolling when top or bottom inside the content div is reached.
     * Default: false
     */
    PREVENT_PAGE_SCROLLING("preventPageScrolling"),
    
    /**
     * Set to true to disable the resize from nanoscroller. Useful if you want total control of the 
     * resize event. If you set this option to true remember to call the reset method so that the 
     * scroll don't have strange behavior.
     * Default: false
     */
    DISABLE_RESIZE("disableResize"),
    
    /**
     * a setting to make the scrollbar always visible.
     */
    ALWAYS_VISIBLE("alwaysVisible"),
    
    /**
     * Use this setting to specify the scrollbar hide delay in milliseconds 
     * if you have enabled the flash option.
     * Default: 1500
     */
    FLASH_DELAY("flashDelay");
    
    private String optionName;

    GwtPerfectScrollOption(String name) {
        this.optionName = name;
    }

    @Override
    public String toString() {
        return optionName;
    }

}
