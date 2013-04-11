/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package css;

/**
 *
 * @author Zhengyi
 */
public class PosCssLoader {
    public final static String MAIN_STYLE = "posStyle.css";
    
    public static String getCssFile(String file) {
        return PosCssLoader.class.getResource(file).toExternalForm();
    }
}
