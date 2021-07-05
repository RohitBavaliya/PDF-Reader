package com.example.pdfapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Myadapter extends FirebaseRecyclerAdapter<Model,ViewHolder>
{

    public Myadapter(@NonNull  FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Model model) {
        holder.headerText.setText(model.getFilename());
        holder.readtext.setText(String.valueOf(model.getView()));
        holder.liketext.setText(String.valueOf(model.getLike()));
        holder.disliketext.setText(String.valueOf(model.getDislike()));

        holder.fileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.fileImage.getContext(),ViewPdf.class);
                intent.putExtra("filename",model.getFilename());
                intent.putExtra("fileurl",model.getFileurl());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.fileImage.getContext().startActivity(intent);
            }
        });

        holder.readImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.likeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.dislikeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design,parent,false);
        return new ViewHolder(view);
    }
}
