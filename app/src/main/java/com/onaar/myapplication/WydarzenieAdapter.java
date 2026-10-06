package com.onaar.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class WydarzenieAdapter extends RecyclerView.Adapter<WydarzenieAdapter.ViewHolder> {

    private final List<Wydarzenie> wydarzenia;

    public WydarzenieAdapter(List<Wydarzenie> wydarzenia) {
        this.wydarzenia = wydarzenia;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wydarzenie, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Wydarzenie wydarzenie = wydarzenia.get(position);

        holder.tvTytul.setText(wydarzenie.getTytul());
        holder.tvTresc.setText(wydarzenie.getTresc());
        holder.tvData.setText(formatujDate(wydarzenie.getData()));
    }

    private String formatujDate(String data) {

        try {
            SimpleDateFormat wejscie =
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault());

            SimpleDateFormat wyjscie =
                    new SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault());

            return wyjscie.format(wejscie.parse(data));

        } catch (ParseException e) {
            return data;
        }
    }

    @Override
    public int getItemCount() {
        return wydarzenia.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTytul;
        TextView tvTresc;
        TextView tvData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTytul = itemView.findViewById(R.id.tvTytul);
            tvTresc = itemView.findViewById(R.id.tvTresc);
            tvData = itemView.findViewById(R.id.tvData);
        }
    }
}
