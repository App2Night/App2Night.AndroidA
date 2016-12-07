package de.dhbw.app2night;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.backendTasks.party.GetPartyList;
import de.dhbw.backendTasks.party.GetPartyListTask;
import de.dhbw.exceptions.GPSUnavailableException;
import de.dhbw.model.Party;
import de.dhbw.utils.GetPartyListSave;
import de.dhbw.utils.Gps;


/**
 * Created by Flo on 27.10.2016.
 */

public class HomeFragment extends Fragment implements GetPartyList {

    public interface OnItemClickListener {
        void onClick(Party party);
    }

    OnItemClickListener mCallback;
    private List<Party> partyList = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private PartiesAdapter pAdapter;
    private PartiesAdapter.OnItemClickListener itemClickListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemClickListener = new PartiesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Party party) {
                mCallback.onClick(party);
            }
        };

        try {
            mCallback = (HomeFragment.OnItemClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement onItemClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        pAdapter = new PartiesAdapter(partyList, itemClickListener);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pAdapter);

        adaptParties(GetPartyListSave.getInstance().getList());
        try {
            double[] gpsResult = Gps.getInstance().getGPSCoordinates();
            new GetPartyListTask(this,gpsResult[0],gpsResult[1]);
        } catch (GPSUnavailableException e) {
            //TODO: Behandlung im Fall, dass GPS nicht verfügbar ist
            e.printStackTrace();
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onPause(){
        super.onPause();
        if (mSwipeRefreshLayout!=null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.destroyDrawingCache();
            mSwipeRefreshLayout.clearAnimation();
        }
    }

    private synchronized void adaptParties(Party[] parties){
        partyList.clear();
        for (Party party:parties) {
            partyList.add(party);
        }
        pAdapter.notifyDataSetChanged();


    }

    void refreshItems()
    {
        try {
            double[] gpsResult = Gps.getInstance().getGPSCoordinates();
            new GetPartyListTask(this,gpsResult[0],gpsResult[1]);
        } catch (GPSUnavailableException e) {
            //TODO: Behandlung im Fall, dass GPS nicht verfügbar ist
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessGetPartyList(Party[] parties) {
        adaptParties(parties);
        if (getActivity() != null) {
            Toast.makeText(getActivity(), "Parties wurden erfolgreich geladen", Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onFailGetPartyList(Party[] parties) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), "Parties laden ist fehlgeschlagen. Alte Liste wurde geladen.", Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
