package fyi.jackson.drew.homeassistantwidgets.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fyi.jackson.drew.homeassistantwidgets.R;
import fyi.jackson.drew.homeassistantwidgets.network.State;
import fyi.jackson.drew.homeassistantwidgets.recycler.holders.StateViewHolder;


public class AddComponentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<State> stateList = null;

    public AddComponentAdapter() {}

    public AddComponentAdapter(List<State> stateList) {
        setStateList(stateList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.view_holder_state, parent, false);
        RecyclerView.ViewHolder viewHolder = new StateViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String name = stateList.get(position).getAttributes().getFriendlyName();
        if (name == null) stateList.get(position).getEntityId();
        ((StateViewHolder) holder).entityIdTextView.setText(name);
    }

    @Override
    public int getItemCount() {
        if (stateList == null) return 0;
        return stateList.size();
    }

    public void setStateList(List<State> stateList) {
        this.stateList = stateList;
    }
}
