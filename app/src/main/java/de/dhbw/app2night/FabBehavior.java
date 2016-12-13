package de.dhbw.app2night;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by SchmidtRo on 13.12.2016.
 */

public class FabBehavior extends FloatingActionButton.Behavior {

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed)
    {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
                dyUnconsumed);

        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE)
        {
            child.hide();
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE)
        {
            child.show();
        }

    }

    public FabBehavior(Context context, AttributeSet attrs)
    {
        super();
    }


}
