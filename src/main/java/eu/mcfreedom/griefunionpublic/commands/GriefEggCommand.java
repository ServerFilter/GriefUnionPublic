package eu.mcfreedom.griefunionpublic.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import meteordevelopment.meteorclient.systems.commands.Command;
import net.minecraft.command.CommandSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.LiteralText;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class GriefEggCommand extends Command {
    private final static SimpleCommandExceptionType NOT_IN_CREATIVE = new SimpleCommandExceptionType(new LiteralText("You must be in creative mode to use this."));

    public GriefEggCommand() {
        super("grief-egg", "Creates a fireball spawnegg with a huge power.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(ctx -> {
            ItemStack stack = new ItemStack(Items.BAT_SPAWN_EGG);
            NbtCompound tag = new NbtCompound();
            NbtList power = new NbtList();
            power.add(NbtDouble.of(0.0));
            power.add(NbtDouble.of(-1.0));
            power.add(NbtDouble.of(0.0));
            tag.put("id", NbtString.of("minecraft:fireball"));
            tag.put("ExplosionPower", NbtDouble.of(96));
            tag.put("power", power);
            stack.setSubNbt("EntityTag", tag);
            if (!mc.player.getAbilities().creativeMode) throw NOT_IN_CREATIVE.create();
            mc.interactionManager.clickCreativeStack(stack, 36 + mc.player.getInventory().selectedSlot);
            return SINGLE_SUCCESS;
        });
    }
}