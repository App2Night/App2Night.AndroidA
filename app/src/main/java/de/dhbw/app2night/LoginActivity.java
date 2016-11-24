package de.dhbw.app2night;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import de.dhbw.utils.ContextManager;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginFragment.OnRegistrationButtonClickListener, RegisterFragment.OnRegisterButtonClickListener {



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

    @Override
    public void onInputFragmentRegister(String userName) {
        Fragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(LoginFragment.ARG_USERNAME, userName);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.login_container_body, fragment);
        fragmentTransaction.commit();
    }
}

