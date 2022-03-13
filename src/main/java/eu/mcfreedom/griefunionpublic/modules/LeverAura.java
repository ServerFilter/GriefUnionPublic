package eu.mcfreedom.griefunionpublic.modules;

import eu.mcfreedom.griefunionpublic.GriefUnionPublic;
import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class LeverAura extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> amount = sgGeneral.add(new DoubleSetting.Builder()
            .name("amount")
            .description("How many times to interact with the levers per tick.")
            .defaultValue(1)
            .min(0.0)
            .sliderMax(50)
            .build()
    );

    private final Setting<Boolean> autoDisable = sgGeneral.add(new BoolSetting.Builder()
            .name("auto-disable")
            .description("Disables module on kick.")
            .defaultValue(true)
            .build()
    );

    public LeverAura() {
        super(GriefUnionPublic.CATEGORY_GRIEFUNION, "lever-aura", "Spams levers around you.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        for (double x = -4; x < 4 + 1; x++) {
            for (double y = -4; y < 4 + 1; y++) {
                for (double z = -4; z < 4 + 1; z++) {
                    if (new Vec3d(x, y, z).distanceTo(Vec3d.ZERO) >= mc.interactionManager.getReachDistance()) continue;
                    if (mc.world.getBlockState(new BlockPos(mc.player.getPos().add(new Vec3d(x, y, z)))).getBlock() == Blocks.LEVER) {
                        for (int i = 0; i < amount.get(); i++) {
                            mc.player.swingHand(Hand.MAIN_HAND);
                            mc.interactionManager.interactBlock(mc.player, mc.world, Hand.MAIN_HAND, new BlockHitResult(mc.player.getPos().add(new Vec3d(x, y, z)), Direction.DOWN, new BlockPos(mc.player.getPos().add(new Vec3d(x, y, z))), false));
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (autoDisable.get()) toggle();
    }
}