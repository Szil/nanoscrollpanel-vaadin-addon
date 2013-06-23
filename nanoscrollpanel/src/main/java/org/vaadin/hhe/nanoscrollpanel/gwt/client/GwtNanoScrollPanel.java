package org.vaadin.hhe.nanoscrollpanel.gwt.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class GwtNanoScrollPanel extends SimplePanel {
    
    public final Element contentNode = DOM.createDiv();
    
    private final JSONObject scrollerOptions = new JSONObject();
    
    private List<NanoScrollListener> listeners = new ArrayList<NanoScrollListener>();
    
    private String id;
    
    public GwtNanoScrollPanel() {
        this(GUID.get(15));
    }
    
    public GwtNanoScrollPanel(String id) {
        getElement().setId(id);
        this.id = id;
        getElement().addClassName("nano");
        // to enable ios native scroll
//        getElement().getStyle().setProperty("WebkitOverflowScrolling", "touch");
        contentNode.addClassName("content");
        DOM.appendChild(getElement(), contentNode);
        
//        sinkEvents(Event.KEYEVENTS);
    }
    
    private void setBooleanOption(String key, boolean value) {
        scrollerOptions.put(key, JSONBoolean.getInstance(value));
    }
    
    private void setIntegerOption(String key, int value) {
        scrollerOptions.put(key, new JSONNumber(value));
    }
    
    private void setStringOption(String key, String value) {
        scrollerOptions.put(key, new JSONString(value));
    }
    
    private void setElementOption(String key, Widget widget) {
        scrollerOptions.put(key, JSONParser.parseStrict("$(#"+widget.getElement().getId()+")"));
    }
    
    public void setFlashDelay(int flashDelay) {
        setIntegerOption(NanoScrollOption.FLASH_DELAY.toString(), flashDelay);
    }
    
    public void setPreventPageScrolling(boolean isPreventPageScrolling) {
        setBooleanOption(NanoScrollOption.PREVENT_PAGE_SCROLLING.toString(), isPreventPageScrolling);
    }
    
    public void setAlwaysVisible(boolean isAlwaysVisible) {
        setBooleanOption(NanoScrollOption.ALWAYS_VISIBLE.toString(), isAlwaysVisible);
    }
    
    public void setPanelClassName(String className) {
        setStringOption(NanoScrollOption.PANEL_CLASS.toString(), className);
    }
    
    public void setSliderClassName(String className) {
        setStringOption(NanoScrollOption.SLIDER_CLASS.toString(), className);
    }
    
    public void setContentClassName(String className) {
        setStringOption(NanoScrollOption.CONTENT_CLASS.toString(), className);
    }
    
    public void setScrollTop(int offset) {
        setIntegerOption(NanoScrollOption.SCROLL_TOP.toString(), offset);
    }
    
    public void setScrollBottom(int offset) {
        setIntegerOption(NanoScrollOption.SCROLL_BOTTOM.toString(), offset);
    }
    
    public void setScrollTo(Widget widget) {
        setElementOption(NanoScrollOption.SCROLL_TO.toString(), widget);
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
                nativeBuildScroller( GwtNanoScrollPanel.this, id, 
                        scrollerOptions.getJavaScriptObject() );
            }
        });
    }
    
    @Override
    public void setWidget(Widget w) {
        super.setWidget(w);
        nativeUpdateScroller(id);
    }
    
    @Override
    public void onUnload() {
        destroyScroller();
    }
    
    public void addListener(NanoScrollListener l) {
        listeners.add(l);
    }
    
    public void removeListener(NanoScrollListener l) {
        listeners.remove(l);
    }
    
    private void onScrollEnd(Event e) {
        NanoScrollEvent nanoEvent = new NanoScrollEvent(e, this);
        for(NanoScrollListener l : listeners) {
            l.onScrollEnd(nanoEvent);
        }
    }
    
    private void onScrollTop(Event e) {
        NanoScrollEvent nanoEvent = new NanoScrollEvent(e, this);
        for(NanoScrollListener l : listeners) {
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
    
    public void destroyScroller() {
        nativeFlashScrollbar(id);
    }
    
    /*
     * JSNI methods 
     */
    // $wnd.$('#'+id).children('.pane').css("display", "block");
    private native void nativeBuildScroller(GwtNanoScrollPanel panel, String id, JavaScriptObject options) /*-{
        var panelDiv = $wnd.$('#'+id);
        panelDiv.bind("scrollend", function(e) {
            panel.@org.vaadin.hhe.nanoscrollpanel.gwt.client.GwtNanoScrollPanel::onScrollEnd(Lcom/google/gwt/user/client/Event;)(e);
        });
        panelDiv.bind("scrolltop", function(e) {
            panel.@org.vaadin.hhe.nanoscrollpanel.gwt.client.GwtNanoScrollPanel::onScrollTop(Lcom/google/gwt/user/client/Event;)(e);
        });
        
        panelDiv.nanoScroller(options);
        
        panelDiv.find('.content').first().bind('DOMNodeInserted DOMNodeRemoved', function(e) {
            panelDiv.nanoScroller();
        });
        panelDiv.bind('heightChange', function(e) {
            panelDiv.nanoScroller();
        });
    }-*/;
    
    private native void nativeUpdateScroller(String id) /*-{
        $wnd.$('#'+id).nanoScroller();
    }-*/;
    
    private native void nativeDestroyScroller(String id) /*-{
        $wnd.$('#'+id).nanoScroller({
            destroy : true
        });
    }-*/;
    
    private native void nativeTriggerEvent(String id, String eventType) /*-{
        $wnd.$('#'+id).trigger(eventType);
    }-*/;
    
    private native void nativeFlashScrollbar(String id) /*-{
        $wnd.$('#'+id).nanoScroller({
            flash : true
        });
    }-*/;
    
    private native void nativeScrollTop(String id, int offset) /*-{
        $wnd.$('#'+id).nanoScroller({
            scrollTop : offset
        });
    }-*/;
    
    private native void nativeScrollBottom(String id, int offset) /*-{
        $wnd.$('#'+id).nanoScroller({
            scrollBottom : offset
        });
    }-*/;
}
