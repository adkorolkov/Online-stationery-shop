package com.example.onlinestationeryshop.View.Catalog;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinestationeryshop.Repository;
import com.example.onlinestationeryshop.models.Good;

import java.util.ArrayList;
import java.util.List;

public class CatalogViewModel extends AndroidViewModel {
    public CatalogViewModel(@NonNull Application application) {
        super(application);
    }




    public void setSearch(String search){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        repository.setSearch(search);
    }

    public Good getForId(int id){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        return repository.getForId(id);
    }

    public void searchGoods(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        repository.searchgoods();
    }

    public String getGoodInfo(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        return repository.INFOGOOD;
    }

    public ArrayList<Good> search(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        return repository.search();
    }

    public String getChannel(){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        return repository.CHANNEL;
    }

    public ArrayList<String> getImageList(int id){
        Good good = getForId(id);
        ArrayList<String> images = new ArrayList<>();
        List<String> r = good.getImages();
        for (int x=0;x<r.size();x++){
            images.add(r.get(x));
        }
        return images;
    }

    public void putInSearch(String name){
        Repository repository = Repository.getInstance(getApplication().getApplicationContext());
        repository.putInSearch(name);
    }


}
