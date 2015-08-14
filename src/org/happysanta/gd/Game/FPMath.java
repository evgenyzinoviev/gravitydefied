package org.happysanta.gd.Game;

// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 


public class FPMath {

	//	public static final int UNSIGNED_MASK = 0x7fffffff;
//	public static final int SIGN_MASK = 0x80000000;
	//public static final int m_ifI;
//	public static final int m_aI = 1;
	public static int HALF_PI = 0x19220; // 1,57080949111162
	//	public static int DOUBLE_PI = 0x6487f; // 6,283192187380789
	public static int PI = 0x3243f; // 3,141588464179446
	//	public static int ONE = 0x10000;
	private static int m_jI;
	private static int m_kI;
//	private static int SIN_TABLE[] = {
//			0, 1608, 3215, 4821, 6423, 8022, 9616, 11204, 12785, 14359,
//			15923, 17479, 19024, 20557, 22078, 23586, 25079, 26557, 28020, 29465,
//			30893, 32302, 33692, 35061, 36409, 37736, 39039, 40319, 41575, 42806,
//			44011, 45189, 46340, 47464, 48558, 49624, 50660, 51665, 52639, 53581,
//			54491, 55368, 56212, 57022, 57797, 58538, 59243, 59913, 60547, 61144,
//			61705, 62228, 62714, 63162, 63571, 63943, 64276, 64571, 64826, 65043,
//			65220, 65358, 65457, 65516
//	};
//	private static int ARCTG_TABLE[] = {
//			0, 1023, 2047, 3069, 4090, 5109, 6126, 7139, 8149, 9155,
//			10157, 11155, 12146, 13133, 14113, 15087, 16054, 17015, 17967, 18912,
//			19849, 20778, 21698, 22610, 23512, 24405, 25289, 26163, 27027, 27882,
//			28726, 29561, 30385, 31199, 32003, 32796, 33579, 34352, 35114, 35866,
//			36608, 37339, 38060, 38771, 39471, 40161, 40841, 41512, 42172, 42822,
//			43463, 44094, 44716, 45328, 45931, 46524, 47109, 47684, 48251, 48809,
//			49358, 49899, 50431, 50955
//	};

	public FPMath() {
	}

	public static int divide(int i, int j) {
		int res = (int) (((long) i << 32) / (long) j >> 16);
		return res;
	}

	public static int sin(int i) {
		float fi = i / (float) 0xFFFF;
		return (int) Math.round(Math.sin(fi) * 65536);
	}

	public static int _doII(int i) {
		return sin(HALF_PI - i);
	}

	public static int arctg(int i) {
		float fi = i / (float) 0xFFFF;
		return (int) Math.round(Math.atan(fi) * 65536);
	}

	public static int _ifIII(int i, int j) {
		if ((j >= 0 ? j : -j) < 3)
			return (i <= 0 ? -1 : 1) * HALF_PI;
		int k = arctg(divide(i, j));
		if (i > 0)
			if (j > 0)
				return k;
			else
				return PI + k;
		if (j > 0)
			return k;
		else
			return k - PI;
	}

	static {
		m_jI = 64;
		m_kI = m_jI << 16;
	}
}
