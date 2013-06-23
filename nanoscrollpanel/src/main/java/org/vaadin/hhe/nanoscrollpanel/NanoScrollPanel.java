package org.vaadin.hhe.nanoscrollpanel;

import org.vaadin.hhe.nanoscrollpanel.gwt.client.connector.NanoScrollClientRpc;
import org.vaadin.hhe.nanoscrollpanel.gwt.client.shared.NanoScrollPanelState;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractSingleComponentContainer;

@JavaScript({
    "jquery.min.js",
//    "http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.js", 
    "jquery.nanoscroller.min.js"
//    "http://jamesflorentino.github.io/nanoScrollerJS/javascripts/jquery.nanoscroller.js"
})
public class NanoScrollPanel extends AbstractSingleComponentContainer {

    private static final long serialVersionUID = -2168758555251706761L;
    
    @Override
    protected NanoScrollPanelState getState() {
        return (NanoScrollPanelState) super.getState();
    }
    
    public void setPreventPageScrolling(boolean preventPageScrolling) {
        getState().preventPageScrolling = preventPageScrolling;
    }
    
    public void setAlwaysVisible(boolean alwaysVisible) {
        getState().alwaysVisible = alwaysVisible;
    }
    
    public void flashScrollbar() {
        getRpcProxy(NanoScrollClientRpc.class).flashScrollbar();
    }
    
    public void setFlashDelay(int flashDelay) {
        getState().flashDelay = flashDelay;
    }
}
