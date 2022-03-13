package eu.mcfreedom.griefunionpublic.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import meteordevelopment.meteorclient.systems.commands.Command;
import net.minecraft.command.CommandSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.LiteralText;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

// I should make this a module lol
public class CommandBookCommand extends Command {
    private static final SimpleCommandExceptionType NOT_IN_CREATIVE = new SimpleCommandExceptionType(new LiteralText("You must be in creative mode to use this."));

    public CommandBookCommand() {
        super("command-book", "Gives you a book with a click event.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("pages", IntegerArgumentType.integer()).then(argument("text", StringArgumentType.string()).then(argument("command", StringArgumentType.greedyString()).executes(ctx -> {
            int amount = ctx.getArgument("pages", Integer.class);
            if (amount < 1) {
                error("Amount cannot be less than 1.");
                return SINGLE_SUCCESS;
            }
            String text = ctx.getArgument("text", String.class).replace("&", "\247").replace("_", " ");
            String command = ctx.getArgument("command", String.class).replace("&", "\247");
            ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
            NbtCompound tag = book.getOrCreateNbt();
            tag.put("title", NbtString.of("The \"Kaboom\" Story"));
            tag.put("author", NbtString.of("TheKaboomMan37"));
            NbtList pages = new NbtList();
            pages.add(NbtString.of("{\"text\":\"" + (text + " ".repeat(500)) + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + command + "\"}}"));
            for (int i = 0; i < amount - 1; i++) {
                if (amount != 1) pages.add(NbtString.of("{\"text\":\"\"}"));
            }
            tag.put("pages", pages);
            book.setNbt(tag);
            if (!mc.player.getAbilities().creativeMode) throw NOT_IN_CREATIVE.create();
            mc.interactionManager.clickCreativeStack(book, 36 + mc.player.getInventory().selectedSlot);
            return SINGLE_SUCCESS;
        }))));
    }
}