package xyz.tryfle.oitemphysic.mixin;

import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(ItemEntityRenderer.class)
public abstract class ItemEntityRendererMixin {

    @Unique
    private final Map<Integer, Float> oitemphysic$groundYaws = new HashMap<>();

    @ModifyVariable(method = "applyItemBobbing", at = @At("STORE"), ordinal = 2)
    private float removeBobbing(float g) {
        return 0.0F; // this variable applied bobbing. it no longer applies bobbing.
    }

    @Redirect(method = "applyItemBobbing", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/platform/GlStateManager;rotatef(FFFF)V"))
    private void removeSpin(float angle, float x, float y, float z) {}

    @Inject(method = "render(Lnet/minecraft/entity/ItemEntity;DDDFF)V",
            at = @At(value="INVOKE", target = "applyItemBobbing", shift = At.Shift.AFTER))
    public void onRender(ItemEntity entity, double dx, double dy, double dz, float yaw, float tickDelta, CallbackInfo ci) {
        int id = entity.getNetworkId();

        double vx = entity.velocityX;
        double vz = entity.velocityZ;

        float movementYaw;

        if (vx * vx + vz * vz > 0.0001) {
            movementYaw = (float) Math.toDegrees(Math.atan2(-vx, vz)); // tries to position item based on player throwing pos

            float variation = id % 30 - 15; // slight random
            movementYaw += variation;

            oitemphysic$groundYaws.put(id, movementYaw); // try to cache yaw to use for position on land
        } else movementYaw = oitemphysic$groundYaws.computeIfAbsent(id, i -> (float) (id % 360)); // random

        GlStateManager.translatef(0f, -0.1f, 0f); // prevent floating
        GlStateManager.rotatef(movementYaw, 0F, 1F, 0F);

        if (entity.onGround) {
            GlStateManager.translatef(0f, -0.1f, 0f); // prevent floating (2: yes, it's necessary, unfortunately.)
            GlStateManager.rotatef(90F, 1F, 0F, 0F); // make it sit flat.
        } else {
            Vec3d velocity = new Vec3d(entity.velocityX, entity.velocityY, entity.velocityZ);

            float spinX = (float) (velocity.x * 180.0F);
            float spinZ = (float) (velocity.z * 180.0F);
            float angle = ((entity.getAge() + tickDelta) * 8.0F);

            // spinny based on velocity and time
            GlStateManager.rotatef(angle, 1F, 0F, 0F);
            GlStateManager.rotatef(spinX, 0F, 1F, 0F);
            GlStateManager.rotatef(spinZ, 0F, 0F, 1F);
        }
    }
}