package com.example.imagesproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.imagesproject.interactor.ImageRepository;
import com.example.imagesproject.interactor.LoadNewsInteractor;
import com.example.imagesproject.repository.ImageRepositoryImpl;
import com.example.imagesproject.repository.NewsRepositoryImpl;
import com.squareup.picasso.Picasso;

import java.util.List;

class Adapter extends RecyclerView.Adapter<Adapter.SingleViewHolder> {
    Context context;
    LifecycleOwner owner;
    List<LiveData<String>> data;

    Adapter(Context context, LifecycleOwner owner, List<LiveData<String>> data) {
        this.context = context;
        this.owner = owner;
        this.data = data;
    }

    class SingleViewHolder extends RecyclerView.ViewHolder implements Observer<String> {
        LiveData<String> liveData;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bindToLiveData(LiveData<String> newLiveData) {
            if (liveData != null) { liveData.removeObserver(this); }
            liveData = newLiveData;
            liveData.observe(owner, this);
        }

        @Override
        public void onChanged(String s) {
            if (s == null) { return; }
            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            Picasso.with(context).load(s).into(imageView);
        }
    }

    @NonNull
    @Override
    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new SingleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_carrier, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SingleViewHolder holder, int position)
    {
        ((SingleViewHolder) holder).bindToLiveData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView view = (RecyclerView) findViewById(R.id.recycler_view);
        view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // TODO: Use dependency injection
        new LoadNewsInteractor(new NewsRepositoryImpl(), new ImageRepositoryImpl()).loadNews(new Function<List<LiveData<String>>, Void>() {
            @Override
            public Void apply(List<LiveData<String>> input) {
                final List<LiveData<String>> data = input;
                view.setAdapter(new Adapter(MainActivity.this, MainActivity.this, data));
                return null;
            }
        });
    }
}
