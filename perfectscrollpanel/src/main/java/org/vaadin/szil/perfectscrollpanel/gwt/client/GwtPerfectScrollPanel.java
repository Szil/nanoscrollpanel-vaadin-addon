package org.vaadin.szil.perfectscrollpanel.gwt.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.List;

public class GwtPerfectScrollPanel extends SimplePanel {
    
    public final Element contentNode = DOM.createDiv();
    
    private final JSONObject scrollerOptions = new JSONObject();

    private List<GwtPerfectScrollListener> listeners = new ArrayList<GwtPerfectScrollListener>();
    
    private String id;

    public GwtPerfectScrollPanel() {
        this(GUID.get(15));
    }

    public GwtPerfectScrollPanel(String id) {
        getElement().setId(id);
        this.id = id;
        getElement().addClassName("perfect-container");
        contentNode.addClassName("content");
        DOM.appendChild(getElement(), contentNode);
    }
    
    private void setBooleanOption(String key, boolean value) {
        scrollerOptions.put(key, JSONBoolean.getInstance(value));
        updateScroller();
    }
    
    private void setIntegerOption(String key, int value) {
        scrollerOptions.put(key, new JSONNumber(value));
        updateScroller();
    }
    
    private void setStringOption(String key, String value) {
        scrollerOptions.put(key, new JSONString(value));
        updateScroller();
    }
    
    private void setElementOption(String key, Widget widget) {
        scrollerOptions.put(key, JSONParser.parseStrict("$(#"+widget.getElement().getId()+")"));
        updateScroller();
    }
    
    public void setFlashDelay(int flashDelay) {
        setIntegerOption(GwtPerfectScrollOption.FLASH_DELAY.toString(), flashDelay);
    }
    
    public void setPreventPageScrolling(boolean isPreventPageScrolling) {
        setBooleanOption(GwtPerfectScrollOption.PREVENT_PAGE_SCROLLING.toString(), isPreventPageScrolling);
    }
    
    public void setAlwaysVisible(boolean isAlwaysVisible) {
        setBooleanOption(GwtPerfectScrollOption.ALWAYS_VISIBLE.toString(), isAlwaysVisible);
    }
    
    public void setIOSNativeScrolling(boolean iOSNativeScrolling) {
        setBooleanOption(GwtPerfectScrollOption.IOS_NATIVE_SCROLLING.toString(), iOSNativeScrolling);
    }
    
    public void setDisableResize(boolean disableResize) {
        setBooleanOption(GwtPerfectScrollOption.DISABLE_RESIZE.toString(), disableResize);
    }
    
    public void setPanelClassName(String className) {
        setStringOption(GwtPerfectScrollOption.PANEL_CLASS.toString(), className);
    }
    
    public void setSliderClassName(String className) {
        setStringOption(GwtPerfectScrollOption.SLIDER_CLASS.toString(), className);
    }
    
    public void setContentClassName(String className) {
        setStringOption(GwtPerfectScrollOption.CONTENT_CLASS.toString(), className);
    }
    
    @Override
    protected Element getContainerElement() {
        return contentNode;
    }
    
    @Override
    public void onLoad() {
        Scheduler.get().scheduleFinally(new ScheduledCommand() {
            @Override
            public void execute() {
                nativeBuildScroller(GwtPerfectScrollPanel.this, id,
                        scrollerOptions.getJavaScriptObject() );
            }
        });
    }
    
    public void updateScroller() {
        if(isAttached()) nativeUpdateScroller(id);
    }
    
    @Override
    public void setWidget(Widget w) {
        super.setWidget(w);
        updateScroller();
    }
    
    @Override
    public void onUnload() {
        destroyScroller();
    }

    public void addNanoScrollListener(GwtPerfectScrollListener l) {
        listeners.add(l);
    }

    public void removeNanoScrollListener(GwtPerfectScrollListener l) {
        listeners.remove(l);
    }
    
    private void onScrollEnd(Event e) {
        GwtPerfectScrollEvent nanoEvent = new GwtPerfectScrollEvent(e, this);
        for (GwtPerfectScrollListener l : listeners) {
            l.onScrollEnd(nanoEvent);
        }
    }
    
    private void onScrollTop(Event e) {
        GwtPerfectScrollEvent nanoEvent = new GwtPerfectScrollEvent(e, this);
        for (GwtPerfectScrollListener l : listeners) {
            l.onScrollTop(nanoEvent);
        }
    }
    
    public void setFocus(boolean focus) {
        if (focus) {
            getContainerElement().focus();
        } else {
            getContainerElement().blur();
        }
    }
    
//    @Override
//    public void onBrowserEvent(Event event) {
//        super.onBrowserEvent(event);
//        final int type = DOM.eventGetType(event);
//        Logger l = Logger.getLogger("test");
//        if (type == Event.ONKEYDOWN) {
//            l.log(Level.SEVERE, "key down");
//        } else if( type == Event.ONKEYUP) {
//            l.log(Level.SEVERE, "key up");
//        }
//    }
    
    @Override
    public void setHeight(String height) {
        super.setHeight(height);
        nativeTriggerEvent(id, "heightChange");
    }
    
    @Override
    public void setWidth(String width) {
        super.setWidth(width);
        nativeTriggerEvent(id, "widthChange");
    }
    
    public void flashScrollbar() {
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
                nativeFlashScrollbar(id);
            }
        });
    }
    
    public void scrollTop(int offset) {
        nativeScrollTop(id, offset);
    }
    
    public void scrollBottom(int offset) {
        nativeScrollBottom(id, offset);
    }
    
    public void scrollTo(String widgetId) {
        nativeScrollTo(id, widgetId);
    }
    
    public void destroyScroller() {
        nativeFlashScrollbar(id);
    }
    
    /*
     * JSNI methods 
     */
    // $wnd.$('#'+id).children('.pane').css("display", "block");
    private native void nativeBuildScroller(GwtPerfectScrollPanel panel, String id, JavaScriptObject options) /*-{
        var container = $doc.getElementById(id);
        $wnd.Ps.initialize(container);
    }-*/;
    
    private native void nativeUpdateScroller(String id) /*-{
    }-*/;
    
    private native void nativeDestroyScroller(String id) /*-{
    }-*/;
    
    private native void nativeTriggerEvent(String id, String eventType) /*-{
    }-*/;
    
    private native void nativeFlashScrollbar(String id) /*-{
    }-*/;
    
    private native void nativeScrollTop(String id, int offset) /*-{
    }-*/;
    
    private native void nativeScrollBottom(String id, int offset) /*-{
    }-*/;
    
    private native void nativeScrollTo(String id, String widgetId) /*-{
    }-*/;
}
