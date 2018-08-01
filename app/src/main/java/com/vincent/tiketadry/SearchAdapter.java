package com.vincent.tiketadry;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    Context context;
    ArrayList<String> nomorTiketList;
    ArrayList<String> namaTiketList;
    ArrayList<String> alamatTiketList;
    ArrayList<String> nohpTiketList;
    ArrayList<String> verTiketList;
    ArrayList<String> profilePicList;

    class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView nomor_tiket, nama_tiket, alamat_tiket, nohp_tiket, ver_tiket;

        public SearchViewHolder(View itemView) {
            super(itemView);
            profileImage = (ImageView) itemView.findViewById(R.id.profileImage);
            nomor_tiket = (TextView) itemView.findViewById(R.id.nomor_tiket);
            nama_tiket = (TextView) itemView.findViewById(R.id.nama_tiket);
            alamat_tiket = (TextView) itemView.findViewById(R.id.alamat_tiket);
            nohp_tiket = (TextView) itemView.findViewById(R.id.nohp_tiket);
            ver_tiket = (TextView) itemView.findViewById(R.id.ver_tiket);
        }
    }

    public SearchAdapter(Context context, ArrayList<String> nomorTiketList, ArrayList<String> namaTiketList, ArrayList<String> alamatTiketList, ArrayList<String> nohpTiketList, ArrayList<String> verTiketList, ArrayList<String> profilePicList) {
        this.context = context;
        this.nomorTiketList = nomorTiketList;
        this.namaTiketList = namaTiketList;
        this.alamatTiketList = alamatTiketList;
        this.nohpTiketList = nohpTiketList;
        this.verTiketList = verTiketList;
        this.profilePicList = profilePicList;
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_list_items, parent, false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.nomor_tiket.setText(nomorTiketList.get(position));
        holder.nama_tiket.setText(namaTiketList.get(position));
        holder.alamat_tiket.setText(alamatTiketList.get(position));
        holder.nohp_tiket.setText(nohpTiketList.get(position));
        holder.ver_tiket.setText(verTiketList.get(position));
        Glide.with(context).load(profilePicList.get(position)).asBitmap().placeholder(R.mipmap.ic_launcher_round).into(holder.profileImage);

        holder.nomor_tiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Full Name Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return nomorTiketList.size();
    }
}
