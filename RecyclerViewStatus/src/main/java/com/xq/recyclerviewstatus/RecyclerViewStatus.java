package com.xq.recyclerviewstatus;

import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewStatus {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter<?> contentAdapter;
    private RecyclerView.LayoutManager contentLayoutManager;
    private ConcatAdapter concatStateAdapter;
    private RecyclerView.LayoutManager stateLayoutManager;
    private final Map<String,StateAdapter<?>> stateAdapterMap = new HashMap<>();

    public void attachToRecyclerView(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        //
        contentAdapter = recyclerView.getAdapter();
        contentLayoutManager = recyclerView.getLayoutManager();
        //
        if (concatStateAdapter == null){
            concatStateAdapter = new ConcatAdapter();
        } else {
            clearAllAdapter(concatStateAdapter);
        }
        if (stateLayoutManager == null){
            stateLayoutManager = new LinearLayoutManager(recyclerView.getContext(),RecyclerView.VERTICAL,false);
        }
    }

    public void initStates(List<StateInflate<?>> stateInflateList){
        stateAdapterMap.clear();
        for (final StateInflate stateInflate : stateInflateList){
            StateAdapter<?> stateAdapter = new StateAdapter<Object>(stateInflate.getLayoutId()) {
                @Override
                public void onBind(StateViewHolder holder, Object o) {
                    if (stateInflate.getBlock() != null){
                        stateInflate.getBlock().onBind(holder,o);
                    }
                }
            };
            stateAdapterMap.put(stateInflate.getStateId(),stateAdapter);
        }
    }

    public void showContent(){
        clearAllAdapter(concatStateAdapter);
        recyclerView.setAdapter(contentAdapter);
        recyclerView.setLayoutManager(contentLayoutManager);
    }

    public void showState(String stateId){
        showState(stateId,null);
    }

    public void showState(String stateId,Object tag){
        if (stateAdapterMap.containsKey(stateId)){
            //
            StateAdapter stateAdapter = stateAdapterMap.get(stateId);
            stateAdapter.setItem(tag);
            switchToAdapter(concatStateAdapter,stateAdapter);
            recyclerView.setAdapter(concatStateAdapter);
            recyclerView.setLayoutManager(stateLayoutManager);
        }
    }

    public void switchToAdapter(ConcatAdapter concatAdapter,RecyclerView.Adapter<?> adapter){
        clearAllAdapter(concatAdapter);
        concatAdapter.addAdapter(adapter);
    }

    private void clearAllAdapter(ConcatAdapter concatAdapter){
        for (RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter : new ArrayList<>(concatAdapter.getAdapters())) {
            concatAdapter.removeAdapter(adapter);
        }
    }

    public boolean isShowContent(){
        return recyclerView.getAdapter() == contentAdapter;
    }

    public RecyclerView.Adapter<?> getContentAdapter() {
        return contentAdapter;
    }

    public RecyclerView.LayoutManager getContentLayoutManager() {
        return contentLayoutManager;
    }

}
