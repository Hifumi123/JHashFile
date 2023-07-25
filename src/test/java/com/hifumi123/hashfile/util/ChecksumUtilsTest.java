package com.hifumi123.hashfile.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChecksumUtilsTest {

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
	public void crc32HexTest() {
		String crc32 = "649FFE67";
		int bufSize = 8;

		try {
			String crc32Hex1 = ChecksumUtils.crc32Hex(testFile, bufSize);

			Assertions.assertTrue(crc32.equalsIgnoreCase(crc32Hex1));

			String crc32Hex2 = ChecksumUtils.crc32Hex(testFile);

			Assertions.assertTrue(crc32.equalsIgnoreCase(crc32Hex2));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
