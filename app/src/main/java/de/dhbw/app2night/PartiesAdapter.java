package de.dhbw.app2night;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.dhbw.model.Party;

/**
 * Created by Flo on 03.11.2016.
 */

public class PartiesAdapter extends RecyclerView.Adapter<PartiesAdapter.MyViewHolder> {
    private List<Party> partyList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date, location, organizer, price;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            location = (TextView) view.findViewById(R.id.location);
            organizer = (TextView) view.findViewById(R.id.organizer);
            price = (TextView) view.findViewById(R.id.price);
        }
    }


    public PartiesAdapter(List<Party> partyList) {
        this.partyList = partyList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.party_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Party party = partyList.get(position);

        holder.title.setText(party.getPartyName());
        holder.date.setText(party.getPartyDate());
        holder.location.setText(party.getLocation().getCityName() + ", " + party.getLocation().getStreetName() + ' ' + party.getLocation().getHouseNumber());
        holder.organizer.setText(party.getHost().getUserName());
        holder.price.setText( Integer.toString(party.getPrice()));

    }

    @Override
    public int getItemCount() {
        return partyList.size();
    }
}
