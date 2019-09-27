package com.utu.utu;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

@Layout(R.layout.drawer_item)
public class DrawerMenuItem {

    public static final int DRAWER_MENU_ITEM_ADDRESS = 1;
    public static final int DRAWER_MENU_ITEM_SERVICES = 2;
    public static final int DRAWER_MENU_ITEM_PROVIDERS = 3;
    public static final int DRAWER_MENU_ITEM_PROFILE = 4;
    public static final int DRAWER_MENU_ITEM_RECORDS = 5;
    public static final int DRAWER_MENU_ITEM_NOTIFICATIONS = 6;
    public static final int DRAWER_MENU_ITEM_SETTINGS = 7;
    public static final int DRAWER_MENU_ITEM_INFO = 8;
    public static final int DRAWER_MENU_ITEM_LOGOUT = 9;

    private int mMenuPosition;
    private Context mContext;
    private DrawerCallBack mCallBack;

    @View(R.id.itemNameTxt)
    private TextView itemNameTxt;

    @View(R.id.itemIcon)
    private ImageView itemIcon;

    public DrawerMenuItem(Context context, int menuPosition) {
        mContext = context;
        mMenuPosition = menuPosition;
    }

    @Resolve
    private void onResolved() {
        switch (mMenuPosition){
            case DRAWER_MENU_ITEM_ADDRESS:
                itemNameTxt.setText("Address");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_location_24dp));
                break;
            case DRAWER_MENU_ITEM_SERVICES:
                itemNameTxt.setText("Services");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_services_24));
                break;
            case DRAWER_MENU_ITEM_PROVIDERS:
                itemNameTxt.setText("Providers");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_group_work_black_18dp));
                break;
            case DRAWER_MENU_ITEM_PROFILE:
                itemNameTxt.setText("Profile");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_account_circle_black_18dp));
                break;
            case DRAWER_MENU_ITEM_RECORDS:
                itemNameTxt.setText("Records");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_records_24));
                break;
            case DRAWER_MENU_ITEM_NOTIFICATIONS:
                itemNameTxt.setText("Notifications");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_notifications_black_18dp));
                break;
            case DRAWER_MENU_ITEM_SETTINGS:
                itemNameTxt.setText("Settings");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_settings_black_18dp));
                break;
            case DRAWER_MENU_ITEM_INFO:
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_info_24));
                itemNameTxt.setText("Info");
                break;
            case DRAWER_MENU_ITEM_LOGOUT:
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_exit_to_app_black_18dp));
                itemNameTxt.setText("Logout");
                break;
        }
    }

    @Click(R.id.mainView)
    private void onMenuItemClick(){
        switch (mMenuPosition){
            case DRAWER_MENU_ITEM_ADDRESS:
                Toast.makeText(mContext, "Address", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onAddressMenuSelected();
                break;
            case DRAWER_MENU_ITEM_SERVICES:
                if(mCallBack != null)mCallBack.onServicesMenuSelected();
                break;
            case DRAWER_MENU_ITEM_PROVIDERS:
                if(mCallBack != null)mCallBack.onProvidersMenuSelected();
                break;
            case DRAWER_MENU_ITEM_PROFILE:
                if(mCallBack != null)mCallBack.onProfileMenuSelected();
                break;
            case DRAWER_MENU_ITEM_RECORDS:
                Toast.makeText(mContext, "Records", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onRecordsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_NOTIFICATIONS:
                Toast.makeText(mContext, "Notifications", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onNotificationsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_SETTINGS:
                Toast.makeText(mContext, "Settings", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onSettingsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_INFO:
                if(mCallBack != null)mCallBack.onInfoMenuSelected();
                break;
            case DRAWER_MENU_ITEM_LOGOUT:
                if(mCallBack != null)mCallBack.onLogoutMenuSelected();
                break;
        }
    }

    public void setDrawerCallBack(DrawerCallBack callBack) {
        mCallBack = callBack;
    }

    public interface DrawerCallBack{
        void onAddressMenuSelected();
        void onServicesMenuSelected();
        void onProvidersMenuSelected();
        void onProfileMenuSelected();
        void onRecordsMenuSelected();
        void onNotificationsMenuSelected();
        void onSettingsMenuSelected();
        void onInfoMenuSelected();
        void onLogoutMenuSelected();
    }
}