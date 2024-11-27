package RV_RelojCartItem;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lta.airlock.R;

import java.util.List;

import Controllers.SQLite.CartCtrl;
import Model.DBHelper;
import Model.ProdCart;

public class ProdCart_Adapter extends RecyclerView.Adapter<ProdCart_ViewHolder> {

    Context ctx;
    List<ProdCart> prods_carts;

    public ProdCart_Adapter(Context ctx, List<ProdCart> items) {
        this.ctx = ctx;
        this.prods_carts = items;
    }

    @NonNull
    @Override
    public ProdCart_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.cartprod_view, parent, false);
        return new ProdCart_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdCart_ViewHolder holder, int position) {
        ProdCart producto = prods_carts.get(position);

        holder.lblNombreProd.setText(producto.getProd_name() != null ? producto.getProd_name() : "Sin nombre");
        holder.lblPrecioProd.setText(String.valueOf(producto.getPrice()));
        holder.txtCantProd.setText(String.valueOf(producto.getCant()));

        holder.txtCantProd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty() && Integer.parseInt(s.toString()) >= 1) {
                    producto.setCant(Integer.parseInt(s.toString()));
                    new CartCtrl(new DBHelper(ctx)).updateCant(producto.getCart_id(), producto.getCant());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    @Override
    public int getItemCount() {
        return prods_carts.size();
    }
}
