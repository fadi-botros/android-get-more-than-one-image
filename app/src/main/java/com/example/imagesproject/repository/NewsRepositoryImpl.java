package com.example.imagesproject.repository;

import androidx.arch.core.util.Function;

import com.example.imagesproject.interactor.NewsRepository;

import java.util.Arrays;

public class NewsRepositoryImpl implements NewsRepository {
    @Override
    public void getNews(Function<Iterable<String>, Void> callback) {
        // Static data
        callback.apply(Arrays.asList(
                /* Put name of sites to get their images here */
                "https://www.forbes.com/sites/johnkoetsier/2019/10/23/ibm-googles-quantum-supremacy-is-150-million-percent-wrong/"
    }

}
