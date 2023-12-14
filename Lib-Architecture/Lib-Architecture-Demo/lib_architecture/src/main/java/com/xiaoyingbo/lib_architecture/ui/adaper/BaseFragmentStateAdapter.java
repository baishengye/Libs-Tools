package com.xiaoyingbo.lib_architecture.ui.adaper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**ViewPager2状态适配器的基类*/
public class BaseFragmentStateAdapter<T> extends FragmentStateAdapter {
    protected List<T> datum;

    public BaseFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity, List<T> datum) {
        super(fragmentActivity);
        this.datum=datum;
    }

    public BaseFragmentStateAdapter(@NonNull Fragment fragment, List<T> datum) {
        super(fragment);
        this.datum=datum;
    }

    public BaseFragmentStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<T> datum) {
        super(fragmentManager, lifecycle);
        this.datum=datum;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return null;
    }

    @Override
    public int getItemCount() {
        return datum.size();
    }
}
