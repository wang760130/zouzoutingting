package com.zouzoutingting.components.encrypt;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * RSA加密
 * 
 * @author Jerry Wang
 * @Email jerry002@126.com
 * @date 2016年5月28日
 */
public class RSA {

	private static final SecureRandom RANDOM = new SecureRandom();

	private static final BigInteger ONE = new BigInteger("1");

	private static final BigInteger MODULUS = new BigInteger("78185735917280884953975858401670427062416348842126247184258915790046404096631298654272294472800717591233496523850204117229658501401811253286123416021792617297526376561508771763103868923619720148995258295259751638276346330698747026451035848215530295246105020156373622354043938519225845507723806238534289913909");

	private static final BigInteger PUBLIC_KEY = new BigInteger("65537");

	private static final BigInteger PRIVATE_KEY = new BigInteger("3621952397040828336974086487136600951546394175575557112034576931177516255510209846565614630200085121488394272646126916091814443905822649266470401315930883809526241086620656591891710638431238183560399220026512900197559620399128518705932391233177109569523856568972592703799670458169084771825331290832203329921");
//	private static final String CHARSET = "UTF-8";

	public static void genKeys(int n) {
		BigInteger p = BigInteger.probablePrime(n / 2, RANDOM);
		BigInteger q = BigInteger.probablePrime(n / 2, RANDOM);
		BigInteger phi = p.subtract(ONE).multiply(q.subtract(ONE));
		BigInteger modulus = p.multiply(q);
		BigInteger publicKey = new BigInteger("65537");
		BigInteger privateKey = publicKey.modInverse(phi);
		System.out.println("modulus=" + modulus);
		System.out.println("public key=" + publicKey);
		System.out.println("private key=" + privateKey);
	}

	public static BigInteger encrypt(BigInteger message) {
		return message.modPow(PUBLIC_KEY, MODULUS);
	}

	public static BigInteger decrypt(BigInteger encrypted) {
		return encrypted.modPow(PRIVATE_KEY, MODULUS);
	}

	public static BigInteger encrypt(String message)
			throws UnsupportedEncodingException {
		return encrypt(convertString(message));
	}

	private static BigInteger convertString(String s)
			throws UnsupportedEncodingException {
		return new BigInteger(s.getBytes("UTF-8"));
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String message = "ASDUHF8097876986SDJKJHFGDKJAWAWAW";
		System.out.println(encrypt(message));
		System.out.println(decrypt(encrypt(message)));
		System.out.println(new String(decrypt(encrypt(message)).toByteArray()));
	}

}
