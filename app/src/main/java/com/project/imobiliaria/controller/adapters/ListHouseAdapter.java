package com.project.imobiliaria.controller.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.imobiliaria.R;
import com.project.imobiliaria.model.entities.House;

import java.util.List;

public abstract class ListHouseAdapter extends RecyclerView.Adapter<ListHouseAdapter.MyViewHolder> {

    private List<House> houses;

    Context context;


    public ListHouseAdapter(Context context, List<House> houses) {
        this.houses = houses;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(R.layout.item_list_house, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);

        return mvh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
       final House house = getItem(position);
        final ImageView image = holder.image;
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context, R.style.CustomDialog);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.image_house_view);

                ImageView imageBigger = (ImageView) dialog.findViewById(R.id.imageviewHouse);
                if (house.getFoto() != null)
                    Glide.with(context).load(house.getFoto()).into(imageBigger);
                else
                    imageBigger.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_picture));
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();

            }
        });

        holder.proprietario.setText(house.getTitulo());
        holder.nota.setRating(house.getNota().floatValue());
        holder.nQuartos.setText(house.getnQuartos().toString());
        holder.nBanheiros.setText(house.getnBanheiros().toString());
        holder.preco.setText(house.getPreco().toString());
        holder.checkBtnAluguel.setChecked(house.getEhAluguel());
        holder.checkBtnVenda.setChecked(house.getEhVenda());
        holder.checkBtnVenda.setClickable(false);
        holder.checkBtnAluguel.setClickable(false);
        holder.toolbar.setTitle(house.getId().toString());
        holder.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.call) {
                    chamar(house);
                }
                if (item.getItemId() == R.id.edit) {
                    editar(house);
                }
                if (item.getItemId() == R.id.delete) {
                    deletar(house);
                }
                if (item.getItemId() == R.id.verMapa)
                    verMap(house);
                return false;
            }
        });

        if ((house.getId() != null) && ((house.getFoto() != null) && !house.getFoto().equals(""))) {
            Glide.with(context).load(house.getFoto()).into(image);
        } else {
            image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_picture));
        }
    }

    @Override
    public int getItemCount() {
        return houses.size();

    }

    public House getItem(int position) {
        return houses.get(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView proprietario;
        TextView nQuartos;
        RatingBar nota;
        TextView nBanheiros;
        TextView preco;
        CheckBox checkBtnAluguel;
        CheckBox checkBtnVenda;
        ImageView image;
        Toolbar toolbar;

        public MyViewHolder(View itemView) {
            super(itemView);

            proprietario = (TextView) itemView.findViewById(R.id.textViewProprietario);
            nQuartos = (TextView) itemView.findViewById(R.id.textViewNquartos);
            nota = (RatingBar) itemView.findViewById(R.id.ratingBar2);
            nBanheiros = (TextView) itemView.findViewById(R.id.textViewNBanheiros);
            preco = (TextView) itemView.findViewById(R.id.textViewPreco);
            checkBtnAluguel = (CheckBox) itemView.findViewById(R.id.checkBoxAluguel);
            checkBtnVenda = (CheckBox) itemView.findViewById(R.id.checkBoxVenda);
            image = (ImageView) itemView.findViewById(R.id.image);
            toolbar = (Toolbar) itemView.findViewById(R.id.viewToobar);
            toolbar.inflateMenu(R.menu.menu_for_itens);


        }
    }

    public abstract void deletar(House house);

    public abstract void editar(House house);

    public abstract void chamar(House house);

    public abstract void verMap(House house);

}


