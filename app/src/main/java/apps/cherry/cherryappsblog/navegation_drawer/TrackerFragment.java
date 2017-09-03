package apps.cherry.cherryappsblog.navegation_drawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import apps.cherry.cherryappsblog.MainActivity;

/**
 * This class is used as support when their activity should extend
 */
public class TrackerFragment extends Fragment {

    public FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    public TrackerFragment fragment;

    public boolean  isFromDrawer    = true;
    public static int      section_index   = 0;
    public String tag             = "";


    public enum FRAGMENT_TAG {
        FRAG_HOME("home"),
        FRAG_CUSTOMER_WORKPLAN("customer_work_plan"),;

        private final String name;

        private FRAGMENT_TAG(String s) {
            name = s;
        }

        public boolean equalsName(String otherName){
            return (otherName == null)? false:name.equals(otherName);
        }
        public String toString(){
            return name;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(section_index);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).onSectionAttached(section_index);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }


    /**
     * This method is used to to increase the depth of the fragments on activity.
     * @param activity
     */
    public static void addFragmentToStack(Activity activity){

        ((MainActivity) activity).incrementDepthCounter();

    }

}
