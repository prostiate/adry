package com.vincent.tiketadry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchBarActivity extends AppCompatActivity {
    EditText search_edit_text;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> nomorTiketList;
    ArrayList<String> namaTiketList;
    ArrayList<String> alamatTiketList;
    ArrayList<String> nohpTiketList;
    ArrayList<String> verTiketList;
    ArrayList<String> profilePicList;
    SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);

        search_edit_text = (EditText) findViewById(R.id.search_edit_text);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        /*
        * Create a array list for each node you want to use
        * */
        nomorTiketList = new ArrayList<>();
        namaTiketList = new ArrayList<>();
        alamatTiketList = new ArrayList<>();
        nohpTiketList = new ArrayList<>();
        verTiketList = new ArrayList<>();
        profilePicList = new ArrayList<>();

        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    setAdapter(s.toString());
                } else {
                    /*
                    * Clear the list when editText is empty
                    * */
                    nomorTiketList.clear();
                    namaTiketList.clear();
                    alamatTiketList.clear();
                    nohpTiketList.clear();
                    verTiketList.clear();
                    profilePicList.clear();
                    recyclerView.removeAllViews();
                }
            }
        });
    }

    private void setAdapter(final String searchedString) {
        databaseReference.child("tiket").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*
                * Clear the list for every new search
                * */
                nomorTiketList.clear();
                namaTiketList.clear();
                alamatTiketList.clear();
                nohpTiketList.clear();
                verTiketList.clear();
                profilePicList.clear();
                recyclerView.removeAllViews();

                int counter = 0;

                /*
                * Search all users for matching searched string
                * */
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String nomorTiket = snapshot.child("nomorTiket").getValue(String.class);
                    String namaTiket = snapshot.child("namaTiket").getValue(String.class);
                    String alamatTiket = snapshot.child("alamatTiket").getValue(String.class);
                    String nohpTiket = snapshot.child("nohpTiket").getValue(String.class);
                    String verTiket = snapshot.child("verTiket").getValue(String.class);
                    String profile_pic = snapshot.child("profile_pic").getValue(String.class);

                    if (nomorTiket.toLowerCase().contains(searchedString.toLowerCase())) {
                        nomorTiketList.add(nomorTiket);
                        namaTiketList.add(namaTiket);
                        nohpTiketList.add(alamatTiket);
                        alamatTiketList.add(nohpTiket);
                        verTiketList.add(verTiket);
                        profilePicList.add(profile_pic);
                        counter++;
                    }

                    /*
                    * Get maximum of 15 searched results only
                    * */
                    if (counter == 15)
                        break;
                }

                searchAdapter = new SearchAdapter(SearchBarActivity.this, nomorTiketList, namaTiketList, nohpTiketList, alamatTiketList, verTiketList, profilePicList);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
