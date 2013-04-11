/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.UI;

import view.basic.PosTab;

/**
 *
 * @author Icewill
 */
public class NewTab extends PosTab {
    public NewTab(MainPane infoPane) {
        super("+", "new", infoPane);
        setClosable(false);
        setStyle("-fx-base: black");
    }
}