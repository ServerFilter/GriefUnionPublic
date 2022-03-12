package eu.mcfreedom.griefunionpublic.mixin;

import eu.mcfreedom.griefunionpublic.modules.AllowParagraph;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SharedConstants.class)
public class SharedConstantsMixin {
    @Inject(method = "isValidChar", at = @At("HEAD"), cancellable = true)
    private static void allowParagraph(char chr, CallbackInfoReturnable<Boolean> cir) {
        if (Modules.get().isActive(AllowParagraph.class) && chr == '§') {
            cir.setReturnValue(true);
        }
    }
}