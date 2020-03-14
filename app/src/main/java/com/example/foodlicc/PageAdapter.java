package com.example.foodlicc;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends FragmentPagerAdapter {
    private final List<Fragment>fragmentList= new ArrayList<> ();
    private final List<String>fragmentListName= new ArrayList<> ();


    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentListName.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentListName.get(position);
    }
     public void addFrag(Fragment fragment,String title){
         final DatabaseReference table_restaurant;
         table_restaurant= FirebaseDatabase.getInstance().getReference().child("Restaurant/Menu");
        fragmentList.add(fragment);
        fragmentListName.add(title);



     }
}
