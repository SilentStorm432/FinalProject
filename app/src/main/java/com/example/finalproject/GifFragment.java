package com.example.finalproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;

public class GifFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_gif, container, false);

        // Reference the ImageView
        ImageView imageViewGif = view.findViewById(R.id.imageViewGif);

        // Load the GIF using Glide
        Glide.with(this)
                .asGif()
                .load(R.drawable.bbcnews)
                .into(imageViewGif);

        // OnClickListener to open HeadlineActivity
        imageViewGif.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HeadlineActivity.class);
            startActivity(intent);
        });


        return view;
    }
}
