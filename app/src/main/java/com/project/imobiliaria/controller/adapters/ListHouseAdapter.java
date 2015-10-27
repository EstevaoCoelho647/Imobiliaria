package com.project.imobiliaria.controller.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.imobiliaria.R;
import com.project.imobiliaria.controller.activities.ListHouseActivity;
import com.project.imobiliaria.model.entities.House;
import com.project.imobiliaria.model.persistence.HouseRepository;

import java.util.List;

public abstract class ListHouseAdapter extends BaseAdapter {

    private List<House> houses;
    private Activity listHouseActivity;


    public ListHouseAdapter(List<House> houses, Activity listHouseActivity) {
        this.houses = houses;
        this.listHouseActivity = listHouseActivity;
    }

    @Override
    public int getCount() {
        return houses.size();
    }

    @Override
    public Object getItem(int i) {
        return houses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return houses.get(i).getId();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {


        final House house = houses.get(i);


        LayoutInflater inflater = listHouseActivity.getLayoutInflater();
        View linha = inflater.inflate(R.layout.item_list_house, null);

        Toolbar toolbar = (Toolbar) linha.findViewById(R.id.viewToobar);

        toolbar.setTitle(house.getId().toString());
        toolbar.inflateMenu(R.menu.menu_for_itens);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
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

                return true;
            }

        });
        TextView proprietario = (TextView) linha.findViewById(R.id.textViewProprietario);
        proprietario.setText(house.getTitulo());
        TextView nQuartos = (TextView) linha.findViewById(R.id.textViewNquartos);
        nQuartos.setText(house.getnQuartos().toString());
        RatingBar nota = (RatingBar) linha.findViewById(R.id.ratingBar2);
        nota.setRating(house.getNota().floatValue());
        TextView nBanheiros = (TextView) linha.findViewById(R.id.textViewNBanheiros);
        nBanheiros.setText(house.getnBanheiros().toString());
        TextView preco = (TextView) linha.findViewById(R.id.textViewPreco);
        preco.setText(house.getPreco().toString());
        CheckBox checkBtnAluguel = (CheckBox) linha.findViewById(R.id.checkBoxAluguel);
        checkBtnAluguel.setChecked(house.getEhAluguel());
        checkBtnAluguel.setClickable(false);
        CheckBox checkBtnVenda = (CheckBox) linha.findViewById(R.id.checkBoxVenda);
        checkBtnVenda.setChecked(house.getEhVenda());
        checkBtnVenda.setClickable(false);

        ImageView image = (ImageView) linha.findViewById(R.id.image);
        if ((house.getId() != null) && ((house.getFoto() != null) && !house.getFoto().equals(""))) {
            Bitmap imagem = BitmapFactory.decodeFile(house.getFoto());
            Bitmap imageScaled = Bitmap.createScaledBitmap(imagem, 125, 125, false);
            image.setImageBitmap(imageScaled);
        } else {
            image.setImageDrawable(listHouseActivity.getResources().getDrawable(R.drawable.ic_picture));
        }
        return linha;
    }

    public abstract void deletar(House house);

    public abstract void editar(House house);

    public abstract void chamar(House house);

    public abstract void verMap(House house);
}


