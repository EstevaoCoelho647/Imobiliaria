package com.project.imobiliaria.controller.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.project.imobiliaria.R;
import com.project.imobiliaria.model.entities.House;
import com.project.imobiliaria.model.persistence.HouseRepository;

import java.io.File;

/**
 * Created by c1284520 on 16/10/2015.
 */
public class FormHouseActivity extends AppCompatActivity {

    EditText titulo;
    EditText bairro;
    EditText nBanheiros;
    EditText rua;
    EditText nquartos;
    EditText telContato;
    EditText preco;
    RatingBar nota;
    ImageView foto;
    String caminhoArquivo;
    House house;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_house);

        initHouse();
        bindTitulo();
        bindBairro();
        bindNBanheiros();
        bindRua();
        bindNQuartos();
        bindTelContato();
        bindFoto();
        bindNota();
        bindPreco();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ok_add:
                bindHouse();
                HouseRepository.save(house);
                Toast.makeText(FormHouseActivity.this, "Residencia adicionada/editada com sucesso!", Toast.LENGTH_LONG).show();
                finish();

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initHouse() {
        Bundle values = getIntent().getExtras();

        if (values != null) {
            this.house = values.getParcelable("SelectedHouse");
        } else
            this.house = new House();
    }


    private void bindHouse() {
        house.setBairro(bairro.getText().toString());
        house.setTitulo(titulo.getText().toString());
        house.setnBanheiros(Long.parseLong(nBanheiros.getText().toString()));
        house.setnQuartos(Long.parseLong(nquartos.getText().toString()));
        house.setRua(rua.getText().toString());
        house.setTelContato(telContato.getText().toString());
        house.setPreco(Double.parseDouble(preco.getText().toString()));
        house.setNota((double) nota.getRating());
        if (caminhoArquivo != null) {
            house.setFoto(caminhoArquivo);
        }

    }

    private void bindTitulo() {
        titulo = (EditText) findViewById(R.id.titulo_house);
        titulo.setText(house.getTitulo() == null ? "" : house.getTitulo());
    }

    private void bindBairro() {
        bairro = (EditText) findViewById(R.id.editTextBairro);
        bairro.setText(house.getBairro() == null ? "" : house.getBairro());
    }

    private void bindNBanheiros() {
        nBanheiros = (EditText) findViewById(R.id.editTextBanheiros);
        nBanheiros.setText(house.getnBanheiros() == null ? "" : house.getnBanheiros().toString());
    }

    private void bindRua() {
        rua = (EditText) findViewById(R.id.editTextRua);
        rua.setText(house.getRua() == null ? "" : house.getRua());
    }

    private void bindNQuartos() {
        nquartos = (EditText) findViewById(R.id.editTextQuartos);
        nquartos.setText(house.getnQuartos() == null ? "" : house.getnQuartos().toString());
    }

    private void bindTelContato() {
        telContato = (EditText) findViewById(R.id.editTextTelefone);
        telContato.setText(house.getTelContato() == null ? "" : house.getTelContato());
    }

    private void bindNota() {
        nota = (RatingBar) findViewById(R.id.ratingBar);
        nota.setRating(house.getNota() == null ? -1 : house.getNota().floatValue());
    }

    private void bindPreco() {
        preco = (EditText) findViewById(R.id.editTextPreco);
        preco.setText(house.getPreco() == null ? "" : house.getPreco().toString());
    }
    private void bindFoto() {
        foto = (ImageView) findViewById(R.id.image);
        foto.setImageDrawable(getResources().getDrawable(R.drawable.images));

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                caminhoArquivo = Environment.getExternalStorageDirectory().toString() + "/" + System.currentTimeMillis() + ".png";
                File arquivo = new File(caminhoArquivo);

                Uri localImage = Uri.fromFile(arquivo);
                goToCamera.putExtra(MediaStore.EXTRA_OUTPUT, localImage);

                startActivityForResult(goToCamera, 123);
            }
        });

        if ((house.getId() != null) && (!house.getFoto().equals(""))) {
            Bitmap imagem = BitmapFactory.decodeFile(house.getFoto());
            Bitmap imageScaled = Bitmap.createScaledBitmap(imagem, 125, 125, true);
            foto.setImageBitmap(imageScaled);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {
                carregaImagem(caminhoArquivo);
            } else {
                caminhoArquivo = house.getFoto();
            }

        }
    }

    private void carregaImagem(String caminhoArquivo) {
        house.setFoto(caminhoArquivo);
        Bitmap imagem = BitmapFactory.decodeFile(caminhoArquivo);
        Bitmap imageScaled = Bitmap.createScaledBitmap(imagem, 125, 125, false);

        foto.setImageBitmap(imageScaled);
    }

}
