package com.usepjh92.user.myfamilyband.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import com.usepjh92.user.myfamilyband.R;
import com.usepjh92.user.myfamilyband.activity.TodayDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Fragment3 extends android.support.v4.app.Fragment {


    MaterialCalendarView materialCalendarView;
    TextView textView;

    String inputText = "";


    boolean isSelected = false;
    String ymd1 = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        materialCalendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        textView = (TextView) view.findViewById(R.id.text_date);
        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new OneDayDecorator());

        materialCalendarView.setTileHeight(100);


        materialCalendarView.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {

                DateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.KOREA);

                return dateFormat.format(day.getDate());
            }
        });


        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {

            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                int year = date.getYear();
                int month = date.getMonth() + 1;
                int day = date.getDay();
                String ymd = "" + year + month + day;


                if (isSelected && ymd1.equals(ymd)) {

                    Toast.makeText(getActivity(), "두번 클릭", Toast.LENGTH_SHORT).show();
                    isSelected = false;
                    Intent intent = new Intent(getActivity(), TodayDate.class);
                    intent.putExtra("ymd", ymd);
                    startActivityForResult(intent, 15);

                } else {

                    Toast.makeText(getActivity(), "한번 클릭", Toast.LENGTH_SHORT).show();
                    ymd1 = ymd;

                    //서버로 부터 일정 불러오기..

                    loadYmdMsg();


                    isSelected = true;
                }

            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("aaaa", "들어옴");

        Log.e("zxc", requestCode + "");

        if (requestCode == 15) {

            Log.e("bbbb", "들어옴");

            if (resultCode == getActivity().RESULT_OK) {

                Log.e("cccc", "들어옴");

                inputText = data.getStringExtra("input");

                Log.e("value", inputText);

            }
        }


    }

    public class SundayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SundayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SUNDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }

    public class SaturdayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SaturdayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SATURDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.BLUE));
        }
    }

    public class OneDayDecorator implements DayViewDecorator {

        private CalendarDay date;

        public OneDayDecorator() {
            date = CalendarDay.today();
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return date != null && day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new StyleSpan(Typeface.BOLD));
            view.addSpan(new RelativeSizeSpan(1.4f));
            view.addSpan(new ForegroundColorSpan(Color.GREEN));
        }

        public void setDate(Date date) {
            this.date = CalendarDay.from(date);
        }
    }

    public void loadYmdMsg() {

        String loadymdMsg = "http://neworld.dothome.co.kr/android/loadYmdMsg.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        SimpleMultiPartRequest smpr = new SimpleMultiPartRequest(Request.Method.POST, loadymdMsg, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("fail")) {
                    Toast.makeText(getActivity(), "일정이 없습니다.", Toast.LENGTH_SHORT).show();
                    textView.setText("");

                } else {

                    //2017-08-30 일정이 있습니다.
                    Log.e("response", response);
                    String[] rows = response.split("&");
                    String msg = rows[1];
                    textView.setText(msg);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });

        smpr.addStringParam("ymd", ymd1);

        requestQueue.add(smpr);


    }

}
