package com.example.imagesproject.repository;

import androidx.arch.core.util.Function;

import com.example.imagesproject.interactor.NewsRepository;

import java.util.Arrays;

public class NewsRepositoryImpl implements NewsRepository {
    @Override
    public void getNews(Function<Iterable<String>, Void> callback) {
        // Static data
        callback.apply(Arrays.asList(
                "https://www.forbes.com/sites/johnkoetsier/2019/10/23/ibm-googles-quantum-supremacy-is-150-million-percent-wrong/",
                "https://www.forbes.com/sites/forbestechcouncil/2019/06/12/why-sres-should-worry-more-about-third-party-javascript/",
                "https://www.forbes.com/sites/tjmccue/2020/12/31/lock-down-your-phone-the-new-york-times-privacy-project-revelations/",
                "https://www.techrepublic.com/article/why-many-security-pros-lack-confidence-in-their-implementation-of-zero-trust/",
                "https://www.weforum.org/agenda/2019/09/our-cities-are-increasingly-vulnerable-to-cyberattacks-heres-how-they-can-fight-back/"));
    }

}
