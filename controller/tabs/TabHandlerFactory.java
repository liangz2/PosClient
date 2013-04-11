/*
 * A Facotry class that generates different handlers for differret usage
 */
package controller.tabs;

import view.UI.MainPane;

/**
 *
 * @author Icewill
 */
public class TabHandlerFactory {
    private static CloseTabHandler closeTabHandler;
    private static NewTabSelectionHandler newTabSelectHandler;
    private static TabSelectionHandler genericSelectHandler;
    
    /**
     * Creates handler for tab close action
     * @param mainPane
     * @return 
     */
    public static CloseTabHandler buildGenericCloseHandler(MainPane mainPane) {
        if (closeTabHandler == null) {
            closeTabHandler = new CloseTabHandler(mainPane);
        }
        
        return closeTabHandler;
    }
    
    /**
     * Creates handler for tab selection
     * @param mainPane
     * @return 
     */
    public static NewTabSelectionHandler buildNewTabSelectHandler(MainPane mainPane) {
        if (newTabSelectHandler == null) {
            newTabSelectHandler = new NewTabSelectionHandler(mainPane);
        }
        
        return newTabSelectHandler;
    }
    
    /**
     * tab
     * @param mainPane
     * @return 
     */
    public static TabSelectionHandler buildGenericSelectHandler_(MainPane mainPane) {
        return new TabSelectionHandler(mainPane);
    }
}
