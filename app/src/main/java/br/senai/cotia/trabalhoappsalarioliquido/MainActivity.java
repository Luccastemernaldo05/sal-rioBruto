package br.senai.cotia.trabalhoappsalarioliquido;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private TextView descPlanoSaude, descInss, descVt, descVa, descVr, descontoIrrf, sl;
    private EditText salarioBruto, numeroDependentes;
    private Spinner planoSaude;
    private RadioButton simVT, naoVT, simVA, naoVA, simVR, naoVR;
    private Button btCalcular, btLimpar;
    private RadioGroup radioGroup1, radioGroup2, radioGroup3;

    public double planoS, inss, valeTransporte, valeAlimentacao, valeRefeicao, baseIrrf, irrf, salarioLiquido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        salarioBruto = findViewById(R.id.edit_sb);
        numeroDependentes = findViewById(R.id.edit_numeroDependente);
        planoSaude = findViewById(R.id.planoSaude);
        simVT = findViewById(R.id.simVT);
        naoVT = findViewById(R.id.naoVT);
        simVA = findViewById(R.id.simVA);
        naoVA = findViewById(R.id.naoVA);
        simVR = findViewById(R.id.simVR);
        naoVR = findViewById(R.id.naoVR);
        btCalcular = findViewById(R.id.bt_calcular);
        btLimpar = findViewById(R.id.bt_limpar);
        descPlanoSaude = findViewById(R.id.desconto_plano_saude);
        descInss = findViewById(R.id.desconto_inss);
        descVt = findViewById(R.id.desconto_VT);
        descVa = findViewById(R.id.desconto_VA);
        descVr = findViewById(R.id.desconto_VR);
        descontoIrrf = findViewById(R.id.desconto_irrf);
        sl = findViewById(R.id.salario_liquido);
        radioGroup1 = findViewById(R.id.radioGroup1);
        radioGroup2 = findViewById(R.id.radioGroup2);
        radioGroup3 = findViewById(R.id.radioGroup3);

        Spinner dropdown = (Spinner) findViewById(R.id.planoSaude);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipos_plano_saude, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        descPlanoSaude.setVisibility(View.GONE);
        descInss.setVisibility(View.GONE);
        descVt.setVisibility(View.GONE);
        descVr.setVisibility(View.GONE);
        descVa.setVisibility(View.GONE);
        descontoIrrf.setVisibility(View.GONE);
        sl.setVisibility(View.GONE);

        btCalcular.setOnClickListener(v -> {


            if (salarioBruto.getText().toString().isEmpty()) {
                salarioBruto.setError(getString(R.string.valida_sb));
                Snackbar.make(btCalcular, R.string.valida_sb, Snackbar.LENGTH_SHORT).show();
            }else if (numeroDependentes.getText().toString().isEmpty()) {
                numeroDependentes.setError(getString(R.string.valida_numDependentes));
                Snackbar.make(btCalcular, R.string.valida_numDependentes, Snackbar.LENGTH_SHORT).show();
            }else if (radioGroup1.getCheckedRadioButtonId() == -1) {
                Snackbar.make(btCalcular, R.string.valida_vt, Snackbar.LENGTH_SHORT).show();
            }else if (radioGroup2.getCheckedRadioButtonId() == -1) {
                Snackbar.make(btCalcular, R.string.valida_va, Snackbar.LENGTH_SHORT).show();
            }else if (radioGroup3.getCheckedRadioButtonId() == -1) {
                Snackbar.make(btCalcular, R.string.valida_vr, Snackbar.LENGTH_SHORT).show();
            }else {
                descPlanoSaude.setVisibility(View.VISIBLE);
                descInss.setVisibility(View.VISIBLE);
                descVt.setVisibility(View.VISIBLE);
                descVr.setVisibility(View.VISIBLE);
                descVa.setVisibility(View.VISIBLE);
                descontoIrrf.setVisibility(View.VISIBLE);
                sl.setVisibility(View.VISIBLE);

                double salb = Double.parseDouble(salarioBruto.getText().toString());
                double numDependentes = Double.parseDouble(numeroDependentes.getText().toString());

                // Cálculo do Plano de Saúde
                switch (planoSaude.getSelectedItemPosition()) {

                    case 0:
                        if (salb <= 3000.00) {
                            planoS = 60.00;
                            break;

                        } else
                            planoS = 80.00;
                        break;

                    case 1:
                        if (salb <= 3000.00) {
                            planoS = 80.00;
                            break;

                        } else
                            planoS = 110.00;
                        break;

                    case 2:
                        if (salb <= 3000.00) {
                            planoS = 95.00;
                            break;

                        } else
                            planoS = 135.00;
                        break;

                    case 3:
                        if (salb <= 3000.00) {
                            planoS = 135.00;
                            break;

                        } else
                            planoS = 180.00;
                        break;

                    default:
                        return;

                }

                // Cálculo do INSS
                if (salb <= 1212.00) {
                    inss = salb * (7.5 / 100) - 0.00;

                } else if (salb <= 2427.35) {
                    inss = salb * (9.0 / 100) - 18.18;

                } else if (salb <= 3641.03) {
                    inss = salb * (12.0 / 100) - 91.00;

                } else {
                    inss = salb * (14.0 / 100) - 163.82;
                }

                // Cálculo do Vale Transporte
                switch (radioGroup1.getCheckedRadioButtonId()) {
                    case R.id.simVT:
                        valeTransporte = salb * (6.0 / 100);
                        descVt.setText(String.format("Seu desconto do Vale Transporte é de: R$%5.2f" ,valeTransporte));
                        break;

                    case R.id.naoVT:
                        valeTransporte = 0.00;
                        descVt.setText("Não possui Vale Transporte");
                        break;
                }

                // Cálculo do Vale Alimentação
                switch (radioGroup2.getCheckedRadioButtonId()) {
                    case R.id.simVA:
                        if (salb <= 3000.00) {
                            valeAlimentacao = 15.00;

                        } else if (salb <= 5000.00) {
                            valeAlimentacao = 25.00;

                        } else
                            valeAlimentacao = 35.00;
                        descVa.setText(String.format("Seu desconto do Vale Alimentação é de: R$%5.2f" , valeAlimentacao));
                        break;


                    case R.id.naoVA:
                        valeAlimentacao = 0.00;
                        descVa.setText("Não possui Vale Alimentação");
                        break;

                }

                // Cálculo do Vale Refeição
                switch (radioGroup3.getCheckedRadioButtonId()) {
                    case R.id.simVR:
                        if (salb <= 3000.00) {
                            valeRefeicao = salb + (2.60 * 22 - salb);

                        } else if (salb <= 5000.00) {
                            valeRefeicao = salb + (3.65 * 22 - salb);

                        } else
                            valeRefeicao = salb + (6.50 * 22 - salb);
                        descVr.setText(String.format("Seu desconto do Vale Refeição é de: R$%5.2f" , valeRefeicao));
                        break;

                    case R.id.naoVR:
                        valeRefeicao = 0.00;
                        descVr.setText("Não possui Vale Refeição");
                        break;

                }

                // Calculo do IRRF
                baseIrrf = salb - inss - (189.59 * numDependentes);

                if (baseIrrf <= 1903.98) {
                    irrf = 0.00;

                } else if (baseIrrf <= 2826.65) {
                    irrf = baseIrrf * (7.5 / 100) - 142.80;

                } else if (baseIrrf <= 3751.05) {
                    irrf = baseIrrf * (15.0 / 100) - 354.80;

                } else if (baseIrrf <= 4664.68) {
                    irrf = baseIrrf * (22.5 / 100) - 623.13;

                } else {
                    irrf = baseIrrf * (27.5 / 100) - 869.36;
                }

                //Cálculo do Salário Líquido
                salarioLiquido = salb - inss - valeTransporte - valeRefeicao - valeAlimentacao - irrf - planoS;

                //TextViews recebendo os valores
                descPlanoSaude.setText(String.format("Seu desconto do Plano de Saúde é de: R$%5.2f" , planoS));
                descInss.setText(String.format("Seu desconto do Inns é de: R$%5.2f" , inss));
                descontoIrrf.setText(String.format("Seu desconto do Irrf é de: R$%5.2f" , irrf));
                sl.setText(String.format("Seu Salário Líquido é de: R$%5.2f" , salarioLiquido));
            }
        });


        btLimpar.setOnClickListener(v -> {
            descPlanoSaude.setVisibility(View.GONE);
            descInss.setVisibility(View.GONE);
            descVt.setVisibility(View.GONE);
            descVr.setVisibility(View.GONE);
            descVa.setVisibility(View.GONE);
            descontoIrrf.setVisibility(View.GONE);
            sl.setVisibility(View.GONE);
            salarioBruto.setText("");
            numeroDependentes.setText("");
            simVT.setChecked(false);
            naoVT.setChecked(false);
            simVA.setChecked(false);
            naoVA.setChecked(false);
            simVR.setChecked(false);
            naoVR.setChecked(false);
            descPlanoSaude.setText("");
            descInss.setText("");
            descVt.setText("");
            descVa.setText("");
            descVr.setText("");
            descontoIrrf.setText("");
            sl.setText("");

        });

    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        boolean checked1 = ((RadioButton) view).isChecked();
        boolean checked2 = ((RadioButton) view).isChecked();


        switch (view.getId()) {
            case R.id.simVT:
                if (checked)
                    break;

            case R.id.naoVT:
                if (checked)

                    break;
        }
        switch (view.getId()) {
            case R.id.simVA:
                if (checked1)
                    break;

            case R.id.naoVA:
                if (checked1)
                    break;
        }
        switch (view.getId()) {
            case R.id.simVR:
                if (checked2)
                    break;

            case R.id.naoVR:
                if (checked2)
                    break;
        }

    }

}