package com.example.esemenyszervezes.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.adapters.TeamAdapter;
import com.example.esemenyszervezes.api.ApiService;
import com.example.esemenyszervezes.api.RetrofitBuilder;
import com.example.esemenyszervezes.pojo.Team;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String token;
    private ApiService service;
    private RecyclerView recyclerView;
    private TeamAdapter adapter;
    private List<Team> teamList;

    public TeamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_team, container, false);
        teamList = new ArrayList<>();
        recyclerView = (RecyclerView)view.findViewById(R.id.team_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        service = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);
        adapter = new TeamAdapter(getContext(), teamList);
        recyclerView.setAdapter(adapter);
       // loadData();
        return view;
    }
/*
    public void loadData(){
        Intent i = Objects.requireNonNull(getActivity()).getIntent() ;
        token = i.getStringExtra("token");
        Call<List<Team>> call = service.listTeams("Bearer " + token);
        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(@NonNull Call<List<Team>> call, @NonNull Response<List<Team>> response) {
                teamList = response.body();
                adapter.loadTeams(teamList);
                assert response.body() != null;
                Log.d("Response", "onResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(@NonNull Call<List<Team>> call, @NonNull Throwable t) {
                Log.d("Response", "onFailure: " + t.toString());
            }
        });
    }*/


}
