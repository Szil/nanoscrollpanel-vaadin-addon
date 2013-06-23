package org.vaadin.hhe.nanoscrollpanel.gwt.client.connector;

public interface NanoScrollClientRpc extends com.vaadin.shared.communication.ClientRpc {
    
    /**
     * To flash the scrollbar gadget for an amount of time defined in plugin settings (defaults to 1,5s).
     * Useful if you want to show the user (e.g. on pageload) that there is more content waiting for him.
     */
    void flashScrollbar();
    
    /**
     * Scroll at the top with an offset value
     * @param offset offsetY
     */
    void scrollTop(int offset);
    
    /**
     * Scroll at the bottom with an offset value
     * @param offset offsetY
     */
    void scrollBottom(int offset);
    
    /**
     * Scroll to an element
     */
//    void scrollTo();
    
    /**
     * Destroys nanoScroller and restores browser's native scrollbar.
     */
    void destory();
}
