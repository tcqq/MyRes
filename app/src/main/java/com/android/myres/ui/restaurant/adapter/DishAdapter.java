package com.android.myres.ui.restaurant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.myres.databinding.ItemDishBinding;
import com.android.myres.ui.restaurant.MenuManagementActivity;
import com.android.myres.ui.restaurant.data.Dish;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

    private List<Dish> dishList;
    private MenuManagementActivity handler;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public DishAdapter(List<Dish> dishList, MenuManagementActivity handler) {
        this.dishList = dishList;
        this.handler = handler;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemDishBinding itemBinding = ItemDishBinding.inflate(layoutInflater, parent, false);
        return new DishViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish dish = dishList.get(position);
        holder.bind(dish, handler);
        holder.itemView.setSelected(selectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    public class DishViewHolder extends RecyclerView.ViewHolder {

        private final ItemDishBinding binding;

        public DishViewHolder(ItemDishBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> {
                if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

                notifyItemChanged(selectedPosition);
                selectedPosition = getAdapterPosition();
                notifyItemChanged(selectedPosition);

                handler.onDishClicked(dishList.get(selectedPosition));
            });

            binding.deleteButton.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    handler.deleteDish(dishList.get(getAdapterPosition()));
                }
            });
        }

        public void bind(Dish dish, MenuManagementActivity handler) {
            binding.setDish(dish);
            binding.executePendingBindings();
        }
    }

    public void updateDishes(List<Dish> newDishes) {
        dishList = newDishes;
        notifyDataSetChanged();
    }
}
