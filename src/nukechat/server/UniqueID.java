package nukechat.server;

import java.util.*;

public class UniqueID {

	private static int i = 0;

	private static List<Integer> ids = new ArrayList<>();
	private static final int ID_RANGE = 10000;

	private UniqueID() {}

	static {
		for (int i = 0; i < ID_RANGE; i++) {
			ids.add(i);
		}
		Collections.shuffle(ids);
	}

	public static int getID() {
		if (i > ids.size() - 1) i = 0;
		return ids.get(i);
	}
}