/*
 * InventoryMaster scene is a scene that lets the people with the adequate rank
 * to edit the inventory
 */
package controller.scene;

import controller.RemoteDBUtil;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.ShortStringConverter;
import model.data.DataFactory;
import model.product.Product;
import view.UI.MainPane;

/**
 *
 * @author Icewill
 */
public class InventoryMasterScene extends PosScene{
    @FXML private TableView productTable;
    @FXML private Label productCount;
//    @FXML private TableColumn id;
    @FXML private TableColumn model;
    @FXML private TableColumn quantity;
    @FXML private TableColumn price;
    @FXML private TableColumn weight;
    @FXML private TableColumn dateAdded;
    @FXML private TableColumn status;
    @FXML private TableColumn name;
    @FXML private TableColumn remove;
    @FXML private TableColumn update;
    @FXML private TextArea newName;
    @FXML private TextField newWeight;
    @FXML private TextField newModel;
    @FXML private TextField newPrice;
    @FXML private TextField newQuantity;
    @FXML private TextField searchBar;
    @FXML private CheckBox newActive;
    @FXML private Button newProductButton; 
    @FXML private Button refreshButton;
    private ObservableList<Product> products;
    private ObservableList<Product>[] searchStages;
    private ObservableList<Product> currentProductList;
    private HashMap<Character, Integer> index;
    private String searchKey;
    private final int stageNum = 6;
    private int selectedIndex = 0;
    private Product[] productArray;
    private Product selectedProduct;
    /**
     * constructor
     * @param fxmlLocation
     * @param myTask
     * @param mainPane 
     */
    public InventoryMasterScene(URL fxmlLocation, Task myTask, MainPane mainPane) {
        super(fxmlLocation, myTask, mainPane);
        productArray = mainPane.getProductArray();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // initialize index map
        index = new HashMap();
        loadData(myTask);
        // initialize searchStages
        searchStages = new ObservableList[stageNum];
        for (int i = 0; i < stageNum; i++) {
            searchStages[i] = FXCollections.observableArrayList();
        }

        // set the loaded flag and the pointer
        mainPane.setProductsAreLoaded(true);
        mainPane.setProducts(productArray);
        // product quantity column
        quantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        quantity.setCellFactory(TextFieldTableCell.forTableColumn(new ShortCellConverter()));
        // initialize behaviour editable cells column
        quantity.setOnEditCommit(new EventHandler<CellEditEvent<Product, Short>>() {
            @Override
            public void handle(CellEditEvent<Product, Short> t) {
                if (!t.getNewValue().equals(t.getOldValue())) {
                    Product selectedProduct = currentProductList
                            .get(t.getTablePosition().getRow());
                    ((Button) selectedProduct.getUpdateButton())
                            .setVisible(true);
                    selectedProduct.setQuantity(t.getNewValue());
                }
            }
        });
        // product model column
        model.setCellValueFactory(new PropertyValueFactory("model"));
        model.setCellFactory(TextFieldTableCell.forTableColumn());
        // initialize behaviour editable cells column
        model.setOnEditCommit(new EventHandler<CellEditEvent<Product, String>>() {
            @Override
            public void handle(CellEditEvent<Product, String> t) {
                if (!t.getNewValue().equals(t.getOldValue())) {
                    Product selectedProduct = currentProductList
                            .get(t.getTablePosition().getRow());
                    ((Button) selectedProduct.getUpdateButton())
                            .setVisible(true);
                    selectedProduct.setModel(t.getNewValue());
                }
            }
        });
        // product name column
        name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        // initialize behaviour editable cells column
        name.setOnEditCommit(new EventHandler<CellEditEvent<Product, String>>() {
            @Override
            public void handle(CellEditEvent<Product, String> t) {
                if (!t.getNewValue().equals(t.getOldValue())) {
                    Product selectedProduct = currentProductList
                            .get(t.getTablePosition().getRow());
                    ((Button) selectedProduct.getUpdateButton())
                            .setVisible(true);
                    selectedProduct.setName(t.getNewValue());
                }
            }
        });
        // product price column
        price.setCellValueFactory(new PropertyValueFactory<Product, Float>("price"));
        price.setCellFactory(TextFieldTableCell.forTableColumn(new FloatCellConverter()));
        // set editing behavior
        price.setOnEditCommit(new EventHandler<CellEditEvent<Product, Float>>() {
            @Override
            public void handle(CellEditEvent<Product, Float> t) {
                if (!t.getNewValue().equals(t.getOldValue())) {
                    Product selectedProduct = currentProductList
                            .get(t.getTablePosition().getRow());
                    ((Button) selectedProduct.getUpdateButton())
                            .setVisible(true);
                    selectedProduct.setPrice(t.getNewValue());
                }
            }
        });
        // product weight column
        weight.setCellValueFactory(new PropertyValueFactory<Product, Float>("weight"));
        weight.setCellFactory(TextFieldTableCell.forTableColumn(new FloatCellConverter()));
        // set editing behavior
        weight.setOnEditCommit(new EventHandler<CellEditEvent<Product, Float>>() {
            @Override
            public void handle(CellEditEvent<Product, Float> t) {
                if (!t.getNewValue().equals(t.getOldValue())) {
                    Product selectedProduct = currentProductList
                            .get(t.getTablePosition().getRow());
                    ((Button) selectedProduct.getUpdateButton())
                            .setVisible(true);
                    selectedProduct.setWeight(t.getNewValue());
                }
            }
        });
        // product date column
        dateAdded.setCellValueFactory(new PropertyValueFactory("dateAdded"));
        // product status column
        status.setCellValueFactory(new PropertyValueFactory<Product, Boolean>("status"));
        status.setCellFactory(new Callback<TableColumn<Product, Boolean>, TableCell<Product, Boolean>>() {

           @Override
           public TableCell<Product, Boolean> call(TableColumn<Product, Boolean> p) {
               return new BooleanCell();
           }
           
        });
        // update product column;
        update.setCellValueFactory(new PropertyValueFactory<Product, Boolean>("changed"));
        update.setCellFactory(new Callback<TableColumn<Product, Boolean>, TableCell<Product, Boolean>>() {

            @Override
            public TableCell<Product, Boolean> call(TableColumn<Product, Boolean> p) {
                return new UpdateButtonCell();
            }
            
        });
        // proudct remove button column
        remove.setMinWidth(70);
        remove.setCellValueFactory(new PropertyValueFactory<Product, Boolean>("remove"));
        remove.setCellFactory(new Callback<TableColumn<Product, Boolean>, TableCell<Product, Boolean>>() {
            
            @Override
            public TableCell<Product, Boolean> call(TableColumn<Product, Boolean> p) {
                return new RemoveButtonCell();
            }
            
        });
        // end of setting up columns
        productTable.setEditable(true);
        productTable.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            Button removeButton;
            
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                selectedIndex = t1.intValue();
                if (selectedIndex >= 0) {
                    if (removeButton != null) {
                        removeButton.setVisible(false);
                    }
                    selectedProduct = currentProductList.get(selectedIndex);
                    removeButton = (Button) selectedProduct.getRemoveButton();
                    if (removeButton != null) {
                        removeButton.setVisible(true);
                    }
                }
            }
            
        });
        // add action perform handler to the addProduct button
        newProductButton.setOnAction(new AddProductHandler());
        // add action perform handler to the refresh butotn
        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    refreshTable();
                } catch (Exception ex) {
                    System.err.println(ex.getStackTrace());
                }
            }
        });
        // add key listener to the search bar
        searchBar.textProperty().addListener(new SearchBarHandler());
    }
    
    /**
     * method that load the data and fill up the table
     * @param myTask 
     */
    @Override
    public void loadData(Task myTask) {
        super.loadData(myTask);
        try {
           refreshTable();
        } catch (Exception ex) {
            ex.printStackTrace();
            mainPane.setCriticalMessage(ErrorMessage.NO_CONNECTION);
            myTask.cancel();
        }
    }
    /**
     * 
     */
    private void refreshTable() throws Exception {
        RemoteDBUtil dbUtil = DataFactory.getDBUtil();
        if ((productArray = mainPane.getProductArray()) == null) {
            productArray = dbUtil.getProductList();
            mainPane.setProducts(productArray);
        }
        // clear the list and suggest the garbage collector to run
        currentProductList = products = FXCollections.observableArrayList(productArray);
        productTable.setItems(products);
        // set the total product count label
        productCount.setText("Total Product Count: " + products.size());
        char c = 0;
        for (int i = 0; i < products.size(); i++) {
            if (!products.get(i).getModel().isEmpty()) {
                char s = products.get(i).getModel().charAt(0);
                if (s != c) {
                    c = s;
                    index.put(s, i);
                }
            }
        }
        // reset all search stages if the search bar has contents
        if (!searchBar.getText().isEmpty()) {
            searchBar.clear();
        }
    }
    /* ========================== Private Classes ============================== */
    /**
     * 
     */

    private class SearchBarHandler implements ChangeListener<String> {
        private Integer start = null;
        private Integer end = null;
        private ObservableList<Product> subList;
        @Override
        public void changed(ObservableValue<? extends String> ov, String t, String t1) {
            if (t1.length() == 1) {
                char c = t1.toUpperCase().charAt(0);
                start = index.get(c);
                if (start != null) {
                    while ((end = index.get((char) (c + 1))) == null) {
                        if (c++ >= 'z') {
                            end = products.size();
                            break;
                        }
                    }
                }
                else {
                    start = 0;
                    end = 0;
                }
                // if there exists such index
                subList = currentProductList = FXCollections.observableList(products.subList(start, end));
            } else if (t1.isEmpty()) {
                // if search bar is empty
                if (currentProductList != products)
                    currentProductList = products;
            } else {
                // if search bar has more than one letter
                start = 0;
                boolean startMarked = false;
                String t2 = t1.toUpperCase();
                // if user is inputting keywords
                if (t1.length() > t.length()) {
                    for (end = 0; end < currentProductList.size(); end++) {
                        String s;
                        try { 
                            s = currentProductList.get(end).getModel().substring(0, t2.length());
                        } catch (StringIndexOutOfBoundsException ex) {
                            continue;
                        }
                        if (s.equals(t2) && !startMarked) {
                            start = end;
                            startMarked = true;
                        }
                        else if (!s.equals(t2) && startMarked) {
                            break;
                        }
                    }
                    if (!startMarked) {
                        end = 0;
                    }
                    if (currentProductList.size() != 0) {
                        currentProductList = FXCollections.observableList(currentProductList.subList(start, end));
                    }
                } else {  
                    // if user is removing keywords
                    for (end = 0; end < subList.size(); end++) {
                        String s;
                        try {
                            s = subList.get(end).getModel().substring(0, t2.length());
                        } catch (StringIndexOutOfBoundsException ex) {
                            continue;
                        }
                        if (s.equals(t2) && !startMarked ) {
                            start = end;
                            startMarked = true;
                        }
                        else if (!s.equals(t2) && startMarked) {
                            break;
                        }
                    }
                    if (!startMarked) {
                        end = 0;
                    }
                    if (subList.size() != 0) {
                        currentProductList = FXCollections.observableList(subList.subList(start, end));
                    }
                }
            }
            // refresh table
            productTable.setItems(currentProductList);
            productCount.setText(currentProductList.size() + " items found");
        }
    }
    
    /**
     * Custom remove button cell
     */
    private class RemoveButtonCell extends TableCell<Product, Boolean> {
        private Button removeButton;
        private RemoveButtonCell() {
            setAlignment(Pos.CENTER);
        }

        /**
         * 
         * @param item
         * @param empty 
         */
        @Override
        public void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                removeButton = new Button("Remove");
                removeButton.setVisible(item);
                removeButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        try {
                            RemoteDBUtil dbUtil = DataFactory.getDBUtil();
                            dbUtil.removeProduct(selectedProduct.getId());
                        } catch (Exception ex) {
                            mainPane.displayErrorMessage(ErrorMessage.NO_CONNECTION);
                            return;
                        }
                        productTable.getSelectionModel().clearSelection();
                        mainPane.displaySuccessMessage("Remove item: "
                                + selectedProduct.getModel()
                                + " successfully.");
                        // remove from the sub list therefore reflect on the main list
                        currentProductList.remove(selectedProduct); 
                        productCount.setText(currentProductList.size() + " items found");
                        if (currentProductList.isEmpty()) {
                            currentProductList = products;
                            searchBar.clear();
                            productTable.setItems(currentProductList);
                        }                        
                    }
                });
                currentProductList.get(getIndex()).setRemoveButton(removeButton);
                this.setGraphic(removeButton);
                this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
        }
    }
    /**
     * Custom update button cell
     */
    private class UpdateButtonCell extends TableCell<Product, Boolean> {
        private Button updateButton;
        private UpdateButtonCell() {
            setAlignment(Pos.CENTER);
        }
        
        /**
         * 
         * @param item
         * @param empty 
         */
        @Override
        public void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEmpty()) {
                updateButton = new Button("Update");
                updateButton.setVisible(item);
                updateButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        try {
                            RemoteDBUtil dbUtil = DataFactory.getDBUtil();
                            if (!dbUtil.updateProduct(new Product(selectedProduct))) {
                                mainPane.displayCriticalErrorMessage(ErrorMessage.ACTION_PERFORM_FAILURE);
                                return;
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            mainPane.displayErrorMessage(ErrorMessage.NO_CONNECTION);
                            return;
                        }
                        updateButton.setVisible(false);
                        mainPane.displaySuccessMessage("Update item " 
                                + selectedProduct.getModel()
                                + " successfully.");
                    }
                });
                currentProductList.get(getIndex()).setUpdateButton(updateButton);
                this.setGraphic(updateButton);
                this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
        }
    }
    /**
     * 
     */
    private class ShortCellConverter extends ShortStringConverter {
        String oldValue = "";
        /**
         * 
         * @param string
         * @return 
         */
        @Override
        public Short fromString(String string) {
            short number;
            try {
                number = super.fromString(string);
            } catch (NumberFormatException ex) {
                mainPane.displayCriticalErrorMessage(ErrorMessage.INVALID_NUMBER);
                number = super.fromString(oldValue);
            }
            return number;
        }
        /**
         * 
         * @param s
         * @return 
         */
        @Override
        public String toString(Short s) {
            oldValue = super.toString(s);
            return oldValue;
        }
    }
    /**
     * Custom FloatStringConverter to catch NumberFormatException
     * for customized error message display
     */
    private class FloatCellConverter extends FloatStringConverter {
        String oldValue = "";
        /**
         * catch the exception
         * @param string
         * @return 
         */

        @Override
        public Float fromString(String string) {
            float number;
            try {
                number = super.fromString(string);
            } catch (NumberFormatException ex) {
                mainPane.displayCriticalErrorMessage(ErrorMessage.INVALID_NUMBER);
                number = super.fromString(oldValue);
            }
            return number;
        }
        /**
         * save the old value in case an invalid entry
         * @param f
         * @return 
         */
        @Override
        public String toString(Float f) {
            oldValue = super.toString(f);
            return oldValue;
        }
    }
    /**
     * Custom CheckBoxTableCell to react to CheckBox clicking actions
     */
    private class BooleanCell extends TableCell<Product, Boolean> {
        private CheckBox checkBox;
        private Product selectedProduct;
        private BooleanCell() {
            this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            this.setEditable(true);
            this.setAlignment(Pos.CENTER);
        }
        
        @Override
        public void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                checkBox = new CheckBox();
                checkBox.setDisable(false);
                // action listener to get the selected checkbox
                checkBox.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        selectedProduct = currentProductList.get(getIndex());
                        Button updateButton = (Button) selectedProduct.getUpdateButton();
                        boolean status = ((CheckBox) t.getSource()).isSelected();
                        if (status != selectedProduct.getStatus()) {
                            selectedProduct.setStatus(status);
                            if (!updateButton.isVisible()) {
                                updateButton.setVisible(true);
                            }
                        }
                    }
                });
                setGraphic(checkBox);
                checkBox.setSelected(item);
            }
        }
    }

    /**
     * Custom ActionEvent for add product button
     */
    private class AddProductHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent t) {
            RemoteDBUtil dbUtil = DataFactory.getDBUtil();
            float price;
            float weight;
            short quantity;
            // display a updating message
            mainPane.displayCriticalSuccessMessage("Updating product, please wait...");
            // check if numbers are in correct format
            try {
                price = Float.parseFloat(newPrice.getText());
                weight = Float.parseFloat(newWeight.getText());
                quantity = Short.parseShort(newQuantity.getText());
            } catch (NumberFormatException ex) {
                mainPane.displayCriticalErrorMessage(ErrorMessage.INVALID_NUMBER);
                return;
            }
            // if numbers are good proceed to add new product
            Product newProduct = new Product();
            newProduct.setModel(newModel.getText());
            newProduct.setName(newName.getText());
            newProduct.setPrice(price);
            newProduct.setWeight(weight);
            newProduct.setQuantity(quantity);
            newProduct.setStatus(newActive.isSelected());
            try {
                Object rs = dbUtil.addProduct(newProduct);
                if (rs instanceof Product) {
                    Product p = (Product) rs;
                    if (p.getId() >= 0) {
                        refreshTable();
                        mainPane.displayCriticalSuccessMessage("Added new product "
                                + p.getModel() + " successfully");
                        productTable.getSelectionModel().select(p);
                    }
                } else {
                    mainPane.displayCriticalErrorMessage((String) rs);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                mainPane.displayCriticalErrorMessage(ErrorMessage.NO_CONNECTION);
            }
        }
    }
}
