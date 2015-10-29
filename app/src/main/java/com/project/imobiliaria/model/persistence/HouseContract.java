package com.project.imobiliaria.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.view.ViewAnimationUtils;

import com.project.imobiliaria.model.entities.House;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1284520 on 16/10/2015.
 */
public class HouseContract {

    public static String TABLE = "house";
    public static String ID = "id";
    public static String TITLE = "title";
    public static String BAIRRO = "bairro";
    public static String RUA = "rua";
    public static String NQUARTOS = "nQuartos";
    public static String NBANHEIROS = "nBanheiros";
    public static String NOTA ="nota";
    public static String TELCONTATO = "telContato";
    public static String PRECO = "preco";
    public static String IMG ="img";
    public static String NUMERO = "num";
    public static String EHALUGUEL = "ehAluguel";
    public static String EHVENDA = "ehVenda";

    public static final String[] COLUNS = {ID, TITLE, BAIRRO, RUA, NQUARTOS, NBANHEIROS, NOTA, IMG, TELCONTATO, PRECO, NUMERO,EHALUGUEL, EHVENDA };

    public static String getCreateTableScript() {
        final StringBuilder create =  new StringBuilder();

        create.append(" CREATE TABLE " + TABLE);
        create.append(" ( ");
        create.append( ID +  " INTEGER PRIMARY KEY, " );
        create.append( TITLE + " TEXT, ");
        create.append( BAIRRO + " TEXT, ");
        create.append( RUA + " TEXT, ");
        create.append( NQUARTOS + " INTEGER, ");
        create.append( NBANHEIROS + " INTEGER, ");
        create.append( NOTA + " REAL, ");
        create.append( IMG + " TEXT,  ");
        create.append( TELCONTATO + " TEXT, ");
        create.append( PRECO + " TEXT, ");
        create.append( NUMERO + " INTEGER, ");
        create.append( EHALUGUEL + " INTEGER, ");
        create.append( EHVENDA + " INTEGER ");
        create.append( " ); ");
        return create.toString();
    }

    public static ContentValues getContentValues(House house) {
        ContentValues values = new ContentValues();
        values.put(HouseContract.ID, house.getId());
        values.put(HouseContract.TITLE, house.getTitulo());
        values.put(HouseContract.BAIRRO, house.getBairro());
        values.put(HouseContract.RUA, house.getRua());
        values.put(HouseContract.NQUARTOS, house.getnQuartos());
        values.put(HouseContract.NBANHEIROS, house.getnBanheiros());
        values.put(HouseContract.NOTA, house.getNota());
        values.put(HouseContract.IMG, house.getFoto());
        values.put(HouseContract.TELCONTATO, house.getTelContato());
        values.put(HouseContract.PRECO, house.getPreco());
        values.put(HouseContract.NUMERO, house.getNumero());
        values.put(HouseContract.EHALUGUEL, house.getEhAluguel() == true ? 0 : 1);
        values.put(HouseContract.EHVENDA, house.getEhVenda() == true ? 0 : 1);

        return values;
    }

    static House getHouse(Cursor cursor) {
        House house = new House();
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            house.setId(cursor.getLong(cursor.getColumnIndex(HouseContract.ID)));
            house.setTitulo(cursor.getString(cursor.getColumnIndex(HouseContract.TITLE)));
            house.setBairro(cursor.getString(cursor.getColumnIndex(HouseContract.BAIRRO)));
            house.setRua(cursor.getString(cursor.getColumnIndex(HouseContract.RUA)));
            house.setnQuartos(cursor.getLong(cursor.getColumnIndex(HouseContract.NQUARTOS)));
            house.setnBanheiros(cursor.getLong(cursor.getColumnIndex(HouseContract.NBANHEIROS)));
            house.setNota(cursor.getDouble(cursor.getColumnIndex(HouseContract.NOTA)));
            house.setFoto(cursor.getString(cursor.getColumnIndex(HouseContract.IMG)));
            house.setTelContato(cursor.getString(cursor.getColumnIndex(HouseContract.TELCONTATO)));
            house.setPreco(cursor.getDouble(cursor.getColumnIndex(HouseContract.PRECO)));
            house.setNumero(cursor.getLong(cursor.getColumnIndex(HouseContract.NUMERO)));
            house.setEhAluguel(cursor.getInt(cursor.getColumnIndex(HouseContract.EHALUGUEL)) == 0 ? true : false);
            house.setEhVenda(cursor.getInt(cursor.getColumnIndex(HouseContract.EHVENDA)) == 0 ? true : false);
            return house;
        }
        return null;
    }

    public static List getHouses(Cursor cursor) {
        ArrayList<House> houses = new ArrayList();
        while (cursor.moveToNext()) {
            houses.add(getHouse(cursor));
        }
        return houses;
    }
}
