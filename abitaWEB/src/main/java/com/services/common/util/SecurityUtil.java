/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.common.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Util to encode password.
 * @author
 *
 */
public final class SecurityUtil {

	/** BYTE_HEXA_CODE */
	private static final int BYTE_HEXA_CODE = 0x0F;

	/** BYTE_HEXA_CODE_240 */
	private static final int BYTE_HEXA_CODE_240 = 0xF0;

	/** NB_CAR_HEXA_OCTET */
	private static final int NB_CAR_HEXA_OCTET = 2;

	/** NB_BYTES_OCTET */
	private static final int NB_BYTES_OCTET = 4;

	/** NB_BYTES_4_OCTET */
	private static final int NB_BYTES_4_OCTET = 16;

	/** LOG */
	private static final Log LOG = LogFactory.getLog(com.services.common.util.SecurityUtil.class);

	/**
	 * Constructor
	 */
	private SecurityUtil() {
		super();
	}

	/**
	 * security key for encoding and decoding
	 */
	public static final String SECRET_KEY = "mnemo0071234567887654321";

	/**
	 * Encode password with DESede
	 * @param passwd password
	 * @param secret secret key.
	 * @return encoded password
	 */
	public static String encode(String passwd, String secret) {
		if (passwd == null || secret == null) {
			return null;
		}
		// Uses triple DES; password is up to 24 bytes long
		try {
			SecretKey key = generateSecretKey(secret);
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] eba = cipher.doFinal(passwd.getBytes());
			return byteArray2String(eba);
		} catch (InvalidKeyException invalidKeyException) {
            LOG.error(invalidKeyException.getMessage());
            throw new RuntimeException(invalidKeyException.getMessage(), invalidKeyException);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            LOG.error(noSuchAlgorithmException.getMessage());
            throw new RuntimeException(noSuchAlgorithmException.getMessage(), noSuchAlgorithmException);
        } catch (InvalidKeySpecException invalidKeySpecException) {
            LOG.error(invalidKeySpecException.getMessage());
            throw new RuntimeException(invalidKeySpecException.getMessage(), invalidKeySpecException);
        } catch (NoSuchPaddingException noSuchPaddingException) {
            LOG.error(noSuchPaddingException.getMessage());
            throw new RuntimeException(noSuchPaddingException.getMessage(), noSuchPaddingException);
        } catch (IllegalBlockSizeException illegalBlockSizeException) {
            LOG.error(illegalBlockSizeException.getMessage());
            throw new RuntimeException(illegalBlockSizeException.getMessage(), illegalBlockSizeException);
        } catch (BadPaddingException badPaddingException) {
            LOG.error(badPaddingException.getMessage());
            throw new RuntimeException(badPaddingException.getMessage(), badPaddingException);
        }
	}

	/**
	 * Decode password with DESede
	 * @param passwd password
	 * @param secret secret key.
	 * @return unencoded password
	 */
	public static String decode(String passwd, String secret) {
		if (passwd == null || secret == null) {
			return null;
		}
		// Uses triple DES; password is up to 24 bytes long
		try {
			SecretKey key = generateSecretKey(secret);
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] encoded = string2byteArray(passwd);
			byte[] dba = cipher.doFinal(encoded);
			return new String(dba);
		} catch (InvalidKeyException invalidKeyException) {
		    LOG.error(invalidKeyException.getMessage());
		    throw new RuntimeException(invalidKeyException.getMessage(), invalidKeyException);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            LOG.error(noSuchAlgorithmException.getMessage());
            throw new RuntimeException(noSuchAlgorithmException.getMessage(), noSuchAlgorithmException);
        } catch (InvalidKeySpecException invalidKeySpecException) {
            LOG.error(invalidKeySpecException.getMessage());
            throw new RuntimeException(invalidKeySpecException.getMessage(), invalidKeySpecException);
        } catch (NoSuchPaddingException noSuchPaddingException) {
            LOG.error(noSuchPaddingException.getMessage());
            throw new RuntimeException(noSuchPaddingException.getMessage(), noSuchPaddingException);
        } catch (IllegalBlockSizeException illegalBlockSizeException) {
            LOG.error(illegalBlockSizeException.getMessage());
            throw new RuntimeException(illegalBlockSizeException.getMessage(), illegalBlockSizeException);
        } catch (BadPaddingException badPaddingException) {
            LOG.error(badPaddingException.getMessage());
            throw new RuntimeException(badPaddingException.getMessage(), badPaddingException);
        }
	}

	/**
	 * @param data byte array to transform
	 * @return byte array transformed to string
	 */
	private static String byteArray2String(byte[] data) {
		StringBuilder builder = new StringBuilder();
		for (byte dataByte : data) {
			// Un octet est represente par 2 caracteres hexadecimaux
			builder.append(Integer.toHexString((dataByte & BYTE_HEXA_CODE_240) >> NB_BYTES_OCTET));
			builder.append(Integer.toHexString(dataByte & BYTE_HEXA_CODE));
		}
		return builder.toString();
	}

	/**
	 * Convert an hexadecimal string to byte array.
	 * @param data data.length % 2 = 0
	 * @return string transformed to byte array
	 */
	public static byte[] string2byteArray(String data) {
		// Verification des arguments
		int length = data.length();
		if (length % NB_CAR_HEXA_OCTET != 0) {
			throw new IllegalArgumentException("Odd number of characters.");
		}

		// 2 caracteres hexadecimaux sont utilises pour representer un octet
		try {
			byte[] bytes = new byte[length / NB_CAR_HEXA_OCTET];
			for (int i = 0, j = 0; i < length; i = i + NB_CAR_HEXA_OCTET) {
				bytes[j++] = (byte) Integer.parseInt(data.substring(i, i + NB_CAR_HEXA_OCTET), NB_BYTES_4_OCTET);
			}
			return bytes;
		} catch (NumberFormatException numberFormatException) {
			throw new IllegalArgumentException("Illegal hexadecimal character.", numberFormatException);
		}
	}

	/**
	 * @param secret secret
	 * @return secret key
	 * @throws NoSuchAlgorithmException exception on generating secret key
	 * @throws InvalidKeyException exception on generating secret key
	 * @throws InvalidKeySpecException exception on generating secret key
	 */
	private static SecretKey generateSecretKey(String secret)
        throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {

		SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
		DESedeKeySpec keyspec = new DESedeKeySpec(secret.getBytes());
		return factory.generateSecret(keyspec);
	}
}
