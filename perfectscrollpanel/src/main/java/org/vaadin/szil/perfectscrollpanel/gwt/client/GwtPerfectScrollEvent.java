package org.vaadin.szil.perfectscrollpanel.gwt.client;

import com.google.gwt.user.client.Event;

public class GwtPerfectScrollEvent {

    private final GwtPerfectScrollPanel source;
    
    private final Event event;

    public GwtPerfectScrollEvent(Event event, GwtPerfectScrollPanel source) {
        this.event = event;
        this.source = source;
    }

    public GwtPerfectScrollPanel getSource() {
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
