package eu.mcfreedom.griefunionpublic.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.systems.commands.Command;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class FakeChatCommand extends Command {
    public FakeChatCommand() {
        super("fake-chat", "Allows you to send a fake chat message.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("message", StringArgumentType.greedyString()).executes(ctx -> {
            mc.inGameHud.getChatHud().addMessage(Text.of(ctx.getArgument("message", String.class).replace("&", "\247")));
            return SINGLE_SUCCESS;
        }));
    }
}