package com.iHamster.blockbreaker.events;

import com.iHamster.blockbreaker.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
/*
@Mod.EventBusSubscriber(modid = "blockbreaker", bus =  Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)

// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
// Event bus for receiving Registry Events)

public class ModServerEvents {

    public static DirectionProperty facing;

    @SubscribeEvent
    public static void power (BlockEvent.NeighborNotifyEvent event){
        BlockState blockState = event.getState();
        BlockPos pos = event.getPos();
        Direction direction = blockState.get(facing);
        World world = (World) event.getWorld();
        Boolean power = world.isBlockPowered(pos);


        if (blockState.getBlock()== RegistryHandler.BLOCKBREAKERUNPOWERED_BLOCK.get().getBlock() && power){
            world.setBlockState(pos,RegistryHandler.BLOCKBREAKERPOWERED_BLOCK.get().getDefaultState().with(facing, direction));
        }

    }
}
*/