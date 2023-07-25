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
}
