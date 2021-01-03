package com.iHamster.blockbreaker.util;


import com.iHamster.blockbreaker.BlockBreaker;
import com.iHamster.blockbreaker.blocks.BlockBreakerPoweredBlock;
import com.iHamster.blockbreaker.blocks.BlockBreakerUnpoweredBlock;
import com.iHamster.blockbreaker.blocks.BlockBreakerPoweredTile;
import com.iHamster.blockbreaker.blocks.BlockBreakerUnpoweredTile;
import com.iHamster.blockbreaker.blocks.BlockItemBase;

import net.minecraft.block.Block;
import net.minecraft.item.Item;


import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {

    public static final DeferredRegister<Item> ITEMS =  DeferredRegister.create(ForgeRegistries.ITEMS, BlockBreaker.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BlockBreaker.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BlockBreaker.MOD_ID);



    public static void init(){

        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());

    }


    public static final RegistryObject<Block> BLOCKBREAKERUNPOWERED_BLOCK = BLOCKS.register("block_breaker_unpowered", BlockBreakerUnpoweredBlock::new);
    public static final RegistryObject<Block> BLOCKBREAKERPOWERED_BLOCK = BLOCKS.register("block_breaker_powered", BlockBreakerPoweredBlock::new);

//    public static final RegistryObject<Item> BLOCKBREAKERUNPOWERED_ITEM = ITEMS.register("block_breaker_unpowered", ()-> new BlockItemBase(BLOCKBREAKERUNPOWERED_BLOCK.get()));
    public static final RegistryObject<Item> BLOCKBREAKERPOWERED_ITEM = ITEMS.register("block_breaker_powered", ()-> new BlockItemBase(BLOCKBREAKERPOWERED_BLOCK.get()));

    public static final RegistryObject<TileEntityType<BlockBreakerUnpoweredTile>> BLOCKBREAKERUNPOWERED_TILE = TILES.register("block_breaker_unpowered", ()-> TileEntityType.Builder.create(BlockBreakerUnpoweredTile::new,BLOCKBREAKERUNPOWERED_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<BlockBreakerPoweredTile>> BLOCKBREAKERPOWERED_TILE = TILES.register("block_breaker_powered", ()->TileEntityType.Builder.create(BlockBreakerPoweredTile::new,BLOCKBREAKERPOWERED_BLOCK.get()).build(null));
}
