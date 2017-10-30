package com.android.app.buystoreapp.listener;

public interface OnShoppingCartChangeListener {
    void onDataChange(String selectCount, String selectMoney,String selectedFright);
    void onSelectItem(boolean isSelectedAll); 
}
