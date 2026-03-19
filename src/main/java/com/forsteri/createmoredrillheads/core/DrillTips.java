package com.forsteri.createmoredrillheads.core;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public enum DrillTips {
    NONE(null, null, () -> ItemStack.EMPTY),
    FORTUNE_I("redstone_dusts", ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/redstone")), () -> {
        ItemStack stack = Items.STICK.getDefaultInstance();
        applyEnchantment(stack, Enchantments.FORTUNE, 1);
        return stack;
    }),
    FORTUNE_II("quartz_dusts", ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/quartz")),
             () -> {
        ItemStack stack = Items.STICK.getDefaultInstance();
        applyEnchantment(stack, Enchantments.FORTUNE, 2);
        return stack;
    }),
    FORTUNE_III("emerald_dusts", ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/emerald")),() -> {
        ItemStack stack = Items.STICK.getDefaultInstance();
        applyEnchantment(stack, Enchantments.FORTUNE, 3);
        return stack;
    }),
    SILK_TOUCH("amethyst_dusts", ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/amethyst")), () -> {
        ItemStack stack = Items.STICK.getDefaultInstance();
        applyEnchantment(stack, Enchantments.SILK_TOUCH, 1);
        return stack;
    });

    private static void applyEnchantment(ItemStack stack, net.minecraft.resources.ResourceKey<Enchantment> enchantmentKey, int level) {
        stack.update(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY, map -> {
            ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(map);
            // In 1.21.1, we need to get the Holder for the enchantment. 
            // This assumes the level is accessible, which it is during runtime.
            var registry = net.neoforged.neoforge.server.ServerLifecycleHooks.getCurrentServer().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
            var holder = registry.getHolderOrThrow(enchantmentKey);
            mutable.set(holder, level);
            return mutable.toImmutable();
        });
    }

    public String getName() {
        return name;
    }

    public @Nullable TagKey<Item> getMaterial() {
        return material;
    }
    public ItemStack getItemStack() {
        return itemStackSupplier.get();
    }
    private final @Nullable TagKey<Item> material;
    private final String name;
    private final Supplier<ItemStack> itemStackSupplier;
    DrillTips(String name, @Nullable TagKey<Item> material, Supplier<ItemStack> supplier) {
        this.material = material;
        this.name = name;
        this.itemStackSupplier = supplier;
    }
}
