package io.github.coolmineman.bitsandchisels;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.coolmineman.bitsandchisels.blueprints.Blueprint;
import io.github.coolmineman.bitsandchisels.chisel.DiamondChisel;
import io.github.coolmineman.bitsandchisels.chisel.IronChisel;
import io.github.coolmineman.bitsandchisels.chisel.SmartChisel;
import io.github.coolmineman.bitsandchisels.wrench.WrenchItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BitsAndChisels implements ModInitializer {

	public static final String MODID = "bitsandchisels";

	public static final Logger LOGGER = LoggerFactory.getLogger("BitsAndChisels");

	public static final boolean CANVAS = FabricLoader.getInstance().isModLoaded("canvas");

	public static final BitsBlock BITS_BLOCK = new BitsBlock(FabricBlockSettings.of(Material.METAL).nonOpaque().dynamicBounds().hardness(4.0f).luminance(s -> s.get(BitsBlock.LIGHT_LEVEL)));
	public static final BlockItem BITS_BLOCK_ITEM = new BlockItem(BITS_BLOCK, new Item.Settings());
	public static final DiamondChisel DIAMOND_CHISEL = new DiamondChisel(new Item.Settings());
	public static final IronChisel IRON_CHISEL = new IronChisel(new Item.Settings());
	public static final SmartChisel SMART_CHISEL = new SmartChisel(new Item.Settings());
	public static final WrenchItem WRENCH_ITEM = new WrenchItem(new Item.Settings());
	public static final Blueprint BLUEPRINT = new Blueprint(new Item.Settings().maxCount(1));
	public static BlockEntityType<BitsBlockEntity> BITS_BLOCK_ENTITY;

	public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder(new Identifier(MODID, MODID)).icon(BitsAndChisels::getDiamondChiselStack).entries((context, entries) -> {
		entries.add(DIAMOND_CHISEL);
		entries.add(IRON_CHISEL);
		entries.add(SMART_CHISEL);
		entries.add(WRENCH_ITEM);
		entries.add(BLUEPRINT);
	}).build();

	public static final BitItem BIT_ITEM = new BitItem(new Item.Settings().maxCount(1_000_000_000));

	//Hack around "Cannot reference a field before it is defined"
	private static ItemStack getDiamondChiselStack() {
		return new ItemStack(DIAMOND_CHISEL);
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(Registries.BLOCK, new Identifier(MODID, "bits_block"), BITS_BLOCK);
		BITS_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "bitsandchisels:bits_block_entity", FabricBlockEntityTypeBuilder.create(BitsBlockEntity::new, BITS_BLOCK).build(null));
		Registry.register(Registries.ITEM, new Identifier(MODID, "bits_block"), BITS_BLOCK_ITEM);
		Registry.register(Registries.ITEM, new Identifier(MODID, "diamond_chisel"), DIAMOND_CHISEL);
		Registry.register(Registries.ITEM, new Identifier(MODID, "iron_chisel"), IRON_CHISEL);
		Registry.register(Registries.ITEM, new Identifier(MODID, "smart_chisel"), SMART_CHISEL);
		Registry.register(Registries.ITEM, new Identifier(MODID, "bit_item"), BIT_ITEM);
		Registry.register(Registries.ITEM, new Identifier(MODID, "wrench"), WRENCH_ITEM);
		Registry.register(Registries.ITEM, new Identifier(MODID, "blueprint"), BLUEPRINT);
	}
	
}
