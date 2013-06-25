package org.vaadin.hhe.nanoscrollpanel.gwt.client.connector;

import com.vaadin.shared.communication.ServerRpc;

public interface NanoScrollServerRpc extends ServerRpc {
    
    void scrollEnd();
    
    void scrollTop();
}
