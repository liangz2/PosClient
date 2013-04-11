/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.task;

import com.caucho.hessian.client.HessianProxyFactory;
import controller.RemoteDBUtil;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import model.product.Product;

/**
 *
 * @author Icewill
 */
public class InventoryLoader extends Task<ObservableList<Product>> {

    /**
     * 
     * @return 
     */
    
    @Override
    protected ObservableList<Product> call() throws Exception {
        ObservableList<Product> products = null;
        try {
            HessianProxyFactory factory = new HessianProxyFactory();
            
            RemoteDBUtil dbUtil = (RemoteDBUtil) factory.create(RemoteDBUtil.class, RemoteDBUtil.HOST);
            products = FXCollections.observableList(Arrays.asList(dbUtil.getProductList()));
            //System.out.println(dbUtil.test());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return products;
    }
}
