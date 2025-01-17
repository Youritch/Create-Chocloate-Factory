package fr.cleboost.createchocolatefactory.block.cocoablock;

import fr.cleboost.createchocolatefactory.utils.ModBlocks;
import fr.cleboost.createchocolatefactory.utils.ModItems;
import fr.cleboost.createchocolatefactory.utils.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class CocoaClosedBlock extends DirectionalBlock {

    public static final boolean CustomModel = true;

    public CocoaClosedBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState p_154346_, @NotNull BlockGetter p_154347_, @NotNull BlockPos p_154348_, @NotNull CollisionContext p_154349_) {
        return Block.box(3.0D, 0.0D, 6.0D, 14.7D, 4.0D, 10.0D);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.createchocolatefactory.cocoa_block_closed"));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide() && pPlayer.getItemInHand(pHand).is(ModTags.Items.MACHETE_LIKE)) {
            Random random = new Random();
            pLevel.destroyBlock(pPos, false);
            pLevel.setBlockAndUpdate(pPos, ModBlocks.COCOA_BLOCK_OPENED.get().defaultBlockState());
            Block.popResource(pLevel, pPos, new ItemStack(ModItems.COCOA_BARK.get(), random.nextInt(1, 4)));//1-3
            pPlayer.getItemInHand(pHand).hurtAndBreak(1,pPlayer,playerEvent -> pPlayer.broadcastBreakEvent(pPlayer.getUsedItemHand()));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
