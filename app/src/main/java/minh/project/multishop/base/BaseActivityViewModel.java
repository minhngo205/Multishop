package minh.project.multishop.base;

import androidx.lifecycle.ViewModel;

public abstract class BaseActivityViewModel<T extends BaseActivity> {
    // Activity object
    protected T mActivity;

    /**
     * constructor
     *
     * @param t Activity object
     */
    public BaseActivityViewModel(T t) {
        this.mActivity = t;
    }

    /**
     * Used to initialize the layout
     */
    public abstract void initView();

    /**
     * Set the click event.
     *
     * @param viewId Control ID
     */
    public abstract void onClickEvent(int viewId);
}
