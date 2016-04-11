package org.vaadin.szil.perfectscrollpanel.gwt.client.connector;

import com.google.gwt.dom.client.NativeEvent;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractSingleComponentContainerConnector;
import com.vaadin.client.ui.ClickEventHandler;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;
import org.vaadin.szil.perfectscrollpanel.PerfectScrollPanel;
import org.vaadin.szil.perfectscrollpanel.gwt.client.GwtPerfectScrollEvent;
import org.vaadin.szil.perfectscrollpanel.gwt.client.GwtPerfectScrollListener;
import org.vaadin.szil.perfectscrollpanel.gwt.client.GwtPerfectScrollPanel;
import org.vaadin.szil.perfectscrollpanel.gwt.client.shared.PerfectEventId;
import org.vaadin.szil.perfectscrollpanel.gwt.client.shared.PerfectScrollPanelState;

@Connect(PerfectScrollPanel.class)
public class PerfectScrollPanelConnector extends AbstractSingleComponentContainerConnector {

    private static final long serialVersionUID = -1002224617479532996L;

    private final PerfectScrollServerRpc serverRpc = RpcProxy.create(PerfectScrollServerRpc.class, this);
    
    private ClickEventHandler clickEventHandler = new ClickEventHandler(this) {
        @Override
        protected void fireClick(NativeEvent event, MouseEventDetails mouseDetails) {
            serverRpc.click(mouseDetails);
        }
    };

    private PerfectScrollClientRpc clientRpc = new PerfectScrollClientRpc() {
        
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
        registerRpc(PerfectScrollClientRpc.class, clientRpc);
        
        // add listener
        getWidget().addNanoScrollListener(new GwtPerfectScrollListener() {
            @Override
            public void onScrollTop(GwtPerfectScrollEvent e) {
                if (hasEventListener(PerfectEventId.NANO_SCROLL))
                    serverRpc.scrollTop();
            }
            
            @Override
            public void onScrollEnd(GwtPerfectScrollEvent e) {
                if (hasEventListener(PerfectEventId.NANO_SCROLL))
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
    public GwtPerfectScrollPanel getWidget() {
        return (GwtPerfectScrollPanel) super.getWidget();
    }
    
    @Override
    public PerfectScrollPanelState getState() {
        return (PerfectScrollPanelState) super.getState();
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
