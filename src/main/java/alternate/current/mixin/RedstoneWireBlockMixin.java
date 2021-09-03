package alternate.current.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import alternate.current.interfaces.mixin.IServerWorld;
import alternate.current.redstone.WireBlock;
import alternate.current.redstone.WireNode;
import alternate.current.redstone.WorldAccess;

import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(RedstoneWireBlock.class)
public abstract class RedstoneWireBlockMixin implements WireBlock {
	
	@Inject(
			method = "onBlockAdded",
			cancellable = true,
			at = @At(
					value = "HEAD"
			)
	)
	private void onOnBlockAddedInjectAtHead(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
		ci.cancel(); // replaced by WireBlock.onWireAdded
	}
	
	@Inject(
			method = "onStateReplaced",
			cancellable = true,
			at = @At(
					value = "HEAD"
			)
	)
	private void onOnStateReplacedInjectAtHead(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved, CallbackInfo ci) {
		ci.cancel(); // replaced by WireBlock.onWireRemoved
	}
	
	@Inject(
			method = "update",
			cancellable = true,
			at = @At(
					value = "HEAD"
			)
	)
	private void onUpdateInjectAtHead(World world, BlockPos pos, BlockState state, CallbackInfo ci) {
		WorldAccess worldAccess = ((IServerWorld)world).getAccess(this);
		WireNode wire = worldAccess.getWire(pos, true, true);
		
		if (wire != null) {
			tryUpdatePower(wire);
		}
		
		ci.cancel();
	}
}
