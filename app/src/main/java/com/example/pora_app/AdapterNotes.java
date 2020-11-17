package com.example.pora_app;

import android.content.Context;
import android.graphics.Color;
import android.os.NetworkOnMainThreadException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib.Note;


public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.ViewHolder> {


    private ApplicationNotes app;
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);

        void onItemLongClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AdapterNotes(ApplicationNotes app) {
        this.app = app;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View view = inflater.inflate(R.layout.notes_rowlayout, parent, false);
        // Return a new holder instance
        AdapterNotes.ViewHolder viewHolder = new AdapterNotes.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Note current = app.getList().get(position);
        holder.txtHeader.setText(current.getName());
        holder.txtFooter.setText(current.getContent() + " ");
        String dateT = String.format("%1$tb %1$te, %1$tY", current.getDueDate());
        holder.txtDate.setText(dateT);
        if (position % 2 == 1) {
            holder.background.setBackgroundColor(Color.LTGRAY);
        } else {
            holder.background.setBackgroundColor(Color.WHITE);
        }
        holder.txtHeader.setTextColor(Color.BLACK);
    }

    @Override
    public int getItemCount() {
        return app.getList().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHeader;
        public TextView txtFooter;
        public TextView txtDate;
        public View background;

        public ViewHolder(@NonNull View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            txtDate = (TextView) v.findViewById(R.id.thirdLine);
            background = v.findViewById(R.id.mylayoutrow);
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemLongClick(itemView, position);
                        }
                    }
                    return true;
                }
            });
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }
    }
}








