package com.linxcool.wechoice.presenter;

import com.linxcool.andbase.retrofit.SimpleObserver;
import com.linxcool.andbase.util.LogUtil;
import com.linxcool.wechoice.contract.ImageListContract;
import com.linxcool.wechoice.data.entity.ImageItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Predicate;

import static android.R.attr.value;

/**
 * Created by linxcool on 17/3/16.
 */

public class ImageListPresenter implements ImageListContract.Presenter {

    ImageListContract.View view;
    ImageListContract.Model model;

    public ImageListPresenter(ImageListContract.View view, ImageListContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void start() {
        // Empty
    }

    @Override
    public void loadImages(final boolean fromNetwork, final int page) {
        final String cid = view.getCategoryId();

        LogUtil.iFormat("load %s images page %d, fromNetwork %s", cid, page, String.valueOf(fromNetwork));

        model.loadImages(fromNetwork, cid, page).filter(new Predicate<List<ImageItem>>() {
            @Override
            public boolean test(List<ImageItem> list) throws Exception {
                List<ImageItem> viewData = view.getImages();
                List<ImageItem> clearList = new ArrayList<>();
                for (ImageItem item : list) {
                    if (viewData.contains(item) || item.getId() == null) {
                        clearList.add(item);
                    }
                }
                list.removeAll(clearList);
                if(clearList.size() > 0) {
                    LogUtil.w("remove duplicate images count " + clearList.size());
                }
                return true;
            }
        }).subscribe(new SimpleObserver<List<ImageItem>>() {
            @Override
            public void onNext(List<ImageItem> value) {
                view.showImages(value);
            }

            @Override
            public void onError(Throwable e) {
                if (fromNetwork) {
                    view.showLoadImagesFailure("请检查网络或重试");
                    e.printStackTrace();
                } else {
                    LogUtil.eFormat("can't find data(%s) from cache", cid);
                    loadImages(true, page);
                }
            }

        });
    }
}
