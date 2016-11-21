package de.dhbw.app2night;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.backendTasks.user.LoginTask;
import de.dhbw.utils.ContextManager;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginFragment.OnRegisterButtonClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ContextManager.getInstance().setContext(this);

        displayView(R.layout.fragment_login);
    }

    private void displayView(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.layout.fragment_login:
                fragment = new LoginFragment();
                break;
            case R.layout.fragment_register:
                fragment = new RegisterFragment();
                break;
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.login_container_body, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onInput(String userName) {
        Fragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(RegisterFragment.ARG_USERNAME, userName);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.login_container_body, fragment).addToBackStack("register");
        fragmentTransaction.commit();
    }
}

