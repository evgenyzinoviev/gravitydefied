package org.happysanta.gd.Levels;

import org.happysanta.gd.GDActivity;
import org.happysanta.gd.Game.GameView;
import org.happysanta.gd.Menu.SimpleMenuElement;
import org.happysanta.gd.Game.Physics;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.happysanta.gd.Helpers.logDebug;

public class Loader {

	//public static final int m_bI;
	// public static final int m_forI = 1;
	// public static final int m_intI = 2;
	//public static final int m_doI;
	// public static final int m_ifI = 1;

	public File levelsFile;
	public Level levels;
	public int m_nullI;
	public int m_fI;
	public String names[][];
	public int m_jI;
	public int m_iI;
	public int m_longI;
	public int m_eI;
	public int m_dI;

	private boolean perspectiveEnabled = true;
	private boolean shadowsEnabled = true;
	private int pointers[][] = new int[3][];
	private int m_eaI = 0;
	private int m_faI = 0;
	private int m_aI = 0;
	private int m_kI = 0;
	private int m_saaI[][];
	private int m_haI[];
	private int m_vaI[];
	private int m_daI;

	public Loader() throws IOException {
		reset();
	}

	public Loader(File levelsFile) throws IOException {
		if (levelsFile != null)
			this.levelsFile = levelsFile;
		reset();
	}

	private void reset() throws IOException {
		m_saaI = null;
		levels = null;
		m_haI = new int[3];
		m_vaI = new int[3];
		m_nullI = 0;
		m_fI = -1;
		names = new String[3][];
		m_daI = 0;
		for (int j = 0; j < 3; j++) {
			m_haI[j] = (int) ((long) (Physics.m_foraI[j] + 19660 >> 1) * (long) (Physics.m_foraI[j] + 19660 >> 1) >> 16);
			m_vaI[j] = (int) ((long) (Physics.m_foraI[j] - 19660 >> 1) * (long) (Physics.m_foraI[j] - 19660 >> 1) >> 16);
		}

		// try {
		readLevels();
		// } catch ( _ex) {
		//	_ex.printStackTrace();
		//}
		_ifvV();
	}


	public void setLevelsFile(File file) throws IOException {
		levelsFile = file;
		reset();
	}

	public InputStream getLevelsInputStream(String s) throws IOException {
		if (levelsFile == null)
			return GDActivity.shared.getAssets().open(s);
		else
			return new FileInputStream(levelsFile);
	}

	private void readLevels() throws IOException {
		//try {
		InputStream in = getLevelsInputStream("levels.mrg");
		LevelHeader header = Reader.readHeader(in);

		pointers = header.getPointers();
		names = header.getNames();
		//} catch (Exception ex) {
		//	logDebug("Levels.Loader: error while reading mrg file");
		//	ex.printStackTrace();
		//}
	}

	public String getLevelName(int j, int k) {
		if (j < names.length && k < names[j].length)
			return names[j][k];
		else
			return "---";
	}

	public void _ifvV() {
		try {
			_doIII(m_nullI, m_fI + 1);
		} catch (InvalidTrackException e) {
			e.printStackTrace();
		}
	}

	public int _doIII(int j, int k) throws InvalidTrackException {
		m_nullI = j;
		m_fI = k;
		if (m_fI >= names[m_nullI].length)
			m_fI = 0;
		_hIIV(m_nullI + 1, m_fI + 1);
		return m_fI;
	}

	private void _hIIV(int j, int k) throws InvalidTrackException {
		try {
			InputStream is = getLevelsInputStream("levels.mrg");
			DataInputStream dis = new DataInputStream(is);
			for (int i1 = pointers[j - 1][k - 1]; i1 > 0; i1 -= dis.skipBytes(i1)) ;
			if (levels == null)
				levels = new Level();
			levels.readTrackData(dis);
			dis.close();
			load(levels);
		} catch (IOException _ex) {
			_ex.printStackTrace();
		}
	}

	public void _ifIV(int j) {
		m_jI = levels.startX << 1;
		m_iI = levels.startY << 1;
	}

	public int _dovI() {
		return levels.points[levels.m_forI][0] << 1;
	}

	public int _intvI() {
		return levels.points[levels.m_gotoI][0] << 1;
	}

	public int _newvI() {
		return levels.startX << 1;
	}

	public int _avI() {
		return levels.startY << 1;
	}

	public int _aII(int j) {
		return levels._doII(j >> 1);
	}

	public void load(Level l1) throws InvalidTrackException {
		try {
			m_longI = 0x80000000;
			levels = l1;
			int j = levels.pointsCount;
			if (m_saaI == null || m_daI < j) {
				m_saaI = (int[][]) null;
				// System.gc();
				m_daI = j >= 100 ? j : 100;
				m_saaI = new int[m_daI][2];
			}
			m_eaI = 0;
			m_faI = 0;
			m_aI = l1.points[m_eaI][0];
			m_kI = l1.points[m_faI][0];
			for (int k = 0; k < j; k++) {
				int i1 = l1.points[(k + 1) % j][0] - l1.points[k][0];
				int j1 = l1.points[(k + 1) % j][1] - l1.points[k][1];
				if (k != 0 && k != j - 1)
					m_longI = m_longI >= l1.points[k][0] ? m_longI : l1.points[k][0];
				int k1 = -j1;
				int i2 = i1;
				int j2 = Physics._doIII(k1, i2);
				m_saaI[k][0] = (int) (((long) k1 << 32) / (long) j2 >> 16);
				m_saaI[k][1] = (int) (((long) i2 << 32) / (long) j2 >> 16);
				if (levels.m_gotoI == 0 && l1.points[k][0] > levels.startX)
					levels.m_gotoI = k + 1;
				if (levels.m_forI == 0 && l1.points[k][0] > levels.finishX)
					levels.m_forI = k;
			}

			m_eaI = 0;
			m_faI = 0;
			m_aI = 0;
			m_kI = 0;
		} catch (ArithmeticException e) {
			throw new InvalidTrackException(e);
		}
	}

	public void _ifIIV(int j, int k) {
		levels._ifIIV(j, k);
	}

	public void _aiIV(GameView j, int k, int i1) {
		if (j != null) {
			j.setColor(0, 170, 0);
			k >>= 1;
			i1 >>= 1;
			levels._aiIV(j, k, i1);
		}
	}

	public void _aiV(GameView j) {
		j.setColor(0, 255, 0);
		levels._aiV(j);
	}

	public void _aIIV(int j, int k, int i1) {
		levels._aIIV(j + 0x18000 >> 1, k - 0x18000 >> 1, i1 >> 1);
		k >>= 1;
		j >>= 1;
		m_faI = m_faI >= levels.pointsCount - 1 ? levels.pointsCount - 1 : m_faI;
		m_eaI = m_eaI >= 0 ? m_eaI : 0;
		if (k > m_kI)
			while (m_faI < levels.pointsCount - 1 && k > levels.points[++m_faI][0]) ;
		else if (j < m_aI) {
			while (m_eaI > 0 && j < levels.points[--m_eaI][0]) ;
		} else {
			while (m_eaI < levels.pointsCount && j > levels.points[++m_eaI][0]) ;
			if (m_eaI > 0)
				m_eaI--;
			while (m_faI > 0 && k < levels.points[--m_faI][0]) ;
			m_faI = m_faI + 1 >= levels.pointsCount - 1 ? levels.pointsCount - 1 : m_faI + 1;
		}
		m_aI = levels.points[m_eaI][0];
		m_kI = levels.points[m_faI][0];
	}

	public int _anvI(SimpleMenuElement n1, int j) {
		int k3 = 0;
		byte byte1 = 2;
		int l3 = n1.x >> 1;
		int i4 = n1.y >> 1;
		if (perspectiveEnabled)
			i4 -= 0x10000;
		int j4 = 0;
		int k4 = 0;
		for (int l4 = m_eaI; l4 < m_faI; l4++) {
			int k = levels.points[l4][0];
			int i1 = levels.points[l4][1];
			int j1 = levels.points[l4 + 1][0];
			int k1;
			int _tmp = (k1 = levels.points[l4 + 1][1]) < i1 ? i1 : k1;
			if (l3 - m_haI[j] > j1 || l3 + m_haI[j] < k)
				continue;
			int l1 = k - j1;
			int i2 = i1 - k1;
			int j2 = (int) ((long) l1 * (long) l1 >> 16) + (int) ((long) i2 * (long) i2 >> 16);
			int k2 = (int) ((long) (l3 - k) * (long) (-l1) >> 16) + (int) ((long) (i4 - i1) * (long) (-i2) >> 16);
			int l2;
			if ((j2 >= 0 ? j2 : -j2) >= 3)
				l2 = (int) (((long) k2 << 32) / (long) j2 >> 16);
			else
				l2 = (k2 <= 0 ? -1 : 1) * (j2 <= 0 ? -1 : 1) * 0x7fffffff;
			if (l2 < 0)
				l2 = 0;
			if (l2 > 0x10000)
				l2 = 0x10000;
			int i3 = k + (int) ((long) l2 * (long) (-l1) >> 16);
			int j3 = i1 + (int) ((long) l2 * (long) (-i2) >> 16);
			l1 = l3 - i3;
			i2 = i4 - j3;
			byte byte0;
			long l5;
			if ((l5 = ((long) l1 * (long) l1 >> 16) + ((long) i2 * (long) i2 >> 16)) < (long) m_haI[j]) {
				if (l5 >= (long) m_vaI[j])
					byte0 = 1;
				else
					byte0 = 0;
			} else {
				byte0 = 2;
			}
			if (byte0 == 0 && (int) ((long) m_saaI[l4][0] * (long) n1.m_eI >> 16) + (int) ((long) m_saaI[l4][1] * (long) n1.m_dI >> 16) < 0) {
				m_eI = m_saaI[l4][0];
				m_dI = m_saaI[l4][1];
				return 0;
			}
			if (byte0 != 1 || (int) ((long) m_saaI[l4][0] * (long) n1.m_eI >> 16) + (int) ((long) m_saaI[l4][1] * (long) n1.m_dI >> 16) >= 0)
				continue;
			k3++;
			byte1 = 1;
			if (k3 == 1) {
				j4 = m_saaI[l4][0];
				k4 = m_saaI[l4][1];
			} else {
				j4 += m_saaI[l4][0];
				k4 += m_saaI[l4][1];
			}
		}

		if (byte1 == 1) {
			if ((int) ((long) j4 * (long) n1.m_eI >> 16) + (int) ((long) k4 * (long) n1.m_dI >> 16) >= 0)
				return 2;
			m_eI = j4;
			m_dI = k4;
		}
		return byte1;
	}

	public boolean isShadowsEnabled() {
		return shadowsEnabled;
	}

	public boolean isPerspectiveEnabled() {
		return perspectiveEnabled;
	}

	public void setPerspectiveEnabled(boolean enabled) {
		perspectiveEnabled = enabled;
	}

	public void setShadowsEnabled(boolean enabled) {
		shadowsEnabled = enabled;
	}

}
