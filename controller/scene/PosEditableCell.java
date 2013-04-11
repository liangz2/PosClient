/*
 * Generic cell calls that is editable with double clicking with basic functionalities
 * such as the TextField show/hide for start/cancel edit
 */
package controller.scene;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.product.NewPurchaseItem;

/**
 *
 * @author Wilson
 */
public class PosEditableCell extends TableCell<NewPurchaseItem, String> {
    protected TextField myTextField;
    protected boolean isModelCell;
    protected boolean cancelEditAllowed;
    protected Label extraLabel;
    private Runnable focusTextField;
    protected Runnable focusNextTextField;
    private TableColumn columnToEdit;
    private TextField currentTextField;
    
    public PosEditableCell(final TableColumn columnToEdit) {
        // initialize isModelCell
        isModelCell = false;
        cancelEditAllowed = true;
        // next column to edit
        this.columnToEdit = columnToEdit;
        // initialize the TextField
        myTextField = new TextField();
        // set up shortcut key for this cell
        setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.F2) {
                    startEdit();
                } else if (t.getCode() == KeyCode.INSERT && t.isControlDown()) {
                    cancelEdit();
                }
            }
        });
        // things to do when enter is hit
        myTextField.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                commitEdit(myTextField.getText());
            }
            
        });
        // exit editing mode when focus is changed
        myTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (ov.getValue() == false) {
                    if (cancelEditAllowed) {
                        cancelEdit();
                    }
                }
            }
        });
        // remove the necessity to click on the cell three times in order
        // to enter edit mode
        focusTextField = new Runnable() {
            @Override
            public void run() {
                myTextField.requestFocus();
            }
        };
        // focus the next column
        focusNextTextField = new Runnable() {
            @Override
            public void run() {
                if (columnToEdit != null) {
                    getTableView().edit(getIndex(), columnToEdit);
                }
            }
        };
        // disable editing to start with if not
        setEditable(false);
    }
    
    @Override
    public void startEdit() {
        super.startEdit();
        if (isEditable()) {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(myTextField);
            myTextField.setText(getItem());
            currentTextField = myTextField;
            Platform.runLater(focusTextField);
        }
    }
    
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            // enable editing for the current cell only
            if(isModelCell || (!item.isEmpty() && !isEditable())) {
                setEditable(true);
            }
            if(isEditing()) {
                myTextField.setText(getItem());
                setGraphic(myTextField);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                if (item.isEmpty()) {
                    if (isModelCell) {
                        setGraphic(extraLabel);
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }
                } else {
                    setText(item);
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        } else {
            setText(null);
            setGraphic(null);
        }
    }
    
    /**
    * 
    * @param cancelEdit 
    */
   public void setCancelEditAllowed(boolean cancelEditAllowed) {
       this.cancelEditAllowed = cancelEditAllowed;
   }
}
