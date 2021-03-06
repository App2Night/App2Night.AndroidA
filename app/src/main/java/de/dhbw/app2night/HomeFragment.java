package de.dhbw.app2night;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Array;
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

    public interface OnFloatButtonClickListener {
        void onFloatButtonClick(ArrayList<Party> parties);
    }

    public static final String ARG_STARTUP = "arg_startup";
    OnItemClickListener mItemClickCallback;
    OnFloatButtonClickListener mFloatButtonClickCallback;
    private ArrayList<Party> partyList = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private PartiesAdapter pAdapter;
    FloatingActionButton fab_map;
    private PartiesAdapter.OnItemClickListener itemClickListener;
    private LinearLayout error_header;
    Animation error_fade_in, error_fade_out;
    View rootView;
    Boolean startTask;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Start Parameter fuer Taskstart wird ausgelesen,wenn nichts gelesen werden kann, wird Startparameter false gesetzt
        if(getArguments() != null ) {
            startTask = getArguments().getBoolean(ARG_STARTUP);
        }else{
            startTask = false;
        }

        itemClickListener = new PartiesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Party party) {
                mItemClickCallback.onClick(party);
            }
        };

        try {
            mItemClickCallback = (HomeFragment.OnItemClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement onItemClickListener");
        }

        try {
            mFloatButtonClickCallback = (HomeFragment.OnFloatButtonClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement onFloatButtonClickListener");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        initializeViews();


        // Inflate the layout for this fragment
        return rootView;
    }


    private void initializeViews() {
        error_header = (LinearLayout) rootView.findViewById(R.id.nogps_header);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        pAdapter = new PartiesAdapter(partyList, itemClickListener);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pAdapter);


        fab_map = (FloatingActionButton) rootView.findViewById(R.id.fab_map);
        fab_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (partyList.size() > 0)
                    mFloatButtonClickCallback.onFloatButtonClick(partyList);
                else
                    Toast.makeText(getActivity(), "Keine Partys zum Anzeigen in der Liste", Toast.LENGTH_SHORT).show();

            }
        });


        error_fade_in = AnimationUtils.loadAnimation(getActivity(), R.anim.image_fade_in);
        error_fade_out = AnimationUtils.loadAnimation(getActivity(), R.anim.image_fade_out);

        adaptParties(GetPartyListSave.getInstance().getList());
        //Wenn der Startparameter startTask gesetzt wurde, da das Fragment von der MainActivity gestartet wurde, fuehre Task aus
        if(startTask) {
            try {
                double[] gpsResult = Gps.getInstance().getGPSCoordinates();
                new GetPartyListTask(this, gpsResult[0], gpsResult[1]);
                if (error_header.getVisibility() == View.VISIBLE) {
                    error_header.startAnimation(error_fade_out);
                    error_header.setVisibility(View.INVISIBLE);
                    error_header.setClickable(false);
                }
            } catch (GPSUnavailableException e) {
                showGpsUnavailableDialog();
                if (error_header.getVisibility() == View.INVISIBLE) {
                    error_header.setVisibility(View.VISIBLE);
                    error_header.startAnimation(error_fade_in);
                    error_header.setClickable(true);
                }
                e.printStackTrace();
            }
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (error_header.getVisibility() == View.VISIBLE) {
                    error_header.startAnimation(error_fade_out);
                    error_header.setVisibility(View.INVISIBLE);
                    error_header.setClickable(false);
                }
                refreshItems();
            }
        });
        
        //Floating Action Button beim scrollen aus- bzw. einblenden
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0 ||dy<0 && fab_map.isShown())
                    fab_map.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    fab_map.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }


        });
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

    public void showGpsUnavailableDialog()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("GPS nicht verfügbar");
        alert.setMessage("App2Night benötigt deinen derzeitgen Aufenthaltsort, um dir Partys in deiner Nähe anzuzeigen.");
        alert.setPositiveButton("Später", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("Zu den Einstellungen", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Intent I = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(I);
            }
        });
        AlertDialog al_gps = alert.create();
        al_gps.show();
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
            if (error_header.getVisibility() == View.VISIBLE) {
                error_header.startAnimation(error_fade_out);
                error_header.setVisibility(View.INVISIBLE);
                error_header.setClickable(false);
            }
        } catch (GPSUnavailableException e) {
            showGpsUnavailableDialog();
            if (error_header.getVisibility() == View.INVISIBLE){
                error_header.setVisibility(View.VISIBLE);
                error_header.startAnimation(error_fade_in);
                error_header.setClickable(true);
            }
            mSwipeRefreshLayout.setRefreshing(false);
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
    public void onFailGetPartyList() {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), "Parties laden ist fehlgeschlagen. Alte Liste wurde geladen", Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
