package eu.mcfreedom.griefunionpublic;

import eu.mcfreedom.griefunionpublic.commands.LagMessageCommand;
import eu.mcfreedom.griefunionpublic.commands.SpamCommand;
import eu.mcfreedom.griefunionpublic.commands.FakeChatCommand;
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

        Modules.get().add(new AACCrash());
        Modules.get().add(new BoatCrash());
        Modules.get().add(new BookCrash());
        Modules.get().add(new ContainerCrash());
        Modules.get().add(new EntityCrash());
        Modules.get().add(new InvalidPositionCrash());
        Modules.get().add(new LoginCrash());
        Modules.get().add(new MovementCrash());
        Modules.get().add(new PlayerCrash());
        Modules.get().add(new SignCrash());
        Modules.get().add(new TryUseCrash());
        Modules.get().add(new EntityFly());
        Modules.get().add(new AntiVanish());
        Modules.get().add(new TPConfuse());
        Modules.get().add(new PortalGodMode());
        Modules.get().add(new OOBCrash());
        Modules.get().add(new NoComCrash());
        Modules.get().add(new CommandBlockBypasser());
        Modules.get().add(new APIRestrictions());
        Modules.get().add(new Phase());
        Modules.get().add(new BetterFlight());
        Modules.get().add(new ResourcePackSpoofer());
        Modules.get().add(new PacketPhase());
        Modules.get().add(new BowBomb());
        Modules.get().add(new PacketFly());
        Modules.get().add(new NoJumpCooldown());
        Modules.get().add(new LeverAura());
        Modules.get().add(new HologramSpammer());
        Modules.get().add(new ItemSpammer());
        Modules.get().add(new AntiCrash());
        Modules.get().add(new OtherBoatCrash());
        Modules.get().add(new RainbowArmor());
        Modules.get().add(new AntiBot());
        Modules.get().add(new AntiDesync());
        Modules.get().add(new EventCoordLogger());
        Modules.get().add(new TextFileSpam());
        Modules.get().add(new FireballSpammer());
        Modules.get().add(new BorderSizeChanger());
        Modules.get().add(new FireballClicker());
        Modules.get().add(new AutoMountBypassDupe());
        Modules.get().add(new AllowParagraph());
        Modules.get().add(new ChatImageSpammer());
        Modules.get().add(new MultiTask());
        Modules.get().add(new DeathSpectate());
        Modules.get().add(new SlotSpammer());
        Modules.get().add(new InstaPaperCrash());

        Commands.get().add(new SpigotKickCommand());
        Commands.get().add(new KickCommand());
        Commands.get().add(new ServerCommand());
        Commands.get().add(new SetVelocityCommand());
        Commands.get().add(new TeleportCommand());
        Commands.get().add(new CrashCommand());
        Commands.get().add(new DupeCommand());
        Commands.get().add(new HologramCommand());
        Commands.get().add(new ForEachCommand());
        Commands.get().add(new GhostCommand());
        Commands.get().add(new GriefEggCommand());
        Commands.get().add(new CommandBookCommand());
        Commands.get().add(new OpOnlyCommand());
        Commands.get().add(new ItemEggCommand());
        Commands.get().add(new BlockBanCommand());
        Commands.get().add(new ArmorCommand());
        Commands.get().add(new FakeChatCommand());
        Commands.get().add(new LagMessageCommand());
        Commands.get().add(new WitherAdCommand());
        Commands.get().add(new PotionSpamCommand());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY_GRIEFUNION);
    }
}