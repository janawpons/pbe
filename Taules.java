package com.example.appbe;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Taules {

    public static void crearTaula(Context context, String result, TableLayout taula, TextView respostaError,  TextView titolTaula, String dataType) {
        try {
            JSONArray jsonArray = new JSONArray(result);

            taula.removeAllViews();
            respostaError.setText("");
            titolTaula.setText("");

            if (jsonArray.length() > 0) {
                JSONObject primer = jsonArray.getJSONObject(0);
                TableRow headerRow = new TableRow(context);

                for (int i = 0; i < primer.names().length(); i++) {
                    String key = primer.names().getString(i);
                    if (key.equals("student_id")) continue;

                    TextView header = new TextView(context);
                    header.setText(key);
                    header.setPadding(20, 10, 20, 10);
                    header.setGravity(Gravity.CENTER);
                    header.setBackgroundColor(context.getResources().getColor(R.color.purple_700));
                    header.setTextColor(context.getResources().getColor(R.color.white));
                    header.setBackgroundResource(R.drawable.header_bg);
                    headerRow.addView(header);
                }

                taula.addView(headerRow);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject originalItem = jsonArray.getJSONObject(i);
                    JSONObject item = new JSONObject(originalItem.toString());
                    item.remove("student_id");
                    TableRow dataRow = new TableRow(context);

                    for (int j = 0; j < item.names().length(); j++) {
                        String key = item.names().getString(j);
                        TextView cell = new TextView(context);
                        String value = item.getString(key);
                        if (key.equalsIgnoreCase("date") && value.contains("T")) {
                            value = value.split("T")[0];  // Només la part abans de la T
                        }
                        cell.setText(value);
                        cell.setPadding(20, 10, 20, 10);
                        cell.setGravity(Gravity.CENTER);

                        if (i % 2 == 0) {
                            cell.setBackgroundResource(R.drawable.cell_bg_a);
                        } else {
                            cell.setBackgroundResource(R.drawable.cell_bg_b);
                        }

                        cell.setTextColor(context.getResources().getColor(R.color.black));
                        dataRow.addView(cell);
                    }

                    taula.addView(dataRow);
                }

                titolTaula.setText(dataType.toUpperCase());
            } else {
                respostaError.setText("Sense resultats.");
            }

        } catch (JSONException e) {
            respostaError.setText("Error al processar la resposta JSON.");
            e.printStackTrace();
        }
    }
}
