package com.iHamster.blockbreaker.blocks;

import com.iHamster.blockbreaker.util.RegistryHandler;

import net.minecraft.block.*;

import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.nbt.CompoundNBT;


import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.items.CapabilityItemHandler;
import org.lwjgl.system.CallbackI;

import java.util.Collections;
import java.util.List;


public class BlockBreakerPoweredTile extends TileEntity implements ITickableTileEntity {
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

    public BlockBreakerPoweredTile() {
        super(RegistryHandler.BLOCKBREAKERPOWERED_TILE.get());
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

        if (!this.hasWorld()) return;  // prevent crash
        World world = this.getWorld();
        if (world.isRemote) return;


        BlockState blockState = getBlockState();
        boolean power = blockState.get(isPowered);
        direction = blockState.get(facing);
        if (!world.isBlockPowered(pos)&& power)  { //power off?


            world.setBlockState(pos, RegistryHandler.BLOCKBREAKERUNPOWERED_BLOCK.get().getDefaultState().with(BlockStateProperties.POWERED, false).with(facing, direction));
            this.remove();
            return;
            }
        BlockPos inFront = pos, behind = pos;


        if (direction == Direction.NORTH) {
            inFront = pos.add(0, 0, -1);
            behind = pos.add(0, 0, 1);
        } else if (direction == Direction.SOUTH) {
            inFront = pos.add(0,0,1);
            behind = pos.add(0,0,-1);
        } else if (direction == Direction.WEST) {
            inFront = pos.add(-1, 0, 0);
            behind = pos.add(1, 0, 0);
        } else if (direction == Direction.EAST) {
            inFront = pos.add(1,0,0);
            behind = pos.add(-1,0,0);
        }

        BlockState blockInFront = world.getBlockState(inFront);
        BlockState blockBehind = world.getBlockState(behind);
        Block block = blockInFront.getBlock();

        if (block != Blocks.AIR) { //if not in air
            TileEntity tileEntity = world.getTileEntity(behind);  //cheching if there's a container behind
                if (tileEntity!= null && tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)!= null && blockBehind.getBlock() == Blocks.CHEST) { // if there's a supported container behind
                        ItemStack stack;        //checking if block in front of Destroyer is not stone, otherwise change it to Cobblestone. Creating Item Stack.
                        if (block != Blocks.STONE) {
                            stack = new ItemStack(block.asItem(), 1);
                        } else {
                            stack = new ItemStack(Blocks.COBBLESTONE.asItem(), 1);
                        }
                        if (blockBehind.getBlock() == Blocks.CHEST ) {

                            Block containerType = blockBehind.getBlock();
                            if (!AddItemToChest(tileEntity, stack)) {
                                Block.spawnDrops(blockInFront, world, behind);
                            }  //if failed, the destroyed block falls behind
                        }
                    } else {
                        Block.spawnDrops(blockInFront, world, behind);//if there is no container behind, the destroyed block appears behind the breaker
                    }
                    world.destroyBlock(inFront, false);

        }

}

    private boolean AddItemToChest(TileEntity tileEntity, ItemStack stack) {    //adding items to the container
        Boolean success = false;//it fails by default :)

            int slots = ((ChestTileEntity) tileEntity).getSizeInventory();
            System.out.println(slots);
            Item item = stack.getItem();
            for (int i = 0; i <slots; i++) {
            ItemStack stack_in_slot = ((ChestTileEntity) tileEntity).getStackInSlot(i);  //if slot in the chest is empty
            if (stack_in_slot.isEmpty()) {
                ((ChestTileEntity) tileEntity).setInventorySlotContents(i, stack);

                return true;
            } else if ((stack_in_slot.getItem() == item) && stack_in_slot.getCount() < 64) {     //if stack in the slot is not full
                int newCount = stack_in_slot.getCount() + 1;
                stack.setCount(newCount);
                ((ChestTileEntity) tileEntity).setInventorySlotContents(i, stack);

                return true;
            }
        }
        return success;
    }
}



