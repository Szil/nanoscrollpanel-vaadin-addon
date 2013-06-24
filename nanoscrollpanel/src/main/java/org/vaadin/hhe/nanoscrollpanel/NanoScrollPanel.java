package org.vaadin.hhe.nanoscrollpanel;

import org.vaadin.hhe.nanoscrollpanel.gwt.client.connector.NanoScrollClientRpc;
import org.vaadin.hhe.nanoscrollpanel.gwt.client.shared.NanoScrollPanelState;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractSingleComponentContainer;
import com.vaadin.ui.Component;

@JavaScript({
    "jquery.min.js",
//    "http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.js", 
    "jquery.nanoscroller.min.js"
//    "http://jamesflorentino.github.io/nanoScrollerJS/javascripts/jquery.nanoscroller.js"
})
public class NanoScrollPanel extends AbstractSingleComponentContainer {

    private static final long serialVersionUID = -2168758555251706761L;
    
    private final NanoScrollClientRpc clientRpc = getRpcProxy(NanoScrollClientRpc.class);
    
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
    
    public void setIOSNativeScrolling(boolean iOSNativeScrolling) {
        getState().iOSNativeScrolling = iOSNativeScrolling;
    }
    
    public void setDisableResize(boolean disableResize) {
        getState().disableResize = disableResize;
    }
    
    public void flashScrollbar() {
        clientRpc.flashScrollbar();
    }
    
    public void setFlashDelay(int flashDelay) {
        getState().flashDelay = flashDelay;
    }
    
    public void scrollTop(int offset) {
        clientRpc.scrollTop(offset);
    }
    
    public void scrollToTop() {
        clientRpc.scrollTop(1);
    }
    
    public void scrollBottom(int offset) {
        clientRpc.scrollBottom(offset);
    }
    
    public void scrollToBottom() {
        clientRpc.scrollBottom(1);
    }
    
    public void scrollTo(Component target) {
        if(target.getId()==null) return;
        
        boolean foundParent = false;
        Component parent = target.getParent();
        while(parent!=null && !foundParent) {
            if(parent==this) 
                foundParent = true;
            else
                parent = parent.getParent();
        }
        if(!foundParent) return;
        
        scrollTo(target.getId());
    }
    
    public void scrollTo(String targetId) {
        clientRpc.scrollTo(targetId);
    }
}
