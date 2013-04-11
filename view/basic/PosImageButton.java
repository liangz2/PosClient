/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.basic;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;

/**
 *
 * @author Icewill
 */
public class PosImageButton extends Button {
    public PosImageButton(String name, ImageView image) {
        super(name, image);
        setContentDisplay(ContentDisplay.TOP);
    }
}
