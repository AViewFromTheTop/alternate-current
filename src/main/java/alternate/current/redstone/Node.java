package alternate.current.redstone;

import alternate.current.AlternateCurrentMod;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

/**
 * A Node represents a block in the world. It is tied to a
 * specific wire block type so it can be identified as part of
 * a wire network or as a neighbor of a wire network. It also
 * holds a few other pieces of information that speed up the
 * calculations in the WireHandler class.
 * 
 * @author Space Walker
 */
public class Node {
	
	public final WireBlock wireBlock;
	public final WorldAccess world;
	
	public BlockPos pos;
	public BlockState state;
	
	public boolean isWire;
	public boolean isSolidBlock;
	public boolean isRedstoneComponent;
	
	public Node(WireBlock wireBlock, WorldAccess world) {
		this.wireBlock = wireBlock;
		this.world = world;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Node) {
			Node node = (Node)o;
			return world == node.world && wireBlock == node.wireBlock && pos.equals(node.pos);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return pos.hashCode();
	}
	
	public Node update(BlockPos pos, BlockState state) {
		this.pos = pos.toImmutable();
		this.state = state;
		
		this.isWire = false;
		this.isSolidBlock = false;
		this.isRedstoneComponent = false;
		
		if (wireBlock.isOf(state)) {
			AlternateCurrentMod.LOGGER.warn("Cannot update a Node to a WireNode!");
		} else {
			if (world.isSolidBlock(pos, state)) {
				this.isSolidBlock = true;
			}
			if (state.emitsRedstonePower()) {
				this.isRedstoneComponent = true;
			}
		}
		
		return this;
	}
	
	public WireNode asWire() {
		throw new UnsupportedOperationException("Not a WireNode!");
	}
}
