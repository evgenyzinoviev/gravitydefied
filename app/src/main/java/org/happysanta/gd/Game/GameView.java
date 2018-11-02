package org.happysanta.gd.Game;

import android.graphics.*;
import android.view.View;
import org.happysanta.gd.Command;
import org.happysanta.gd.GDActivity;
import org.happysanta.gd.Global;
import org.happysanta.gd.Menu.Menu;
import org.happysanta.gd.Menu.MenuScreen;
import org.happysanta.gd.Menu.SimpleMenuElement;

import java.io.IOException;
import java.util.Timer;

import static org.happysanta.gd.Helpers.*;

public class GameView extends View {

	private static int m_VI = 0;
	private static int m_vcI = 0;
	private final int[] startFlagIndexes = {
			2, 0, 1, 0
	};
	private final int[] finishFlagIndexes = {
			4, 3, 5, 3
	};
	public int m_abI;
	public int m_dI;
	public int m_lI;
	public boolean drawTimer;
	public android.graphics.Bitmap m_zaBitmap[];
	public boolean m_KZ;
	public long m_rJ;
	Menu menu;
	int m_uI;
	int m_aiI;
	int m_agI;
	int m_gI;
	private Canvas canvas;
	private int m_XI;
	private int m_BI;
	private Physics physEngine;
	private int m_TI;
	private int m_QI;
	private GDActivity activity;
	private Paint infoFont;
	private Paint timerFont;
	private boolean m_ahZ;
	private int m_oI;
	private boolean m_AZ;
	private int m_OI;
	private android.graphics.Bitmap m_MBitmap;
	private Canvas m_dcGraphics;
	private boolean m_ecZ;
	private String infoMessage;
	private int gc;
	private Timer timer;
	private Command menuCommand;
	private Paint paint = new Paint();
	private Object m_ocObject;
	private byte[][] m_DaaB = {
			{0, 0},
			{1, 0},
			{0, -1},
			{0, 0},
			{0, 0},
			{0, 1},
			{-1, 0}
	};
	private byte[][][] m_maaaB = {
			{
					{
							0, 0
					},
					{
							1, -1
					},
					{
							1, 0
					},
					{
							1, 1
					},
					{
							0, -1
					},
					{
							-1, 0
					},
					{
							0, 1
					},
					{
							-1, -1
					},
					{
							-1, 0
					},
					{
							-1, 1
					}
			}, {
			{
					0, 0
			},
			{
					1, 0
			},
			{
					0, 0
			},
			{
					0, 0
			},
			{
					-1, 0
			},
			{
					0, -1
			},
			{
					0, 1
			},
			{
					0, 0
			},
			{
					0, 0
			},
			{
					0, 0
			}
	},
			{
					{
							0, 0
					},
					{
							0, 0
					},
					{
							0, 0
					},
					{
							1, 0
					},
					{
							0, -1
					},
					{
							0, 1
					},
					{
							-1, 0
					},
					{
							0, 0
					},
					{
							0, 0
					},
					{
							0, 0
					}
			}
	};
	private int inputOption;
	private boolean[] m_aeaZ;
	private boolean[] m_LaZ;
	// private int defaultHeight;
	// private int defaultWidth;

	public GameView(GDActivity micro) {
		super(micro);
		// clear static
		m_vcI = 0;
		m_VI = 0;

		canvas = null;
		physEngine = null;
		menu = null;
		m_TI = 0;
		m_QI = 0;
		activity = null;
		infoFont = null;
		m_ahZ = false;
		drawTimer = true;
		m_oI = 1;
		m_uI = 0;
		m_zaBitmap = null;

		m_KZ = false;
		m_AZ = true;
		m_MBitmap = null;
		m_dcGraphics = null;
		m_ecZ = false;
		infoMessage = null;
		gc = 0;
		timer = new Timer();
		m_rJ = -1L;
		m_ocObject = new Object();
		m_aiI = 0;
		m_agI = 0;
		m_gI = -1;
		inputOption = 2;
		m_aeaZ = new boolean[7];
		m_LaZ = new boolean[10];
		// String s;
		// String s1;
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1);

		invalidate();
		m_KZ = false;
		activity = micro;
		_ifvV();

		infoFont = new Paint();
		infoFont.setTextSize(20);
		infoFont.setAntiAlias(true);
		infoFont.setTypeface(Global.robotoCondensedTypeface);

		timerFont = new Paint();
		timerFont.setTextSize(18);
		timerFont.setAntiAlias(true);
		timerFont.setTypeface(Global.robotoCondensedTypeface);

		m_XI = 0;
		m_BI = m_dI;
		menuCommand = new Command("Menu", 1, 1);
	}

	public void drawBitmap(Bitmap b, float x, float y) {
		drawBitmap(b, x, y, canvas);
	}

	public void drawBitmap(Bitmap b, float x, float y, Canvas g) {
		Paint paint = null;
		if (!isSDK11OrHigher()) {
			paint = new Paint();
			paint.setFlags(Paint.DITHER_FLAG);
			paint.setFilterBitmap(true);
		}
		g.drawBitmap(b.bitmap,
				new Rect(0, 0, b.getWidth(), b.getHeight()),
				new RectF(x, y, x + b.getWidthDp(), y + b.getHeightDp()),
				paint);
	}

	public static void _dovV() {
		m_vcI += 655;
		int j = 32768 + ((FPMath.sin(m_vcI) >= 0 ? FPMath.sin(m_vcI) : -FPMath.sin(m_vcI)) >> 1);
		m_VI += (int) (6553L * (long) j >> 16);
	}

	/*
	TODO
	суть этого метода в том, что после того, как splash-картинки проигрались, они удаляются из памяти, т.к. они больше нафиг не нужны
	видимо это было критично на старых телефонах
	можно и тут в принципе сделать
	 */
	public void _doIV(int j) {
		m_oI = j;
		if (j == 0) {
			// Bitmap.get(Bitmap.CODEBREW_LOGO) = null;
			// Bitmap.get(Bitmap.GD_LOGO) = null;
		}
	}

	public void _aZV(boolean flag) {
		m_AZ = flag;
		_ifvV();
	}

	public void _ifvV() {
		m_abI = getScaledWidth();
		m_lI = m_dI = getScaledHeight();
		if (m_KZ && m_AZ)
			m_dI -= 80;
		//postInvalidate();
	}

	public android.graphics.Bitmap[] spritesFromBitmap(android.graphics.Bitmap bitmap, int j, int k) {
		int l = bitmap.getWidth() / j;
		int i1 = bitmap.getHeight() / k;
		android.graphics.Bitmap aBitmap[] = new android.graphics.Bitmap[j * k];
		for (int j1 = 0; j1 < j * k; j1++) {
			aBitmap[j1] = android.graphics.Bitmap.createBitmap(l, i1, android.graphics.Bitmap.Config.ARGB_8888);
			new Canvas(aBitmap[j1]).drawBitmap(bitmap, -l * (j1 % j), -i1 * (j1 / j), null);
		}

		return aBitmap;
	}

	public int _intII(int j) {
		synchronized (m_ocObject) {
			try {
				{
					if ((j & 1) != 0) {
						/* try {
							if (Bitmap.get(Bitmap.FENDER) == null) {
								//Bitmap.get(Bitmap.FENDER) = Bitmap.fromAsset("/fender.png");
								//Bitmap.get(Bitmap.FENDER).mulWidth = 1.0f/6.0f;
								//Bitmap.get(Bitmap.FENDER).mulHeight = 1.0f/6.0;
								Bitmap.get(Bitmap.FENDER) = Bitmap.fromDrawable(R.drawable.s_fender);
							}
							if (Bitmap.get(Bitmap.ENGINE) == null) {
								//Bitmap.get(Bitmap.ENGINE) = Bitmap.fromAsset("/engine.png");
								//Bitmap.get(Bitmap.ENGINE).mulHeight = 1.0f/6.0f;
								//Bitmap.get(Bitmap.ENGINE).mulWidth = 1.0f/6.0f;
								Bitmap.get(Bitmap.ENGINE) = Bitmap.fromDrawable(R.drawable.s_engine);
							}
						} catch (Throwable _ex) {
							Bitmap.get(Bitmap.FENDER) = Bitmap.get(Bitmap.ENGINE) = null;
							j &= -2;
						} */
					} else {
						// Bitmap.get(Bitmap.ENGINE) = Bitmap.get(Bitmap.FENDER) = null;
						// System.gc();
					}
					if ((j & 2) != 0) {
						// blueleg
						/*try {
							if (bikerSprites[1] == null)
								bikerSprites[1] = Bitmap.fromDrawable(R.drawable.s_blueleg);
						} catch (Throwable _ex) {
							bikerSprites[1] = null;
							bikerSprites[0] = null;
							bikerSprites[2] = null;
							j &= -3;
							System.out.println("There may be error");
							return 0;
						}*/

						// bluearm
						/*try {
							bikerSprites[0] = Bitmap.fromDrawable(R.drawable.s_bluearm);
						} catch (Throwable _ex) {
							bikerSprites[0] = bikerSprites[1];
						}

						// bluebody
						try {
							bikerSprites[2] = Bitmap.fromDrawable(R.drawable.s_bluebody);
						} catch (Throwable _ex) {
							bikerSprites[2] = bikerSprites[1];
						}*/
					} else {
						// bikerSprites[1] = bikerSprites[2] = bikerSprites[0] = null;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("There may be error [1]");
			}
		}
		return j;
	}

	public android.graphics.Bitmap[] loadSprites(String s, int j, int k) {
		android.graphics.Bitmap bitmap;
		try {
			bitmap = loadBitmapFromAsset(s);
		} catch (IOException _ex) {
			android.graphics.Bitmap aBitmap[] = new android.graphics.Bitmap[j * k];
			for (int l = 0; l < j * k; l++)
				aBitmap[l] = Bitmap.getEmpty().bitmap;

			return aBitmap;
		}
		return spritesFromBitmap(bitmap, j, k);
	}

	public void _casevV() {
		physEngine._nullvV();
		_avV();
		m_aiI = 0;
		m_agI = 0;
	}

	public void setPhysicsEngine(Physics b1) {
		physEngine = b1;
		physEngine._caseIV(m_abI >= m_dI ? m_dI : m_abI);
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	/* public Bitmap _aStringBitmap(String s) {
		Bitmap bitmap = null;
		try {
			bitmap = Global.loadBitmapFromAsset(s);
		} catch (IOException _ex) {
		}
		return bitmap;
	} */

	public void _doIIV(int j, int k) {
		m_XI = j;
		m_BI = k;
		physEngine._ifIIV(-j, -j + m_abI);
	}

	public int _charvI() {
		return m_XI;
	}

	private float offsetX(float j) {
		return j + m_XI;
	}

	private float offsetY(float j) {
		return -j + m_BI - getGDActivity().getButtonsLayoutHeight() / 2;
	}

	public void _newvV() {
		paint.setColor(0xFFFFFFFF);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect(0, m_dI, m_abI, 80, paint);
		byte byte0 = 35;
		int j = m_abI / 2;
		int k = m_dI + 40;
		// m_CGraphics.setColor(150, 0, 0);
		paint.setColor(0xFF960000);
		int i1;
		paint.setStyle(Paint.Style.STROKE);
		if (m_aiI != 0 || m_agI != 0) {
			i1 = (int) (((long) (int) ((long) m_OI * 0xb40000L >> 16) << 32) / 0x3243fL >> 16) >> 16;
			int l = i1 - i1 % 45;
			l -= 90;
			//m_CGraphics.fillArc(j - byte0, k - byte0, 2 * byte0, 2 * byte0, l - 22, 45);
			canvas.drawArc(new RectF(j - byte0, k - byte0, 2 * byte0 + j - byte0, 2 * byte0 + k - byte0), l - 22, 45, true, paint);
		}
		//m_CGraphics.setColor(0, 0, 0);
		paint.setColor(0xFF000000);
		canvas.drawArc(new RectF(j - byte0, k - byte0, 2 * byte0 + j - byte0, 2 * byte0 + k - byte0), 0, 360, true, paint);
		byte0 = 2;
		canvas.drawArc(new RectF(j - byte0, k - byte0, 2 * byte0 + j - byte0, 2 * byte0 + k - byte0), 0, 360, true, paint);
		paint.setStyle(Paint.Style.FILL);
	}

	public void _aIIIV(int j, int k, int l, int i1) {
		canvas.drawLine(offsetX(j), offsetY(k), offsetX(l), offsetY(i1), paint);
	}

	public void drawLine(int j, int k, int l, int i1) {
		canvas.drawLine(offsetX((j << 2) / (float) 0xFFFF), offsetY((k << 2) / (float) 0xFFFF), offsetX((l << 2) / (float) 0xFFFF), offsetY((i1 << 2) / (float) 0xFFFF), paint);
	}

	public void _aIIIV(int j, int k, int l, int i1, int j1) {
		drawBikerPart(j, k, l, i1, j1, 32768);
	}

	public void drawBikerPart(int j, int k, int l, int i1, int j1, int k1) {
		float l1 = offsetX((int) ((long) l * (long) k1 >> 16) + (int) ((long) j * (long) (0x10000 - k1) >> 16) >> 16);
		float i2 = offsetY((int) ((long) i1 * (long) k1 >> 16) + (int) ((long) k * (long) (0x10000 - k1) >> 16) >> 16);
		int j2 = FPMath._ifIII(l - j, i1 - k);
		int index = 0;
		switch (j1) {
			case 0: // '\0'
				index = _aIIII(j2, 0, 0x3243f, 16, false);
				break;

			case 1: // '\001'
				index = _aIIII(j2, 0, 0x3243f, 16, false);
				break;

			case 2: // '\002'
				index = _aIIII(j2, 0, 0x3243f, 16, false);
				break;
		}
		float fAngleDeg = (float) (j2 / (float) 0xFFFF / Math.PI * 180) - 180;
		index = 0;

		Bitmap bikerSprite = Bitmap.get(Bitmap.BIKER, j1);
		if (bikerSprite != null) {
			float x = l1 - bikerSprite.getWidthDp() / 2;
			float y = i2 - bikerSprite.getHeightDp() / 2;

			canvas.save();
			canvas.rotate(fAngleDeg, x + bikerSprite.getWidthDp() / 2, y + bikerSprite.getHeightDp() / 2);
			drawBitmap(bikerSprite, x, y);
			canvas.restore();
		}
	}

	// �������� �����
	public void _ifIIIV(int j, int k, int l, int i1) {
		l++;
		float j1 = offsetX(j - l);
		float k1 = offsetY(k + l);
		int l1 = l << 1;
		if ((i1 = -(int) (((long) (int) ((long) i1 * 0xb40000L >> 16) << 32) / 0x3243fL >> 16)) < 0)
			i1 += 360;
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawArc(new RectF(j1, k1, j1 + l1, k1 + l1), -((i1 >> 16) + 170), -90, false, paint);
		paint.setStyle(Paint.Style.FILL);
	}

	// Draws red circle
	public void drawLineWheel(float j, float k, int l) {
		float i1 = l / 2;
		float j1 = offsetX(j - i1);
		float k1 = offsetY(k + i1);

		paint.setStyle(Paint.Style.STROKE);
		canvas.drawArc(new RectF(j1, k1, j1 + l, k1 + l), 0, 360, true, paint);
		paint.setStyle(Paint.Style.FILL);
	}

	public void _forIIIV(int j, int k, int l, int i1) {
		float j1 = offsetX(j);
		float k1 = offsetY(k);
		canvas.drawRect(j1, k1, j1 + l, k1 + i1, paint);
	}

	public void drawSteering(int j, int k) {
		float x = offsetX(j - Bitmap.get(Bitmap.STEERING).getWidthDp() / 2);
		float y = offsetY(k + Bitmap.get(Bitmap.STEERING).getHeightDp() / 2);

		drawBitmap(Bitmap.get(Bitmap.STEERING), x, y);
	}

	public void drawHelmet(float j, float k, int l) {
		float fAngleDeg = (float) (l / (float) 0xFFFF / Math.PI * 180) - 90 - 10;
		if (fAngleDeg >= 360) fAngleDeg -= 360;
		if (fAngleDeg < 0) fAngleDeg = 360 + fAngleDeg;
		if (Bitmap.get(Bitmap.HELMET) != null) {
			float x = offsetX(j) - Bitmap.get(Bitmap.HELMET).getWidthDp() / 2;
			float y = offsetY(k) - Bitmap.get(Bitmap.HELMET).getHeightDp() / 2;
			canvas.save();
			canvas.rotate(fAngleDeg, x + Bitmap.get(Bitmap.HELMET).getWidthDp() / 2, y + Bitmap.get(Bitmap.HELMET).getHeightDp() / 2);

			drawBitmap(Bitmap.get(Bitmap.HELMET), x, y);
			canvas.restore();
		}
	}

	public void _ifIIIIV(int j, int k, int l, int i1, int j1, int k1) {
	}

	public void drawTimer(long l) {
		// logDebug("Timer: " + l);
		String txt = String.format("%d:%02d:%02d", l / 6000, (l / 100) % 60, l % 100);
		// logDebug("drawTimter: long = " + l + ", string = " + txt);
		canvas.drawText(txt, 18, -infoFont.ascent() + 17, timerFont);
	}

	public synchronized void showInfoMessage(String s, int j) {
		m_ahZ = false;
		gc++;
		infoMessage = s;
		if (timer != null) {
			timer.schedule(new SimpleMenuElement(gc), j);
		}
	}

	public void _tryIV(int j) {
		if (gc == j)
			m_ahZ = true;
	}

	public void drawStartFlag(int j, int k) {
		if (m_VI > 0x38000)
			m_VI = 0;
		setColor(0, 0, 0);
		_aIIIV(j, k, j, k + 32);
		drawBitmap(Bitmap.get(Bitmap.FLAGS, startFlagIndexes[m_VI >> 16]), offsetX(j), offsetY(k) - 32);
	}

	public void drawFinishFlag(int j, int k) {
		if (m_VI > 0x38000)
			m_VI = 0;
		setColor(0, 0, 0);
		_aIIIV(j, k, j, k + 32);
		drawBitmap(Bitmap.get(Bitmap.FLAGS, finishFlagIndexes[m_VI >> 16]), offsetX(j), offsetY(k) - 32);
	}

	public void drawWheel(float j, float k, int l) {
		int wheel;
		if (l == 1)
			wheel = 0; // small
		else
			wheel = 1; // big

		float x = offsetX(j - Bitmap.get(Bitmap.WHEELS, wheel).getWidthDp() / 2);
		float y = offsetY(k + Bitmap.get(Bitmap.WHEELS, wheel).getHeightDp() / 2);

		drawBitmap(Bitmap.get(Bitmap.WHEELS, wheel), x, y);
	}

	int _aIIII(int j, int k, int l, int i1, boolean flag) {
		for (j += k; j < 0; j += l) ;
		for (; j >= l; j -= l) ;
		if (flag)
			j = l - j;
		int j1;
		if ((j1 = (int) ((long) (int) (((long) j << 32) / (long) l >> 16) * (long) (i1 << 16) >> 16)) >> 16 < i1 - 1)
			return j1 >> 16;
		else
			return i1 - 1;
	}

	public void drawEngine(float j, float k, int l) {
		float fAngleDeg = (float) (l / (float) 0xFFFF / Math.PI * 180) - 180;
		float x = offsetX(j) - Bitmap.get(Bitmap.ENGINE).getWidthDp() / 2;
		float y = offsetY(k) - Bitmap.get(Bitmap.ENGINE).getHeightDp() / 2;
		if (Bitmap.get(Bitmap.ENGINE) != null) {
			canvas.save();
			canvas.rotate(fAngleDeg, x + Bitmap.get(Bitmap.ENGINE).getWidthDp() / 2, y + Bitmap.get(Bitmap.ENGINE).getHeightDp() / 2);
			drawBitmap(Bitmap.get(Bitmap.ENGINE), x, y);
			canvas.restore();
		}
	}

	public void drawFender(float j, float k, int l) {
		float fAngleDeg = (float) (l / (float) 0xFFFF / Math.PI * 180) - 180 + 15;
		if (fAngleDeg >= 360) fAngleDeg -= 360;
		if (Bitmap.get(Bitmap.FENDER) != null) {
			float x = offsetX(j) - Bitmap.get(Bitmap.FENDER).getWidthDp() / 2;
			float y = offsetY(k) - Bitmap.get(Bitmap.FENDER).getHeightDp() / 2;

			canvas.save();
			canvas.rotate(fAngleDeg, x + Bitmap.get(Bitmap.FENDER).getWidthDp() / 2, y + Bitmap.get(Bitmap.FENDER).getHeightDp() / 2);
			drawBitmap(Bitmap.get(Bitmap.FENDER), x, y);
			canvas.restore();
		}
	}

	public void _tryvV() {
		paint.setColor(0xFFFFFFFF);
		canvas.drawRect(0, 0, m_abI, m_dI, paint);
	}

	public void setColor(int r, int g, int b) {
		GDActivity _tmp = activity;
		if (getGDActivity().isMenuShown()) {
			r += 128;
			g += 128;
			b += 128;
			if (r > 240)
				r = 240;
			if (g > 240)
				g = 240;
			if (b > 240)
				b = 240;
		}
		//m_CGraphics.setColor(j, k, l);
		paint.setColor(0xFF000000 | (r << 16) | (g << 8) | b);
	}

	// Draw boot logos and something else
	public void drawGame(Canvas g) {
		final GDActivity gd = getGDActivity();
		label0:
		{
			int j;
			synchronized (m_ocObject) {
				if (gd.alive && !gd.m_caseZ)
					break label0;
			}
			return;
		}

		if (m_ecZ)
			canvas = m_dcGraphics;
		else
			canvas = g;
		if (m_oI != 0) {
			if (m_oI == 1) {
				// Draw codebrew
				paint.setColor(0xFFFFFFFF);
				canvas.drawRect(0, 0, getScaledWidth(), getScaledHeight(), paint);
				if (Bitmap.get(Bitmap.CODEBREW_LOGO) != null) {
					drawBitmap(Bitmap.get(Bitmap.CODEBREW_LOGO),
							getScaledWidth() / 2 - Bitmap.get(Bitmap.CODEBREW_LOGO).getWidthDp() / 2,
							(float) (getScaledHeight() / 2 - Bitmap.get(Bitmap.CODEBREW_LOGO).getHeightDp() / 1.6));
				}
			} else {
				// Draw gd
				paint.setColor(0xFFFFFFFF);
				canvas.drawRect(0, 0, getScaledWidth(), getScaledHeight(), paint);
				if (Bitmap.get(Bitmap.GD_LOGO) != null) {
					drawBitmap(Bitmap.get(Bitmap.GD_LOGO),
							getScaledWidth() / 2 - Bitmap.get(Bitmap.GD_LOGO).getWidthDp() / 2,
							(float) (getScaledHeight() / 2 - Bitmap.get(Bitmap.GD_LOGO).getHeightDp() / 1.6));
				}
			}
			int j = (int) (((long) (gd.m_longI << 16) << 32) / 0xa0000L >> 16);
			drawProgress(j, true);
		} else {
			if (m_lI != getHeight())
				_ifvV();
			physEngine._voidvV();
			_doIIV(-physEngine._elsevI() + m_TI + m_abI / 2, physEngine._ifvI() + m_QI + m_dI / 2);
			physEngine._ifiV(this);
			if (drawTimer) {
				long time = 0, finished;
				if (gd.startedTime > 0) {
					if (gd.finishedTime > 0)
						finished = gd.finishedTime;
					else
						finished = System.currentTimeMillis();
					time = (finished - gd.startedTime - gd.pausedTime) / 10;
				}
				drawTimer(time);
			}
			if (infoMessage != null) {
				setColor(0, 0, 0);
				infoFont.setColor(paint.getColor());
				/*if (m_dI <= 128)
					canvas.drawText(infoMessage, m_abI / 2 - infoFont.measureText(infoMessage) / 2, 1, infoFont);
				else*/

				canvas.drawText(infoMessage, m_abI / 2 - infoFont.measureText(infoMessage) / 2, m_dI / 5, infoFont);
				if (m_ahZ) {
					m_ahZ = false;
					infoMessage = null;
				}
			}
			int j = physEngine._tryvI();
			drawProgress(j, false);
			if (m_KZ && m_AZ)
				_newvV();
		}
		canvas = null;
		if (m_ecZ)
			g.drawBitmap(m_MBitmap, 0, 0, null);
	}

	public void drawProgress(int j, boolean flag) {
		double progr = j / (double) 0xFFFF;

		paint.setColor(0xffc4c4c4);
		canvas.drawRect(0, 0, getScaledWidth(), 3, paint);

		paint.setColor(0xff29aa27);
		canvas.drawRect(0, 0, Math.round(getScaledWidth() * Math.min(Math.max(progr, 0), 1)), 3, paint);
	}

	private void _ifIIV(int j, int k) {
		if (!getGDActivity().isMenuShown()) {
			byte byte0 = 0;
			byte byte1 = 0;
			m_aiI = j;
			m_agI = k;
			int l = j << 16;
			int i1 = k << 16;
			int j1 = m_abI / 2 << 16;
			int k1 = m_dI + 40 << 16;
			if (m_KZ && m_AZ) {
				int l1 = FPMath._ifIII(l - j1, i1 - k1);
				for (l1 += 25735; l1 < 0; l1 += 0x6487e) ;
				for (; l1 > 0x6487e; l1 -= 0x6487e) ;
				m_OI = l1;
				int i2;
				if ((i2 = 51471) >= l1)
					byte0 = -1;
				else if (l1 < (int) ((long) i2 * 0x20000L >> 16)) {
					byte0 = -1;
					byte1 = 1;
				} else if (l1 < (int) ((long) i2 * 0x30000L >> 16))
					byte1 = 1;
				else if (l1 < (int) ((long) i2 * 0x40000L >> 16)) {
					byte0 = 1;
					byte1 = 1;
				} else if (l1 < (int) ((long) i2 * 0x50000L >> 16))
					byte0 = 1;
				else if (l1 < (int) ((long) i2 * 0x60000L >> 16)) {
					byte0 = 1;
					byte1 = -1;
				} else if (l1 < (int) ((long) i2 * 0x70000L >> 16))
					byte1 = -1;
				else if (l1 < (int) ((long) i2 * 0x80000L >> 16)) {
					byte0 = -1;
					byte1 = -1;
				}
				physEngine._aIIV(byte0, byte1);
			}
		}
	}

	public void _pointerPressedIIV(int j, int k) {
		if (!getGDActivity().isMenuShown())
			_ifIIV(j, k);
	}

	public void _pointerReleasedIIV(int j, int k) {
		if (!getGDActivity().isMenuShown()) {
			m_aiI = 0;
			m_agI = 0;
			physEngine._nullvV();
		}
	}

	public void _pointerDraggedIIV(int j, int k) {
		if (!getGDActivity().isMenuShown())
			_ifIIV(j, k);
	}

	public void setInputOption(int option) {
		inputOption = option;
	}

	private void _avV() {
		for (int j = 0; j < 10; j++)
			m_LaZ[j] = false;

		for (int k = 0; k < 7; k++)
			m_aeaZ[k] = false;

	}

	private void _xavV() {
		int j = 0;
		int k = 0;
		int l = inputOption;
		for (int i1 = 0; i1 < 10; i1++)
			if (m_LaZ[i1]) {
				j += m_maaaB[l][i1][0];
				k += m_maaaB[l][i1][1];
			}

		for (int j1 = 0; j1 < 7; j1++)
			if (m_aeaZ[j1]) {
				j += m_DaaB[j1][0];
				k += m_DaaB[j1][1];
			}

		physEngine._aIIV(j, k);
	}

	protected void processKeyPressed(int j) {
		int k = getGameAction(j);
		int l;
		if ((l = j - 48) >= 0 && l < 10)
			m_LaZ[l] = true;
		else if (k >= 0 && k < 7)
			m_aeaZ[k] = true;
		_xavV();
	}

	protected void processKeyReleased(int j) {
		int k = getGameAction(j);
		int l;
		if ((l = j - 48) >= 0 && l < 10)
			m_LaZ[l] = false;
		else if (k >= 0 && k < 7)
			m_aeaZ[k] = false;
		_xavV();
	}

	public void showMenu() {
		if (menu != null) {
			menu.m_blZ = true;
			getGDActivity().gameToMenu();
		}
	}

	/* protected void hideNotify() {
		if (!getGDActivity().isMenuShown()) {
			GDActivity.m_cZ = true;
			activity.gameToMenu();
		}
	} */

	/* protected void showNotify() {
		GDActivity.m_cZ = false;
	} */

	protected void keyRepeated(int j) {
		if (getGDActivity().isMenuShown() && menu != null)
			menu._tryIV(j);
	}

	public synchronized void keyPressed(int j) {
		if (getGDActivity().isMenuShown() && menu != null)
			menu.keyPressed(j);
		processKeyPressed(j);
	}

	public synchronized void keyReleased(int j) {
		processKeyReleased(j);
	}

	@Override
	public void onDraw(Canvas g) {
		g.save();
		if (!Global.DISABLE_SCALING)
			g.scale(Global.density, Global.density);
		if (getGDActivity().isMenuShown() && menu != null) {
			menu.draw(g);
		} else {
			drawGame(g);
		}
		g.restore();
		invalidate();
	}

	public void commandAction(Command command) {
		if (getGDActivity().isMenuShown() && menu != null) {
			menu.onCommand(command);
		} else {
			showMenu();
		}
	}

	public void removeMenuCommand() {
		removeCommand(menuCommand);
	}

	public void addMenuCommand() {
		addCommand(menuCommand);
	}

	public static int getGameAction(int key) {
		// logDebug("getGameAction: key = " + key);
		switch (key) {
			case 50: // 2
				return MenuScreen.KEY_UP; // up
			case 56: // 8
				return MenuScreen.KEY_DOWN; // down
			case 52: // 4
				return MenuScreen.KEY_LEFT; // left
			case 54: // 6
				return MenuScreen.KEY_RIGHT; // right
			case 53: // 5
				return MenuScreen.KEY_FIRE; // fire
		}
		return 0;
	}

	public void addCommand(Command cmd) {
		GDActivity.shared.addCommand(cmd);
	}

	public void removeCommand(Command cmd) {
		GDActivity.shared.removeCommand(cmd);
	}

	public int getScaledWidth() {
		float density = Global.DISABLE_SCALING ? 1 : Global.density;
		return Math.round(getWidth() / density);
	}

	public int getScaledHeight() {
		float density = Global.DISABLE_SCALING ? 1 : Global.density;
		return Math.round(getHeight() / density);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int width = MeasureSpec.getSize(widthMeasureSpec), height = MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(width, height);
	}

	public synchronized void destroy() {
		if (timer != null) {
			timer.cancel();
			timer.purge();
			timer = null;
		}
	}

}
