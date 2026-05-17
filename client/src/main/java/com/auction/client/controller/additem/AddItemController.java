package com.auction.client.controller.additem;

import com.auction.client.controller.additem.buttonhandler.CloseButton;
import com.auction.client.controller.additem.buttonhandler.SaveButton;
import com.auction.client.controller.additem.buttonhandler.TypeChangeButton;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddItemController {
    //FXML fields:
    @FXML private ComboBox<String> cbItemType;
    @FXML private TextField txtName, txtPrice;
    @FXML private TextArea txtDescription;
    @FXML private VBox vboxDynamicFields;
    @FXML private Button closeButton, saveButton;
    private TextField txtExtra;
    //other fields
    private int sellerId;

    @FXML
    public void initialize() {
        // Xóa trắng dữ liệu cũ nếu có và thêm các tùy chọn
        cbItemType.getItems().clear();
        cbItemType.getItems().addAll("Nghệ thuật (Art)", "Điện tử (Electronics)", "Phương tiện (Vehicle)");

        //Set on action for buttons:
        this.closeButton.setOnAction(event -> new CloseButton().handle(this));

        this.saveButton.setOnAction(event -> new SaveButton().handle(this, new CloseButton()));

        this.cbItemType.setOnAction(event -> new TypeChangeButton().handle(this));
    }


    // method for other classes to call
    public ComboBox<String> getCbItemType() {return cbItemType;}
    public TextField getTxtName() {return txtName;}
    public TextArea getTxtDescription() {return txtDescription;}
    public VBox getVboxDynamicFields() {return vboxDynamicFields;}
    public TextField getTxtPrice() {return txtPrice;}
    public TextField getTxtExtra() {return txtExtra;}
    public void setTxtExtra(TextField txtExtra) {this.txtExtra = txtExtra;}
    public void setSellerId(int sellerId){
        this.sellerId=sellerId;
    }

    public int getSellerId(){
        return this.sellerId;
    }
}