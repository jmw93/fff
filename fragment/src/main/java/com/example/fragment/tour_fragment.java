package com.example.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class tour_fragment extends Fragment {
    List tourlist = new ArrayList<Tour>();
    XMLPullParserHandler xmlpullparsinghandler = new XMLPullParserHandler();
    Adapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.tourfragment,container,false);
             adapter = new Adapter(new Adapter.onClickListener() {
            @Override
            public void onclick(Tour model) {
                Toast.makeText(getActivity(),model.getName(),Toast.LENGTH_SHORT).show();
            }
        });
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        tourlist = xmlpullparsinghandler.parsing();
                     getActivity().runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             adapter.setItems(tourlist);

                         }
                     });

                    }
                }).start();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        return view;
    }
    private static class Adapter extends RecyclerView.Adapter<Adapter.TourViewHolder> {
        interface onClickListener {
            void onclick(Tour model);
        }

        private onClickListener mListener;

        private List<Tour> mItems = new ArrayList<>();

        public Adapter() {}

        public Adapter(onClickListener listener) {
            mListener = listener;
        }

        public void setItems(List<Tour> items) {
            this.mItems = items;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tour_item, parent, false);
            final TourViewHolder viewHolder = new TourViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        final Tour item = mItems.get(viewHolder.getAdapterPosition());
                        mListener.onclick(item);
                    }
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TourViewHolder holder, int position) {
            Tour item = mItems.get(position);
            holder.name.setText(item.getName());
            holder.address.setText(item.getAddress());
//            holder.image.setImageBitmap(item.getBitmap());
    }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public static class TourViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            TextView address;
//            ImageView image;

            public TourViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                address = itemView.findViewById(R.id.address);
//                image = itemView.findViewById(R.id.image);
            }
        }
    }

}
