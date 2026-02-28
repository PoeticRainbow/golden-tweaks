package io.github.poeticrainbow.goldentweaks.mixin.common;

import net.minecraft.client.renderer.block.ModelBlockRenderer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.client.renderer.block.ModelBlockRenderer$AmbientOcclusionRenderStorage")
public abstract class AmbientOcclusionRenderStorageMixin  {
    //@Inject(method = "calculate", at = @At(target = "Lnet/minecraft/world/level/BlockAndTintGetter;getShade(Lnet/minecraft/core/Direction;Z)F", value = "INVOKE"))
    //private void goldentweaks$whole_face_block_lighting(BlockAndTintGetter blockAndTintGetter, BlockState blockState, BlockPos blockPos, Direction direction, boolean bl, CallbackInfo ci, @Local ModelBlockRenderer.AmbientVertexRemap ambientVertexRemap) {
    //
    //    this.lightmap[ambientVertexRemap.vert0] = blend(l, i, q, v);
    //    this.lightmap[ambientVertexRemap.vert1] = blend(k, i, o, v);
    //    this.lightmap[ambientVertexRemap.vert2] = blend(k, j, s, v);
    //    this.lightmap[ambientVertexRemap.vert3] = blend(l, j, u, v);
    //    this.brightness[ambientVertexRemap.vert0] = x;
    //    this.brightness[ambientVertexRemap.vert1] = y;
    //    this.brightness[ambientVertexRemap.vert2] = z;
    //    this.brightness[ambientVertexRemap.vert3] = aa;
    //}

    /*
    * Faces should never be partial and should always use lighting as if they are full faces
    */
    @Redirect(method = "calculate", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/block/ModelBlockRenderer$AmbientOcclusionRenderStorage;facePartial:Z", opcode = Opcodes.GETFIELD))
    private boolean inject(ModelBlockRenderer.AmbientOcclusionRenderStorage instance) {
        return false;
    }
}
