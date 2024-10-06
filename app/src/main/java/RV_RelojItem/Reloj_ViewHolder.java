package RV_RelojItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lta.airlock.R;

public class Reloj_ViewHolder extends RecyclerView.ViewHolder {

    TextView lblNombreP;
    TextView lblTipoP;
    ImageView img;
    TextView lblPrecioP;

    public Reloj_ViewHolder(@NonNull View itemView) {
        super(itemView);
        lblNombreP = itemView.findViewById(R.id.lblNombreP);
        lblTipoP = itemView.findViewById(R.id.lblTipoP);
        lblPrecioP = itemView.findViewById(R.id.lblPrecioP);
    }
}
