package apps.cherry.cherryappsblog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import apps.cherry.cherryappsblog.navegation_drawer.NavigationDrawerCallbacks;
import apps.cherry.cherryappsblog.navegation_drawer.NavigationDrawerFragment;
import apps.cherry.cherryappsblog.navegation_drawer.TrackerFragment;
import apps.cherry.cherryappsblog.util.simple_dialog.DecisionDialogListener;
import apps.cherry.cherryappsblog.util.simple_dialog.SimpleDialog;

public class MainActivity extends AppCompatActivity implements NavigationDrawerCallbacks, DecisionDialogListener {

    String ACT_TAG = "MainActivity";


    private Toolbar mToolbar;


    public NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence                mTitle;
    public  int                         depthCounter   = 0;

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private TrackerFragment Fragment_Default,fragment;
    private int                         position;
    String                              CURRENT_FRAGMENT_TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);

        mNavigationDrawerFragment.selectItem(0);
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment=getSupportFragmentManager().findFragmentById(R.id.container);
        Log.d(ACT_TAG, "Deep depthCounter Back 1:" + depthCounter);

        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
            return;
        }

        if (depthCounter == 0) {
            Fragment home   = fragmentManager.findFragmentByTag(TrackerFragment.FRAGMENT_TAG.FRAG_HOME.toString());
            if(home != null && home.isAdded()){
                logOut();
            }else{
                mNavigationDrawerFragment.selectItem(0);
            }
        } else {
            super.onBackPressed();
        }

        if (depthCounter > 0)
            depthCounter--;

        Log.d(ACT_TAG, "Deep depthCounter Back 2:" + depthCounter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        fragmentManager = getSupportFragmentManager();

        if (fragmentManager == null)
            Log.d("MAIN ACTIVITY","Error fragment manager");

        fragmentTransaction         = fragmentManager.beginTransaction();
        if(position == this.position){
            return;
        }

        this.position               = position;
        Fragment_Default = null;
        switch (position) {
            case NavigationDrawerFragment.HOME:

                CURRENT_FRAGMENT_TAG    = TrackerFragment.FRAGMENT_TAG.FRAG_HOME.toString();
                /*if(fragmentManager.findFragmentByTag(CURRENT_FRAGMENT_TAG) != null){
                    fragment            = (TrackerFragment)fragmentManager.findFragmentByTag(CURRENT_FRAGMENT_TAG);
                    Fragment_Default    = new FragmentHome();
                }else
                    fragment = new FragmentHome();*/

                break;

            case NavigationDrawerFragment.WORKPLAN:

                CURRENT_FRAGMENT_TAG    = TrackerFragment.FRAGMENT_TAG.FRAG_CUSTOMER_WORKPLAN.toString();
                /*if(fragmentManager.findFragmentByTag(CURRENT_FRAGMENT_TAG) != null){
                    fragment            = (TrackerFragment)fragmentManager.findFragmentByTag(CURRENT_FRAGMENT_TAG);
                    Fragment_Default    = new FragmentWorkPlanList();
                }else
                    fragment = new FragmentWorkPlanList();*/

                break;

            case NavigationDrawerFragment.LOGOUT:
                fragment = null;
                logOut();
                break;
        }
        if (fragment != null)
            prepareTransaction();
    }

    /**
     * This method is used to initialize the contandor, animation and transaction fragments
     */
    public void prepareTransaction(){

        fragment.section_index  = position;
        fragment.tag            = CURRENT_FRAGMENT_TAG;
        //fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.shrink_out, R.anim.slide_from_left, R.anim.shrink_out);
        depthCounter = 0;

        if(Fragment_Default != null)
            fragment = Fragment_Default;

        fragmentTransaction.replace(R.id.container, fragment, CURRENT_FRAGMENT_TAG).commit();

    }


    /**
     * This method is used to extract an array of strings called the title of drawer navigation
     * @param number  is used to indicate the position of the title
     */
    public void onSectionAttached(int number) {
        mTitle = getResources().getStringArray(R.array.drawer_items)[number];
    }

    /**
     * This method is used to to increase the depth of the fragments.
     */
    public void incrementDepthCounter(){
        depthCounter++;
    }

    /**
     * This method is used to send service LOGOUT and exit the application.
     */
    public void logOut() {
        SimpleDialog.showDecisionDialogListener(getString(R.string.log_out), this, this);
    }

    @Override
    public void responseFromDecisionDialog(String confirmMessage, String option) {
        if (option.equalsIgnoreCase("DONE")) {
            finish();
        }
    }
}
