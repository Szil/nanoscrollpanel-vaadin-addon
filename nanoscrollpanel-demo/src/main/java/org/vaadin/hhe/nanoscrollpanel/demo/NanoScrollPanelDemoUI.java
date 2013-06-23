package org.vaadin.hhe.nanoscrollpanel.demo;

import org.vaadin.hhe.nanoscrollpanel.NanoScrollPanel;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class NanoScrollPanelDemoUI extends UI {

    private static final long serialVersionUID = 7992657815796718844L;
    
    @SuppressWarnings("serial")
    @Override
    protected void init(VaadinRequest request) {
        final NanoScrollPanel nPanel = new NanoScrollPanel();
        nPanel.setWidth("400px");
        nPanel.setHeight("400px");
        nPanel.flashScrollbar();
        nPanel.setPreventPageScrolling(true);
        
        final VerticalLayout vLayout = new VerticalLayout();
        for(int i=0; i<50; ++i) {
            vLayout.addComponent(new Label("This is a test "+i+"."));
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
        
        VerticalLayout overallLayout = new VerticalLayout();
        overallLayout.addComponent(nPanel);
        
        Button flashBtn = new Button("Flash Scrollbar");
        flashBtn.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                nPanel.flashScrollbar();
            }
        });
        overallLayout.addComponent(flashBtn);
        
        setContent(overallLayout);
    }
}
