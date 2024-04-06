package se2.alpha.riskapp.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import se2.alpha.riskapp.R;
import se2.alpha.riskapp.model.game.UserState;

import java.util.List;

public class PlayerArrayAdapter extends ArrayAdapter<UserState> {
    private final Context context;
    private final List<UserState> players;

    public PlayerArrayAdapter(Context context, List<UserState> players) {
        super(context, R.layout.lobby_item, players);
        this.context = context;
        this.players = players;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.lobby_item, parent, false);

        TextView playerName = rowView.findViewById(R.id.playerName);
        TextView playerIsReady = rowView.findViewById(R.id.playerIsReady);

        playerName.setText(players.get(position).getUserName());
        playerIsReady.setText(players.get(position).getIsReady() ? "Ready" : "Not Ready");

        return rowView;
    }
}
