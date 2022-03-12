package eu.mcfreedom.griefunionpublic.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.systems.commands.Command;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class SpamCommand extends Command {
    public SpamCommand() {
        super("spam", "Spams something multiple times. (%s to spam something for each player)");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("amount", IntegerArgumentType.integer()).then(argument("message", StringArgumentType.greedyString()).executes(ctx -> {
            int amount = ctx.getArgument("amount", Integer.class);
            if (amount < 1) {
                error("Amount cannot be less than 1.");
                return SINGLE_SUCCESS;
            }
            if (ctx.getArgument("message", String.class).contains("%s")) {
                for (PlayerListEntry playerListEntry : mc.player.networkHandler.getPlayerList()) {
                    for (int i = 0; i < amount; i++) {
                        mc.player.sendChatMessage(String.join(" ", ctx.getArgument("message", String.class)).replaceAll("%s", playerListEntry.getProfile().getName()));
                    }
                }
            } else {
                for (int i = 0; i < amount; i++) {
                    mc.player.sendChatMessage(String.join(" ", ctx.getArgument("message", String.class)));
                }
            }
            return SINGLE_SUCCESS;
        })));
    }
}