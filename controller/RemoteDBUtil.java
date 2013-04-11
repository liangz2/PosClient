/*
 * Remote control interface for the communication between server and the client.
 * The actual implementation is over on the server side, this is only the
 * interface for the client to call. This server-client communication action
 * is achieved by using the Hessian v4.0.7 library
 */
package controller;

import model.order.Order;
import model.product.Product;
import model.user.User;


/**
 *
 * @author Wilson
 */
public interface RemoteDBUtil {
    public static final String HOST = "http://teazone.ca/posData";
    public Product[] getProductList();
    public User getCustomer(String email, String password);
    public boolean registerCustomer(User user);
    public User getSalesPerson(String username, String password);
    public Order getOrder(int invoice);
    public Product getProductById(int pId);
    public Product getProductByModel(String model);
    public Product[] getProductsByModel(String model);
    public boolean placeOrder(Order order);
    public boolean updateProduct(Product product);
    public boolean removeProduct(int pId);
    public Object addProduct(Product product);
}
