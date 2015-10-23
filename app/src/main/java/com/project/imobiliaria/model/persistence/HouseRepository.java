package com.project.imobiliaria.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.imobiliaria.model.entities.House;

import java.util.List;

/**
 * Created by c1284520 on 16/10/2015.
 */
public class HouseRepository {

    public static void save(House house) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = HouseContract.getContentValues(house);

        if (house.getId() == null) {
            db.insert(HouseContract.TABLE, null, values);
        } else {
            String where = HouseContract.ID + " = ?";
            String[] params = {house.getId().toString()};
            db.update(HouseContract.TABLE, values, where, params);
        }
        db.close();
        databaseHelper.close();
    }

    public static List<House> getAll() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(HouseContract.TABLE, HouseContract.COLUNS, null, null, null, null, HouseContract.ID);
        List<House> values = HouseContract.getHouses(cursor);

        db.close();
        databaseHelper.close();
        return values;
    }

    public static void delete(long id) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String where = HouseContract.ID + " = ? ";
        String[] params = {String.valueOf(id)};
        db.delete(HouseContract.TABLE, where, params);

        db.close();
        databaseHelper.close();
    }
}
