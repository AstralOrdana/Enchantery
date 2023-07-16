package com.ordana.enchantery.events;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ordana.enchantery.configs.ClientConfigs;
import com.ordana.enchantery.configs.CommonConfigs;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.Container;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;
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
            Vec3 vec3 = hitResult.getLocation().subtract(blockPos.getX(), blockPos.getY(), blockPos.getZ());
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

    public static List<Component> bookText(ItemStack stack) {
        List<Component> listText = new ArrayList<>();
        listText.add(stack.getDisplayName());
        if (stack.getItem() instanceof EnchantedBookItem book) {
            ListTag storedEnchantments = book.getEnchantments(stack);
            for (int j = 0; j < storedEnchantments.size(); ++j) {
                CompoundTag compoundTag = storedEnchantments.getCompound(j);
                BuiltInRegistries.ENCHANTMENT.getOptional(EnchantmentHelper.getEnchantmentId(compoundTag)).ifPresent((enchantment) -> {

                    if (enchantment.isCurse()) listText.add(Component.translatable(enchantment.getDescriptionId()).setStyle(Style.EMPTY.applyFormat(ChatFormatting.RED)));
                    else listText.add(Component.translatable(enchantment.getDescriptionId()).setStyle(Style.EMPTY.applyFormat(ChatFormatting.WHITE)));

                });
            }
        }
        return listText;
    }

    public static void renderBookName(Level level, HitResult blockHit) {
        if (blockHit instanceof BlockHitResult hit) {
            BlockPos pos = hit.getBlockPos();
            if (ClientConfigs.BOOKSHELF_LABELS_SHIFT.get()) {
                if (!InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), Minecraft.getInstance().options.keyShift.key.getValue())) return;
            }

            if (level.getBlockState(hit.getBlockPos()).getBlock() instanceof ChiseledBookShelfBlock && level.getBlockEntity(pos) instanceof Container bookshelfEntity) {
                BlockState state = level.getBlockState(hit.getBlockPos());
                Optional<Vec2> optional = getRelativeHitCoordinatesForBlockFace(hit, state.getValue(HorizontalDirectionalBlock.FACING));

                if (optional.isPresent()) {
                    int i = getHitSlot(optional.get());

                    if (state.getValue(ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.get(i))) {
                        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
                        PoseStack matrixStack = new PoseStack();
                        matrixStack.mulPose(Axis.XP.rotationDegrees(Minecraft.getInstance().getEntityRenderDispatcher().camera.getXRot()));
                        matrixStack.mulPose(Axis.YP.rotationDegrees(Minecraft.getInstance().getEntityRenderDispatcher().camera.getYRot() + 180.0f));
                        ItemStack stack = bookshelfEntity.getItem(i);


                        if (Minecraft.renderNames() && (stack.hasCustomHoverName() || stack.getItem() instanceof EnchantedBookItem || stack.getItem() instanceof WrittenBookItem)) {

                            List<Component> listText = bookText(stack);


                                renderNameTag(stack, i, hit.getDirection(),
                                        new Vec3(pos.getX(), pos.getY(), pos.getZ()),
                                        listText,
                                        matrixStack, bufferSource, level.getLightEmission(pos));



                            bufferSource.endBatch();
                        }
                    }
                }
            }
        }
    }


    protected static void renderNameTag(ItemStack stack, int slot, Direction dir, Vec3 pos, List<Component> listText, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        double d = Minecraft.getInstance().getEntityRenderDispatcher().distanceToSqr(pos.x, pos.y, pos.z);

        var cameraPos = Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition();
        matrixStack.translate(pos.x - cameraPos.x, pos.y - cameraPos.y, pos.z - cameraPos.z);

        if (d > 4096.0) {
            return;
        }

        matrixStack.pushPose();

        ///

        matrixStack.translate(0.5f + (dir.getNormal().getX() / 1.3f), 1.2f, 0.5f + (dir.getNormal().getZ() / 1.3f));

        //book positioner
        switch (slot) {
            case 0 -> matrixStack.translate((dir.getNormal().getZ() * -0.3), 0, (dir.getNormal().getX() * 0.3));
            case 1 -> matrixStack.translate(0, 0, 0);
            case 2 -> matrixStack.translate((dir.getNormal().getZ() * 0.3), 0, (dir.getNormal().getX() * -0.3));
            case 3 -> matrixStack.translate((dir.getNormal().getZ() * -0.3), -0.5, (dir.getNormal().getX() * 0.3));
            case 4 -> matrixStack.translate(0, -0.5, 0);
            case 5 -> matrixStack.translate((dir.getNormal().getZ() * 0.3), -0.5, (dir.getNormal().getX() * -0.3));
            default -> matrixStack.translate(0, 0, 0);
        };

        matrixStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        matrixStack.scale(-0.025f, -0.025f, 0.025f);
        Matrix4f matrix4f = matrixStack.last().pose();
        float g = Minecraft.getInstance().options.getBackgroundOpacity(0.25f);
        int j = (int)(g * 255.0f) << 24;
        Font font = Minecraft.getInstance().font;

        float y = 0;
        for (Component text : listText) {
            float h = (float)(-font.width(text)) / 2;
            font.drawInBatch(text, h, y, ChatFormatting.WHITE.getId(), false, matrix4f, buffer, Font.DisplayMode.NORMAL, j, 15728880);
            y += 10f;
        }

        matrixStack.popPose();

    }
}
