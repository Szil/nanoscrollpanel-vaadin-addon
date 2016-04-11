package org.vaadin.szil.perfectscrollpanel.demo;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.vaadin.szil.perfectscrollpanel.PerfectScrollPanel;

public class PerfectScrollPanelDemoUI extends UI {

    private static final long serialVersionUID = 7992657815796718844L;
    
    private Component scrollToTarget = null;
    
    @SuppressWarnings("serial")
    @Override
    protected void init(VaadinRequest request) {
        final PerfectScrollPanel nPanel = new PerfectScrollPanel();
        nPanel.setWidth("400px");
        nPanel.setHeight("400px");
        /*-
        // flash user there are more content
        nPanel.flashScrollbar();
        nPanel.setPreventPageScrolling(true);
        nPanel.addNanoScrollListener(new PerfectScrollPanelListener() {
            @Override
            public void onScroll(PerfectScrollEvent event) {
                Notification.show("PerfectScrollEvent catched",
                        "Event type is "+event.getType(),
                        Notification.Type.HUMANIZED_MESSAGE);
            }
        });
        nPanel.addClickListener(new com.vaadin.event.MouseEvents.ClickListener() {
            @Override
            public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
                if(event.isDoubleClick()) {
                    Notification.show("PerfectScrollPanel was double clicked", "Event",
                            Notification.Type.HUMANIZED_MESSAGE);
                } else {
                    Notification.show("PerfectScrollPanel was clicked", "Event",
                            Notification.Type.HUMANIZED_MESSAGE);
                }
            }
        });
        */
        final VerticalLayout vLayout = new VerticalLayout();
        for(int i=0; i<50; ++i) {
            Label l = new Label("This is a example of PerfectScrollPanel " + i + ".");
            l.setId("Label"+i);
            if(i==25) scrollToTarget = l;
            vLayout.addComponent(l);
        }
        
        Button btn = new Button("Add more");
        vLayout.addComponent(btn);
        btn.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                vLayout.addComponent(new Label("This is more and more test."));
            }
        });
        
        Button btn2 = new Button("Remove one");
        vLayout.addComponent(btn2);
        btn2.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                if(vLayout.getComponentCount()>54)
                    vLayout.removeComponent(vLayout.getComponent(vLayout.getComponentCount()-1));
            }
        });
        
        Button btn3 = new Button("Shrink");
        vLayout.addComponent(btn3);
        btn3.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                nPanel.setHeight(nPanel.getHeight()*0.8f, nPanel.getHeightUnits());
            }
        });
        
        Button btn4 = new Button("Expand");
        vLayout.addComponent(btn4);
        btn4.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                nPanel.setHeight(nPanel.getHeight()/0.8f, nPanel.getHeightUnits());
            }
        });
        
        nPanel.setContent(vLayout);
        
        VerticalLayout nanoScrollLayout = new VerticalLayout();
        nanoScrollLayout.addComponent(nPanel);

        Button flashBtn = new Button("Flash Scrollbar");/*
        flashBtn.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                nPanel.flashScrollbar();
            }
        });*/
        nanoScrollLayout.addComponent(flashBtn);

        Button scrollTopBtn = new Button("Scroll To Top");/*
        scrollTopBtn.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                nPanel.flashScrollbar();
                nPanel.scrollToTop();
            }
        });*/
        nanoScrollLayout.addComponent(scrollTopBtn);

        Button scrollBottomBtn = new Button("Scroll To Bottom");/*
        scrollBottomBtn.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                nPanel.flashScrollbar();
                nPanel.scrollToBottom();
            }
        });*/
        nanoScrollLayout.addComponent(scrollBottomBtn);

        Button scrollToBtn = new Button("Scroll To 25");/*
        scrollToBtn.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                nPanel.flashScrollbar();
                nPanel.scrollTo(scrollToTarget);
            }
        });*/
        nanoScrollLayout.addComponent(scrollToBtn);
        
        
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.addComponent(nanoScrollLayout);
        
        Panel normalPanel = new Panel();
        normalPanel.setWidth("400px");
        normalPanel.setHeight("400px");
        final VerticalLayout normalPanelContentLayout = new VerticalLayout();
        normalPanelContentLayout.setMargin(true);
        for(int i=0; i<50; ++i) {
            normalPanelContentLayout.addComponent(new Label("This is a example of Normal Panel "+i+"."));
        }
        normalPanel.setContent(normalPanelContentLayout);
        hLayout.addComponent(normalPanel);
        
        setContent(hLayout);
    }
}
