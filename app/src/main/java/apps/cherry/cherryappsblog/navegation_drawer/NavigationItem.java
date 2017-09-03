package apps.cherry.cherryappsblog.navegation_drawer;

/**
 * @author Created by raymundo.piedra on 07/02/15.
 * This class is used even get the text of the menu item
 */
public class NavigationItem {
    private String nav_item_text;

    public NavigationItem(String text) {
        nav_item_text   = text;
    }

    public String getText() {
        return nav_item_text;
    }

    public void setText(String text) {
        nav_item_text = text;
    }

}
