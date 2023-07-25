package com.hifumi123.hashfile;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox root = FXMLLoader.load(getClass().getResource("/fxml/HashFileView.fxml"));

		Scene scene = new Scene(root);

		primaryStage.setTitle("JHashFile");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
