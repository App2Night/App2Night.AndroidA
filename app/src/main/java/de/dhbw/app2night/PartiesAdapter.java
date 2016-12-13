package de.dhbw.app2night;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.dhbw.model.Party;
import de.dhbw.utils.DateUtil;

/**
 * Created by Flo on 03.11.2016.
 */

public class PartiesAdapter extends RecyclerView.Adapter<PartiesAdapter.MyViewHolder> {
    private List<Party> partyList;
    private OnItemClickListener itemListener;

    public PartiesAdapter(List<Party> partyList, OnItemClickListener itemListener) {
        this.partyList = partyList;
        this.itemListener = itemListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Party party);
    }

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

        public void bind(final Party party, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(party);
                }
            });
        }
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
        holder.date.setText(DateUtil.getInstance().getDate(party.getPartyDate()) + " " + DateUtil.getInstance().getTime(party.getPartyDate()));
        holder.location.setText(party.getLocation().getCityName() + ", " + party.getLocation().getStreetName() + ' ' + party.getLocation().getHouseNumber());
        holder.organizer.setText(party.getHost().getUserName());
        if (party.getPrice() == 0) holder.price.setText("Frei");
        else holder.price.setText( Integer.toString(party.getPrice()));
        holder.bind(partyList.get(position), itemListener);

    }

    @Override
    public int getItemCount() {
        return partyList.size();
    }
}
