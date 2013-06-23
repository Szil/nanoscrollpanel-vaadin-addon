package org.vaadin.hhe.nanoscrollpanel.gwt.client.shared;

import com.vaadin.shared.AbstractComponentState;

public class NanoScrollPanelState extends AbstractComponentState {

    private static final long serialVersionUID = 5807046133211687385L;
    
    public boolean preventPageScrolling = false;
    
    public boolean alwaysVisible = false;
    
    public int flashDelay = 1500;
    
}
