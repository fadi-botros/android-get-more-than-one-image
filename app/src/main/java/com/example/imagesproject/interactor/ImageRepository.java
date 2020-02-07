package com.example.imagesproject.interactor;

import androidx.arch.core.util.Function;

public interface ImageRepository {
    public void imageURL(String fromURL, Function<String, Void> callback);
}
