package RV_RelojItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lta.airlock.R;

import java.util.List;

import Clases.RelojP;

public class Reloj_Adapter extends RecyclerView.Adapter<Reloj_ViewHolder> {

    Context ctx;
    List<RelojP> items;

    public Reloj_Adapter(Context ctx, List<RelojP> items) {
        this.ctx = ctx;
        this.items = items;
    }

    @NonNull
    @Override
    public Reloj_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Reloj_ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.relojitem_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Reloj_ViewHolder holder, int position) {
        holder.lblNombreP.setText(items.get(position).getNombre());
        holder.lblTipoP.setText(items.get(position).getTipo());
        // holder.img.setImageURI(items.get(position).getImg());
        holder.lblPrecioP.setText(Double.toString(items.get(position).getPrecio()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
