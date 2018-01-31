package fyi.jackson.drew.homeassistantwidgets.recycler.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fyi.jackson.drew.homeassistantwidgets.R;

public class StateViewHolder extends RecyclerView.ViewHolder {

    public TextView entityIdTextView;

    public StateViewHolder(View v) {
        super(v);
        entityIdTextView = v.findViewById(R.id.tv_entity_id);
    }
}
