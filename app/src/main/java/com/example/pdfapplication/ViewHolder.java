package com.example.pdfapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView fileImage;
    TextView headerText;
    ImageView readImage, likeImage, dislikeImage;
    TextView readtext, liketext, disliketext;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        fileImage = itemView.findViewById(R.id.pdffile);
        headerText = itemView.findViewById(R.id.rowHeader);

        readImage = itemView.findViewById(R.id.readImage);
        likeImage = itemView.findViewById(R.id.likeImage);
        dislikeImage = itemView.findViewById(R.id.dislikeImage);

        readtext = itemView.findViewById(R.id.readtext);
        liketext = itemView.findViewById(R.id.liketext);
        disliketext = itemView.findViewById(R.id.disliketext);



    }
}
