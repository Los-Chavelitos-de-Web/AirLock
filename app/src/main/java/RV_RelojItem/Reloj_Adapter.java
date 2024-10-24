package RV_RelojItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lta.airlock.R;

import java.util.List;

import Model.Producto;

public class Reloj_Adapter extends RecyclerView.Adapter<Reloj_ViewHolder> {

    Context ctx;
    List<Producto> items;

    public Reloj_Adapter(Context ctx, List<Producto> items) {
        this.ctx = ctx;
        this.items = items;
    }

    @NonNull
    @Override
    public Reloj_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.relojitem_view, parent, false);
        return new Reloj_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Reloj_ViewHolder holder, int position) {
        Producto producto = items.get(position);

        holder.lblNombreP.setText(producto.getNombre() != null ? producto.getNombre() : "Sin nombre");
        holder.lblCantidadP.setText(String.valueOf(producto.getStock()));
        holder.lblPrecioP.setText(Float.toString(producto.getPrecioCompra()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}