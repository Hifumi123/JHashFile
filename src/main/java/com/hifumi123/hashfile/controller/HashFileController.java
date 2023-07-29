package com.hifumi123.hashfile.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.hifumi123.hashfile.entity.Result;
import com.hifumi123.hashfile.util.ChecksumUtils;
import com.hifumi123.hashfile.util.MessageDigestUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
	private CheckBox sha256CheckBox;

	@FXML
	private CheckBox sha512CheckBox;

	@FXML
	private CheckBox crc32CheckBox;

	@FXML
	private TextField checkInputTextField;

	@FXML
	private ChoiceBox<String> checkAlgorithmChoiceBox;

	@FXML
	private Label checkResultLabel;

	@FXML
	private Label stateLabel;

	private FileChooser fileChooser;

	private File lastFile;

	private ObservableList<String> checkAlgorithmNameList;

	@FXML
	private void initialize() {
		fileChooser = new FileChooser();

		lastFile = new File(".");

		checkAlgorithmNameList = FXCollections.observableArrayList();
		checkAlgorithmNameList.add("无");

		addCheckAlgorithmName(md5CheckBox);
		addCheckAlgorithmName(sha1CheckBox);
		addCheckAlgorithmName(sha256CheckBox);
		addCheckAlgorithmName(sha512CheckBox);
		addCheckAlgorithmName(crc32CheckBox);

		checkAlgorithmChoiceBox.setItems(checkAlgorithmNameList);
		checkAlgorithmChoiceBox.setValue(checkAlgorithmNameList.get(0));

		addListenerForAlgorithmCheckBox(md5CheckBox);
		addListenerForAlgorithmCheckBox(sha1CheckBox);
		addListenerForAlgorithmCheckBox(sha256CheckBox);
		addListenerForAlgorithmCheckBox(sha512CheckBox);
		addListenerForAlgorithmCheckBox(crc32CheckBox);
	}

	private void addCheckAlgorithmName(CheckBox checkBox) {
		if (checkBox.isSelected()) {
			checkAlgorithmNameList.add(checkBox.getText());
		}
	}

	private void addListenerForAlgorithmCheckBox(CheckBox checkBox) {
		checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				checkAlgorithmNameList.add(checkBox.getText());
			} else {
				checkAlgorithmNameList.remove(checkBox.getText());
			}
		});
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

					if (sha256CheckBox.isSelected()) {
						String sha256Hex = MessageDigestUtils.sha256Hex(file);

						result.setSha256Hex(sha256Hex);
					}

					if (sha512CheckBox.isSelected()) {
						String sha512Hex = MessageDigestUtils.sha512Hex(file);

						result.setSha512Hex(sha512Hex);
					}

					if (crc32CheckBox.isSelected()) {
						String crc32Hex = ChecksumUtils.crc32Hex(file);

						result.setCrc32Hex(crc32Hex);
					}

					String checkInput = checkInputTextField.getText();
					if (checkInput != null && !checkInput.equals("")) {
						String checkAlgorithmName = checkAlgorithmChoiceBox.getSelectionModel().getSelectedItem();
						switch (checkAlgorithmName) {
						case "MD5":
							result.setCheckResult(checkInput.equals(result.getMd5Hex()) ? "一致" : "不一致");
							break;
						case "SHA1":
							result.setCheckResult(checkInput.equals(result.getSha1Hex()) ? "一致" : "不一致");
							break;
						case "SHA256":
							result.setCheckResult(checkInput.equals(result.getSha256Hex()) ? "一致" : "不一致");
							break;
						case "SHA512":
							result.setCheckResult(checkInput.equals(result.getSha512Hex()) ? "一致" : "不一致");
							break;
						case "CRC32":
							result.setCheckResult(checkInput.equals(result.getCrc32Hex()) ? "一致" : "不一致");
							break;
						default:
							break;
						}
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

					if (result.getCheckResult() == null) {
						checkResultLabel.setText("");
					} else {
						String checkResult = result.getCheckResult();
						checkResultLabel.setText(checkResult);
						checkResultLabel.setTextFill(checkResult.equals("一致") ? Color.LIMEGREEN : Color.RED);
					}
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
