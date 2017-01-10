package com.Jot.ClientProfile.activity;

import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Jot.ClientProfile.R;
import com.Jot.ClientProfile.model.Product;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by D4n on 1/9/2017.
 */

public class SlideshowDialogFragment extends DialogFragment{
    private String TAG = SlideshowDialogFragment.class.getSimpleName();
    private ArrayList<Product> products;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView mProductName, mProductPrice, mCount;
    private int selectedPosition = 0;

    static SlideshowDialogFragment newInstance(){
        SlideshowDialogFragment f = new SlideshowDialogFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_slider,container,false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        mCount = (TextView) v.findViewById(R.id.tv_count);
        mProductName = (TextView) v.findViewById(R.id.tv_product_name);
        mProductPrice = (TextView) v.findViewById(R.id.tv_product_price);

        products = (ArrayList<Product>) getArguments().getSerializable("products");
        selectedPosition = getArguments().getInt("position");
        Log.e(TAG,"position: " + selectedPosition);
        Log.e(TAG,"products size: " + products.size());

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        setCurrentItem(selectedPosition);
        return v;
    }
    private void setCurrentItem(int position){
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    //page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private void displayMetaInfo(int position){
        mCount.setText((position +1 )+ "of" + products.size());

        Product product = products.get(position);
        mProductName.setText(product.getName());
        mProductPrice.setText(product.getPrice());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }
    //	adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.product_fullscreen_preview, container, false);

            ImageView imageViewPreview = (ImageView) view.findViewById(R.id.product_preview);

            Product product = products.get(position);

            Glide.with(getActivity()).load(product.getLarge())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewPreview);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return products.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
