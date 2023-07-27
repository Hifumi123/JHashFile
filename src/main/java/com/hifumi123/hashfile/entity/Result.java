package com.hifumi123.hashfile.entity;

public class Result {

	private String filePath;

	private String fileSize;

	private String md5Hex;

	private String sha1Hex;

	private String sha256Hex;

	private String sha512Hex;

	private String crc32Hex;

	public Result() {
		filePath = null;
		fileSize = null;
		md5Hex = null;
		sha1Hex = null;
		sha256Hex = null;
		sha512Hex = null;
		crc32Hex = null;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getMd5Hex() {
		return md5Hex;
	}

	public void setMd5Hex(String md5Hex) {
		this.md5Hex = md5Hex;
	}

	public String getSha1Hex() {
		return sha1Hex;
	}

	public void setSha1Hex(String sha1Hex) {
		this.sha1Hex = sha1Hex;
	}

	public String getSha256Hex() {
		return sha256Hex;
	}

	public void setSha256Hex(String sha256Hex) {
		this.sha256Hex = sha256Hex;
	}

	public String getSha512Hex() {
		return sha512Hex;
	}

	public void setSha512Hex(String sha512Hex) {
		this.sha512Hex = sha512Hex;
	}

	public String getCrc32Hex() {
		return crc32Hex;
	}

	public void setCrc32Hex(String crc32Hex) {
		this.crc32Hex = crc32Hex;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (filePath != null) {
			sb.append("文件：");
			sb.append(filePath);
			sb.append(System.lineSeparator());
		}

		if (fileSize != null) {
			sb.append("大小：");
			sb.append(fileSize);
			sb.append(" 字节");
			sb.append(System.lineSeparator());
		}

		if (md5Hex != null) {
			sb.append("MD5：");
			sb.append(md5Hex);
			sb.append(System.lineSeparator());
		}

		if (sha1Hex != null) {
			sb.append("SHA1：");
			sb.append(sha1Hex);
			sb.append(System.lineSeparator());
		}

		if (sha256Hex != null) {
			sb.append("SHA256：");
			sb.append(sha256Hex);
			sb.append(System.lineSeparator());
		}

		if (sha512Hex != null) {
			sb.append("SHA512：");
			sb.append(sha512Hex);
			sb.append(System.lineSeparator());
		}

		if (crc32Hex != null) {
			sb.append("CRC32：");
			sb.append(crc32Hex);
			sb.append(System.lineSeparator());
		}

		return sb.toString();
	}
}
