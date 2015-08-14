package org.happysanta.gd.Levels;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.happysanta.gd.Helpers.decodeCp1251;

public class Reader {

	private static final int MAX_VALID_TRACKS = 16384;

	public static LevelHeader readHeader(InputStream in) throws IOException {
		LevelHeader header = new LevelHeader();
		DataInputStream din = new DataInputStream(in);
		byte buf[] = new byte[40];
		String tmp;
		for (int i = 0; i < 3; i++) {
			int tCount = din.readInt();
			if (tCount > MAX_VALID_TRACKS) {
				din.close();
				throw new IOException("Level file is not valid");
			}
			header.setCount(i, tCount);

			label0:
			for (int j = 0; j < header.getCount(i); j++) {
				int trackPointer = din.readInt();
				header.setPointer(i, j, trackPointer);
				int nameLen = 0;
				do {
					if (nameLen >= 40)
						continue label0;

					buf[nameLen] = din.readByte();
					if (buf[nameLen] == 0) {
						// tmp = (new String(buf, 0, nameLen, "CP-1251"));
						tmp = decodeCp1251(buf);
						header.setName(i, j, tmp.replace('_', ' '));
						continue label0;
					}
					nameLen++;
				} while (true);
			}

		}
		din.close();

		return header;
	}

}
