package com.auction.client.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // 1. Load FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/login.fxml"));
        Parent root = loader.load();

        // 2. Dùng StackPane để căn giữa cái form trắng vào giữa màn hình
        StackPane wrapper = new StackPane();
        wrapper.getChildren().add(root);

        // 3. Tạo Scene (Chỉ tạo Scene từ cái wrapper, KHÔNG tạo từ root nữa)
        Scene scene = new Scene(wrapper, 900, 650);

        // 4. Add CSS
        String css = getClass().getResource("/com/auction/client/css/auth-styles.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("SÀN ĐẤU GIÁ TRỰC TUYẾN");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
