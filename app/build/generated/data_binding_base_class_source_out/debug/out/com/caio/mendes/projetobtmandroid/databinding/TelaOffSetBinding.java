// Generated by view binder compiler. Do not edit!
package com.caio.mendes.projetobtmandroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.caio.mendes.projetobtmandroid.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class TelaOffSetBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnOffSet;

  @NonNull
  public final EditText txtOffSet;

  @NonNull
  public final TextView txtSensores;

  private TelaOffSetBinding(@NonNull LinearLayout rootView, @NonNull Button btnOffSet,
      @NonNull EditText txtOffSet, @NonNull TextView txtSensores) {
    this.rootView = rootView;
    this.btnOffSet = btnOffSet;
    this.txtOffSet = txtOffSet;
    this.txtSensores = txtSensores;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static TelaOffSetBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static TelaOffSetBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.tela_off_set, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static TelaOffSetBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnOffSet;
      Button btnOffSet = rootView.findViewById(id);
      if (btnOffSet == null) {
        break missingId;
      }

      id = R.id.txtOffSet;
      EditText txtOffSet = rootView.findViewById(id);
      if (txtOffSet == null) {
        break missingId;
      }

      id = R.id.txtSensores;
      TextView txtSensores = rootView.findViewById(id);
      if (txtSensores == null) {
        break missingId;
      }

      return new TelaOffSetBinding((LinearLayout) rootView, btnOffSet, txtOffSet, txtSensores);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
