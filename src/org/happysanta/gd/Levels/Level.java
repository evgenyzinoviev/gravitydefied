package org.happysanta.gd.Levels;

import org.happysanta.gd.Game.GameView;
import org.happysanta.gd.Game.Physics;

import java.io.DataInputStream;

import static org.happysanta.gd.Helpers.getLevelLoader;
import static org.happysanta.gd.Helpers.logDebug;

public class Level {

	public int startX;
	public int startY;
	public int finishX;
	public int m_gotoI;
	public int m_forI;
	public int finishY;
	public int pointsCount;
	public int m_intI;
	public int points[][];
	public String levelName;
	private int m_aI;
	private int m_dI;
	private int m_eI;
	private int m_bI;
	private int m_gI;
	private int m_rI;

	public Level() {
		m_aI = 0;
		m_dI = 0;
		m_eI = 0;
		m_bI = 0;
		m_gI = 0;
		m_gotoI = 0;
		m_forI = 0;
		points = (int[][]) null;
		levelName = "levelname";
		m_rI = 0;
		clear();
	}

	public void clear() {
		startX = 0;
		startY = 0;
		finishX = 0xc80000;
		pointsCount = 0;
		m_intI = 0;
	}

	public int _doII(int j) {
		int k = j - points[m_gotoI][0];
		int i1;
		if (((i1 = points[m_forI][0] - points[m_gotoI][0]) >= 0 ? i1 : -i1) < 3 || k > i1)
			return 0x10000;
		else
			return (int) (((long) k << 32) / (long) i1 >> 16);
	}

	public void _ifIIV(int j, int k) {
		m_aI = (j << 16) >> 3;
		m_dI = (k << 16) >> 3;
	}

	public void _aIIV(int j, int k) {
		m_eI = j >> 1;
		m_bI = k >> 1;
	}

	public void _aIIV(int j, int k, int i1) {
		m_eI = j;
		m_bI = k;
		m_gI = i1;
	}

	public void _ifiIV(GameView view, int k, int i1) {
		if (i1 <= pointsCount - 1) {
			int j1 = m_gI - (points[k][1] + points[i1 + 1][1] >> 1) >= 0 ? m_gI - (points[k][1] + points[i1 + 1][1] >> 1) : 0;
			if (m_gI <= points[k][1] || m_gI <= points[i1 + 1][1])
				j1 = j1 >= 0x50000 ? 0x50000 : j1;
			m_rI = (int) ((long) m_rI * 49152L >> 16) + (int) ((long) j1 * 16384L >> 16);
			if (m_rI <= 0x88000) {
				int k1 = (int) (0x190000L * (long) m_rI >> 16) >> 16;
				view.setColor(k1, k1, k1);
				int l1 = points[k][0] - points[k + 1][0];
				int i2 = (int) (((long) (points[k][1] - points[k + 1][1]) << 32) / (long) l1 >> 16);
				int j2 = points[k][1] - (int) ((long) points[k][0] * (long) i2 >> 16);
				int k2 = (int) ((long) m_eI * (long) i2 >> 16) + j2;
				l1 = points[i1][0] - points[i1 + 1][0];
				i2 = (int) (((long) (points[i1][1] - points[i1 + 1][1]) << 32) / (long) l1 >> 16);
				j2 = points[i1][1] - (int) ((long) points[i1][0] * (long) i2 >> 16);
				int l2 = (int) ((long) m_bI * (long) i2 >> 16) + j2;
				if (k == i1) {
					view._aIIIV((m_eI << 3) >> 16, (k2 + 0x10000 << 3) >> 16, (m_bI << 3) >> 16, (l2 + 0x10000 << 3) >> 16);
					return;
				}
				view._aIIIV((m_eI << 3) >> 16, (k2 + 0x10000 << 3) >> 16, (points[k + 1][0] << 3) >> 16, (points[k + 1][1] + 0x10000 << 3) >> 16);
				for (int i3 = k + 1; i3 < i1; i3++)
					view._aIIIV((points[i3][0] << 3) >> 16, (points[i3][1] + 0x10000 << 3) >> 16, (points[i3 + 1][0] << 3) >> 16, (points[i3 + 1][1] + 0x10000 << 3) >> 16);

				view._aIIIV((points[i1][0] << 3) >> 16, (points[i1][1] + 0x10000 << 3) >> 16, (m_bI << 3) >> 16, (l2 + 0x10000 << 3) >> 16);
			}
		}
	}

	public synchronized void _aiIV(GameView view, int k, int i1) {
		int k2 = 0;
		int l2 = 0;
		int j2;
		for (j2 = 0; j2 < pointsCount - 1 && points[j2][0] <= m_aI; j2++) ;
		if (j2 > 0)
			j2--;
		int i3 = k - points[j2][0];
		int j3 = (i1 + 0x320000) - points[j2][1];
		int k3 = Physics._doIII(i3, j3);
		i3 = (int) (((long) i3 << 32) / (long) (k3 >> 1 >> 1) >> 16);
		j3 = (int) (((long) j3 << 32) / (long) (k3 >> 1 >> 1) >> 16);
		view.setColor(0, 170, 0);
		do {
			if (j2 >= pointsCount - 1)
				break;
			int j1 = i3;
			int l1 = j3;
			i3 = k - points[j2 + 1][0];
			j3 = (i1 + 0x320000) - points[j2 + 1][1];
			int l3 = Physics._doIII(i3, j3);
			i3 = (int) (((long) i3 << 32) / (long) (l3 >> 1 >> 1) >> 16);
			j3 = (int) (((long) j3 << 32) / (long) (l3 >> 1 >> 1) >> 16);
			view._aIIIV((points[j2][0] + j1 << 3) >> 16, (points[j2][1] + l1 << 3) >> 16, (points[j2 + 1][0] + i3 << 3) >> 16, (points[j2 + 1][1] + j3 << 3) >> 16);
			view._aIIIV((points[j2][0] << 3) >> 16, (points[j2][1] << 3) >> 16, (points[j2][0] + j1 << 3) >> 16, (points[j2][1] + l1 << 3) >> 16);
			if (j2 > 1) {
				if (points[j2][0] > m_eI && k2 == 0)
					k2 = j2 - 1;
				if (points[j2][0] > m_bI && l2 == 0)
					l2 = j2 - 1;
			}
			if (m_gotoI == j2) {
				view.drawStartFlag((points[m_gotoI][0] + j1 << 3) >> 16, (points[m_gotoI][1] + l1 << 3) >> 16);
				view.setColor(0, 170, 0);
			}
			if (m_forI == j2) {
				view.drawFinishFlag((points[m_forI][0] + j1 << 3) >> 16, (points[m_forI][1] + l1 << 3) >> 16);
				view.setColor(0, 170, 0);
			}
			if (points[j2][0] > m_dI)
				break;
			j2++;
		} while (true);
		int k1 = i3;
		int i2 = j3;
		view._aIIIV((points[pointsCount - 1][0] << 3) >> 16, (points[pointsCount - 1][1] << 3) >> 16, (points[pointsCount - 1][0] + k1 << 3) >> 16, (points[pointsCount - 1][1] + i2 << 3) >> 16);
		if (getLevelLoader().isShadowsEnabled())
			_ifiIV(view, k2, l2);
	}

	public synchronized void _aiV(GameView view) {
		int k;
		for (k = 0; k < pointsCount - 1 && points[k][0] <= m_aI; k++) ;
		if (k > 0)
			k--;
		do {
			if (k >= pointsCount - 1)
				break;
			view._aIIIV((points[k][0] << 3) >> 16, (points[k][1] << 3) >> 16, (points[k + 1][0] << 3) >> 16, (points[k + 1][1] << 3) >> 16);
			if (m_gotoI == k) {
				view.drawStartFlag((points[m_gotoI][0] << 3) >> 16, (points[m_gotoI][1] << 3) >> 16);
				view.setColor(0, 255, 0);
			}
			if (m_forI == k) {
				view.drawFinishFlag((points[m_forI][0] << 3) >> 16, (points[m_forI][1] << 3) >> 16);
				view.setColor(0, 255, 0);
			}
			if (points[k][0] > m_dI)
				break;
			k++;
		} while (true);
	}

	public void unpackInt(int x, int y) {
		addPoint((x << 16) >> 3, (y << 16) >> 3);
	}

	public void addPoint(int x, int y) {
		if (points == null || points.length <= pointsCount) {
			int i1 = 100;
			if (points != null)
				i1 = i1 >= points.length + 30 ? i1 : points.length + 30;
			int ai[][] = new int[i1][2];
			if (points != null)
				System.arraycopy(points, 0, ai, 0, points.length);
			points = ai;
		}
		if (pointsCount == 0 || points[pointsCount - 1][0] < x) {
			points[pointsCount][0] = x;
			points[pointsCount][1] = y;
			pointsCount++;
		}
	}

	public synchronized void readTrackData(DataInputStream in) {
		try {
			clear();
			if (in.readByte() == 50) {
				byte bytes[] = new byte[20];
				in.readFully(bytes);
			}
			m_forI = 0;
			m_gotoI = 0;
			startX = in.readInt();
			startY = in.readInt();
			finishX = in.readInt();
			finishY = in.readInt();
			short pointsCount = in.readShort();
			int firstPointX = in.readInt();
			int firstPointY = in.readInt();
			int k1 = firstPointX;
			int l1 = firstPointY;
			unpackInt(k1, l1);
			for (int i = 1; i < pointsCount; i++) {
				int x;
				int y;
				byte byte0;
				if ((byte0 = in.readByte()) == -1) {
					k1 = l1 = 0;
					x = in.readInt();
					y = in.readInt();
				} else {
					x = byte0;
					y = in.readByte();
				}
				k1 += x;
				l1 += y;
				unpackInt(k1, l1);
			}

			/*logDebug("Points: ");
			for (int[] point: points) {
				logDebug("(" + ((point[0] >> 16) << 3) + ", " + ((point[1] >> 16) << 3) + ")");
			}*/
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
