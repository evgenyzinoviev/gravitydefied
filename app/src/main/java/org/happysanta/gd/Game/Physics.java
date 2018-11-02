package org.happysanta.gd.Game;

// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 


import org.happysanta.gd.Levels.Loader;
import org.happysanta.gd.Menu.SimpleMenuElement;

import static org.happysanta.gd.Helpers.getGDActivity;
import static org.happysanta.gd.Helpers.getLevelLoader;

public class Physics {

	/*public static final int m_agI = 1;
	//public static final int m_byteI;
	public static final int m_ahI = 1;
	public static final int m_LI = 2;
	public static final int m_uI = 3;
	//public static final int m_aI;
	public static final int m_VI = 1;
	public static final int m_BI = 2;
	public static final int m_acI = 3;
	public static final int m_newI = 4;
	public static final int m_ZI = 5 */
	public static int m_YI;
	public static int m_voidI;
	public static int m_gI;
	public static int m_fI;
	public static int m_eI;
	public static int m_aeI;
	public static int m_adI;
	public static int m_yI;
	public static int m_qI;
	public static int m_xI;
	public static int m_foraI[] = {
			0x1c000, 0x10000, 32768
	};
	public static int m_PI;
	public static int m_jI;
	public static int m_QI;
	public static int m_charI;
	public static int m_abI;
	public static int m_WI;
	public static int m_AI;
	public static int m_longI;
	public static int m_hI = 0;
	// private final int m_pI = 3276;
	private final int m_KaaI[][] = {
			{
					0x2cccc, -52428
			}, {
			0x40000, 0xfffd8000
	}, {
			0x63333, 0xffff0000
	}, {
			0x6cccc, -39321
	}, {
			0x39999, 39321
	}, {
			16384, 0xfffdcccd
	}, {
			13107, 0xfffecccd
	}, {
			0x46666, 0x14000
	}
	};
	private final int m_ucaaI[][] = {
			{
					0x2e666, 0xfffe4ccd
			}, {
			0x4b333, 0xfffc6667
	}, {
			0x51999, 0xfffe4000
	}, {
			0x60000, -58982
	}, {
			0x40000, 0x18000
	}, {
			0x10000, 0xfffe199a
	}, {
			13107, 0xfffecccd
	}, {
			0x46666, 0x14000
	}
	};
	private final int m_SaaI[][] = {
			{
					0x26666, 13107
			}, {
			0x48000, -13107
	}, {
			0x59999, 0x16666
	}, {
			0x63333, 0x2e666
	}, {
			0x54ccc, 0x11999
	}, {
			39321, 0xfffe8000
	}, {
			13107, -52428
	}, {
			0x48000, 0x14000
	}
	};
	private final int m_wcaaI[][] = {
			{
					0x2cccc, -39321
			}, {
			0x40000, 0xfffe0000
	}, {
			0x60000, 0xffff0000
	}, {
			0x70000, -39321
	}, {
			0x48000, 6553
	}, {
			16384, 0xfffdcccd
	}, {
			13107, 0xfffecccd
	}, {
			0x46666, 0x14ccc
	}
	};
	private final int m_DaaI[][] = {
			{
					0x2e666, 0xfffe999a
			}, {
			0x3e666, 0xfffc6667
	}, {
			0x51999, 0xfffe4000
	}, {
			0x60000, -42598
	}, {
			0x49999, 6553
	}, {
			0x10000, 0xfffecccd
	}, {
			13107, 0xfffecccd
	}, {
			0x46666, 0x14ccc
	}
	};
	private final int m_MaaI[][] = {
			{
					0x26666, 13107
			}, {
			0x48000, -13107
	}, {
			0x59999, 0x19999
	}, {
			0x63333, 0x2b333
	}, {
			0x54ccc, 0x11999
	}, {
			39321, 0xfffe8000
	}, {
			13107, -52428
	}, {
			0x46666, 0x14ccc
	}
	};
	public k m_Hak[];
	public boolean m_bZ;
	public int m_zI;
	public boolean m_elseZ;
	public boolean m_UZ;
	public boolean m_NZ;
	SimpleMenuElement m_aaan[];
	private int m_vaI;
	private int m_waI;
	private int m_xaI;
	private SimpleMenuElement m_ian[];
	private int m_cI;
	private Loader m_lf;
	private int m_EI;
	private int m_CI;
	private boolean m_IZ;
	private boolean m_mZ;
	private int m_TI;
	private int m_kI;
	private boolean m_vZ;
	private boolean m_afZ;
	private int m_tI;
	private boolean m_dZ;
	private boolean m_FZ;
	private boolean m_XZ;
	private boolean m_wZ;
	private boolean m_ifZ;
	private boolean m_sZ;
	private boolean m_OZ;
	private boolean m_rZ;
	private boolean m_RZ;
	private boolean m_doZ;
	private int m_oI;
	private int m_nI;
	private int m_GI;
	private int m_JaaI[][] = {
			{
					45875
			}, {
			32768
	}, {
			52428
	}
	};
	private final int leftWheelUpdatingFrequency = 20;
	private long leftWheelLastUpdated = 0;
	private int leftWheelParams[][];

	public Physics(Loader f1) {
		m_vaI = 0;
		m_waI = 1;
		m_xaI = -1;
		m_cI = 0;
		m_EI = 0;
		m_CI = 0;
		m_IZ = false;
		m_mZ = false;
		m_TI = 32768;
		m_kI = 0;
		m_vZ = false;
		m_bZ = false;
		m_afZ = false;
		m_aaan = new SimpleMenuElement[6];
		for (int j = 0; j < 6; j++)
			m_aaan[j] = new SimpleMenuElement();

		m_tI = 0;
		m_zI = 0;
		m_elseZ = false;
		m_UZ = false;
		m_dZ = false;
		m_FZ = false;
		m_XZ = false;
		m_wZ = false;
		m_ifZ = false;
		m_sZ = false;
		m_OZ = false;
		m_rZ = false;
		m_RZ = false;
		m_NZ = false;
		m_doZ = true;
		m_oI = 0;
		m_nI = 0;
		m_GI = 0xa0000;
		m_lf = f1;
		_doZV(true);
		m_vZ = false;
		_charvV();
		m_IZ = false;

		leftWheelParams = new int[5][4];
	}

	public static int _doIII(int j, int i1) {
		int j1 = j >= 0 ? j : -j;
		int k1;
		int l1;
		int i2;
		if ((k1 = i1 >= 0 ? i1 : -i1) >= j1) {
			l1 = k1;
			i2 = j1;
		} else {
			l1 = j1;
			i2 = k1;
		}
		return (int) (64448L * (long) l1 >> 16) + (int) (28224L * (long) i2 >> 16);
	}

	public int _bytevI() {
		if (m_elseZ && m_UZ)
			return 3;
		if (m_UZ)
			return 1;
		return !m_elseZ ? 0 : 2;
	}

	public void _doIV(int j) {
		m_elseZ = false;
		m_UZ = false;
		if ((j & 2) != 0)
			m_elseZ = true;
		if ((j & 1) != 0)
			m_UZ = true;
	}

	public void _byteIV(int j) {
		m_zI = j;
		switch (j) {
			case 1: // '\001'
			default:
				m_YI = 1310;
				break;
		}
		m_voidI = 0x190000;
		setLeague(1);
		_doZV(true);
	}

	public void setLeague(int j) {
		m_hI = j;
		m_gI = 45875;
		m_fI = 13107;
		m_eI = 39321;
		m_yI = 0x140000;
		m_xI = 0x40000;
		m_jI = 6553;
		switch (j) {
			case 3: // '\003'
				m_aeI = 32768;
				m_adI = 32768;
				m_PI = 0x160000;
				m_QI = 0x4b00000;
				m_charI = 0x360000;
				m_abI = 6553;
				m_WI = 26214;
				m_AI = 0x10000;
				m_longI = 0x140000;
				m_qI = 0x14a0000;
				break;

			case 2: // '\002'
				m_aeI = 32768;
				m_adI = 32768;
				m_PI = 0x140000;
				m_QI = 0x47e0000;
				m_charI = 0x350000;
				m_abI = 6553;
				m_WI = 26214;
				m_AI = 39321;
				m_longI = 0x50000;
				m_qI = 0x14a0000;
				break;

			case 1: // '\001'
				m_aeI = 32768;
				m_adI = 32768;
				m_PI = 0x110000;
				m_QI = 0x3e80000;
				m_charI = 0x320000;
				m_abI = 6553;
				m_WI = 26214;
				m_AI = 26214;
				m_longI = 0x50000;
				m_qI = 0x12c0000;
				break;

			case 0: // '\0'
			default:
				m_aeI = 19660;
				m_adI = 19660;
				m_PI = 0x110000;
				m_QI = 0x3200000;
				m_charI = 0x320000;
				m_abI = 327;
				m_WI = 0;
				m_AI = 32768;
				m_longI = 0x50000;
				m_qI = 0x12c0000;
				break;
		}
		_doZV(true);
	}

	public void _doZV(boolean flag) {
		m_tI = 0;
		_iIIV(m_lf._newvI(), m_lf._avI());
		m_cI = 0;
		m_kI = 0;
		m_IZ = false;
		m_mZ = false;
		m_RZ = false;
		m_NZ = false;
		m_vZ = false;
		m_bZ = false;
		m_afZ = false;
		m_lf.levels._aIIV((m_Hak[2].m_ifan[5].x + 0x18000) - m_foraI[0], (m_Hak[1].m_ifan[5].x - 0x18000) + m_foraI[0]);
	}

	public void _aZV(boolean flag) {
		int j = (flag ? 0x10000 : 0xffff0000) << 1;
		for (int i1 = 0; i1 < 6; i1++) {
			for (int j1 = 0; j1 < 6; j1++)
				m_Hak[i1].m_ifan[j1].y += j;
		}

	}

	private void _iIIV(int j, int i1) {
		if (m_Hak == null)
			m_Hak = new k[6];
		if (m_ian == null)
			m_ian = new SimpleMenuElement[10];
		int l1 = 0;
		int i2 = 0;
		int j2 = 0;
		int k2 = 0;
		for (int j1 = 0; j1 < 6; j1++) {
			int l2 = 0;
			switch (j1) {
				case 0: // '\0'
					i2 = 1;
					l1 = 0x58000;
					j2 = 0;
					k2 = 0;
					break;

				case 4: // '\004'
					i2 = 1;
					l1 = 0x38000;
					j2 = 0xfffe0000;
					k2 = 0x30000;
					break;

				case 3: // '\003'
					i2 = 1;
					l1 = 0x38000;
					j2 = 0x20000;
					k2 = 0x30000;
					break;

				case 1: // '\001'
					i2 = 0;
					l1 = 0x18000;
					j2 = 0x38000;
					k2 = 0;
					break;

				case 2: // '\002'
					i2 = 0;
					l1 = 0x58000;
					j2 = 0xfffc8000;
					k2 = 0;
					l2 = 21626;
					break;

				case 5: // '\005'
					i2 = 2;
					l1 = 0x48000;
					j2 = 0;
					k2 = 0x50000;
					break;
			}
			if (m_Hak[j1] == null)
				m_Hak[j1] = new k();
			m_Hak[j1]._avV();
			m_Hak[j1].m_aI = m_foraI[i2];
			m_Hak[j1].m_intI = i2;
			m_Hak[j1].m_forI = (int) ((long) (int) (0x1000000000000L / (long) l1 >> 16) * (long) m_yI >> 16);
			m_Hak[j1].m_ifan[m_vaI].x = j + j2;
			m_Hak[j1].m_ifan[m_vaI].y = i1 + k2;
			m_Hak[j1].m_ifan[5].x = j + j2;
			m_Hak[j1].m_ifan[5].y = i1 + k2;
			m_Hak[j1].m_newI = l2;
		}

		for (int k1 = 0; k1 < 10; k1++) {
			if (m_ian[k1] == null)
				m_ian[k1] = new SimpleMenuElement();
			m_ian[k1].init();
			m_ian[k1].x = m_qI;
			m_ian[k1].m_bI = m_xI;
		}

		m_ian[0].y = 0x38000;
		m_ian[1].y = 0x38000;
		m_ian[2].y = 0x39b05;
		m_ian[3].y = 0x39b05;
		m_ian[4].y = 0x40000;
		m_ian[5].y = 0x35aa6;
		m_ian[6].y = 0x35aa6;
		m_ian[7].y = 0x2d413;
		m_ian[8].y = 0x2d413;
		m_ian[9].y = 0x50000;
		m_ian[5].m_bI = (int) ((long) m_xI * 45875L >> 16);
		m_ian[6].x = (int) (6553L * (long) m_qI >> 16);
		m_ian[5].x = (int) (6553L * (long) m_qI >> 16);
		m_ian[9].x = (int) (0x11999L * (long) m_qI >> 16);
		m_ian[8].x = (int) (0x11999L * (long) m_qI >> 16);
		m_ian[7].x = (int) (0x11999L * (long) m_qI >> 16);
	}

	public void _ifIIV(int j, int i1) {
		m_lf._ifIIV(j, i1);
	}

	public void _nullvV() {
		m_ifZ = m_sZ = m_rZ = m_OZ = false;
	}

	public void _aIIV(int j, int i1) {
		if (!m_vZ) {
			m_ifZ = m_sZ = m_rZ = m_OZ = false;
			if (j > 0)
				m_ifZ = true;
			else if (j < 0)
				m_sZ = true;
			if (i1 > 0) {
				m_rZ = true;
				return;
			}
			if (i1 < 0)
				m_OZ = true;
		}
	}

	public synchronized void _casevV() {
		_doZV(true);
		m_vZ = true;
	}

	public synchronized void _avV() {
		m_vZ = false;
	}

	public boolean _gotovZ() {
		return m_vZ;
	}

	private void _pvV() {
		int j = m_Hak[1].m_ifan[m_vaI].x - m_Hak[2].m_ifan[m_vaI].x;
		int i1 = m_Hak[1].m_ifan[m_vaI].y - m_Hak[2].m_ifan[m_vaI].y;
		int j1 = _doIII(j, i1);
		int _tmp = (int) (((long) j << 32) / (long) j1 >> 16);
		i1 = (int) (((long) i1 << 32) / (long) j1 >> 16);
		m_FZ = false;
		if (i1 < 0) {
			m_XZ = true;
			m_wZ = false;
		} else if (i1 > 0) {
			m_wZ = true;
			m_XZ = false;
		}
		boolean flag;
		if ((flag = (m_Hak[2].m_ifan[m_vaI].y - m_Hak[0].m_ifan[m_vaI].y <= 0 ? -1 : 1) * (m_Hak[2].m_ifan[m_vaI].m_eI - m_Hak[0].m_ifan[m_vaI].m_eI <= 0 ? -1 : 1) > 0) && m_wZ || !flag && m_XZ) {
			m_dZ = true;
			return;
		} else {
			m_dZ = false;
			return;
		}
	}

	private void _qvV() {
		if (!m_IZ) {
			int j = m_Hak[1].m_ifan[m_vaI].x - m_Hak[2].m_ifan[m_vaI].x;
			int i1 = m_Hak[1].m_ifan[m_vaI].y - m_Hak[2].m_ifan[m_vaI].y;
			int j1 = _doIII(j, i1);
			j = (int) (((long) j << 32) / (long) j1 >> 16);
			i1 = (int) (((long) i1 << 32) / (long) j1 >> 16);
			if (m_dZ && m_cI >= -m_QI)
				m_cI -= m_charI;
			if (m_FZ) {
				m_cI = 0;
				m_Hak[1].m_ifan[m_vaI].m_gotoI = (int) ((long) m_Hak[1].m_ifan[m_vaI].m_gotoI * (long) (0x10000 - m_abI) >> 16);
				m_Hak[2].m_ifan[m_vaI].m_gotoI = (int) ((long) m_Hak[2].m_ifan[m_vaI].m_gotoI * (long) (0x10000 - m_abI) >> 16);
				if (m_Hak[1].m_ifan[m_vaI].m_gotoI < 6553)
					m_Hak[1].m_ifan[m_vaI].m_gotoI = 0;
				if (m_Hak[2].m_ifan[m_vaI].m_gotoI < 6553)
					m_Hak[2].m_ifan[m_vaI].m_gotoI = 0;
			}
			m_Hak[0].m_forI = (int) (11915L * (long) m_yI >> 16);
			m_Hak[0].m_forI = (int) (11915L * (long) m_yI >> 16);
			m_Hak[4].m_forI = (int) (18724L * (long) m_yI >> 16);
			m_Hak[3].m_forI = (int) (18724L * (long) m_yI >> 16);
			m_Hak[1].m_forI = (int) (43690L * (long) m_yI >> 16);
			m_Hak[2].m_forI = (int) (11915L * (long) m_yI >> 16);
			m_Hak[5].m_forI = (int) (14563L * (long) m_yI >> 16);
			if (m_XZ) {
				m_Hak[0].m_forI = (int) (18724L * (long) m_yI >> 16);
				m_Hak[4].m_forI = (int) (14563L * (long) m_yI >> 16);
				m_Hak[3].m_forI = (int) (18724L * (long) m_yI >> 16);
				m_Hak[1].m_forI = (int) (43690L * (long) m_yI >> 16);
				m_Hak[2].m_forI = (int) (10082L * (long) m_yI >> 16);
			} else if (m_wZ) {
				m_Hak[0].m_forI = (int) (18724L * (long) m_yI >> 16);
				m_Hak[4].m_forI = (int) (18724L * (long) m_yI >> 16);
				m_Hak[3].m_forI = (int) (14563L * (long) m_yI >> 16);
				m_Hak[1].m_forI = (int) (26214L * (long) m_yI >> 16);
				m_Hak[2].m_forI = (int) (11915L * (long) m_yI >> 16);
			}
			if (m_XZ || m_wZ) {
				int k1 = -i1;
				int l1 = j;
				if (m_XZ && m_kI > -m_longI) {
					int i2 = 0x10000;
					if (m_kI < 0)
						i2 = (int) (((long) (m_longI - (m_kI >= 0 ? m_kI : -m_kI)) << 32) / (long) m_longI >> 16);
					int k2 = (int) ((long) m_AI * (long) i2 >> 16);
					int i3 = (int) ((long) k1 * (long) k2 >> 16);
					int k3 = (int) ((long) l1 * (long) k2 >> 16);
					int i4 = (int) ((long) j * (long) k2 >> 16);
					int k4 = (int) ((long) i1 * (long) k2 >> 16);
					if (m_TI > 32768)
						m_TI = m_TI - 1638 >= 0 ? m_TI - 1638 : 0;
					else
						m_TI = m_TI - 3276 >= 0 ? m_TI - 3276 : 0;
					m_Hak[4].m_ifan[m_vaI].m_eI -= i3;
					m_Hak[4].m_ifan[m_vaI].m_dI -= k3;
					m_Hak[3].m_ifan[m_vaI].m_eI += i3;
					m_Hak[3].m_ifan[m_vaI].m_dI += k3;
					m_Hak[5].m_ifan[m_vaI].m_eI -= i4;
					m_Hak[5].m_ifan[m_vaI].m_dI -= k4;
				}
				if (m_wZ && m_kI < m_longI) {
					int j2 = 0x10000;
					if (m_kI > 0)
						j2 = (int) (((long) (m_longI - m_kI) << 32) / (long) m_longI >> 16);
					int l2 = (int) ((long) m_AI * (long) j2 >> 16);
					int j3 = (int) ((long) k1 * (long) l2 >> 16);
					int l3 = (int) ((long) l1 * (long) l2 >> 16);
					int j4 = (int) ((long) j * (long) l2 >> 16);
					int l4 = (int) ((long) i1 * (long) l2 >> 16);
					if (m_TI > 32768)
						m_TI = m_TI + 1638 >= 0x10000 ? 0x10000 : m_TI + 1638;
					else
						m_TI = m_TI + 3276 >= 0x10000 ? 0x10000 : m_TI + 3276;
					m_Hak[4].m_ifan[m_vaI].m_eI += j3;
					m_Hak[4].m_ifan[m_vaI].m_dI += l3;
					m_Hak[3].m_ifan[m_vaI].m_eI -= j3;
					m_Hak[3].m_ifan[m_vaI].m_dI -= l3;
					m_Hak[5].m_ifan[m_vaI].m_eI += j4;
					m_Hak[5].m_ifan[m_vaI].m_dI += l4;
				}
				return;
			}
			if (m_TI < 26214) {
				m_TI += 3276;
				return;
			}
			if (m_TI > 39321) {
				m_TI -= 3276;
				return;
			}
			m_TI = 32768;
		}
	}

	public synchronized int _dovI() {
		m_dZ = m_ifZ;
		m_FZ = m_sZ;
		m_XZ = m_OZ;
		m_wZ = m_rZ;
		if (m_vZ)
			_pvV();
		GameView._dovV();
		_qvV();
		int j;
		if ((j = _uII(m_YI)) == 5 || m_mZ)
			return 5;
		if (m_IZ)
			return 3;
		if (_newvZ()) {
			m_NZ = false;
			return 4;
		} else {
			return j;
		}
	}

	public boolean _newvZ() {
		return m_Hak[1].m_ifan[m_vaI].x < m_lf._intvI();
	}

	public boolean _longvZ() {
		return m_Hak[1].m_ifan[m_waI].x > m_lf._dovI() || m_Hak[2].m_ifan[m_waI].x > m_lf._dovI();
	}

	private int _uII(int j) {
		boolean flag = m_RZ;
		int i1 = 0;
		int j1 = j;
		int j2;
		do {
			if (i1 >= j)
				break;
			_aaIV(j1 - i1);
			int k1;
			if (!flag && _longvZ())
				k1 = 3;
			else
				k1 = _baII(m_waI);
			if (!flag && m_RZ)
				return k1 == 3 ? 1 : 2;
			if (k1 == 0) {
				if (((j1 = i1 + j1 >> 1) - i1 >= 0 ? j1 - i1 : -(j1 - i1)) < 65)
					return 5;
			} else if (k1 == 3) {
				m_RZ = true;
				j1 = i1 + j1 >> 1;
			} else {
				int i2;
				if (k1 == 1)
					do {
						_caIV(m_waI);
						j2 = _baII(m_waI);
						i2 = j2;
						if (j2 == 0)
							return 5;
					} while (i2 != 2);
				i1 = j1;
				j1 = j;
				m_vaI = m_vaI != 1 ? 1 : 0;
				m_waI = m_waI != 1 ? 1 : 0;
			}
		} while (true);
		int l1;
		if ((l1 = (int) ((long) (m_Hak[1].m_ifan[m_vaI].x - m_Hak[2].m_ifan[m_vaI].x) * (long) (m_Hak[1].m_ifan[m_vaI].x - m_Hak[2].m_ifan[m_vaI].x) >> 16) + (int) ((long) (m_Hak[1].m_ifan[m_vaI].y - m_Hak[2].m_ifan[m_vaI].y) * (long) (m_Hak[1].m_ifan[m_vaI].y - m_Hak[2].m_ifan[m_vaI].y) >> 16)) < 0xf0000)
			m_IZ = true;
		if (l1 > 0x460000)
			m_IZ = true;
		return 0;
	}

	private void _aIV(int j) {
		for (int i1 = 0; i1 < 6; i1++) {
			k k1;
			SimpleMenuElement n1;
			(n1 = (k1 = m_Hak[i1]).m_ifan[j]).m_nullI = 0;
			n1.m_longI = 0;
			n1.m_fI = 0;
			n1.m_longI -= (int) (((long) m_voidI << 32) / (long) k1.m_forI >> 16);
		}

		if (!m_IZ) {
			_akkV(m_Hak[0], m_ian[1], m_Hak[2], j, 0x10000);
			_akkV(m_Hak[0], m_ian[0], m_Hak[1], j, 0x10000);
			_akkV(m_Hak[2], m_ian[6], m_Hak[4], j, 0x20000);
			_akkV(m_Hak[1], m_ian[5], m_Hak[3], j, 0x20000);
		}
		_akkV(m_Hak[0], m_ian[2], m_Hak[3], j, 0x10000);
		_akkV(m_Hak[0], m_ian[3], m_Hak[4], j, 0x10000);
		_akkV(m_Hak[3], m_ian[4], m_Hak[4], j, 0x10000);
		_akkV(m_Hak[5], m_ian[8], m_Hak[3], j, 0x10000);
		_akkV(m_Hak[5], m_ian[7], m_Hak[4], j, 0x10000);
		_akkV(m_Hak[5], m_ian[9], m_Hak[0], j, 0x10000);
		SimpleMenuElement n2 = m_Hak[2].m_ifan[j];
		m_cI = (int) ((long) m_cI * (long) (0x10000 - m_jI) >> 16);
		n2.m_fI = m_cI;
		if (n2.m_gotoI > m_PI)
			n2.m_gotoI = m_PI;
		if (n2.m_gotoI < -m_PI)
			n2.m_gotoI = -m_PI;
		int j1 = 0;
		int l1 = 0;
		for (int i2 = 0; i2 < 6; i2++) {
			j1 += m_Hak[i2].m_ifan[j].m_eI;
			l1 += m_Hak[i2].m_ifan[j].m_dI;
		}

		j1 = (int) (((long) j1 << 32) / 0x60000L >> 16);
		l1 = (int) (((long) l1 << 32) / 0x60000L >> 16);
		int j3 = 0;
		for (int k3 = 0; k3 < 6; k3++) {
			int j2 = m_Hak[k3].m_ifan[j].m_eI - j1;
			int k2 = m_Hak[k3].m_ifan[j].m_dI - l1;
			if ((j3 = _doIII(j2, k2)) > 0x1e0000) {
				int l2 = (int) (((long) j2 << 32) / (long) j3 >> 16);
				int i3 = (int) (((long) k2 << 32) / (long) j3 >> 16);
				m_Hak[k3].m_ifan[j].m_eI -= l2;
				m_Hak[k3].m_ifan[j].m_dI -= i3;
			}
		}

		byte byte0 = ((byte) (m_Hak[2].m_ifan[j].y - m_Hak[0].m_ifan[j].y < 0 ? -1 : 1));
		byte byte1 = ((byte) (m_Hak[2].m_ifan[j].m_eI - m_Hak[0].m_ifan[j].m_eI < 0 ? -1 : 1));
		if (byte0 * byte1 > 0) {
			m_kI = j3;
			return;
		} else {
			m_kI = -j3;
			return;
		}
	}

	private void _akkV(k k1, SimpleMenuElement n1, k k2, int j, int i1) {
		SimpleMenuElement n2 = k1.m_ifan[j];
		SimpleMenuElement n3 = k2.m_ifan[j];
		int j1 = n2.x - n3.x;
		int l1 = n2.y - n3.y;
		int i2;
		if (((i2 = _doIII(j1, l1)) >= 0 ? i2 : -i2) >= 3) {
			j1 = (int) (((long) j1 << 32) / (long) i2 >> 16);
			l1 = (int) (((long) l1 << 32) / (long) i2 >> 16);
			int j2 = i2 - n1.y;
			int l2 = (int) ((long) j1 * (long) (int) ((long) j2 * (long) n1.x >> 16) >> 16);
			int i3 = (int) ((long) l1 * (long) (int) ((long) j2 * (long) n1.x >> 16) >> 16);
			int j3 = n2.m_eI - n3.m_eI;
			int k3 = n2.m_dI - n3.m_dI;
			int l3 = (int) ((long) ((int) ((long) j1 * (long) j3 >> 16) + (int) ((long) l1 * (long) k3 >> 16)) * (long) n1.m_bI >> 16);
			l2 += (int) ((long) j1 * (long) l3 >> 16);
			i3 += (int) ((long) l1 * (long) l3 >> 16);
			l2 = (int) ((long) l2 * (long) i1 >> 16);
			i3 = (int) ((long) i3 * (long) i1 >> 16);
			n2.m_nullI -= l2;
			n2.m_longI -= i3;
			n3.m_nullI += l2;
			n3.m_longI += i3;
		}
	}

	private void _aIIV(int j, int i1, int j1) {
		for (int l1 = 0; l1 < 6; l1++) {
			SimpleMenuElement n1 = m_Hak[l1].m_ifan[j];
			SimpleMenuElement n2;
			(n2 = m_Hak[l1].m_ifan[i1]).x = (int) ((long) n1.m_eI * (long) j1 >> 16);
			n2.y = (int) ((long) n1.m_dI * (long) j1 >> 16);
			int k1 = (int) ((long) j1 * (long) m_Hak[l1].m_forI >> 16);
			n2.m_eI = (int) ((long) n1.m_nullI * (long) k1 >> 16);
			n2.m_dI = (int) ((long) n1.m_longI * (long) k1 >> 16);
		}

	}

	private void _zIIV(int j, int i1, int j1) {
		for (int k1 = 0; k1 < 6; k1++) {
			SimpleMenuElement n1 = m_Hak[k1].m_ifan[j];
			SimpleMenuElement n2 = m_Hak[k1].m_ifan[i1];
			SimpleMenuElement n3 = m_Hak[k1].m_ifan[j1];
			n1.x = n2.x + (n3.x >> 1);
			n1.y = n2.y + (n3.y >> 1);
			n1.m_eI = n2.m_eI + (n3.m_eI >> 1);
			n1.m_dI = n2.m_dI + (n3.m_dI >> 1);
		}

	}

	private void _aaIV(int j) {
		_aIV(m_vaI);
		_aIIV(m_vaI, 2, j);
		_zIIV(4, m_vaI, 2);
		_aIV(4);
		_aIIV(4, 3, j >> 1);
		_zIIV(4, m_vaI, 3);
		_zIIV(m_waI, m_vaI, 2);
		_zIIV(m_waI, m_waI, 3);

		// wheels?!?!?!?! oh my god i found it!!!!!
		for (int i1 = 1; i1 <= 2; i1++) {
			SimpleMenuElement n1 = m_Hak[i1].m_ifan[m_vaI];
			SimpleMenuElement n2;
			(n2 = m_Hak[i1].m_ifan[m_waI]).m_bI = n1.m_bI + (int) ((long) j * (long) n1.m_gotoI >> 16);
			n2.m_gotoI = n1.m_gotoI + (int) ((long) j * (long) (int) ((long) m_Hak[i1].m_newI * (long) n1.m_fI >> 16) >> 16);
		}

	}

	private int _baII(int j) {
		byte byte0 = 2;
		int i1;
		i1 = (i1 = m_Hak[1].m_ifan[j].x >= m_Hak[2].m_ifan[j].x ? m_Hak[1].m_ifan[j].x : m_Hak[2].m_ifan[j].x) >= m_Hak[5].m_ifan[j].x ? i1 : m_Hak[5].m_ifan[j].x;
		int j1;
		j1 = (j1 = m_Hak[1].m_ifan[j].x >= m_Hak[2].m_ifan[j].x ? m_Hak[2].m_ifan[j].x : m_Hak[1].m_ifan[j].x) >= m_Hak[5].m_ifan[j].x ? m_Hak[5].m_ifan[j].x : j1;
		m_lf._aIIV(j1 - m_foraI[0], i1 + m_foraI[0], m_Hak[5].m_ifan[j].y);
		int k1 = m_Hak[1].m_ifan[j].x - m_Hak[2].m_ifan[j].x;
		int l1 = m_Hak[1].m_ifan[j].y - m_Hak[2].m_ifan[j].y;
		int i2 = _doIII(k1, l1);
		k1 = (int) (((long) k1 << 32) / (long) i2 >> 16);
		int j2 = -(int) (((long) l1 << 32) / (long) i2 >> 16);
		int k2 = k1;
		for (int l2 = 0; l2 < 6; l2++) {
			if (l2 == 4 || l2 == 3)
				continue;
			SimpleMenuElement n1 = m_Hak[l2].m_ifan[j];
			if (l2 == 0) {
				n1.x += (int) ((long) j2 * 0x10000L >> 16);
				n1.y += (int) ((long) k2 * 0x10000L >> 16);
			}
			int i3 = m_lf._anvI(n1, m_Hak[l2].m_intI);
			if (l2 == 0) {
				n1.x -= (int) ((long) j2 * 0x10000L >> 16);
				n1.y -= (int) ((long) k2 * 0x10000L >> 16);
			}
			m_EI = m_lf.m_eI;
			m_CI = m_lf.m_dI;
			if (l2 == 5 && i3 != 2)
				m_mZ = true;
			if (l2 == 1 && i3 != 2)
				m_NZ = true;
			if (i3 == 1) {
				m_xaI = l2;
				byte0 = 1;
				continue;
			}
			if (i3 != 0)
				continue;
			m_xaI = l2;
			byte0 = 0;
			break;
		}

		return byte0;
	}

	private void _caIV(int j) {
		k k1;
		SimpleMenuElement n1;
		(n1 = (k1 = m_Hak[m_xaI]).m_ifan[j]).x += (int) ((long) m_EI * 3276L >> 16);
		n1.y += (int) ((long) m_CI * 3276L >> 16);
		int i1;
		int j1;
		int l1;
		int i2;
		int j2;
		if (m_FZ && (m_xaI == 2 || m_xaI == 1) && n1.m_gotoI < 6553) {
			i1 = m_gI - m_WI;
			j1 = 13107;
			l1 = 39321;
			i2 = 26214 - m_WI;
			j2 = 26214 - m_WI;
		} else {
			i1 = m_gI;
			j1 = m_fI;
			l1 = m_eI;
			i2 = m_aeI;
			j2 = m_adI;
		}
		int k2 = _doIII(m_EI, m_CI);
		m_EI = (int) (((long) m_EI << 32) / (long) k2 >> 16);
		m_CI = (int) (((long) m_CI << 32) / (long) k2 >> 16);
		int l2 = n1.m_eI;
		int i3 = n1.m_dI;
		int j3 = -((int) ((long) l2 * (long) m_EI >> 16) + (int) ((long) i3 * (long) m_CI >> 16));
		int k3 = -((int) ((long) l2 * (long) (-m_CI) >> 16) + (int) ((long) i3 * (long) m_EI >> 16));
		int l3 = (int) ((long) i1 * (long) n1.m_gotoI >> 16) - (int) ((long) j1 * (long) (int) (((long) k3 << 32) / (long) k1.m_aI >> 16) >> 16);
		int i4 = (int) ((long) i2 * (long) k3 >> 16) - (int) ((long) l1 * (long) (int) ((long) n1.m_gotoI * (long) k1.m_aI >> 16) >> 16);
		int j4 = -(int) ((long) j2 * (long) j3 >> 16);
		int k4 = (int) ((long) (-i4) * (long) (-m_CI) >> 16);
		int l4 = (int) ((long) (-i4) * (long) m_EI >> 16);
		int i5 = (int) ((long) (-j4) * (long) m_EI >> 16);
		int j5 = (int) ((long) (-j4) * (long) m_CI >> 16);
		n1.m_gotoI = l3;
		n1.m_eI = k4 + i5;
		n1.m_dI = l4 + j5;
	}

	public void _ifZV(boolean flag) {
		m_doZ = flag;
	}

	public void _caseIV(int j) {
		m_GI = (int) (((long) (int) (0xa0000L * (long) (j << 16) >> 16) << 32) / 0x800000L >> 16);
	}

	public int _elsevI() {
		if (m_doZ)
			m_oI = (int) (((long) m_aaan[0].m_eI << 32) / 0x180000L >> 16) + (int) ((long) m_oI * 57344L >> 16);
		else
			m_oI = 0;
		m_oI = m_oI >= m_GI ? m_GI : m_oI;
		m_oI = m_oI >= -m_GI ? m_oI : -m_GI;
		return (m_aaan[0].x + m_oI << 2) >> 16;
	}

	public int _ifvI() {
		if (m_doZ)
			m_nI = (int) (((long) m_aaan[0].m_dI << 32) / 0x180000L >> 16) + (int) ((long) m_nI * 57344L >> 16);
		else
			m_nI = 0;
		m_nI = m_nI >= m_GI ? m_GI : m_nI;
		m_nI = m_nI >= -m_GI ? m_nI : -m_GI;
		return (m_aaan[0].y + m_nI << 2) >> 16;
	}

	public int _tryvI() {
		int j = m_aaan[1].x >= m_aaan[2].x ? m_aaan[1].x : m_aaan[2].x;
		if (m_IZ)
			return m_lf._aII(m_aaan[0].x);
		else
			return m_lf._aII(j);
	}

	public void _charvV() {
		synchronized (m_Hak) {
			for (int j = 0; j < 6; j++) {
				m_Hak[j].m_ifan[5].x = m_Hak[j].m_ifan[m_vaI].x;
				m_Hak[j].m_ifan[5].y = m_Hak[j].m_ifan[m_vaI].y;
				m_Hak[j].m_ifan[5].m_bI = m_Hak[j].m_ifan[m_vaI].m_bI;
			}

			m_Hak[0].m_ifan[5].m_eI = m_Hak[0].m_ifan[m_vaI].m_eI;
			m_Hak[0].m_ifan[5].m_dI = m_Hak[0].m_ifan[m_vaI].m_dI;
			m_Hak[2].m_ifan[5].m_gotoI = m_Hak[2].m_ifan[m_vaI].m_gotoI;
		}
	}

	public void _voidvV() {
		synchronized (m_Hak) {
			for (int j = 0; j < 6; j++) {
				m_aaan[j].x = m_Hak[j].m_ifan[5].x;
				m_aaan[j].y = m_Hak[j].m_ifan[5].y;
				m_aaan[j].m_bI = m_Hak[j].m_ifan[5].m_bI;
			}

			m_aaan[0].m_eI = m_Hak[0].m_ifan[5].m_eI;
			m_aaan[0].m_dI = m_Hak[0].m_ifan[5].m_dI;
			m_aaan[2].m_gotoI = m_Hak[2].m_ifan[5].m_gotoI;
		}
	}

	private void _aiIV(GameView view, int i1, int j1) {
		int k1 = FPMath._ifIII(m_aaan[0].x - m_aaan[3].x, m_aaan[0].y - m_aaan[3].y);
		int l1 = FPMath._ifIII(m_aaan[0].x - m_aaan[4].x, m_aaan[0].y - m_aaan[4].y);
		int engineX = (m_aaan[0].x >> 1) + (m_aaan[3].x >> 1);
		int engineY = (m_aaan[0].y >> 1) + (m_aaan[3].y >> 1);
		int fenderX = (m_aaan[0].x >> 1) + (m_aaan[4].x >> 1);
		int fenderY = (m_aaan[0].y >> 1) + (m_aaan[4].y >> 1);
		int i3 = -j1;
		int j3 = i1;
		engineX += (int) ((long) i3 * 0x10000L >> 16) - (int) ((long) i1 * 32768L >> 16);
		engineY += (int) ((long) j3 * 0x10000L >> 16) - (int) ((long) j1 * 32768L >> 16);
		fenderX += (int) ((long) i3 * 0x10000L >> 16) - (int) ((long) i1 * 0x1ccccL >> 16);
		fenderY += (int) ((long) j3 * 0x10000L >> 16) - (int) ((long) j1 * 0x20000L >> 16);
		view.drawFender((fenderX << 2) / (float) 0xFFFF /*>> 16*/, (fenderY << 2) / (float) 0xFFFF /*>> 16*/, l1);
		view.drawEngine((engineX << 2) / (float) 0xFFFF /*>> 16*/, (engineY << 2) / (float) 0xFFFF /*>> 16*/, k1);
	}

	private void _laiV(GameView view) {
		view.setColor(128, 128, 128);
		view.drawLine(m_aaan[3].x, m_aaan[3].y, m_aaan[1].x, m_aaan[1].y);
	}

	private void _aiV(GameView gameView) {
		int i1 = 1;
		int j1 = 1;
		switch (m_hI) {
			case 2: // '\002'
			case 3: // '\003'
				i1 = j1 = 0;
				break;

			case 1: // '\001'
				i1 = 0;
				break;
		}
		gameView.drawWheel((m_aaan[2].x << 2) / (float) 0xFFFF /*>> 16*/, (m_aaan[2].y << 2) / (float) 0xFFFF /*>> 16*/, i1);
		gameView.drawWheel((m_aaan[1].x << 2) / (float) 0xFFFF /*>> 16*/, (m_aaan[1].y << 2) / (float) 0xFFFF /*>> 16*/, j1);
	}

	private void _doiV(GameView gameView) {
		int i1;
		int j1 = (int) ((long) (i1 = m_Hak[1].m_aI) * 58982L >> 16);
		int k1 = (int) ((long) i1 * 45875L >> 16);
		gameView.setColor(0, 0, 0);
		if (getGDActivity().isMenuShown()) {
			gameView.drawLineWheel((m_aaan[1].x << 2) >> 16, (m_aaan[1].y << 2) >> 16, (i1 + i1 << 2) >> 16);
			gameView.drawLineWheel((m_aaan[1].x << 2) >> 16, (m_aaan[1].y << 2) >> 16, (j1 + j1 << 2) >> 16);
			gameView.drawLineWheel((m_aaan[2].x << 2) >> 16, (m_aaan[2].y << 2) >> 16, (i1 + i1 << 2) >> 16);
			gameView.drawLineWheel((m_aaan[2].x << 2) >> 16, (m_aaan[2].y << 2) >> 16, (k1 + k1 << 2) >> 16);
		}

		// right wheel
		int l1 = j1;
		int i2 = 0;
		int j2;
		int k2 = FPMath._doII(j2 = m_aaan[1].m_bI);
		int l2 = FPMath.sin(j2);
		int i3 = l1;
		l1 = (int) ((long) k2 * (long) l1 >> 16) + (int) ((long) (-l2) * (long) i2 >> 16);
		i2 = (int) ((long) l2 * (long) i3 >> 16) + (int) ((long) k2 * (long) i2 >> 16);
		k2 = FPMath._doII(j2 = 0x141b2);
		l2 = FPMath.sin(j2);
		for (int k3 = 0; k3 < 5; k3++) {
			gameView.drawLine(m_aaan[1].x, m_aaan[1].y, m_aaan[1].x + l1, m_aaan[1].y + i2);
			i3 = l1;
			l1 = (int) ((long) k2 * (long) l1 >> 16) + (int) ((long) (-l2) * (long) i2 >> 16);
			i2 = (int) ((long) l2 * (long) i3 >> 16) + (int) ((long) k2 * (long) i2 >> 16);
		}

		// left wheel
		l1 = j1;
		i2 = 0;
		// k2 = FPMath._doII(j2 = m_aaan[2].m_bI);
		k2 = FPMath._doII(j2 = Math.round(m_aaan[2].m_bI / 1.75f));
		l2 = FPMath.sin(j2);
		i3 = l1;
		l1 = (int) ((long) k2 * (long) l1 >> 16) + (int) ((long) (-l2) * (long) i2 >> 16);
		i2 = (int) ((long) l2 * (long) i3 >> 16) + (int) ((long) k2 * (long) i2 >> 16);
		k2 = FPMath._doII(j2 = 0x141b2);
		l2 = FPMath.sin(j2);

		boolean toUpdate = true;
		for (int l3 = 0; l3 < 5; l3++) {
			if (toUpdate) {
				// Log.d("AGDTR", "toUpdate is true");
				leftWheelParams[l3][0] = m_aaan[2].x;
				leftWheelParams[l3][1] = m_aaan[2].y;
				leftWheelParams[l3][2] = m_aaan[2].x + l1;
				leftWheelParams[l3][3] = m_aaan[2].y + i2;
			}
			// gameView.drawLine(m_aaan[2].x, m_aaan[2].y, m_aaan[2].x + l1, m_aaan[2].y + i2);
			gameView.drawLine(leftWheelParams[l3][0], leftWheelParams[l3][1], leftWheelParams[l3][2], leftWheelParams[l3][3]);
			int j3 = l1;
			l1 = (int) ((long) k2 * (long) l1 >> 16) + (int) ((long) (-l2) * (long) i2 >> 16);
			i2 = (int) ((long) l2 * (long) j3 >> 16) + (int) ((long) k2 * (long) i2 >> 16);
		}
		// if (toUpdate) leftWheelLastUpdated = System.currentTimeMillis();
		// Log.d("AGDTR", "diff: " + (System.currentTimeMillis() - leftWheelLastUpdated));

		if (m_hI > 0) {
			gameView.setColor(255, 0, 0);
			if (m_hI > 2)
				gameView.setColor(100, 100, 255);
			gameView.drawLineWheel((m_aaan[2].x << 2) / (float) 0xFFFF /*>> 16*/, (m_aaan[2].y << 2) / (float) 0xFFFF /*>> 16*/, 4);
			gameView.drawLineWheel((m_aaan[1].x << 2) / (float) 0xFFFF /*>> 16*/, (m_aaan[1].y << 2) / (float) 0xFFFF /*>> 16*/, 4);
		}
	}

	private void _ifiIIV(GameView j, int i1, int j1, int k1, int l1) {
		int i2 = 0;
		int j2 = 0x10000;
		int k2 = m_aaan[0].x;
		int l2 = m_aaan[0].y;
		int i3 = 0;
		int j3 = 0;
		int k3 = 0;
		int l3 = 0;
		int i4 = 0;
		int j4 = 0;
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		int j5 = 0;
		int k5 = 0;
		int l5 = 0;
		int i6 = 0;
		int j6 = 0;
		int k6 = 0;
		int l6 = 0;
		int ai[][] = (int[][]) null;
		int ai1[][] = (int[][]) null;
		int ai2[][] = (int[][]) null;
		if (m_elseZ) {
			if (m_TI < 32768) {
				ai1 = m_ucaaI;
				ai2 = m_KaaI;
				j2 = (int) ((long) m_TI * 0x20000L >> 16);
			} else if (m_TI > 32768) {
				i2 = 1;
				ai1 = m_KaaI;
				ai2 = m_SaaI;
				j2 = (int) ((long) (m_TI - 32768) * 0x20000L >> 16);
			} else {
				ai = m_KaaI;
			}
		} else if (m_TI < 32768) {
			ai1 = m_DaaI;
			ai2 = m_wcaaI;
			j2 = (int) ((long) m_TI * 0x20000L >> 16);
		} else if (m_TI > 32768) {
			i2 = 1;
			ai1 = m_wcaaI;
			ai2 = m_MaaI;
			j2 = (int) ((long) (m_TI - 32768) * 0x20000L >> 16);
		} else {
			ai = m_wcaaI;
		}
		for (int j7 = 0; j7 < m_KaaI.length; j7++) {
			int i8;
			int j8;
			if (ai1 != null) {
				j8 = (int) ((long) ai1[j7][0] * (long) (0x10000 - j2) >> 16) + (int) ((long) ai2[j7][0] * (long) j2 >> 16);
				i8 = (int) ((long) ai1[j7][1] * (long) (0x10000 - j2) >> 16) + (int) ((long) ai2[j7][1] * (long) j2 >> 16);
			} else {
				j8 = ai[j7][0];
				i8 = ai[j7][1];
			}
			int k8 = k2 + (int) ((long) k1 * (long) j8 >> 16) + (int) ((long) i1 * (long) i8 >> 16);
			int l8 = l2 + (int) ((long) l1 * (long) j8 >> 16) + (int) ((long) j1 * (long) i8 >> 16);
			switch (j7) {
				case 0: // '\0'
					k4 = k8;
					l4 = l8;
					break;

				case 1: // '\001'
					i5 = k8;
					j5 = l8;
					break;

				case 2: // '\002'
					k5 = k8;
					l5 = l8;
					break;

				case 3: // '\003'
					i6 = k8;
					j6 = l8;
					break;

				case 4: // '\004'
					k6 = k8;
					l6 = l8;
					break;

				case 5: // '\005'
					k3 = k8;
					l3 = l8;
					break;

				case 6: // '\006'
					i4 = k8;
					j4 = l8;
					break;

				case 7: // '\007'
					i3 = k8;
					j3 = l8;
					break;
			}
		}

		int i7 = (int) ((long) m_JaaI[i2][0] * (long) (0x10000 - j2) >> 16) + (int) ((long) m_JaaI[i2 + 1][0] * (long) j2 >> 16);
		if (m_elseZ) {
			j._aIIIV(k3 << 2, l3 << 2, k4 << 2, l4 << 2, 1);
			j._aIIIV(k4 << 2, l4 << 2, i5 << 2, j5 << 2, 1);
			j.drawBikerPart(i5 << 2, j5 << 2, k5 << 2, l5 << 2, 2, i7);
			j._aIIIV(k5 << 2, l5 << 2, k6 << 2, l6 << 2, 0);
			int k7 = FPMath._ifIII(i1, j1);
			if (m_TI > 32768)
				k7 += 20588;
			j.drawHelmet((i6 << 2) / (float) 0xFFFF /*>> 16*/, (j6 << 2) / (float) 0xFFFF /*>> 16*/, k7);
		} else {
			j.setColor(0, 0, 0);
			j.drawLine(k3, l3, k4, l4);
			j.drawLine(k4, l4, i5, j5);
			j.setColor(0, 0, 128);
			j.drawLine(i5, j5, k5, l5);
			j.drawLine(k5, l5, k6, l6);
			j.drawLine(k6, l6, i3, j3);
			int l7 = 0x10000;
			j.setColor(156, 0, 0);
			j.drawLineWheel((i6 << 2) >> 16, (j6 << 2) >> 16, (l7 + l7 << 2) >> 16);
		}
		j.setColor(0, 0, 0);
		j.drawSteering((i3 << 2) >> 16, (j3 << 2) >> 16);
		j.drawSteering((i4 << 2) >> 16, (j4 << 2) >> 16);
	}

	private void _aiIIV(GameView j, int i1, int j1, int k1, int l1) {
		int i2 = m_aaan[2].x;
		int j2 = m_aaan[2].y;
		int k2 = i2 + (int) ((long) k1 * (long) 32768 >> 16);
		int l2 = j2 + (int) ((long) l1 * (long) 32768 >> 16);
		int i3 = i2 - (int) ((long) k1 * (long) 32768 >> 16);
		int j3 = j2 - (int) ((long) l1 * (long) 32768 >> 16);
		int k3 = m_aaan[0].x + (int) ((long) i1 * 32768L >> 16);
		int l3 = m_aaan[0].y + (int) ((long) j1 * 32768L >> 16);
		int i4 = k3 - (int) ((long) i1 * 0x20000L >> 16);
		int j4 = l3 - (int) ((long) j1 * 0x20000L >> 16);
		int k4 = i4 + (int) ((long) k1 * 0x10000L >> 16);
		int l4 = j4 + (int) ((long) l1 * 0x10000L >> 16);
		int i5 = i4 + (int) ((long) i1 * 49152L >> 16) + (int) ((long) k1 * 49152L >> 16);
		int j5 = j4 + (int) ((long) j1 * 49152L >> 16) + (int) ((long) l1 * 49152L >> 16);
		int k5 = i4 + (int) ((long) k1 * 32768L >> 16);
		int l5 = j4 + (int) ((long) l1 * 32768L >> 16);
		int i6 = m_aaan[1].x;
		int j6 = m_aaan[1].y;
		int k6 = m_aaan[4].x - (int) ((long) i1 * 49152L >> 16);
		int l6 = m_aaan[4].y - (int) ((long) j1 * 49152L >> 16);
		int i7 = k6 - (int) ((long) k1 * 32768L >> 16);
		int j7 = l6 - (int) ((long) l1 * 32768L >> 16);
		int k7 = (k6 - (int) ((long) i1 * 0x20000L >> 16)) + (int) ((long) k1 * 16384L >> 16);
		int l7 = (l6 - (int) ((long) j1 * 0x20000L >> 16)) + (int) ((long) l1 * 16384L >> 16);
		int i8 = m_aaan[3].x;
		int j8 = m_aaan[3].y;
		int k8 = i8 + (int) ((long) k1 * 32768L >> 16);
		int l8 = j8 + (int) ((long) l1 * 32768L >> 16);
		int i9 = (i8 + (int) ((long) k1 * 0x1c000L >> 16)) - (int) ((long) i1 * 32768L >> 16);
		int j9 = (j8 + (int) ((long) l1 * 0x1c000L >> 16)) - (int) ((long) j1 * 32768L >> 16);
		j.setColor(50, 50, 50);
		j.drawLineWheel((k5 << 2) >> 16, (l5 << 2) >> 16, (32768 + 32768 << 2) >> 16);
		if (!m_IZ) {
			j.drawLine(k2, l2, k4, l4);
			j.drawLine(i3, j3, i4, j4);
		}
		j.drawLine(k3, l3, i4, j4);
		j.drawLine(k3, l3, i8, j8);
		j.drawLine(i5, j5, k8, l8);
		j.drawLine(k8, l8, i9, j9);
		if (!m_IZ) {
			j.drawLine(i8, j8, i6, j6);
			j.drawLine(i9, j9, i6, j6);
		}
		j.drawLine(k4, l4, i7, j7);
		j.drawLine(i5, j5, k6, l6);
		j.drawLine(k6, l6, k7, l7);
		j.drawLine(i7, j7, k7, l7);
	}

	public void _ifiV(GameView j) {
		j._tryvV();
		int i1 = m_aaan[3].x - m_aaan[4].x;
		int j1 = m_aaan[3].y - m_aaan[4].y;
		int k1;
		if ((k1 = _doIII(i1, j1)) != 0) {
			i1 = (int) (((long) i1 << 32) / (long) k1 >> 16);
			j1 = (int) (((long) j1 << 32) / (long) k1 >> 16);
		}
		int l1 = -j1;
		int i2 = i1;
		if (m_IZ) {
			int k2 = m_aaan[4].x;
			int j2;
			if ((j2 = m_aaan[3].x) >= k2) {
				int l2 = j2;
				j2 = k2;
				k2 = l2;
			}
			m_lf.levels._aIIV(j2, k2);
		}

		Loader loader = getLevelLoader();
		if (loader != null && loader.isPerspectiveEnabled())
			m_lf._aiIV(j, m_aaan[0].x, m_aaan[0].y);
		if (m_UZ)
			_aiIV(j, i1, j1);
		if (!getGDActivity().isMenuShown())
			_aiV(j);
		_doiV(j);
		if (m_UZ)
			j.setColor(170, 0, 0);
		else
			j.setColor(50, 50, 50);
		j._ifIIIV((m_aaan[1].x << 2) >> 16, (m_aaan[1].y << 2) >> 16, (m_foraI[0] << 2) >> 16, FPMath._ifIII(i1, j1));
		if (!m_IZ)
			_laiV(j);
		_ifiIIV(j, i1, j1, l1, i2);
		if (!m_UZ)
			_aiIIV(j, i1, j1, l1, i2);
		m_lf._aiV(j);
	}

}
