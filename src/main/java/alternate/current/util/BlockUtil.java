package alternate.current.util;

import net.minecraft.util.math.Direction;

public class BlockUtil {
	
	/** Directions in the order in which they are used for emitting shape updates. */
	public static final Direction[] DIRECTIONS = { Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.DOWN, Direction.UP };
	
	public static final int FLAG_NOTIFY_CLIENTS = 0b10;
	
}
