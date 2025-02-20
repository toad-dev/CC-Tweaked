// SPDX-FileCopyrightText: 2022 The CC: Tweaked Developers
//
// SPDX-License-Identifier: MPL-2.0

package dan200.computercraft.data;

import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Consumer;

/**
 * All data providers for ComputerCraft. We require a mod-loader abstraction {@link GeneratorSink} (instead of
 * {@link PackOutput})to handle the slight differences between how Forge and Fabric expose Minecraft's data providers.
 */
public final class DataProviders {
    private DataProviders() {
    }

    public static void add(GeneratorSink generator) {
        var turtleUpgrades = generator.add(TurtleUpgradeProvider::new);
        var pocketUpgrades = generator.add(PocketUpgradeProvider::new);
        generator.add(out -> new RecipeProvider(out, turtleUpgrades, pocketUpgrades));

        var blockTags = generator.blockTags(TagProvider::blockTags);
        generator.itemTags(TagProvider::itemTags, blockTags);

        generator.lootTable(LootTableProvider.getTables());

        generator.models(BlockModelProvider::addBlockModels, ItemModelProvider::addItemModels);

        generator.add(out -> new LanguageProvider(out, turtleUpgrades, pocketUpgrades));
    }

    interface GeneratorSink {
        <T extends DataProvider> T add(DataProvider.Factory<T> factory);

        void lootTable(List<SubProviderEntry> tables);

        TagsProvider<Block> blockTags(Consumer<TagProvider.TagConsumer<Block>> tags);

        TagsProvider<Item> itemTags(Consumer<TagProvider.ItemTagConsumer> tags, TagsProvider<Block> blocks);

        void models(Consumer<BlockModelGenerators> blocks, Consumer<ItemModelGenerators> items);
    }
}
