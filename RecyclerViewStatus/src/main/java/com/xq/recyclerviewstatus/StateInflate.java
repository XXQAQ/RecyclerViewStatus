package com.xq.recyclerviewstatus;

public class StateInflate<T> {

    private final String stateId;
    private final int layoutId;
    private final Block<T> block;

    public StateInflate(String stateId, int layoutId) {
        this.stateId = stateId;
        this.layoutId = layoutId;
        this.block = null;
    }

    public StateInflate(String stateId, int layoutId, Block<T> block) {
        this.stateId = stateId;
        this.layoutId = layoutId;
        this.block = block;
    }

    public String getStateId() {
        return stateId;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public Block<T> getBlock() {
        return block;
    }

    public interface Block<T>{
        void onBind(StateAdapter.StateViewHolder viewHolder, T t);
    }

}
