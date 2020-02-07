package com.example.imagesproject.interactor;

import android.media.Image;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class LoadNewsInteractor {
    private NewsRepository newsRepository;
    private ImageRepository imageRepository;

    public LoadNewsInteractor(NewsRepository newsRepository, ImageRepository imageRepository) {
        this.newsRepository = newsRepository;
        this.imageRepository = imageRepository;
    }

    /**
     * Loads news using the repositories given.
     *
     * @param callback Function that receives a {@link List} of {@link LiveData}, where each LiveData
     *                 would receive a URL for the image to be drawn.
     */
    public void loadNews(final Function<List<LiveData<String>>, Void> callback) {
        newsRepository.getNews(new Function<Iterable<String>, Void>() {
            @Override
            public Void apply(Iterable<String> strings) {
                List<LiveData<String>> ret = new ArrayList<>();
                for(String string: strings) {
                    // Create a LiveData that would receive the image URL when available
                    final MutableLiveData<String> imgURLReciever = new MutableLiveData<>();
                    imageRepository.imageURL(string, new Function<String, Void>() {
                        @Override
                        public Void apply(String input) {
                            imgURLReciever.postValue(input);
                            return null;
                        }
                    });
                    ret.add(imgURLReciever);
                }
                callback.apply(ret);
                return null;
            }
        });
    }
}
