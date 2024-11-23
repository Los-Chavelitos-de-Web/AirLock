package RV_RelojCartItem;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lta.airlock.R;

public class ProdCart_ViewHolder extends RecyclerView.ViewHolder {

    ImageView uri;
    TextView lblNombreProd;
    TextView lblPrecioProd;
    EditText txtCantProd;

    public ProdCart_ViewHolder(@NonNull View itemView) {
        super(itemView);
        uri = itemView.findViewById(R.id.uriProd);
        lblNombreProd = itemView.findViewById(R.id.lblNombreProd);
        lblPrecioProd = itemView.findViewById(R.id.lblPrecioProd);
        txtCantProd = itemView.findViewById(R.id.txtCantProd);
    }
}
