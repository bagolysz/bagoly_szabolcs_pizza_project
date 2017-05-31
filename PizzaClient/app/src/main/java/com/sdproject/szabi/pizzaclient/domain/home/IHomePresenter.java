package com.sdproject.szabi.pizzaclient.domain.home;

/**
 * Created by Szabi on 5/21/2017.
 */

public interface IHomePresenter {
    void retrieveCategories();

    void retrieveOffers();

    void retrieveOffers(int category);

    void clearUser();

}
