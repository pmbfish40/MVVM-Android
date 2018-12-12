package android.mvvm.mg.com.mvvm_android.fragments.base;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

public abstract class BaseViewModelItemClick<O> extends BaseViewModel implements IBaseOnItemClickListener<O> {

    public final ObservableField<IBaseOnItemClickListener<O>> itemClickListener = new ObservableField<>();

    public BaseViewModelItemClick(final @NonNull Application application) {
        super(application);
        itemClickListener.set(this);
    }
}
