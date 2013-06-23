package org.vaadin.hhe.nanoscrollpanel.gwt.client.connector;

public interface NanoScrollClientRpc extends com.vaadin.shared.communication.ClientRpc {
    
    void flashScrollbar();
    
    //TODO-hhe: support more actions, like void scrollTo();
}
