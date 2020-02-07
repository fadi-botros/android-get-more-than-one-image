package com.example.imagesproject.interactor;

import androidx.arch.core.util.Function;

public interface NewsRepository {
    public void getNews(Function<Iterable<String>, Void> callback);
}
