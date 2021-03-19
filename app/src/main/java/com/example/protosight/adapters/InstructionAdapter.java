package com.example.protosight.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.protosight.R;
import com.example.protosight.models.InstructionItem;

import java.util.List;

public class InstructionAdapter extends PagerAdapter {
    Context context;
    List<InstructionItem> mListScreen;

    public InstructionAdapter(Context context, List<InstructionItem> mListScreen) {
        this.context = context;
        this.mListScreen = mListScreen;
    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = layoutInflater.inflate(R.layout.instruction_screen, null);
        ImageView imageView = layoutScreen.findViewById(R.id.intro_img);
        TextView textView = layoutScreen.findViewById(R.id.intro_title);
        textView.setText(mListScreen.get(position).getDescription());
        imageView.setImageResource(mListScreen.get(position).getScreenImg());

        container.addView(layoutScreen);
        return layoutScreen;

    }
}
