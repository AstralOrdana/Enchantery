package com.ordana.enchantery.events;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.Optional;

public class BookshelfNameRendererEvent {

    private static int getHitSlot(Vec2 hitPos) {
        int i = hitPos.y >= 0.5F ? 0 : 1;
        int j = getSection(hitPos.x);
        return j + i * 3;
    }

    private static int getSection(float x) {
        float f = 0.0625F;
        float g = 0.375F;
        if (x < 0.375F) {
            return 0;
        } else {
            float h = 0.6875F;
            return x < 0.6875F ? 1 : 2;
        }
    }


    private static Optional<Vec2> getRelativeHitCoordinatesForBlockFace(BlockHitResult hitResult, Direction face) {
        Direction direction = hitResult.getDirection();
        if (face != direction) {
            return Optional.empty();
        } else {
            BlockPos blockPos = hitResult.getBlockPos().relative(direction);
            Vec3 vec3 = hitResult.getLocation().subtract((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ());
            double d = vec3.x();
            double e = vec3.y();
            double f = vec3.z();
            Optional var10000 = switch (direction) {
                case NORTH -> Optional.of(new Vec2((float) (1.0D - d), (float) e));
                case SOUTH -> Optional.of(new Vec2((float) d, (float) e));
                case WEST -> Optional.of(new Vec2((float) f, (float) e));
                case EAST -> Optional.of(new Vec2((float) (1.0D - f), (float) e));
                case DOWN, UP -> Optional.empty();
                default -> throw new IncompatibleClassChangeError();
            };

            return var10000;
        }
    }
    public static void myFunc(Level level, HitResult blockHit) {
        if (blockHit instanceof BlockHitResult hit) {
            BlockState state = level.getBlockState(hit.getBlockPos());
            BlockPos pos = hit.getBlockPos();
            if (level.getBlockState(hit.getBlockPos()).getBlock() instanceof GlassBlock) {


                MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
                renderNameTag(new Vec3(pos.getX(), pos.getY(), pos.getZ()),
                        Component.literal("pisspisspisspiss"),
                        new PoseStack(), bufferSource, level.getLightEmission(pos));


                bufferSource.endBatch();

            }
        }
    }


    /*
    public static void myFunc(Level level, HitResult blockHit) {
        if (blockHit instanceof BlockHitResult hit) {
            BlockState state = level.getBlockState(hit.getBlockPos());
            BlockPos pos = hit.getBlockPos();
            if (level.getBlockState(hit.getBlockPos()).getBlock() instanceof ChiseledBookShelfBlock bookshelf && level.getBlockEntity(pos) instanceof ChiseledBookShelfBlockEntity bookshelfEntity) {


                Optional<Vec2> optional = getRelativeHitCoordinatesForBlockFace(hit, state.getValue(HorizontalDirectionalBlock.FACING));
                if (optional.isPresent()) {

                    Component name = bookshelfEntity.getItem(getHitSlot(optional.get())).getHoverName();

                if (bookshelfEntity.getItem(getHitSlot(optional.get())).getItem() instanceof EnchantedBookItem book) {

                    ListTag storedEnchantments = new ItemStack(book).getEnchantmentTags();

                    for (int i = 0; i < storedEnchantments.size(); ++i) {
                        if (Screen.hasShiftDown()) {
                            CompoundTag compoundTag = storedEnchantments.getCompound(i);
                            BuiltInRegistries.ENCHANTMENT.getOptional(EnchantmentHelper.getEnchantmentId(compoundTag)).ifPresent((enchantment) -> {

                                name = Component.literal(enchantment.getDescriptionId());

                            });
                        }
                    }
                }
                    name = Component.literal("piss");

                    MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
                    renderNameTag(new Vec3(pos.getX(), pos.getY(), pos.getZ()),
                            name,
                            new PoseStack(), bufferSource, level.getLightEmission(pos));


                    bufferSource.endBatch();
                }
            }
        }
    }
    */

    protected static void renderNameTag(Vec3 pos, Component displayName, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {

        double d = Minecraft.getInstance().getEntityRenderDispatcher().distanceToSqr(pos.x, pos.y, pos.z);

        if (!(d > 4096.0D)) {
            //boolean bl = !entity.isDiscrete();
            float f = 0.2f; //entity.getNameTagOffsetY();
            int i = "deadmau5".equals(displayName.getString()) ? -10 : 0;
            matrixStack.pushPose();
            matrixStack.translate(0.0F, f, 0.0F);
            //matrixStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
            matrixStack.scale(-0.025F, -0.025F, 0.025F);

            //var cameraPos = Minecraft.getInstance().cameraEntity;
            //matrixStack.translate(pos.x - cameraPos.getX(), pos.y - cameraPos.getY(), pos.z - cameraPos.getZ());

            Matrix4f matrix4f = matrixStack.last().pose();
            float g = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
            int j = (int)(g * 255.0F) << 24;
            Font font = Minecraft.getInstance().font;
            float h = (float)(-font.width(displayName) / 2);
            font.drawInBatch(displayName, h, (float)i, 553648127, false, matrix4f, buffer, Font.DisplayMode.NORMAL, j, packedLight);
            font.drawInBatch(displayName, h, (float)i, -1, false, matrix4f, buffer, Font.DisplayMode.NORMAL, 0, packedLight);


            matrixStack.popPose();
        }
    }
}
