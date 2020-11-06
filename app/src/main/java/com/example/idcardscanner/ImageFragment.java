package com.example.idcardscanner;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageFragment extends Fragment {

    private Bitmap bitmap,filteredImage,filtredImageFirst,filtredImageFinal;

    @BindView(R.id.res_photo)
    ImageView resPhoto;

    public void imageSetupFragment(Bitmap bitmap) {
        if (bitmap != null) {
            this.bitmap = bitmap;
        }
    }

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {

        ThumbnailsManager.clearThumbs();
        List<Filter> filters = FilterPack.getFilterPack(getActivity());

        if (bitmap != null) {

            Filter filter =  filters.get(3);
            filteredImage = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            filtredImageFirst = filter.processFilter(filteredImage);

            float contrast = (float) 1.25;
            int brightness = 0;
            float saturation = (float) 1.1;


            Filter filterCustom = new Filter();
            filterCustom.addSubFilter(new ContrastSubFilter(contrast));
            filterCustom.addSubFilter(new BrightnessSubFilter(brightness));
            filterCustom.addSubFilter(new SaturationSubfilter(saturation));

            filtredImageFinal = filterCustom.processFilter(filtredImageFirst);

            resPhoto.setImageBitmap(filtredImageFinal);


        }
    }

}
