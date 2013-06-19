package org.vaadin.hhe.nanoscrollpanel.demo;

import javax.servlet.ServletException;

import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinServlet;

public class NanoScrollPanelDemoServlet extends VaadinServlet implements BootstrapListener {

    private static final long serialVersionUID = 7802036602410966883L;
    
    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(new SessionInitListener() {
            private static final long serialVersionUID = -3690270047781554869L;
            @Override
            public void sessionInit(SessionInitEvent event) {
                event.getSession().addBootstrapListener(NanoScrollPanelDemoServlet.this);
            }
        });
    }

    @Override
    public void modifyBootstrapFragment(BootstrapFragmentResponse response) {
        
    }

    @Override
    public void modifyBootstrapPage(BootstrapPageResponse response) {
        response.getDocument().head().append("<script type=\"text/javascript\" " +
        		"src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.js\"></script>");
        response.getDocument().head().append("<script type=\"text/javascript\" " +
        		"src=\"./VAADIN/javascripts/jquery.nanoscroller.js\"></script>");
    }

}
