package com.hifumi123.hashfile.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

public class ChecksumUtils {

	private static final int _1M = 1024 * 1024;

	private ChecksumUtils() {
	}

	public static String crc32Hex(File file, int bufSize) throws FileNotFoundException, IOException {
		CRC32 crc = new CRC32();

		byte[] buf = new byte[bufSize];

		FileInputStream fis = new FileInputStream(file);
		CheckedInputStream cis = new CheckedInputStream(fis, crc);

		while (cis.read(buf) != -1) {
		}

		cis.close();

		return Long.toHexString(crc.getValue());
	}

	public static String crc32Hex(File file) throws FileNotFoundException, IOException {
		return crc32Hex(file, _1M);
	}
}
