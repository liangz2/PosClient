/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.tooltips;

import javafx.scene.control.Tooltip;

/**
 *
 * @author Wilson
 */
public class PosTooltipFactory {
    private Tooltip salesSearchBox;
    
    public PosTooltipFactory() {
        salesSearchBox = new Tooltip("Enter any related information");
    }
    public Tooltip getSalesSearchBoxTooltip() {
        return salesSearchBox;
    }
}
