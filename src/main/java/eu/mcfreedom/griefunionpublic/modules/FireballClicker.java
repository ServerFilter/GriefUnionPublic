package eu.mcfreedom.griefunionpublic.modules;

import eu.mcfreedom.griefunionpublic.GriefUnionPublic;
import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.meteor.MouseButtonEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.misc.input.KeyAction;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class FireballClicker extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder()
            .name("fireball-speed")
            .description("How fast the fireballs should be fired.")
            .defaultValue(3)
            .min(0)
            .sliderMax(10)
            .build()
    );

    private final Setting<Double> power = sgGeneral.add(new DoubleSetting.Builder()
            .name("fireball-power")
            .description("How powerful the fireball should be.")
            .defaultValue(5)
            .min(0)
            .sliderMax(127)
            .build()
    );

    private final Setting<Boolean> autoDisable = sgGeneral.add(new BoolSetting.Builder()
            .name("auto-disable")
            .description("Disables module on kick.")
            .defaultValue(true)
            .build()
    );

    public FireballClicker() {
        super(GriefUnionPublic.CATEGORY_GRIEFUNION, "fireball-clicker", "Spawns a fireball at where you're clicking.");
    }

    @Override
    public void onActivate() {
        if (!mc.player.getAbilities().creativeMode) {
            error("You must be in creative mode to use this.");
            toggle();
        }
    }

    @Override
    public void onDeactivate() {
        mc.player.getInventory().selectedSlot = 0;
        mc.interactionManager.clickCreativeStack(new ItemStack(Items.AIR), 36);
    }

    @EventHandler
    private void onKey(MouseButtonEvent event) {
        if (mc.currentScreen != null) return;
        if (event.button == GLFW_MOUSE_BUTTON_LEFT && event.action == KeyAction.Press) {
            event.setCancelled(true);
            Vec3d vec = mc.player.getRotationVector().multiply(speed.get() / 10d);
            BlockHitResult bhr = new BlockHitResult(mc.player.getEyePos(), Direction.DOWN, new BlockPos(mc.player.getEyePos()), false);
            ItemStack spawnEgg = new ItemStack(Items.BAT_SPAWN_EGG);
            NbtCompound entityTag = spawnEgg.getOrCreateSubNbt("EntityTag");
            entityTag.put("id", NbtString.of("minecraft:fireball"));
            NbtList nbtList = new NbtList();
            nbtList.add(NbtDouble.of(vec.getX()));
            nbtList.add(NbtDouble.of(vec.getY()));
            nbtList.add(NbtDouble.of(vec.getZ()));
            entityTag.put("power", nbtList);
            entityTag.put("ExplosionPower", NbtDouble.of(power.get()));
            mc.interactionManager.clickCreativeStack(spawnEgg, 36);
            mc.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, bhr));
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (!mc.player.getAbilities().creativeMode) {
            error("You must be in creative mode to use this.");
            toggle();
            return;
        }
        mc.player.getInventory().selectedSlot = 0;
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (autoDisable.get()) toggle();
    }
}