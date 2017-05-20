package com.linxcool.wechoice.contract;

import com.linxcool.andbase.mvp.BaseModel;
import com.linxcool.andbase.mvp.BasePresenter;
import com.linxcool.andbase.mvp.BaseView;
import com.linxcool.andbase.retrofit.Reply;
import com.linxcool.wechoice.data.entity.ImageCategory;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by linxcool on 17/4/8.
 */

public interface MineContract {

    interface View extends BaseView<BaseView> {

    }

    interface Presenter extends BasePresenter {

    }

    interface Model extends BaseModel {

    }

}
