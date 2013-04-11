/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.basic;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import view.UI.MainPane;

/**
 *
 * @author Icewill
 */
public abstract class PosTab extends Tab {
    private MainPane mainPane;
    protected ArrayList<PosImageButton> initButtons;
    public PosTab(String name, String id, final MainPane mainPane) {
        super(name);
        setId(id);
        this.mainPane = mainPane;
        initButtons = new ArrayList<>();
        initTabClosable();
    }
    
    /**
     * when opening a new tab, this is always called to make
     * sure all tabs are closable
     */
    private void initTabClosable() {
        if (mainPane.getTabs().size() > 1) {
            for (Tab tab: mainPane.getTabs()) {
                if (!tab.isClosable()) {
                    tab.setClosable(true);
                }
            }
            setClosable(true);
        }
        setClosable(false);
    }
    
    /**
     * set the handler to all buttons
     * @param e 
     */
    public final void addButtonHandler(EventHandler e) {
        for (Iterator<PosImageButton> it = initButtons.iterator(); it.hasNext();) {
            PosImageButton b = it.next();
            b.setOnAction(e);
        }
    }
    
    /**
     * freeing the button list
     */
    public void clearButtonList() {
        for (Iterator<PosImageButton> it = initButtons.iterator(); it.hasNext();) {
            PosImageButton b = it.next();
            b = null;
        }
        initButtons.clear();
        initButtons = null;
    }    
}
