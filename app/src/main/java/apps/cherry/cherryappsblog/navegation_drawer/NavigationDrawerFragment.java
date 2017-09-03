package apps.cherry.cherryappsblog.navegation_drawer;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.List;

import apps.cherry.cherryappsblog.R;
import apps.cherry.cherryappsblog.util.Utilities;

public class NavigationDrawerFragment extends Fragment implements NavigationDrawerCallbacks {

    public static final int HOME                = 0;
    public static final int WORKPLAN            = 1;
    public static final int LOGOUT              = 2;

    private NavigationDrawerCallbacks mCallbacks;
    private RecyclerView mDrawerList;
    private View mFragmentContainerView;
    public DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private boolean mFromSavedInstanceState;
    private int mCurrentSelectedPosition    = -1;

    private TextView txv_name_user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mDrawerList = (RecyclerView) view.findViewById(R.id.drawerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDrawerList.setLayoutManager(layoutManager);
        mDrawerList.setHasFixedSize(true);

        txv_name_user   = (TextView)view.findViewById(R.id.txv_name_user);

        String name_user = Utilities.getPreferencesValueForKey("name_user", getActivity());

        txv_name_user.setText(name_user);


        final List<NavigationItem> navigationItems = ProfileManager.getDrawerModulesFromProfile(getActivity());
        final List<NavigationItem> navigationItemsDrawer = ProfileManager.getDrawerModulesIconsFromProfile(getActivity());
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(getActivity(), navigationItems,navigationItemsDrawer);
        adapter.setNavigationDrawerCallbacks(this);
        mDrawerList.setAdapter(adapter);
        selectItem(mCurrentSelectedPosition);

        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return view;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    /**
     * @deprecated
     * @return
     */
    public ActionBarDrawerToggle getActionBarDrawerToggle() {
        return mActionBarDrawerToggle;
    }

    /**
     * @deprecated
     * @param actionBarDrawerToggle
     */
    public void setActionBarDrawerToggle(ActionBarDrawerToggle actionBarDrawerToggle) {
        mActionBarDrawerToggle = actionBarDrawerToggle;
    }

    public void setup(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        mFragmentContainerView  = getActivity().findViewById(fragmentId);
        mDrawerLayout           = drawerLayout;
        mDrawerLayout.setStatusBarBackgroundColor(
                getResources().getColor(R.color.colorWhite));

        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) return;
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(toolbar.getWindowToken(), 0);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!isAdded()) return;
                getActivity().invalidateOptionsMenu();
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        };

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    /**
     * @deprecated
     */
    public void openDrawer() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    /**
     * This method is used to close the navigationDrawer
     */
    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
        Log.e("cerro: ", String.valueOf(mFragmentContainerView));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    /**
     * This method is used to select an option from the navigation drawer
     * @param position returns the position of the option.
     */
    public void selectItem(int position) {

        mCurrentSelectedPosition = position;
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
        ((NavigationDrawerAdapter) mDrawerList.getAdapter()).selectPosition(position);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        selectItem(position);
    }

    /**
     * @deprecated
     * @return
     */
    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    /**
     * @deprecated
     * @param drawerLayout
     */
    public void setDrawerLayout(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
    }

}
