package com.jonahmiddleton.players;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PlayerScoreAdapter extends ArrayAdapter<Player> {
    private final int viewResource;

    static class PlayerScoreViewHolder {
        TextView pScoreName, pScoreWins, pScoreLosses, pScoreTies;
    }

    public PlayerScoreAdapter(@NonNull Context context, int resource,  @NonNull List<Player> objects) {
        super(context, resource, objects);
        viewResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        PlayerScoreViewHolder viewHolder;

        if(row == null){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(viewResource, parent, false);

            viewHolder = new PlayerScoreViewHolder();
            viewHolder.pScoreName = (TextView)row.findViewById(R.id.pScoreName);
            viewHolder.pScoreWins = (TextView)row.findViewById(R.id.pScoreWins);
            viewHolder.pScoreLosses = (TextView)row.findViewById(R.id.pScoreLosses);
            viewHolder.pScoreTies = (TextView)row.findViewById(R.id.pScoreTies);
            row.setTag(viewHolder);
        }else{
            viewHolder = (PlayerScoreViewHolder)row.getTag();
        }

        Player player = getItem(position);
        viewHolder.pScoreName.setText(player.getName());
        viewHolder.pScoreWins.setText(Integer.toString(player.getWins()));
        viewHolder.pScoreLosses.setText(Integer.toString(player.getLosses()));
        viewHolder.pScoreTies.setText(Integer.toString(player.getTies()));
        return row;
    }
}
