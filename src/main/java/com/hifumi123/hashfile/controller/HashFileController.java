package com.hifumi123.hashfile.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.hifumi123.hashfile.entity.Result;
import com.hifumi123.hashfile.util.ChecksumUtils;
import com.hifumi123.hashfile.util.MessageDigestUtils;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class HashFileController {

	@FXML
	private VBox rootVBox;

	@FXML
	private TextArea resultTextArea;

	@FXML
	private CheckBox md5CheckBox;

	@FXML
	private CheckBox sha1CheckBox;

	@FXML
	private CheckBox crc32CheckBox;

	@FXML
	private Label stateLabel;

	private FileChooser fileChooser;

	private File lastFile;

	@FXML
	private void initialize() {
		fileChooser = new FileChooser();

		lastFile = new File(".");
	}

	@FXML
	private void openAndHashFile(ActionEvent event) {
		fileChooser.setTitle("打开并校验文件");
		fileChooser.setInitialDirectory(lastFile);
		fileChooser.getExtensionFilters().clear();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("文件", "*.*"));

		File file = fileChooser.showOpenDialog(rootVBox.getScene().getWindow());
		if (file != null) {
			stateLabel.setText("校验中...");

			Result result = new Result();
			result.setFilePath(file.getAbsolutePath());
			result.setFileSize(Long.toString(file.length()));

			Task<Void> task = new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					if (md5CheckBox.isSelected()) {
						String md5Hex = MessageDigestUtils.md5Hex(file);

						result.setMd5Hex(md5Hex);
					}

					if (sha1CheckBox.isSelected()) {
						String sha1Hex = MessageDigestUtils.sha1Hex(file);

						result.setSha1Hex(sha1Hex);
					}

					if (crc32CheckBox.isSelected()) {
						String crc32Hex = ChecksumUtils.crc32Hex(file);

						result.setCrc32Hex(crc32Hex);
					}

					return null;
				}
			};
			task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

				@Override
				public void handle(WorkerStateEvent event) {
					stateLabel.setText("校验完成");

					String text = resultTextArea.getText();
					if (text != null && !text.equals("")) {
						resultTextArea.appendText(System.lineSeparator());
					}

					resultTextArea.appendText(result.toString());
				}
			});

			Thread t = new Thread(task);
			t.setDaemon(true);
			t.start();
		}
	}

	@FXML
	private void clearResultTextArea(ActionEvent event) {
		resultTextArea.clear();
	}

	@FXML
	private void copyToClipboard(ActionEvent event) {
		String text = resultTextArea.getText();
		if (text != null && !text.equals("")) {
			ClipboardContent content = new ClipboardContent();
			content.putString(text);

			Clipboard clipboard = Clipboard.getSystemClipboard();
			clipboard.setContent(content);
		}
	}

	@FXML
	private void saveResultAsFile(ActionEvent event) {
		String text = resultTextArea.getText();
		if (text != null && !text.equals("")) {
			fileChooser.setTitle("保存校验结果");
			fileChooser.setInitialDirectory(lastFile);
			fileChooser.getExtensionFilters().clear();
			fileChooser.getExtensionFilters().add(new ExtensionFilter("文本格式", "*.txt"));

			File file = fileChooser.showSaveDialog(rootVBox.getScene().getWindow());
			if (file != null) {
				try {
					writeResult(file, text);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void writeResult(File file, String resultText) throws IOException {
		FileWriter fw = new FileWriter(file);

		fw.write(resultText);
		fw.flush();

		fw.close();
	}
}
