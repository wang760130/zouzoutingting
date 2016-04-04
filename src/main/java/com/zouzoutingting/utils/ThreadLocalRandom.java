package com.zouzoutingting.utils;

import java.util.Random;

public class ThreadLocalRandom extends Random {
	private static final long multiplier = 25214903917L;
	private static final long addend = 11L;
	private static final long mask = 281474976710655L;
	private long rnd;
	boolean initialized;
	private long pad0;
	private long pad1;
	private long pad2;
	private long pad3;
	private long pad4;
	private long pad5;
	private long pad6;
	private long pad7;
	private static final ThreadLocal<ThreadLocalRandom> localRandom = new ThreadLocal() {
		protected ThreadLocalRandom initialValue() {
			return new ThreadLocalRandom();
		}
	};
	private static final long serialVersionUID = -5851777807851030925L;

	public static ThreadLocalRandom current() {
		return ((ThreadLocalRandom) localRandom.get());
	}

	public void setSeed(long seed) {
		if (this.initialized)
			throw new UnsupportedOperationException();
		this.initialized = true;
		this.rnd = ((seed ^ 0xDEECE66D) & 0xFFFFFFFF);
	}

	protected int next(int bits) {
		this.rnd = (this.rnd * 25214903917L + 11L & 0xFFFFFFFF);
		return (int) (this.rnd >>> 48 - bits);
	}

	public int nextInt(int least, int bound) {
		if (least >= bound)
			throw new IllegalArgumentException();
		return (nextInt(bound - least) + least);
	}

	public long nextLong(long n) {
		if (n <= 0L) {
			throw new IllegalArgumentException("n must be positive");
		}

		long offset = 0L;
		while (n >= 2147483647L) {
			int bits = next(2);
			long half = n >>> 1;
			long nextn = ((bits & 0x2) == 0) ? half : n - half;
			if ((bits & 0x1) == 0)
				offset += n - nextn;
			n = nextn;
		}
		return (offset + nextInt((int) n));
	}

	public long nextLong(long least, long bound) {
		if (least >= bound)
			throw new IllegalArgumentException();
		return (nextLong(bound - least) + least);
	}

	public double nextDouble(double n) {
		if (n <= 0.0D)
			throw new IllegalArgumentException("n must be positive");
		return (nextDouble() * n);
	}

	public double nextDouble(double least, double bound) {
		if (least >= bound)
			throw new IllegalArgumentException();
		return (nextDouble() * (bound - least) + least);
	}
}