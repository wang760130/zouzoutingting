package com.zouzoutingting.utils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年5月31日
 */
public class IdUtil {

	private static final long ONE_STEP = 7;
	private static final Lock LOCK = new ReentrantLock();
	private static long lastTime = System.currentTimeMillis();
	private static short lastCount = 0;
	private static final long idSeed = 317300400000L;
	static final long sequenceBits = 3L;
	static final long sequenceMask = -1L ^ -1L << sequenceBits;

	public static long getId() {
		LOCK.lock();
		try {
			long now = System.currentTimeMillis();
			if (now == lastTime) {
				if (lastCount == ONE_STEP) {
					boolean done = false;
					while (!done) {
						now = System.currentTimeMillis();
						if (now == lastTime) {
							try {
								Thread.currentThread();
								Thread.sleep(1);
							} catch (InterruptedException e) {
							}
							continue;
						} else {
							lastTime = now;
							lastCount = 0;
							done = true;
						}
					}
				}
			} else {
				lastTime = now;
				lastCount = 0;
			}

			long genid = (lastTime - idSeed) << sequenceBits | lastCount;
			genid = ((genid << 3) << 6);
			lastCount++;
			return genid;
		} finally {
			LOCK.unlock();
		}
	}
	
}
