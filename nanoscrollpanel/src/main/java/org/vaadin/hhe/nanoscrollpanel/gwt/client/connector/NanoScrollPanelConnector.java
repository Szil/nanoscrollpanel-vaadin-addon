package org.vaadin.hhe.nanoscrollpanel.gwt.client.connector;

import org.vaadin.hhe.nanoscrollpanel.NanoScrollPanel;
import org.vaadin.hhe.nanoscrollpanel.gwt.client.GwtNanoScrollPanel;
import org.vaadin.hhe.nanoscrollpanel.gwt.client.GwtNanoScrollEvent;
import org.vaadin.hhe.nanoscrollpanel.gwt.client.GwtNanoScrollListener;
import org.vaadin.hhe.nanoscrollpanel.gwt.client.shared.NanoEventId;
import org.vaadin.hhe.nanoscrollpanel.gwt.client.shared.NanoScrollPanelState;

import com.google.gwt.dom.client.NativeEvent;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractSingleComponentContainerConnector;
import com.vaadin.client.ui.ClickEventHandler;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;

@Connect(NanoScrollPanel.class)
public class NanoScrollPanelConnector extends AbstractSingleComponentContainerConnector {

    private static final long serialVersionUID = -1002224617479532996L;
    
    private final NanoScrollServerRpc serverRpc = RpcProxy.create(NanoScrollServerRpc.class, this);
    
    private ClickEventHandler clickEventHandler = new ClickEventHandler(this) {
        @Override
        protected void fireClick(NativeEvent event, MouseEventDetails mouseDetails) {
            serverRpc.click(mouseDetails);
        }
    };
    
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

        @Override
        public void updateScroller() {
            getWidget().updateScroller();
        }
        
    };
    
    @Override
    public void init() {
        super.init();
        registerRpc(NanoScrollClientRpc.class, clientRpc);
        
        // add listener
        getWidget().addNanoScrollListener(new GwtNanoScrollListener() {
            @Override
            public void onScrollTop(GwtNanoScrollEvent e) {
                if(hasEventListener(NanoEventId.NANO_SCROLL))
                    serverRpc.scrollTop();
            }
            
            @Override
            public void onScrollEnd(GwtNanoScrollEvent e) {
                if(hasEventListener(NanoEventId.NANO_SCROLL))
                    serverRpc.scrollEnd();
            }
        });
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
        clickEventHandler.handleEventHandlerRegistration();
        
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
