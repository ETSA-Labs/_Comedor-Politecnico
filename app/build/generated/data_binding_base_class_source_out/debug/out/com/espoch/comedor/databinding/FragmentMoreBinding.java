// Generated by view binder compiler. Do not edit!
package com.espoch.comedor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.espoch.comedor.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentMoreBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final LinearLayout bottomButtons;

  @NonNull
  public final MaterialButton btnAdminPanel;

  @NonNull
  public final LinearLayout middleButtons;

  @NonNull
  public final LinearLayout topButtons;

  private FragmentMoreBinding(@NonNull RelativeLayout rootView, @NonNull LinearLayout bottomButtons,
      @NonNull MaterialButton btnAdminPanel, @NonNull LinearLayout middleButtons,
      @NonNull LinearLayout topButtons) {
    this.rootView = rootView;
    this.bottomButtons = bottomButtons;
    this.btnAdminPanel = btnAdminPanel;
    this.middleButtons = middleButtons;
    this.topButtons = topButtons;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMoreBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMoreBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_more, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMoreBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottom_buttons;
      LinearLayout bottomButtons = ViewBindings.findChildViewById(rootView, id);
      if (bottomButtons == null) {
        break missingId;
      }

      id = R.id.btn_admin_panel;
      MaterialButton btnAdminPanel = ViewBindings.findChildViewById(rootView, id);
      if (btnAdminPanel == null) {
        break missingId;
      }

      id = R.id.middle_buttons;
      LinearLayout middleButtons = ViewBindings.findChildViewById(rootView, id);
      if (middleButtons == null) {
        break missingId;
      }

      id = R.id.top_buttons;
      LinearLayout topButtons = ViewBindings.findChildViewById(rootView, id);
      if (topButtons == null) {
        break missingId;
      }

      return new FragmentMoreBinding((RelativeLayout) rootView, bottomButtons, btnAdminPanel,
          middleButtons, topButtons);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
