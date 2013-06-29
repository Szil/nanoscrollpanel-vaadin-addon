package org.vaadin.hhe.nanoscrollpanel;

import java.lang.reflect.Method;

import org.vaadin.hhe.nanoscrollpanel.gwt.client.connector.NanoScrollClientRpc;
import org.vaadin.hhe.nanoscrollpanel.gwt.client.connector.NanoScrollServerRpc;
import org.vaadin.hhe.nanoscrollpanel.gwt.client.shared.NanoEventId;
import org.vaadin.hhe.nanoscrollpanel.gwt.client.shared.NanoScrollPanelState;

import com.vaadin.annotations.JavaScript;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.shared.EventId;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.AbstractSingleComponentContainer;
import com.vaadin.ui.Component;
import com.vaadin.util.ReflectTools;

/**
 * {@link NanoScrollPanel} wraps nanoScroller.js (a jQuery plugin) that offers a simplistic way of 
 * implementing Mac OS X Lion-styled scrollbars for your application.
 * 
 * @author Henry.He
 *
 */
@JavaScript({
    "jquery.min.js",
//    "http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.js", 
    "jquery.nanoscroller.min.js"
//    "http://jamesflorentino.github.io/nanoScrollerJS/javascripts/jquery.nanoscroller.js"
})
public class NanoScrollPanel extends AbstractSingleComponentContainer {

    private static final long serialVersionUID = -2168758555251706761L;
    
    private final NanoScrollClientRpc clientRpc = getRpcProxy(NanoScrollClientRpc.class);
    
    private final NanoScrollServerRpc serverRpc = new NanoScrollServerRpc() {

        private static final long serialVersionUID = 4068997274300819669L;

        @Override
        public void scrollEnd() {
            NanoScrollPanel.this.fireEvent(new NanoScrollEvent("end", NanoScrollPanel.this));
        }

        @Override
        public void scrollTop() {
            NanoScrollPanel.this.fireEvent(new NanoScrollEvent("top", NanoScrollPanel.this));
        }

        @Override
        public void click(MouseEventDetails mouseDetails) {
            fireEvent(new ClickEvent(NanoScrollPanel.this, mouseDetails));
        }
        
    };
    
    public NanoScrollPanel() {
        registerRpc(serverRpc);
    }
    
    @Override
    protected NanoScrollPanelState getState() {
        return (NanoScrollPanelState) super.getState();
    }
    
    /**
     * a setting to prevent the rest of the page being scrolled when user scrolls the `.content` element.
     * @param preventPageScrolling
     */
    public void setPreventPageScrolling(boolean preventPageScrolling) {
        getState().preventPageScrolling = preventPageScrolling;
    }
    
    /**
     * a setting to make the scrollbar always visible.
     * @param alwaysVisible
     */
    public void setAlwaysVisible(boolean alwaysVisible) {
        getState().alwaysVisible = alwaysVisible;
    }
    
    /**
     * a setting to enable native scrolling in iOS devices.
     * @param iOSNativeScrolling
     */
    public void setIOSNativeScrolling(boolean iOSNativeScrolling) {
        getState().iOSNativeScrolling = iOSNativeScrolling;
    }
    
    /**
     * a setting to make the scrollbar always visible.
     * @param disableResize
     */
    public void setDisableResize(boolean disableResize) {
        getState().disableResize = disableResize;
    }
    
    /**
     * To flash the scrollbar gadget for an amount of time defined in plugin settings (defaults to 1,5s).
     * Useful if you want to show the user (e.g. on pageload) that there is more content waiting for him.
     */
    public void flashScrollbar() {
        clientRpc.flashScrollbar();
    }
    
    /**
     * a default timeout for the `flash()` method.
     * @param flashDelay
     */
    public void setFlashDelay(int flashDelay) {
        getState().flashDelay = flashDelay;
    }
    
    /**
     * Scroll at the top with an offset value
     * @param offset
     */
    public void scrollTop(int offset) {
        clientRpc.scrollTop(offset);
    }
    
    /**
     * Scroll to top
     */
    public void scrollToTop() {
        clientRpc.scrollTop(0);
    }
    
    /**
     * Scroll at the bottom with an offset value
     * @param offset
     */
    public void scrollBottom(int offset) {
        clientRpc.scrollBottom(offset);
    }
    
    /**
     * Scroll to bottom
     */
    public void scrollToBottom() {
        clientRpc.scrollBottom(0);
    }
    
    /**
     * Scroll to a component
     * @param target
     */
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
    
    /**
     * Scroll to a component with specified id
     * @param targetId
     */
    public void scrollTo(String targetId) {
        clientRpc.scrollTo(targetId);
    }
    
    /**
     * Update scrolller when content scrollHeight changes. {@code GwtNanoScrollPanel} can auto update scroller height 
     * when the scrollHeight change was caused by child element directly added into content div. However, if the 
     * scrollHeight change was caused by child element indirectly added into content div, {@code GwtNanoScrollPanel} 
     * will not update scroller height. User need to explicitly call this method to update the scroller height.
     */
    public void updateScroller() {
        clientRpc.updateScroller();
    }
    
    /**
     * Add nano scroll listener
     * @param listener
     */
    public void addNanoScrollListener(NanoScrollPanelListener listener) {
        addListener(NanoEventId.NANO_SCROLL, NanoScrollEvent.class, 
                listener, NanoScrollPanelListener.scrollMethod);
    }
    
    /**
     * Remove nano scroll listener
     * @param listener
     */
    public void removeNanoScrollListener(NanoScrollPanelListener listener) {
        removeListener(NanoEventId.NANO_SCROLL, NanoScrollEvent.class, listener);
    }
    
    /**
     * Add a click listener to the Panel. The listener is called whenever the
     * user clicks inside the Panel. Also when the click targets a component
     * inside the Panel, provided the targeted component does not prevent the
     * click event from propagating.
     * 
     * Use {@link #removeListener(ClickListener)} to remove the listener.
     * 
     * @param listener
     *            The listener to add
     */
    public void addClickListener(ClickListener listener) {
        addListener(EventId.CLICK_EVENT_IDENTIFIER, ClickEvent.class, 
                listener, ClickListener.clickMethod);
    }
    
    /**
     * Remove a click listener from the Panel. The listener should earlier have
     * been added using {@link #addListener(ClickListener)}.
     * 
     * @param listener
     *            The listener to remove
     */
    public void removeClickListener(ClickListener listener) {
        removeListener(EventId.CLICK_EVENT_IDENTIFIER, ClickEvent.class, listener);
    }
    
    /**
     * Panel scroll event. Event was fired when scroll to the top or end.
     */
    public static class NanoScrollEvent extends Component.Event {

        private static final long serialVersionUID = 5910391742489280087L;

        private String type;
        
        public NanoScrollEvent(String type, Component source) {
            super(source);
            this.type = type;
        }
        
        public String getType() {
            return type;
        }
    }
    
    /**
     * Panel scroll listener
     */
    public interface NanoScrollPanelListener {
        public static final Method scrollMethod = ReflectTools.findMethod(
                NanoScrollPanelListener.class, "onScroll", NanoScrollEvent.class);
        
        void onScroll(NanoScrollEvent event);
    }
}
