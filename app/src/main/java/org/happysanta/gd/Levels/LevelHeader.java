package org.happysanta.gd.Levels;

public class LevelHeader {

	private int pointers[][] = new int[3][];
	private String names[][] = new String[3][];
	private int counts[] = new int[3];

	public LevelHeader() {
	}

	public void setCount(int level, int count) {
		if (level >= counts.length)
			return;

		pointers[level] = new int[count];
		names[level] = new String[count];
		counts[level] = count;
	}

	public int getCount(int level) {
		if (level < counts.length)
			return counts[level];
		else
			return 0;
	}

	public void setPointer(int level, int index, int value) {
		pointers[level][index] = value;
	}

	public void setName(int level, int index, String value) {
		names[level][index] = value;
	}

	public int[][] getPointers() {
		return pointers;
	}

	public String[][] getNames() {
		return names;
	}

	public boolean isCountsOk() {
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] <= 0)
				return false;
		}

		return true;
	}

}
