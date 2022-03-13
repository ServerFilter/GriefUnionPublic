package eu.mcfreedom.griefunionpublic.modules;

import eu.mcfreedom.griefunionpublic.GriefUnionPublic;
import meteordevelopment.meteorclient.events.entity.player.BreakBlockEvent;
import meteordevelopment.meteorclient.events.entity.player.PlaceBlockEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.Direction;

public class AntiDesync extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> checkPlace = sgGeneral.add(new BoolSetting.Builder()
            .name("place-check")
            .description("Attempts to stop de-syncing for block placing.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> checkBreak = sgGeneral.add(new BoolSetting.Builder()
            .name("break-check")
            .description("Attempts to stop de-syncing for block breaking.")
            .defaultValue(true)
            .build()
    );

    public AntiDesync() {
        super(GriefUnionPublic.CATEGORY_GRIEFUNION, "anti-desync", "Prevents ghost blocks from forming.");
    }

    @EventHandler
    private void onBlockPlace(PlaceBlockEvent event) {
        if (checkPlace.get() && event.blockPos != null) {
            mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, event.blockPos, Direction.UP));
        }
    }

    @EventHandler
    private void onBlockBreak(BreakBlockEvent event) {
        if (checkBreak.get() && event.blockPos != null) {
            mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, event.blockPos, Direction.UP));
        }
    }
}