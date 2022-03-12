package eu.mcfreedom.griefunionpublic.modules;

import eu.mcfreedom.griefunionpublic.GriefUnionPublic;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.mixin.LivingEntityAccessor;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;

public class NoJumpCooldown extends Module {
    public NoJumpCooldown() {
        super(GriefUnionPublic.CATEGORY_GRIEFUNION, "no-jump-cooldown", "Removes the jump cooldown.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        ((LivingEntityAccessor) mc.player).setJumpCooldown(0);
    }
}