package syconn.swe.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import syconn.swe.block.FluidBaseBlock;

import java.util.stream.Stream;

public class PipeModule {

    private final boolean n, e, s, w;
    private final boolean u, d;
    private final CanisterStorageType type;

    PipeModule(boolean n, boolean e, boolean s, boolean w, boolean u, boolean d, CanisterStorageType type) {
        this.n = n; this.e = e; this.s = s; this.w = w; this.u = u; this.d = d; this.type = type;
    }

    public PipeModule(BlockState state){
        this(state.getValue(PipeBlock.NORTH), state.getValue(PipeBlock.EAST), state.getValue(PipeBlock.SOUTH), state.getValue(PipeBlock.WEST), state.getValue(PipeBlock.UP), state.getValue(PipeBlock.DOWN), state.getValue(FluidBaseBlock.FLUID_TYPE));
    }

    public PipeModule() {
        this(false, false, false, false, false, false, CanisterStorageType.EMPTY);
    }

    private boolean isQuad(){
        return n && e && s && w;
    }

    private boolean isStraightDouble(){
        return n && s || w && e;
    }

    private boolean isLDouble(){
        return n && e || e && s || s && w || w && n;
    }

    private boolean isTriple(){
        return n && e && s || e && s && w || s && w && n || w && n && e;
    }

    private boolean isSingle(){
        return getPipeSides(n, w, e, s) == 1;
    }

    public CanisterStorageType getType() {
        return type;
    }

    public boolean isUp() {
        return u;
    }

    public boolean isDown() {
        return d;
    }

    private int getYRotation(){
        int sides = getPipeSides(n, s, e, w);
        switch (sides) {
            case 1: {
                if (e) return 90;
                if (s) return 180;
                if (w) return 270;
            }
            case 2: {
                if (e && w || e && s) return 90;
                if (s && w) return 180;
                if (n && w) return 270;
            }
            case 3 : {
                if (e && s && w) return 90;
                if (s && w && n) return 180;
                if (w && n && e) return 270;
            }
        }
        return 0;
    }

    private int getPipeSides(boolean... s){
        int c = 0;
        for (boolean v : s) if (v) c++;
        return c;
    }

    public VoxelShape getShape(){
        if (isQuad()) return Stream.of(Block.box(0, 5, 5, 5, 11, 11), Block.box(5, 5, 0, 11, 11, 16),
                Block.box(11, 5, 5, 16, 11, 11)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        if (isTriple()){
            if (getYRotation() == 0) return Shapes.join(Block.box(11, 5, 5, 16, 11, 11), Block.box(5, 5, 0, 11, 11, 16), BooleanOp.OR);
            if (getYRotation() == 90) return Shapes.join(Block.box(5, 5, 11, 11, 11, 16), Block.box(0, 5, 5, 16, 11, 11), BooleanOp.OR);
            if (getYRotation() == 180) return Shapes.join(Block.box(0, 5, 5, 5, 11, 11), Block.box(5, 5, 0, 11, 11, 16), BooleanOp.OR);
            return Shapes.join(Block.box(5, 5, 0, 11, 11, 5), Block.box(0, 5, 5, 16, 11, 11), BooleanOp.OR);
        }
        if (isSingle()){
            if (getYRotation() == 0) return Block.box(5, 5, 0, 11, 11, 11);
            if (getYRotation() == 90) return Block.box(5, 5, 5, 16, 11, 11);
            if (getYRotation() == 180) return Block.box(5, 5, 5, 11, 11, 16);
            return Block.box(5, 5, 5, 11, 11, 16);
        }
        if (isLDouble()){
            if (getYRotation() == 0) return Shapes.join(Block.box(5, 5, 0, 11, 11, 11), Block.box(11, 5, 5, 16, 11, 11), BooleanOp.OR);
            if (getYRotation() == 90) return Shapes.join(Block.box(5, 5, 5, 16, 11, 11), Block.box(5, 5, 11, 11, 11, 16), BooleanOp.OR);
            if (getYRotation() == 180) return Shapes.join(Block.box(5, 5, 5, 11, 11, 16), Block.box(0, 5, 5, 5, 11, 11), BooleanOp.OR);
            return Shapes.join(Block.box(0, 5, 5, 11, 11, 11), Block.box(5, 5, 0, 11, 11, 5), BooleanOp.OR);
        }
        if (isStraightDouble()) {
            if (getYRotation() == 0) return Block.box(5, 5, 0, 11, 11, 16);
            return Block.box(0, 5, 5, 16, 11, 11);
        }
        return Block.box(5, 5, 5, 11, 11, 11);
    }

    public BlockState getStateForPlacement(BlockState state, BlockPos pos, LevelAccessor l){
        return state.setValue(PipeBlock.NORTH, isBlock(pos, l, Direction.NORTH)).setValue(PipeBlock.WEST, isBlock(pos, l, Direction.WEST)).setValue(PipeBlock.SOUTH, isBlock(pos, l, Direction.SOUTH)).setValue(PipeBlock.EAST, isBlock(pos, l, Direction.EAST)).setValue(PipeBlock.UP, isBlock(pos, l, Direction.UP)).setValue(PipeBlock.DOWN, isBlock(pos, l, Direction.DOWN));
    }

    private boolean isBlock(BlockPos pos, LevelAccessor l, Direction d){
        return l.getBlockState(pos.offset(d.getStepX(), d.getStepY(), d.getStepZ())).getBlock() instanceof FluidBaseBlock;
    }
}
