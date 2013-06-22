package org.vaadin.hhe.nanoscrollpanel;

import org.vaadin.hhe.nanoscrollpanel.gwt.client.shared.NanoScrollPanelState;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractSingleComponentContainer;

@JavaScript({"http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js","jquery.nanoscroller.min.js"})
public class NanoScrollPanel extends AbstractSingleComponentContainer {

    private static final long serialVersionUID = -2168758555251706761L;
    
    @Override
    protected NanoScrollPanelState getState() {
        return (NanoScrollPanelState) super.getState();
    }
}
