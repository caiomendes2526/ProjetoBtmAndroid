// Generated by view binder compiler. Do not edit!
package com.caio.mendes.tratamentotermico.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.caio.mendes.tratamentotermico.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class TelaTemperaturaBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ListView ListViewUltimaTemp;

  @NonNull
  public final LinearLayout swipeContainer;

  private TelaTemperaturaBinding(@NonNull LinearLayout rootView,
      @NonNull ListView ListViewUltimaTemp, @NonNull LinearLayout swipeContainer) {
    this.rootView = rootView;
    this.ListViewUltimaTemp = ListViewUltimaTemp;
    this.swipeContainer = swipeContainer;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static TelaTemperaturaBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static TelaTemperaturaBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.tela_temperatura, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static TelaTemperaturaBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ListViewUltimaTemp;
      ListView ListViewUltimaTemp = rootView.findViewById(id);
      if (ListViewUltimaTemp == null) {
        break missingId;
      }

      LinearLayout swipeContainer = (LinearLayout) rootView;

      return new TelaTemperaturaBinding((LinearLayout) rootView, ListViewUltimaTemp,
          swipeContainer);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}