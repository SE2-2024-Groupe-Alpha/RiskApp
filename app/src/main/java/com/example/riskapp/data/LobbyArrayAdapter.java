package com.example.riskapp.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.riskapp.R;
import com.example.riskapp.model.game.GameSession;

import java.util.List;

public class LobbyArrayAdapter extends ArrayAdapter<GameSession> {
    private final Context context;
    private final List<GameSession> lobbys;

    public LobbyArrayAdapter(Context context, List<GameSession> values) {
        super(context, R.layout.lobby_list_entry, values);
        this.context = context;
        this.lobbys = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.lobby_list_entry, parent, false);
        TextView lobbyNameTextView = rowView.findViewById(R.id.lobbyNameTextView);
        TextView playersTextView = rowView.findViewById(R.id.playersTextView);

        GameSession session = lobbys.get(position);
        lobbyNameTextView.setText(session.getName());
        playersTextView.setText(session.getUsers() + "/6 Players"); // Assuming you have a way to get the max players

        return rowView;
    }
}
