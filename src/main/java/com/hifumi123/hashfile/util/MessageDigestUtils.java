package com.hifumi123.hashfile.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

public class MessageDigestUtils {

	private MessageDigestUtils() {
	}

	public static String md5Hex(File file) throws IOException {
		DigestUtils du = new DigestUtils(MessageDigestAlgorithms.MD5);
		return du.digestAsHex(file);
	}

	public static String sha1Hex(File file) throws IOException {
		DigestUtils du = new DigestUtils(MessageDigestAlgorithms.SHA_1);
		return du.digestAsHex(file);
	}

	public static String sha256Hex(File file) throws IOException {
		DigestUtils du = new DigestUtils(MessageDigestAlgorithms.SHA_256);
		return du.digestAsHex(file);
	}

	public static String sha512Hex(File file) throws IOException {
		DigestUtils du = new DigestUtils(MessageDigestAlgorithms.SHA_512);
		return du.digestAsHex(file);
	}
}
