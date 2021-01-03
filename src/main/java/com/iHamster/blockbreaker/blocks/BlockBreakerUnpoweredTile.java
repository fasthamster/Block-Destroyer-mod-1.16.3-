package com.iHamster.blockbreaker.blocks;

import com.iHamster.blockbreaker.util.RegistryHandler;

import net.minecraft.block.*;

import net.minecraft.nbt.*;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.World;


public class BlockBreakerUnpoweredTile extends TileEntity implements ITickableTileEntity {
    public static Direction direction;
    private static DirectionProperty facing = BlockStateProperties.HORIZONTAL_FACING;
    public static BooleanProperty isPowered =  BlockStateProperties.POWERED;

    @Override
    public CompoundNBT getUpdateTag()
    {
        CompoundNBT nbtTagCompound = new CompoundNBT();
        write(nbtTagCompound);
        return nbtTagCompound;
    }

    /* Populates this TileEntity with information from the tag, used by vanilla to transmit from server to client
     */
    @Override
    public void handleUpdateTag(BlockState blockState, CompoundNBT parentNBTTagCompound)
    {
        this.read(blockState, parentNBTTagCompound);
    }

    public BlockBreakerUnpoweredTile() {
        super(RegistryHandler.BLOCKBREAKERUNPOWERED_TILE.get());
    }
    /*
        public void read(BlockState state, CompoundNBT nbt) {
            super.read(state, nbt);
            this.breakerState = NBTUtil.readBlockState(nbt.getCompound("blockState"));
        }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put("blockState", NBTUtil.writeBlockState(this.breakerState));
        return compound;
    }
    */
    @Override
    public void tick() {

        if (!this.hasWorld()) return;
        World world = this.getWorld();
        if (world.isRemote) return;


        BlockState blockState = getBlockState();
        boolean power = blockState.get(isPowered);


        if (world.isBlockPowered(pos)&& !power) {
            direction = blockState.get(facing);
            world.setBlockState(pos, RegistryHandler.BLOCKBREAKERPOWERED_BLOCK.get().getDefaultState().with(BlockStateProperties.POWERED, true).with(facing, direction));
            this.remove();
            return;
        }

    }
}

