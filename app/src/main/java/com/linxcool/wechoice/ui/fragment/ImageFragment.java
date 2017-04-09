package com.linxcool.wechoice.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class ImageFragment extends Fragment {

    public static ImageFragment newInstance() {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}
