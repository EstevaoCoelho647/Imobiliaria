package com.project.imobiliaria.controller.activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.project.imobiliaria.R;
import com.project.imobiliaria.controller.adapters.ListHouseAdapter;
import com.project.imobiliaria.model.entities.House;
import com.project.imobiliaria.model.persistence.HouseRepository;

import java.util.List;

public class ListHouseActivity extends AppCompatActivity {
    ListView listHouse;
    House itemSelected;
    FloatingActionButton fab;
    Toolbar toolbar;
    int nQuartos;
    int nBanheiros;
    Double preco;
    boolean ehVenda;
    boolean ehAluguel;
    Button btnBusca;
    boolean ehPesquisa;

    CheckBox checkBtnAluguel;
    CheckBox checkBtnVenda;
    EditText editTextnBanheiros;
    EditText editTextnQuartos;
    EditText editTextPreco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_house);

        binToolbar();
        binLisHouse();
        binFloatButton();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void binToolbar() {
        toolbar = (Toolbar) findViewById(R.id.viewToobar);
        toolbar.inflateMenu(R.menu.menu_list_house);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.mapa) {
                    Intent goToMapa = new Intent(ListHouseActivity.this, ViewHouses.class);
                    startActivity(goToMapa);
                }
                if (item.getItemId() == R.id.busca) {

                    LayoutInflater view = getLayoutInflater();
                    View dialoglayout = view.inflate(R.layout.busca_layout, null);
                    final Dialog dialog = new Dialog(ListHouseActivity.this);
                    dialog.setContentView(dialoglayout);
                    dialog.setTitle("Busca");
                    binItens(dialog);
                    btnBusca.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            nBanheiros = Integer.parseInt(editTextnBanheiros.getText().toString());
                            nQuartos = Integer.parseInt(editTextnQuartos.getText().toString());
                            preco = Double.parseDouble(editTextPreco.getText().toString());
                            ehAluguel = checkBtnAluguel.isChecked();
                            ehVenda = checkBtnAluguel.isChecked();
                            ehPesquisa = true;
                            dialog.cancel();
                            onResume();
                        }
                    });
                    dialog.show();
                }
                return false;
            }
        });
    }

    private void binItens(Dialog dialog) {
        checkBtnAluguel = (CheckBox) dialog.findViewById(R.id.checkButtonAluguel);
        checkBtnVenda = (CheckBox) dialog.findViewById(R.id.checkButtonvenda);
        editTextnBanheiros = (EditText) dialog.findViewById(R.id.editTextNBanheiros);
        editTextnQuartos = (EditText) dialog.findViewById(R.id.editTextNQuartos);
        editTextPreco = (EditText) dialog.findViewById(R.id.editTextPreco);
        btnBusca = (Button) dialog.findViewById(R.id.btn_busca);
    }

    private void binLisHouse() {
        listHouse = (ListView) findViewById(R.id.list_houses);
        listHouse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
            }
        });

        listHouse.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long l) {
                itemSelected = (House) adapter.getItemAtPosition(posicao);
                return false;
            }
        });

    }

    private void binFloatButton() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToFormHouse = new Intent(ListHouseActivity.this, FormHouseActivity.class);
                startActivity(goToFormHouse);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (ehPesquisa) {
            carregaListaPesquisa();

            ehPesquisa = false;
        } else {
            carregaLista();
        }

    }

    private void carregaListaPesquisa() {


        List<House> houses = HouseRepository.findByFilterItens(nBanheiros, nQuartos, ehVenda == true ? 0 : 1, ehAluguel == true ? 0 : 1, preco);
        if (houses.isEmpty()) {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setMessage("Nenhum resultado encontrado :(");
            dialogBuilder.setTitle("Aviso");
            dialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogBuilder.setCancelable(true);
                }
            });
            dialogBuilder.show();
        } else {
            setAdapterListHouses(houses);
        }
    }

    private void carregaLista() {
        List<House> houses = HouseRepository.getAll();

        setAdapterListHouses(houses);
    }

    private void setAdapterListHouses(final List<House> houses) {
        listHouse.setAdapter(new ListHouseAdapter(houses, this) {
            @Override
            public void deletar(House house) {
                itemSelected = house;
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ListHouseActivity.this);
                dialogBuilder.setMessage("Deseja realmente deletar este item?");
                dialogBuilder.setTitle("Deletar");
                dialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HouseRepository.delete(itemSelected.getId());
                        Toast.makeText(ListHouseActivity.this, "Residencia excluida com sucesso!", Toast.LENGTH_SHORT).show();
                        carregaLista();
                    }
                });
                dialogBuilder.show();
            }

            public void editar(House house) {
                itemSelected = house;
                Intent goToEditForm = new Intent(ListHouseActivity.this, FormHouseActivity.class);
                goToEditForm.putExtra("SelectedHouse", itemSelected);
                startActivity(goToEditForm);
            }

            public void chamar(House house) {
                itemSelected = house;
                Intent goToCall = new Intent(Intent.ACTION_CALL);
                Uri telContato = Uri.parse("tel:" + itemSelected.getTelContato());
                goToCall.setData(telContato);
                startActivity(goToCall);
            }

            @Override
            public void verMap(House house) {
                itemSelected = house;
                Intent goToMapa = new Intent(ListHouseActivity.this, ViewHouses.class);
                goToMapa.putExtra("SelectedHouse", itemSelected);
                startActivity(goToMapa);
            }
        });
    }


}
