/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.data;

import com.caucho.hessian.client.HessianProxyFactory;
import controller.RemoteDBUtil;
import java.net.MalformedURLException;

/**
 *
 * @author Icewill
 */
public class DataFactory {
    private static HessianProxyFactory factory;
    private static RemoteDBUtil dbUtil;
    
    public static RemoteDBUtil getDBUtil() {
        if (factory == null) {
            factory = new HessianProxyFactory();
        }
        if (dbUtil == null) {
            try {
                dbUtil = (RemoteDBUtil) factory.create(RemoteDBUtil.class, RemoteDBUtil.HOST);
            } catch (MalformedURLException ex) {
                return null;
            }
        }
        return dbUtil;
    }
}
