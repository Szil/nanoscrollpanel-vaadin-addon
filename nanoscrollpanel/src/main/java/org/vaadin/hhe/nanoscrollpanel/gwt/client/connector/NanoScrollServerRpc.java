package org.vaadin.hhe.nanoscrollpanel.gwt.client.connector;

import com.vaadin.shared.communication.ServerRpc;
import com.vaadin.shared.ui.ClickRpc;

public interface NanoScrollServerRpc extends ClickRpc, ServerRpc {
    
    void scrollEnd();
    
    void scrollTop();
}
