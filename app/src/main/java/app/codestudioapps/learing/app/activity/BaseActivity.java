package app.codestudioapps.learing.app.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.codestudioapps.learing.app.R;
import app.codestudioapps.learing.app.data.constant.AppConstant;
import app.codestudioapps.learing.app.utility.ActivityUtilities;
import app.codestudioapps.learing.app.utility.AdsUtilities;
import app.codestudioapps.learing.app.utility.AppUtilities;
import app.codestudioapps.learing.app.utility.DialogUtilities;


public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DialogUtilities.OnCompleteListener {

    private Context mContext;
    private Activity mActivity;

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private LinearLayout mLoadingView, mNoDataView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = BaseActivity.this;
        mContext = mActivity.getApplicationContext();

        // uncomment this line to disable ads from entire application
        //disableAds();

    }

    public NavigationView getNavigationView() {
        return mNavigationView;
    }

    public void initDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (this, mDrawerLayout, mToolbar, R.string.openDrawer, R.string.closeDrawer) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };


        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
        mNavigationView.setItemIconTintList(null);
        getNavigationView().setNavigationItemSelectedListener(this);
    }

    public void initToolbar(boolean isTitleEnabled) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(isTitleEnabled);
    }

    public void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void enableUpButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    public void initLoader() {
        mLoadingView = (LinearLayout) findViewById(R.id.loadingView);
        mNoDataView = (LinearLayout) findViewById(R.id.noDataView);
    }


    public void showLoader() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }

        if (mNoDataView != null) {
            mNoDataView.setVisibility(View.GONE);
        }
    }

    public void hideLoader() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
        if (mNoDataView != null) {
            mNoDataView.setVisibility(View.GONE);
        }
    }

    public void showEmptyView() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
        if (mNoDataView != null) {
            mNoDataView.setVisibility(View.VISIBLE);
        }
    }

    private void disableAds() {
        AdsUtilities.getInstance(mContext).disableBannerAd();
        AdsUtilities.getInstance(mContext).disableInterstitialAd();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        // main items
        if (id == R.id.action_quiz) {
            ActivityUtilities.getInstance().invokeNewActivity(mActivity, QuizPromptActivity.class, true);
        } else if (id == R.id.action_fav) {
            ActivityUtilities.getInstance().invokeNewActivity(mActivity, FavoriteListActivity.class, false);
        } else if (id == R.id.action_settings) {
            ActivityUtilities.getInstance().invokeNewActivity(mActivity, SettingsActivity.class, false);
        } else if (id == R.id.action_about_dev) {
            ActivityUtilities.getInstance().invokeNewActivity(mActivity, AboutDevActivity.class, false);
        }

        // social
        if (id == R.id.action_youtube) {
            AppUtilities.youtubeLink(mActivity);
        } else if (id == R.id.action_facebook) {
            AppUtilities.faceBookLink(mActivity);
        } else if (id == R.id.action_twitter) {
            AppUtilities.twitterLink(mActivity);
        } else if (id == R.id.action_google_plus) {
            AppUtilities.googlePlusLink(mActivity);
        }

        // others
         else if (id == R.id.action_rate_app) {
            AppUtilities.rateThisApp(mActivity); // this feature will only work after publish the app
        }   else if (id == R.id.action_exit) {
            FragmentManager manager = getSupportFragmentManager();
            DialogUtilities dialog = DialogUtilities.newInstance(getString(R.string.exit), getString(R.string.close_prompt), getString(R.string.yes), getString(R.string.no), AppConstant.BUNDLE_KEY_EXIT_OPTION);
            dialog.show(manager, AppConstant.BUNDLE_KEY_DIALOG_FRAGMENT);
        }

        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        return true;

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onComplete(Boolean isOkPressed, String viewIdText) {
        if (isOkPressed) {
            if (viewIdText.equals(AppConstant.BUNDLE_KEY_EXIT_OPTION)) {
                mActivity.finishAffinity();
            }
        }
    }

}