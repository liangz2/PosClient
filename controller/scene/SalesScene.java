/*
 * One of the major scene controller for the SalesScene
 * mainly consists of buttons and the item purchasing info TableView
 */
package controller.scene;

import controller.RemoteDBUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.data.DataFactory;
import model.product.NewPurchaseItem;
import model.product.Product;
import view.UI.Loading;
import view.UI.MainPane;
import view.scene.SceneFactory;

/**
 *
 * @author Zhengyi
 */
public final class SalesScene extends PosScene {
    @FXML private TextField refNum;
    @FXML private TextField customerId;
    @FXML private TextField purchaseTotal;
    @FXML private TextField purchaseSubTotal;
    @FXML private TextArea customerInfo;
    @FXML private ComboBox soldBy;
    @FXML private Button invLookUpButton;
    @FXML private Button paymentButton;
    @FXML private TableView purchaseItems;
    @FXML private TableColumn model;
    @FXML private TableColumn quantity;
    @FXML private TableColumn description;
    @FXML private TableColumn listPrice;
    @FXML private TableColumn unitPrice;
    @FXML private TableColumn total;
    @FXML private TableColumn dateCode;
    @FXML private TableColumn notes;
    @FXML private TableColumn allocated;
    @FXML private TextField searchBox;
    @FXML private AnchorPane salesPane;
    @FXML private Button saveButton;
    private RemoteDBUtil dbUtil;
    private NotFoundMessage notFound;
    private ObservableList<NewPurchaseItem> items;
    private Region loadingBackground;
    private Runnable focusCurrentTextField;
    private NewPurchaseItem currentItem;
    private Runnable editNotes;
    private int indexToEdit;
    private TextField currentTextField;
    private PosEditableCell currentModelCell;
    private SalesSceneEventHandler salesSceneEventHandler;
    
    public SalesScene(URL fxmlLocation, Task myThread, MainPane mainPane) {
        super(fxmlLocation, myThread, mainPane);
    }
    
    /** 
     * Function where the Initializable object initialize the scene
     * @param arg0
     * @param arg1 
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // setup background 
        notFound = SceneFactory.buildNotFoundMessage();
        // sales scene event handler
        salesSceneEventHandler 
                = new SalesSceneEventHandler(mainPane, this, notFound, salesPane);
        notFound.setNoButtonHandler(salesSceneEventHandler.
                getNoButtonHandler());
        notFound.setYesButtonHandler(salesSceneEventHandler.
                getYesButtonHandler());
        // tooltip setup
        searchBox.setTooltip(mainPane.getTooltipFactory().getSalesSearchBoxTooltip());
        // end of tooltip setup
        items = FXCollections.observableArrayList();
        dbUtil = DataFactory.getDBUtil();
        loadingBackground = Loading.getLoadingBackground();
        // always start with one model
        items.add(new NewPurchaseItem());
        refNum.setPromptText("10000");
        customerId.setPromptText("20000");
        customerInfo.setPromptText("Customer information");
        soldBy.getItems().setAll(mainPane.getLoginUser());
        soldBy.getSelectionModel().select(mainPane.getLoginUser());
        // setup inventory lookup button
        invLookUpButton.setGraphic(loadImage("images/inventory_32x32.png"));
        invLookUpButton.setOnAction(salesSceneEventHandler.getInvLookupScene());
        // setup payment button
        paymentButton.setGraphic(loadImage("images/payment_48x48.png"));
        // capture hotkeys on the purchase table fast product addidtion
        mainPane.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                if (t.isControlDown() && t.getCode() == KeyCode.INSERT) {
                    purchaseItems.edit(items.size() - 1, model);
                }
            }
        });
        // model column
        model.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new ProductModelCell();
            }
        });
        model.setCellValueFactory(
                new Callback<CellDataFeatures<NewPurchaseItem, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<NewPurchaseItem, String> p) {
                        return p.getValue().getModel();
                    }
        });
        // quantity column
        quantity.setCellValueFactory(
                new Callback<CellDataFeatures<NewPurchaseItem, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<NewPurchaseItem, String> p) {
                        return p.getValue().getQuantity();
                    }
        });
        quantity.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new PurchaseQuantityCell();
            }
        });
        // description column
        description.setCellValueFactory(
                new Callback<CellDataFeatures<NewPurchaseItem, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<NewPurchaseItem, String> p) {
                        return p.getValue().getDescription();
                    }
        });
        // dateCode column
        dateCode.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new ProductDateCodeCell();
            }
        });
        dateCode.setCellValueFactory(
                new Callback<CellDataFeatures<NewPurchaseItem, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<NewPurchaseItem, String> p) {
                        return p.getValue().getDateCode();
                    }
        });
        // unitPrice column
        unitPrice.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new UnitPriceCell();
            }
        });
        unitPrice.setCellValueFactory(
                new Callback<CellDataFeatures<NewPurchaseItem, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<NewPurchaseItem, String> p) {
                        return p.getValue().getUnitPrice();
                    }
        });
        // listPrice column
        listPrice.setCellValueFactory(
                new Callback<CellDataFeatures<NewPurchaseItem, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<NewPurchaseItem, String> p) {
                        return p.getValue().getListPrice();
                    }
        });
        // total column
        total.setCellValueFactory(
                new Callback<CellDataFeatures<NewPurchaseItem, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<NewPurchaseItem, String> p) {
                        return p.getValue().getTotal();
                    }
        });
        // notes column
        notes.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new ProductNoteCell();
            }
        });
        notes.setCellValueFactory(
                new Callback<CellDataFeatures<NewPurchaseItem, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<NewPurchaseItem, String> p) {
                        return p.getValue().getNotes();
                    }
        });
        // set the ObservableList to the TableView
        purchaseItems.setItems(items);
        // add error scene on top
        getChildren().addAll(loadingBackground, notFound);
    }
        
    /**
     * 
     * @param product 
     */
    public void addNewPurchaseItem(Product product) {
        items.add(purchaseItems
                .getEditingCell().getRow(), new NewPurchaseItem(product));
        currentModelCell.setCancelEditAllowed(true); 
    }
        
    /**
     * function that sets the purchaseTotal TextField
     */
    private void setTotalTextFields() {
        float t = 0.0f;
        for (NewPurchaseItem n: items) {
            if(!n.isEmpty()) {
                t += Float.parseFloat(n.getTotal().getValue());
            }
        }
        // set the TextField
        purchaseSubTotal.setText(String.format("%.2f", t));
        purchaseTotal.setText(String.format("%.2f", (float) (t * 1.05)));
    }
    
    public Region getLoadingBackground() {
        return loadingBackground;
    }
    
    public TextField getCurrentTextField() {
        return currentTextField;
    }
    
    public PosEditableCell getCurrentModelCell() {
        return currentModelCell;
    }
    
    /* ======================================== Private Classes ================================= */
    private class SearchBoxHandler implements EventHandler<KeyEvent> {
        private Product[] matchingProducts;
        @Override
        public void handle(KeyEvent t) {
            matchingProducts = dbUtil.getProductsByModel(t.getText());
        }
        
    }
    
    /**
     * 
     */
    private final class NotFoundMessageAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent t) {
            if (((Button) t.getSource()).getId().equals(NotFoundMessage.YES_BUTTON_ID)) {
                
            } else {
                salesPane.setDisable(false);
                currentTextField.requestFocus();
                
            }
            notFound.setVisible(false);
            loadingBackground.setVisible(false);
        }
    }
    
    /**
     * 
     */
    private final class MainTableKeyHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent t) {
            if (t.getCode() == KeyCode.RIGHT) {
                purchaseItems.getSelectionModel().selectRightCell();
            }
        }
        
    }
    
    /**
     * UnitPriceCell is a cell that takes over the list price
     * in the end when calculating the total
     */
    private final class UnitPriceCell extends PosEditableCell {
        public UnitPriceCell() {
            super(null);
            // force users to type numbers
            myTextField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    char c = t.getCharacter().charAt(0);
                    if (c < '0' || c > '9') {
                        if (c != '.') {
                            t.consume();
                        }
                    }
                }
                
            });
        }
        
        /**
         * overrides the parent's commitEdit
         * @param dateCode 
         */
        @Override
        public void commitEdit(String unitPrice) {
            if (!unitPrice.isEmpty()) {
                float princef = Float.parseFloat(unitPrice);
                String price = String.format("%.2f", princef);
                super.commitEdit(price);
                currentItem = items.get(this.getIndex());
                currentItem.setUnitPrice(price);
                // recalculate and set the total
                float totalAmount = princef 
                        * Float.parseFloat(currentItem.getQuantity().getValue());
                currentItem.setTotal(String.format("%.2f", totalAmount));
                // reset the total display field
                setTotalTextFields();
            }
        }
         
        /**
         * overrides the parent's cancelEdit
         */
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem());
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
    }
    
    /**
     * ProductNoteCell is a cell that takes notes for items
     */
    private final class ProductNoteCell extends PosEditableCell {
        public ProductNoteCell() {
            super(unitPrice);
        }
        
        /**
         * overrides the parent's commitEdit
         * @param dateCode 
         */
        @Override
        public void commitEdit(String notes) {
            if (!notes.isEmpty()) {
                super.commitEdit(notes);
                currentItem = items.get(this.getIndex());
                currentItem.setNotes(notes);
                Platform.runLater(focusNextTextField);
            }
        }
        
        /**
         * overrides the parent's cancelEdit
         */
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem());
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
    }
    
    /**
     * dateCode cell class
     */
    private final class ProductDateCodeCell extends PosEditableCell {
        public ProductDateCodeCell() {
            super(notes);
        }
        
        /**
         * overrides the parent's commitEdit
         * @param dateCode 
         */
        @Override
        public void commitEdit(String dateCode) {
            if (!dateCode.isEmpty()) {
                super.commitEdit(dateCode);
                currentItem = items.get(this.getIndex());
                currentItem.setDateCode(dateCode);
                Platform.runLater(focusNextTextField);
            }
        }
        
        /**
         * overrides the parent's cancelEdit
         */
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem());
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
    }
    
    /**
     * quantity cell class
     */
    private final class PurchaseQuantityCell extends PosEditableCell {
        public PurchaseQuantityCell() {
            // setup the cell
            super(dateCode);
            myTextField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    char c = t.getCharacter().charAt(0);
                    if (c < '0' || c > '9') {
                        t.consume();
                    }
                }
            });
        }

        /**
         * sets the targeted underlying NewPurchaseItem model
         * @param quantity 
         */
        @Override
        public void commitEdit(String quantity) {
            if (!quantity.isEmpty()) {
                super.commitEdit(quantity);
                currentItem = items.get(this.getIndex());
                currentItem.setQuantity(quantity);
                float totalAmount = Integer.parseInt(quantity) 
                        * Float.parseFloat(currentItem.getUnitPrice().getValue());
                currentItem.setTotal(Float.toString(totalAmount));
                setTotalTextFields();
                Platform.runLater(focusNextTextField);
            }
        }
        /**
         * overrides the parent cancelEdit method
         */
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem());
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
        /**
         * 
         * @param model
         * @param empty 
         */
        @Override
        public void updateItem(String model, boolean empty) {
            super.updateItem(model, empty);
        }
    }
    
    /**
     * model model cell class
     */
    private final class ProductModelCell extends PosEditableCell {
        private Product p;
        public ProductModelCell() {
            // setup the cell
            super(quantity);
            // new "Add Product" label for empty purchase model
            extraLabel = new Label("Add Product");
            extraLabel.setTextFill(Color.GREY);
            isModelCell = true;
            
            setOnKeyReleased(salesSceneEventHandler
                    .getModelCellF9Handler());
        }
        
        /**
         * 
         */
        @Override
        public void startEdit() {
            currentModelCell = this;
            super.startEdit();
        }
        
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            if (getItem().isEmpty()) {
                setGraphic(extraLabel);
            } else {
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }

        @Override
        public void commitEdit(String model) {
            if (model.isEmpty()) {
                return;
            }
            if((p = dbUtil.getProductByModel(model)) == null) {
                cancelEditAllowed = false;
                loadingBackground.setVisible(true);
                notFound.setVisible(true);
                notFound.focusYesButton();
                currentTextField = myTextField;
            } else {
                cancelEditAllowed = true;
                if (getItem().isEmpty()) {
                    items.add(getIndex(), new NewPurchaseItem(p));
                } else {
                    items.get(getIndex()).setItem(p);
                }
                super.commitEdit(p.getModel());
                setTotalTextFields();
                Platform.runLater(focusNextTextField);
            }
        }
    }
}