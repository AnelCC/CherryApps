package apps.cherry.cherryappsblog.navegation_drawer;

import android.app.Activity;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import apps.cherry.cherryappsblog.R;


public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {

    private List<NavigationItem> mData, drawers;
    private NavigationDrawerCallbacks mNavigationDrawerCallbacks;
    private int mSelectedPosition;
    private int mTouchedPosition = -1;
    TypedArray drawableArray,drawableArrayBlue;
    Activity activity;
    int resource_id;

    public NavigationDrawerAdapter(Activity activity, List<NavigationItem> data, List<NavigationItem> drawers) {
        mData = data;
        this.drawers = drawers;
        this.activity = activity;

        drawableArray   = activity.getResources().obtainTypedArray(R.array.icons);
        drawableArrayBlue = activity.getResources().obtainTypedArray(R.array.icons_accent);
    }

    /**
     * @deprecated
     * @return
     */
    public NavigationDrawerCallbacks getNavigationDrawerCallbacks() {
        return mNavigationDrawerCallbacks;
    }

    public void setNavigationDrawerCallbacks(NavigationDrawerCallbacks navigationDrawerCallbacks) {
        mNavigationDrawerCallbacks = navigationDrawerCallbacks;
    }

    @Override
    public NavigationDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.navigation_drawer_row, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NavigationDrawerAdapter.ViewHolder viewHolder, final int i) {

        resource_id = drawableArray.getResourceId(i, -1);
        viewHolder.textView.setText(mData.get(i).getText());
        viewHolder.img_drawer.setImageDrawable(viewHolder.img_drawer.getContext().getResources().getDrawable(resource_id));
        //viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(mData.get(i).getDrawable(), null, null, null);

        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
                                                   @Override
                                                   public boolean onTouch(View v, MotionEvent event) {

                                                       switch (event.getAction()) {
                                                           case MotionEvent.ACTION_DOWN:
                                                               touchPosition(i);
                                                               return false;
                                                           case MotionEvent.ACTION_CANCEL:
                                                               touchPosition(-1);
                                                               return false;
                                                           case MotionEvent.ACTION_MOVE:
                                                               return false;
                                                           case MotionEvent.ACTION_UP:
                                                               touchPosition(-1);
                                                               return false;
                                                       }
                                                       return true;
                                                   }
                                               }
        );
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       if (mNavigationDrawerCallbacks != null)
                                                           mNavigationDrawerCallbacks.onNavigationDrawerItemSelected(i);
                                                   }
                                               }
        );

        if (mSelectedPosition == i || mTouchedPosition == i) {

            resource_id = drawableArrayBlue.getResourceId(i, -1);
            viewHolder.img_drawer.setImageDrawable(viewHolder.img_drawer.getContext().getResources().getDrawable(resource_id));
            viewHolder.textView.setTextColor(viewHolder.textView.getContext().getResources().getColor(R.color.colorPrimaryText));

        } else {
            resource_id = drawableArray.getResourceId(i, -1);
            viewHolder.img_drawer.setImageDrawable(viewHolder.img_drawer.getContext().getResources().getDrawable(resource_id));
            viewHolder.textView.setTextColor(viewHolder.textView.getContext().getResources().getColor(R.color.colorPrimaryText));
        }
    }

    /**
     * This method is used to notify any registered observers that the item at position has changed.
     * @param position
     */
    private void touchPosition(int position) {
        int lastPosition = mTouchedPosition;
        mTouchedPosition = position;
        if (lastPosition >= 0)
            notifyItemChanged(lastPosition);
        if (position >= 0)
            notifyItemChanged(position);
    }

    /**
     * This method is used to obtain the position of the item.
     * @param position
     */
    public void selectPosition(int position) {
        int lastPosition = mSelectedPosition;
        mSelectedPosition = position;
        notifyItemChanged(lastPosition);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView img_drawer;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.txt_item_name);
            img_drawer = (ImageView) itemView.findViewById(R.id.img_drawer);
        }
    }
}
