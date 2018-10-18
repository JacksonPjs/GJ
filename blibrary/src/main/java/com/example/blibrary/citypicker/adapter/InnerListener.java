package com.example.blibrary.citypicker.adapter;


import com.example.blibrary.citypicker.model.City;

public interface InnerListener {
    void dismiss(int position, City data);
    void locate();
}
