package com.xq.recyclerviewstatus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class StateAdapter<T> extends RecyclerView.Adapter<StateAdapter.StateViewHolder> {

    private final int itemView;

    public StateAdapter(int itemView) {
        this.itemView = itemView;
    }

    private final List<T> data = new ArrayList<>();

    public void setItem(T t){
        data.clear();
        data.add(t);
    }

    @NonNull
    @Override
    public StateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StateViewHolder(LayoutInflater.from(parent.getContext()).inflate(itemView, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StateViewHolder holder, int position) {
        onBind(holder,data.get(position));
    }

    public abstract void onBind(StateViewHolder holder,T t);

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class StateViewHolder extends RecyclerView.ViewHolder {

        private final Map<Integer, View> array = new HashMap<>();

        public StateViewHolder(@NonNull View itemView) {
            super(itemView);
            array.putAll(getAllHasIdView(itemView));
        }

        private Map<Integer,View> getAllHasIdView(View view)
        {
            Map<Integer,View> idViewMap = new HashMap<>();
            if (view.getId() != View.NO_ID){
                idViewMap.put(view.getId(),view);
            }
            if (view instanceof ViewGroup){
                ViewGroup viewGroup = (ViewGroup) view;
                for (int index=0;index<viewGroup.getChildCount();index++){
                    idViewMap.putAll(getAllHasIdView(viewGroup.getChildAt(index)));
                }
            }
            return idViewMap;
        }

        public <T extends View> T getView(int viewId){
            return (T) array.get(viewId);
        }

        public ImageView getImageView(int viewId){
            return getView(viewId);
        }

        public TextView getTextView(int viewId){
            return getView(viewId);
        }

        public ViewGroup getViewGroup(int viewId){
            return getView(viewId);
        }

        public CompoundButton getCompoundButton(int viewId){
            return getView(viewId);
        }

        public Spinner getSpinner(int viewId){
            return getView(viewId);
        }

        public RecyclerView getRecyclerView(int viewId) {
            return getView(viewId);
        }
    }

}
