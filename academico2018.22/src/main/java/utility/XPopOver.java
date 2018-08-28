/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import static config.Config.i18n;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.controlsfx.control.PopOver;

/**
 *
 * @author Idomar Cerutti
 */
public class XPopOver {
    
    private FXMLLoader loader;
    
    public XPopOver(String arquivoFXML, String titulo, Node node) {
        try {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(arquivoFXML));
            loader.setResources(i18n);
            
            PopOver popOver = new PopOver();
            popOver.setContentNode(loader.load());
            popOver.setAutoFix(true);
            popOver.setAutoHide(true);
            popOver.setHideOnEscape(true);
            popOver.setHeaderAlwaysVisible(true);
            popOver.setArrowLocation(PopOver.ArrowLocation.TOP_LEFT);
            popOver.setTitle(titulo);
            popOver.show(node);
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(XPopOver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public FXMLLoader getLoader() {
        return loader;
    }
    
    
}
