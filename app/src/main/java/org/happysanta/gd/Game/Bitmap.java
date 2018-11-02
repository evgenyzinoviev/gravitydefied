package org.happysanta.gd.Game;

import org.happysanta.gd.Global;
import org.happysanta.gd.R;

import java.io.IOException;

import static org.happysanta.gd.Helpers.*;

public class Bitmap {

	public final static int HELMET = 0;
	public final static int CODEBREW_LOGO = 1;
	public final static int GD_LOGO = 2;
	public final static int STEERING = 3;
	public final static int WHEELS = 4;
	public final static int ARROWS = 5;
	public final static int FLAGS = 6;
	public final static int LOCKS = 7;
	public final static int MEDALS = 8;
	public final static int LEVELS_WHEELS = 9;
	public final static int FENDER = 10;
	public final static int ENGINE = 11;
	public final static int BIKER = 12;

	public final static int BLUEARM = 100;
	public final static int BLUELEG = 101;
	public final static int BLUEBODY = 102;

	public android.graphics.Bitmap bitmap;
	protected static GDBitmapHolder holders[];
	protected static Bitmap empty = null;

	Bitmap(android.graphics.Bitmap b) {
		bitmap = b;
	}

	static {
		holders = new GDBitmapHolder[]{
				// 0
				new GDBitmapHolder(Bitmap.fromDrawable(R.drawable.s_helmet)),

				// 1
				new GDBitmapHolder(Bitmap.fromDrawable(R.drawable.codebrew)),

				// 2
				new GDBitmapHolder(Bitmap.fromDrawable(R.drawable.gd)),

				// 3
				new GDBitmapHolder(Bitmap.fromDrawable(R.drawable.s_steering)),

				// 4
				new GDBitmapHolder(new Bitmap[]{
						Bitmap.fromDrawable(R.drawable.s_wheel1),
						Bitmap.fromDrawable(R.drawable.s_wheel2)
				}),

				// 5
				new GDBitmapHolder(new Bitmap[]{
						Bitmap.fromDrawable(R.drawable.s_arrow_up),
						Bitmap.fromDrawable(R.drawable.s_arrow_down)
				}),

				// 6
				new GDBitmapHolder(new Bitmap[]{
						Bitmap.fromDrawable(R.drawable.s_flag_start0),
						Bitmap.fromDrawable(R.drawable.s_flag_start1),
						Bitmap.fromDrawable(R.drawable.s_flag_start2),
						Bitmap.fromDrawable(R.drawable.s_flag_finish0),
						Bitmap.fromDrawable(R.drawable.s_flag_finish1),
						Bitmap.fromDrawable(R.drawable.s_flag_finish2)
				}),

				// 7
				new GDBitmapHolder(new Bitmap[]{
						Bitmap.fromDrawable(R.drawable.s_lock0),
						Bitmap.fromDrawable(R.drawable.s_lock1),
						Bitmap.fromDrawable(R.drawable.s_lock2)
				}),

				// 8
				new GDBitmapHolder(new Bitmap[]{
						Bitmap.fromDrawable(R.drawable.s_medal_gold),
						Bitmap.fromDrawable(R.drawable.s_medal_silver),
						Bitmap.fromDrawable(R.drawable.s_medal_bronze)
				}),

				// 9
				new GDBitmapHolder(new Bitmap[]{
						Bitmap.fromDrawable(R.drawable.levels_wheel0),
						Bitmap.fromDrawable(R.drawable.levels_wheel1),
						Bitmap.fromDrawable(R.drawable.levels_wheel2)
				}),

				// 10
				new GDBitmapHolder(Bitmap.fromDrawable(R.drawable.s_fender)),

				// 11
				new GDBitmapHolder(Bitmap.fromDrawable(R.drawable.s_engine)),

				// 12
				new GDBitmapHolder(new Bitmap[]{
						Bitmap.fromDrawable(R.drawable.s_bluearm),
						Bitmap.fromDrawable(R.drawable.s_blueleg),
						Bitmap.fromDrawable(R.drawable.s_bluebody)
				})
		};
		empty = new Bitmap(android.graphics.Bitmap.createBitmap(1, 1, android.graphics.Bitmap.Config.ARGB_8888));
	}

	public static Bitmap get(int index) {
		if (index >= BLUEARM && index <= BLUEBODY) {
			return get(BIKER, index - 100);
		}

		if (holders.length >= index - 1) {
			GDBitmapHolder holder = holders[index];

			if (holder != null && !holder.isArray && holder.bitmap != null) {
				return holder.bitmap;
			}
		}

		return empty;
	}

	public static Bitmap get(int index, int arrayIndex) {
		if (holders.length >= index - 1) {
			GDBitmapHolder holder = holders[index];
			if (holder != null && holder.isArray && holder.bitmaps != null && holder.bitmaps.length >= arrayIndex - 1) {
				return holder.bitmaps[arrayIndex];
			}
		}

		return empty;
	}

	public static Bitmap getEmpty() {
		return empty;
	}

	public static Bitmap fromDrawable(int id) {
		return new Bitmap(loadBitmapFromDrawable(id));
	}

	public static Bitmap fromAsset(String s) throws IOException {
		return new Bitmap(loadBitmapFromAsset(s));
	}

	public int getWidth() {
		return bitmap.getWidth();
	}

	public int getHeight() {
		return bitmap.getHeight();
	}

	public int getWidthDp() {
		return Math.round(getWidth() / Global.density);
	}

	public int getHeightDp() {
		return Math.round(getHeight() / Global.density);
	}

	private static class GDBitmapHolder {

		public Bitmap bitmap = null;
		public Bitmap bitmaps[] = null;
		public boolean isArray = false;

		GDBitmapHolder(Bitmap bitmap) {
			this.bitmap = bitmap;
		}

		GDBitmapHolder(Bitmap bitmaps[]) {
			this.bitmaps = new Bitmap[bitmaps.length];
			System.arraycopy(bitmaps, 0, this.bitmaps, 0, bitmaps.length);
			isArray = true;
		}

	}

}
