package com.usepjh92.user.myfamilyband.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.RelativeLayout;

import com.usepjh92.user.myfamilyband.R;

public class Fragment3 extends android.support.v4.app.Fragment {

    CalendarView calendarView;
    RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        calendarView = (CalendarView) view.findViewById(R.id.calendar);
        relativeLayout = (RelativeLayout)view.findViewById(R.id.relative);

        return view;
    }

}
