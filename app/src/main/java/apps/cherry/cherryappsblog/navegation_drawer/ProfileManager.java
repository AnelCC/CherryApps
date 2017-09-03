package apps.cherry.cherryappsblog.navegation_drawer;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import apps.cherry.cherryappsblog.R;

/**
 * Created by raymundo.piedra on 07/02/15.
 * This class is used to get the text or image of an array.
 */
public class ProfileManager {
    /**
     * This class is used to get the text an array.
     * @param context
     * @return
     */
    public static List<NavigationItem> getDrawerModulesFromProfile(Activity context){

        List<NavigationItem> items = new ArrayList<NavigationItem>();

        String[] titlesArray    = context.getResources().getStringArray(R.array.drawer_items);

            for (int i=0; i<titlesArray.length; i++){
                items.add(new NavigationItem(titlesArray[i]));
            }

        return items;
    }

    /**
     * This class is used to get the image an array.
     * @param context
     * @return
     */
    public static List<NavigationItem> getDrawerModulesIconsFromProfile(Activity context){

        List<NavigationItem> items = new ArrayList<NavigationItem>();

        String[] titlesArray    = context.getResources().getStringArray(R.array.icons);

        for (int i=0; i<titlesArray.length; i++){
            items.add(new NavigationItem(titlesArray[i]));
        }

        return items;
    }
}
