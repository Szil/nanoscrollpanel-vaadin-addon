package org.vaadin.szil.perfectscrollpanel;

import com.vaadin.annotations.JavaScript;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.shared.EventId;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.AbstractSingleComponentContainer;
import com.vaadin.ui.Component;
import com.vaadin.util.ReflectTools;
import org.vaadin.szil.perfectscrollpanel.gwt.client.connector.PerfectScrollClientRpc;
import org.vaadin.szil.perfectscrollpanel.gwt.client.connector.PerfectScrollServerRpc;
import org.vaadin.szil.perfectscrollpanel.gwt.client.shared.PerfectEventId;
import org.vaadin.szil.perfectscrollpanel.gwt.client.shared.PerfectScrollPanelState;

import java.lang.reflect.Method;

/**
 * {@link PerfectScrollPanel} wraps perfect-scrollbar a Minimalistic but perfect custom scrollbar plugin to work with Vaadin.
 * This addon used the nanoscrollpanel Vaadin addon as a starting point.
 *
 * @author Henry.He (original author)
 * @author Szilank
 * @see <a href="https://github.com/hekailiang/nanoscrollpanel-vaadin-addon">Github: nanoscrollpanel-vaadin-addon</a>
 */
/*@JavaScript({
    "jquery.min.js",
//    "http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.js", 
    "jquery.nanoscroller.min.js"
//    "http://jamesflorentino.github.io/nanoScrollerJS/javascripts/jquery.nanoscroller.js"
})*/
@JavaScript("perfect-scrollbar.min.js")
public class PerfectScrollPanel extends AbstractSingleComponentContainer {


    private final PerfectScrollClientRpc clientRpc = getRpcProxy(PerfectScrollClientRpc.class);

    private final PerfectScrollServerRpc serverRpc = new PerfectScrollServerRpc() {


        @Override
        public void scrollEnd() {
            PerfectScrollPanel.this.fireEvent(new PerfectScrollEvent("end", PerfectScrollPanel.this));
        }

        @Override
        public void scrollTop() {
            PerfectScrollPanel.this.fireEvent(new PerfectScrollEvent("top", PerfectScrollPanel.this));
        }

        @Override
        public void click(MouseEventDetails mouseDetails) {
            fireEvent(new ClickEvent(PerfectScrollPanel.this, mouseDetails));
        }

    };

    public PerfectScrollPanel() {
        registerRpc(serverRpc);
    }

    @Override
    protected PerfectScrollPanelState getState() {
        return (PerfectScrollPanelState) super.getState();
    }

    /**
     * a setting to prevent the rest of the page being scrolled when user scrolls the `.content` element.
     *
     */
    public void setPreventPageScrolling(boolean preventPageScrolling) {
        getState().preventPageScrolling = preventPageScrolling;
    }

    /**
     * a setting to make the scrollbar always visible.
     *
     */
    public void setAlwaysVisible(boolean alwaysVisible) {
        getState().alwaysVisible = alwaysVisible;
    }

    /**
     * a setting to enable native scrolling in iOS devices.
     *
     */
    public void setIOSNativeScrolling(boolean iOSNativeScrolling) {
        getState().iOSNativeScrolling = iOSNativeScrolling;
    }

    /**
     * a setting to make the scrollbar always visible.
     *
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
     *
     */
    public void setFlashDelay(int flashDelay) {
        getState().flashDelay = flashDelay;
    }

    /**
     * Scroll at the top with an offset value
     *
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
     *
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
     *
     */
    public void scrollTo(Component target) {
        if (target.getId() == null) return;

        boolean foundParent = false;
        Component parent = target.getParent();
        while (parent != null && !foundParent) {
            if (parent == this)
                foundParent = true;
            else
                parent = parent.getParent();
        }
        if (!foundParent) return;

        scrollTo(target.getId());
    }

    /**
     * Scroll to a component with specified id
     *
     */
    public void scrollTo(String targetId) {
        clientRpc.scrollTo(targetId);
    }

    /**
     * Update scrolller when content scrollHeight changes. {@code GwtPerfectScrollPanel} can auto update scroller height
     * when the scrollHeight change was caused by child element directly added into content div. However, if the
     * scrollHeight change was caused by child element indirectly added into content div, {@code GwtPerfectScrollPanel}
     * will not update scroller height. User need to explicitly call this method to update the scroller height.
     */
    public void updateScroller() {
        clientRpc.updateScroller();
    }

    /**
     * Add nano scroll listener
     *
     */
    public void addNanoScrollListener(PerfectScrollPanelListener listener) {
        addListener(PerfectEventId.NANO_SCROLL, PerfectScrollEvent.class,
                listener, PerfectScrollPanelListener.scrollMethod);
    }

    /**
     * Remove nano scroll listener
     *
     */
    public void removeNanoScrollListener(PerfectScrollPanelListener listener) {
        removeListener(PerfectEventId.NANO_SCROLL, PerfectScrollEvent.class, listener);
    }

    /**
     * Add a click listener to the Panel. The listener is called whenever the
     * user clicks inside the Panel. Also when the click targets a component
     * inside the Panel, provided the targeted component does not prevent the
     * click event from propagating.
     * <p>
     * Use {@link #removeClickListener(ClickListener)} to remove the listener.
     *
     * @param listener The listener to add
     */
    public void addClickListener(ClickListener listener) {
        addListener(EventId.CLICK_EVENT_IDENTIFIER, ClickEvent.class,
                listener, ClickListener.clickMethod);
    }

    /**
     * Remove a click listener from the Panel. The listener should earlier have
     * been added using {@link #addClickListener(ClickListener)}.
     *
     * @param listener The listener to remove
     */
    public void removeClickListener(ClickListener listener) {
        removeListener(EventId.CLICK_EVENT_IDENTIFIER, ClickEvent.class, listener);
    }

    /**
     * Panel scroll event. Event was fired when scroll to the top or end.
     */
    public static class PerfectScrollEvent extends Component.Event {

        private static final long serialVersionUID = 5910391742489280087L;

        private String type;

        public PerfectScrollEvent(String type, Component source) {
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
    public interface PerfectScrollPanelListener {
        Method scrollMethod = ReflectTools.findMethod(
                PerfectScrollPanelListener.class, "onScroll", PerfectScrollEvent.class);

        void onScroll(PerfectScrollEvent event);
    }
}
