package RV_RelojItem;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lta.airlock.R;

import java.util.List;

import Model.Producto;

public class Reloj_Adapter extends RecyclerView.Adapter<Reloj_ViewHolder> {

    Context ctx;
    List<Producto> productos;
    private OnItemClickListener listener;

    public Reloj_Adapter(Context ctx, List<Producto> items, OnItemClickListener listener) {
        this.ctx = ctx;
        this.productos = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Reloj_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.relojitem_view, parent, false);
        return new Reloj_ViewHolder(view);
    }

    public void updateData(List<Producto> newProductos) {
        this.productos = newProductos;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Producto producto);  // Pass the clicked product
    }

    @Override
    public void onBindViewHolder(@NonNull Reloj_ViewHolder holder, int position) {
        Producto producto = productos.get(position);

        holder.lblNombreP.setText(producto.getNombre() != null ? producto.getNombre() : "Sin nombre");

        String imageUrl = producto.getImg();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.img);

        holder.lblPrecioP.setText(String.format("S/. %s", producto.getPrecioCompra()));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(producto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }
}