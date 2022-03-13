package eu.mcfreedom.griefunionpublic.modules;

import eu.mcfreedom.griefunionpublic.GriefUnionPublic;
import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;

import java.util.Random;

public class ItemSpammer extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Integer> dropSpeed = sgGeneral.add(new IntSetting.Builder()
            .name("drop-speed")
            .description("How many items to drop per tick.")
            .defaultValue(1)
            .min(1)
            .sliderMin(1)
            .sliderMax(36)
            .build()
    );

    private final Setting<Integer> stackSize = sgGeneral.add(new IntSetting.Builder()
            .name("stack-size")
            .description("How many items to place in a stack.")
            .defaultValue(1)
            .min(1)
            .sliderMin(1)
            .sliderMax(64)
            .build()
    );

    private final Setting<Boolean> autoDisable = sgGeneral.add(new BoolSetting.Builder()
            .name("auto-disable")
            .description("Disables module on kick.")
            .defaultValue(true)
            .build()
    );

    public ItemSpammer() {
        super(GriefUnionPublic.CATEGORY_GRIEFUNION, "item-spammer", "Repeatedly drops random items.");
    }

    @Override
    public void onActivate() {
        if (!mc.player.getAbilities().creativeMode) {
            error("You must be in creative mode to use this.");
            toggle();
        }
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (autoDisable.get()) toggle();
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (!mc.player.getAbilities().creativeMode) {
            error("You must be in creative mode to use this.");
            toggle();
            return;
        }
        for (int i = 9; i < 9 + dropSpeed.get(); i++) {
            mc.interactionManager.clickCreativeStack(new ItemStack(Registry.ITEM.getRandom(new Random()).map(RegistryEntry::value).orElse(Items.AIR), stackSize.get()), i);
            InvUtils.drop().slot(i);
        }
    }
}