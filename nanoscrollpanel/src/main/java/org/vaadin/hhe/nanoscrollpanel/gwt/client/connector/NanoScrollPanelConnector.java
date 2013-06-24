package org.vaadin.hhe.nanoscrollpanel.gwt.client.connector;

import org.vaadin.hhe.nanoscrollpanel.NanoScrollPanel;
import org.vaadin.hhe.nanoscrollpanel.gwt.client.GwtNanoScrollPanel;
import org.vaadin.hhe.nanoscrollpanel.gwt.client.shared.NanoScrollPanelState;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractSingleComponentContainerConnector;
import com.vaadin.shared.ui.Connect;

@Connect(NanoScrollPanel.class)
public class NanoScrollPanelConnector extends AbstractSingleComponentContainerConnector {

    private static final long serialVersionUID = -1002224617479532996L;
    
    private NanoScrollClientRpc clientRpc = new NanoScrollClientRpc() {
        
        private static final long serialVersionUID = -8736936944968275980L;
        
        @Override
        public void flashScrollbar() {
            getWidget().flashScrollbar();
        }

        @Override
        public void scrollTop(int offset) {
            getWidget().scrollTop(offset);
        }

        @Override
        public void scrollBottom(int offset) {
            getWidget().scrollBottom(offset);
        }

        @Override
        public void destory() {
            getWidget().destroyScroller();
        }

        @Override
        public void scrollTo(String widgetId) {
            getWidget().scrollTo(widgetId);
        }
        
    };
    
    @Override
    public void init() {
        super.init();
        registerRpc(NanoScrollClientRpc.class, clientRpc);
    }

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
    
    @Override
    public NanoScrollPanelState getState() {
        return (NanoScrollPanelState) super.getState();
    }
    
    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
        if(stateChangeEvent.hasPropertyChanged("preventPageScrolling")) {
            getWidget().setPreventPageScrolling(getState().preventPageScrolling);
        }
        if(stateChangeEvent.hasPropertyChanged("alwaysVisible")) {
            getWidget().setAlwaysVisible(getState().alwaysVisible);
        }
        if(stateChangeEvent.hasPropertyChanged("flashDelay")) {
            getWidget().setFlashDelay(getState().flashDelay);
        }
        if(stateChangeEvent.hasPropertyChanged("iOSNativeScrolling")) {
            getWidget().setIOSNativeScrolling(getState().iOSNativeScrolling);
        }
        if(stateChangeEvent.hasPropertyChanged("disableResize")) {
            getWidget().setDisableResize(getState().disableResize);
        }
    }
}
