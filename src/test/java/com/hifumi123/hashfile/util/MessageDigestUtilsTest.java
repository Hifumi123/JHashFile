package com.hifumi123.hashfile.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessageDigestUtilsTest {

	private File testFile;

	@BeforeEach
	public void init() {
		try {
			testFile = new File(getClass().getResource("/test-file/001.txt").toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@AfterEach
	public void over() {
		testFile = null;
	}

	@Test
	public void md5HexTest() {
		String md5 = "B22F2FE3E52E0AB17011E126FBD14F05";

		try {
			String md5Hex = MessageDigestUtils.md5Hex(testFile);

			Assertions.assertTrue(md5.equalsIgnoreCase(md5Hex));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void sha1HexTest() {
		String sha1 = "09BCA6E5F9463FC96BD8D3FFF5AB5AC848828109";

		try {
			String sha1Hex = MessageDigestUtils.sha1Hex(testFile);

			Assertions.assertTrue(sha1.equalsIgnoreCase(sha1Hex));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void sha256HexTest() {
		String sha256 = "ebf5d369faaab60da6325ae80d8fcf17e957d1ec3229820e62ba0abb9ecfd5f3";

		try {
			String sha256Hex = MessageDigestUtils.sha256Hex(testFile);

			Assertions.assertTrue(sha256.equalsIgnoreCase(sha256Hex));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void sha512HexTest() {
		String sha512 = "622ecb073d05089d10f4822ce3d6769fb0557aa8ed8556244d5e9ea7bd0909443228c2cd00826adbbc5b9a544125ed7dd4968750f14692135f7e03a81c347566";

		try {
			String sha512Hex = MessageDigestUtils.sha512Hex(testFile);

			Assertions.assertTrue(sha512.equalsIgnoreCase(sha512Hex));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
