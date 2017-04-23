package com.wuliao.mvp.welcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wuliao.R;

/**
 * A placeholder fragment containing a simple view.
 */

public class WelcomeFragment extends Fragment {

    private AppCompatTextView sectionLabel;
    private AppCompatTextView sectionBody;
    private ImageView sectionImg;

    private int page;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public WelcomeFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static WelcomeFragment newInstance(int sectionNumber) {
        WelcomeFragment fragment = new WelcomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page=getArguments().getInt(ARG_SECTION_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.welcome_fragment_main, container, false);
        initView(rootView);
        switch (page){
            case 0:
                sectionImg.setBackgroundResource(R.drawable.wel_ic_messenger);
                sectionLabel.setText(R.string.onboarding_chat_pager);
                sectionBody.setText(R.string.onboarding_chat_pager_content);
                break;
            case 1:
                sectionImg.setBackgroundResource(R.drawable.wel_ic_search_black_24dp);
                sectionLabel.setText(R.string.onboarding_search_pager);
                sectionBody.setText(R.string.onboarding_search_pager_content);
                break;
            case 2:
                sectionImg.setBackgroundResource(R.drawable.wel_ic_read);
                sectionLabel.setText(R.string.onboarding_read_pager);
                sectionBody.setText(R.string.onboarding_read_pager_content);
                break;
            default:
                break;
        }
        return rootView;
    }
    private void initView(View view){
        sectionLabel = (AppCompatTextView) view.findViewById(R.id.section_label);
        sectionBody = (AppCompatTextView) view.findViewById(R.id.section_body);
        sectionImg = (ImageView) view.findViewById(R.id.scrtion_img);
    }
}