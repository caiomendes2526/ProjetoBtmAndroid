// Generated by view binder compiler. Do not edit!
package com.caio.mendes.tratamentotermico.databinding;

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
import com.caio.mendes.tratamentotermico.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class TelaNovoAlarmeBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnAlarme;

  @NonNull
  public final EditText txtAlarmeAlta;

  @NonNull
  public final EditText txtAlarmeBaixa;

  @NonNull
  public final TextView txtSensores;

  private TelaNovoAlarmeBinding(@NonNull LinearLayout rootView, @NonNull Button btnAlarme,
      @NonNull EditText txtAlarmeAlta, @NonNull EditText txtAlarmeBaixa,
      @NonNull TextView txtSensores) {
    this.rootView = rootView;
    this.btnAlarme = btnAlarme;
    this.txtAlarmeAlta = txtAlarmeAlta;
    this.txtAlarmeBaixa = txtAlarmeBaixa;
    this.txtSensores = txtSensores;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static TelaNovoAlarmeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static TelaNovoAlarmeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.tela_novo_alarme, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static TelaNovoAlarmeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnAlarme;
      Button btnAlarme = rootView.findViewById(id);
      if (btnAlarme == null) {
        break missingId;
      }

      id = R.id.txtAlarmeAlta;
      EditText txtAlarmeAlta = rootView.findViewById(id);
      if (txtAlarmeAlta == null) {
        break missingId;
      }

      id = R.id.txtAlarmeBaixa;
      EditText txtAlarmeBaixa = rootView.findViewById(id);
      if (txtAlarmeBaixa == null) {
        break missingId;
      }

      id = R.id.txtSensores;
      TextView txtSensores = rootView.findViewById(id);
      if (txtSensores == null) {
        break missingId;
      }

      return new TelaNovoAlarmeBinding((LinearLayout) rootView, btnAlarme, txtAlarmeAlta,
          txtAlarmeBaixa, txtSensores);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}