package android.mvvm.mg.com.mvvm_android.fragments.settings.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.mvvm.mg.com.mvvm_android.R;
import android.mvvm.mg.com.mvvm_android.databinding.FragmentSettingsBinding;
import android.mvvm.mg.com.mvvm_android.dialog.MVVMDialog;
import android.mvvm.mg.com.mvvm_android.fragments.base.BaseFragment;
import android.mvvm.mg.com.mvvm_android.fragments.settings.handler.ISettingsHandler;
import android.mvvm.mg.com.mvvm_android.fragments.settings.viewModel.SettingsViewModel;
import android.mvvm.mg.com.mvvm_android.models.RequestError;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dm.dmnetworking.api_client.base.DMLiveDataBag;
import com.eraz.camera.activities.ErazCameraActivity;
import com.eraz.camera.constants.IConstants;

public class SettingsFragment extends BaseFragment<SettingsViewModel> implements ISettingsHandler {

    private SettingsViewModel mViewModel;
    private FragmentSettingsBinding mBinding;

    public SettingsFragment() {
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        mViewModel.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentSettingsBinding.inflate(inflater, container, false);

        init();

        return mBinding.getRoot();
    }

    private void init() {
        setTitle(R.string.settings_title);

        mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        mBinding.setViewModel(mViewModel);
        mBinding.setHandler(this);

        mViewModel.showProfilePhoto();

        subscribeOnGetImage();
    }

    @Override
    public void onImageClick(final View view) {
        final Intent intent = new Intent(mActivity, ErazCameraActivity.class);

        intent.putExtra(IConstants.BundleKey.CAMERA_TYPE, IConstants.Camera.PHOTO_ONLY);
        intent.putExtra(IConstants.BundleKey.IS_MULTIPLY_GALLERY_IMAGE, false);
        intent.putExtra(IConstants.BundleKey.PICKER_TYPE, IConstants.Picker.DEFAULT);
        intent.putExtra(IConstants.BundleKey.ACTIONBAR_TITLE, getResources().getString(R.string.settings_gallery_title));
        intent.putExtra(IConstants.BundleKey.MAX_COUNT, 1);

        startActivityForResult(intent, RequestCode.CAMERA);
    }

    private void subscribeOnGetImage() {
        mViewModel.<String>getAction(Action.ON_NEW_IMAGE_PATH).observe(this, this::subscribeOnFileUpload);
    }

    private void subscribeOnFileUpload(final String path) {
        final DMLiveDataBag<String, RequestError> liveDataBag = mViewModel.sendImage(path);

        liveDataBag.getSuccessJsonResponse().observe(mActivity, jsonObject -> mViewModel.updateImagePath(path));
        liveDataBag.getFileProgress().observe(mActivity, fileProgress -> mViewModel.updateProgress(fileProgress));
        liveDataBag.getNoInternetConnection().observe(mActivity, s -> MVVMDialog.showNoInternetDialog(mActivity));
    }
}
