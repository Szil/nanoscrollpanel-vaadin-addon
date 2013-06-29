package org.vaadin.hhe.nanoscrollpanel.gwt.client;

import com.google.gwt.user.client.Event;

public class GwtNanoScrollEvent {
    
    private final GwtNanoScrollPanel source;
    
    private final Event event;
    
    public GwtNanoScrollEvent(Event event, GwtNanoScrollPanel source) {
        this.event = event;
        this.source = source;
    }
    
    public GwtNanoScrollPanel getSource() {
        return source;
    }
    
    public Event getEvent() {
        return event;
    }
    
    // scrollend or scrolltop
    public String getType() {
        return event.getType();
    }

}
