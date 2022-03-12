package eu.mcfreedom.griefunionpublic.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.systems.commands.Command;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class LagMessageCommand extends Command {
    public LagMessageCommand() {
        super("lag-message", "Spams a random unicode message in the chat.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("color").then(argument("amount", IntegerArgumentType.integer()).executes(ctx -> {
            int amount = ctx.getArgument("amount", Integer.class);
            if (amount < 1) {
                error("Amount cannot be less than 1.");
                return SINGLE_SUCCESS;
            }
            for (int i = 0; i < amount; i++) mc.player.sendChatMessage("&k" + generateMessage());
            return SINGLE_SUCCESS;
        })));
        builder.then(literal("nocolor").then(argument("amount", IntegerArgumentType.integer()).executes(ctx -> {
            int amount = ctx.getArgument("amount", Integer.class);
            if (amount < 1) {
                error("Amount cannot be less than 1.");
                return SINGLE_SUCCESS;
            }
            for (int i = 0; i < amount; i++) mc.player.sendChatMessage(generateMessage());
            return SINGLE_SUCCESS;
        })));
    }

    private String generateMessage() {
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < 256; i++) message.append((char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1))));
        return message.toString();
    }
}