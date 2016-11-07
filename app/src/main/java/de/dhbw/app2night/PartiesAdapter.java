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
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
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
        holder.genre.setText(Integer.toString(party.getPartyType()));
        holder.year.setText(party.getPartyDate());
    }

    @Override
    public int getItemCount() {
        return partyList.size();
    }
}
