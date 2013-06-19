package org.vaadin.hhe.nanoscrollpanel.gwt.client.connector;

import org.vaadin.hhe.nanoscrollpanel.NanoScrollPanel;
import org.vaadin.hhe.nanoscrollpanel.gwt.client.GwtNanoScrollPanel;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.ui.AbstractSingleComponentContainerConnector;
import com.vaadin.shared.ui.Connect;

@Connect(NanoScrollPanel.class)
public class NanoScrollPanelConnector extends AbstractSingleComponentContainerConnector {

    private static final long serialVersionUID = -1002224617479532996L;

    @Override
    public void updateCaption(ComponentConnector connector) {
        
    }

    @Override
    public void onConnectorHierarchyChange(
            ConnectorHierarchyChangeEvent connectorHierarchyChangeEvent) {
        getWidget().setWidget(getContentWidget());
    }
    
    @Override
    public GwtNanoScrollPanel getWidget() {
        return (GwtNanoScrollPanel)super.getWidget();
    }

}
