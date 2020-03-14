package com.example.foodlicc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import com.example.foodlicc.Database.Database;
import com.example.foodlicc.model.Order;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    private List<Order> orders;
    Context context;



    public CartAdapter(Context context, List<Order> orders) {
        this.orders = orders;
        this.context = context;

    }




    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewItemName, textViewItemPrice,qnt;

//        private ImageView imageView;
        ViewHolder(View itemView) {
            super(itemView);

            textViewItemName = itemView.findViewById(R.id.cart_item_name);
            textViewItemPrice = itemView.findViewById(R.id.cart_item_price);

//            textViewTotalPrice =  itemView.findViewById(R.id.order_price);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.textViewItemName.setText(orders.get(position).getProductName());



        //final int price = Integer.parseInt(orders.get(position).getPrice()) * Integer.parseInt(orders.get(position).getQuantity())
           // - Integer.parseInt(orders.get(position).getDiscount()) * Integer.parseInt(orders.get(position).getQuantity());
       holder.textViewItemPrice.setText(orders.get(position).getPrice()+"DA");



//        TextDrawable textDrawable = TextDrawable.builder().buildRound(orders.get(position).getQuantity(), Color.RED);
//        holder.imageView.setImageDrawable(textDrawable);
    }


    @Override
    public int getItemCount() {
        return orders.size();
    }

}
