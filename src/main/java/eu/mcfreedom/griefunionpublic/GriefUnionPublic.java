package eu.mcfreedom.griefunionpublic;

import eu.mcfreedom.griefunionpublic.commands.*;
import eu.mcfreedom.griefunionpublic.modules.AllowParagraph;
import eu.mcfreedom.griefunionpublic.modules.NoJumpCooldown;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.commands.Commands;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.item.Items;

import java.lang.invoke.MethodHandles;

public class GriefUnionPublic extends MeteorAddon {
    public static final Category CATEGORY_GRIEFUNION = new Category("Grief Union Public", Items.TNT.getDefaultStack());

    @Override
    public void onInitialize() {
        MeteorClient.EVENT_BUS.registerLambdaFactory("eu.mcfreedom.griefunionpublic", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));

        Modules.get().add(new NoJumpCooldown());
        Modules.get().add(new AllowParagraph());

        Commands.get().add(new SpamCommand());
        Commands.get().add(new FakeChatCommand());
        Commands.get().add(new LagMessageCommand());
        Commands.get().add(new HologramCommand());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY_GRIEFUNION);
    }
}