package com.example.blibrary.citypicker.adapter;


import com.example.blibrary.citypicker.model.City;

public interface OnPickListener {
    void onPick(int position, City data);
    void onLocate();
}
