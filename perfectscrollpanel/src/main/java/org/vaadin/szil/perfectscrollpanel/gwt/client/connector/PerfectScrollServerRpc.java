package org.vaadin.szil.perfectscrollpanel.gwt.client.connector;

import com.vaadin.shared.communication.ServerRpc;
import com.vaadin.shared.ui.ClickRpc;

public interface PerfectScrollServerRpc extends ClickRpc, ServerRpc {
    
    void scrollEnd();
    
    void scrollTop();
}
