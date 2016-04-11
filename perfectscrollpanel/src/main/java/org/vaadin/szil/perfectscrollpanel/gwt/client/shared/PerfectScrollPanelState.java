package org.vaadin.szil.perfectscrollpanel.gwt.client.shared;

import com.vaadin.shared.AbstractComponentState;

public class PerfectScrollPanelState extends AbstractComponentState {

    private static final long serialVersionUID = 5807046133211687385L;
    
    public boolean preventPageScrolling = false;
    
    public boolean alwaysVisible = false;
    
    public int flashDelay = 1500;
    
    public boolean iOSNativeScrolling = false;
    
    public boolean disableResize = false;
    
}
