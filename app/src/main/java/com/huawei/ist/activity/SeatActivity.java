package com.huawei.ist.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.ist.R;
import com.huawei.ist.utility.Constant;

import java.util.ArrayList;
import java.util.List;

public class SeatActivity extends AppCompatActivity implements View.OnClickListener {
    ViewGroup layout;

    String seats = "a_UUUUUUAAAAAUAUA_/"
            + "_________________/"
            + "b_UU__AAAAAAAAA__UU/"
            + "c_UU__UUUAAAAAA__AA/"
            + "d_AA__AAAAAAAAA__AA/"
            + "e_AA__AAAUUUUAA__AA/"
            + "f_UU__UUUA_UUUU__AA/"
            + "g_AA__AAAA_AAAA__UU/"
            + "h_AA__AAUU_UUUU__UU/"
            + "i_AA__UUAA_UUAA__AA/"
            + "_________________/"
            + "j_UU_AAAAAAAUUUU_AA/"
            + "k_UU_AAAAAAAAAAA_UU/"
            + "l_AA_UUAAAAAUUUU_AA/"
            + "o_AA_AAAAAAUUUUU_AA/"
            + "_________________/";

    List<TextView> seatViewList = new ArrayList<>();
    int seatSize = 85;
    int seatGaping = 10;

    int STATUS_AVAILABLE = 1;
    int STATUS_BOOKED = 2;
    int STATUS_RESERVED = 3;
    String selectedIds = "";
    private LinearLayout layoutSeat;
    private TextView txtHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_seat);
        txtHeader = findViewById(R.id.txtHeader);
        txtHeader.setTypeface(Constant.getTypeface(this,1));
        txtHeader.setText("SEAT SELECTION");
        layout = findViewById(R.id.layoutSeat);

        seats = "/" + seats;

        layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping);
        layout.addView(layoutSeat);

        LinearLayout layout = null;

        int count = 0;

        for (int index = 0; index < seats.length(); index++) {
//            if (seats.charAt(index) == 'a') {
//                getSeatsAlphabets("A");
//            }else if (seats.charAt(index) == 'b') {
//                getSeatsAlphabets("B");
//            }else if (seats.charAt(index) == 'c') {
//                getSeatsAlphabets("C");
//            }else if (seats.charAt(index) == 'd') {
//                getSeatsAlphabets("D");
//            }else if (seats.charAt(index) == 'e') {
//                getSeatsAlphabets("E");
//            }else if (seats.charAt(index) == 'f') {
//                getSeatsAlphabets("F");
//            }else if (seats.charAt(index) == 'g') {
//                getSeatsAlphabets("G");
//            }else if (seats.charAt(index) == 'h') {
//                getSeatsAlphabets("H");
//            }else if (seats.charAt(index) == 'i') {
//                getSeatsAlphabets("I");
//            }else if (seats.charAt(index) == 'j') {
//                getSeatsAlphabets("J");
//            }else if (seats.charAt(index) == 'K') {
//                getSeatsAlphabets("K");
//            }else if (seats.charAt(index) == 'l') {
//                getSeatsAlphabets("L");
//            }else if (seats.charAt(index) == 'o') {
//                getSeatsAlphabets("O");
//            }else

            if (seats.charAt(index) == '/') {
                layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layoutSeat.addView(layout);
            } else if (seats.charAt(index) == 'U') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_booked);
                view.setTextColor(Color.WHITE);
                view.setTag(STATUS_BOOKED);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == 'A') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_book);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                view.setTextColor(Color.BLACK);
                view.setTag(STATUS_AVAILABLE);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == '_') {
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(Color.TRANSPARENT);
                view.setText("");
                layout.addView(view);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if ((int) view.getTag() == STATUS_AVAILABLE) {
            if (selectedIds.contains(view.getId() + ",")) {
                selectedIds = selectedIds.replace(+view.getId() + ",", "");
                view.setBackgroundResource(R.drawable.ic_seats_book);
            } else {
                selectedIds = selectedIds + view.getId() + ",";
                view.setBackgroundResource(R.drawable.ic_seats_selected);
            }
        } else if ((int) view.getTag() == STATUS_BOOKED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Booked", Toast.LENGTH_SHORT).show();
        } else if ((int) view.getTag() == STATUS_RESERVED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Reserved", Toast.LENGTH_SHORT).show();
        }
    }

    private void getSeatsAlphabets(String alphabet){
        LinearLayout layout = null;
        layout = new LinearLayout(this);
        TextView view = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
        layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
        view.setLayoutParams(layoutParams);
        view.setBackgroundColor(Color.TRANSPARENT);
        view.setText(alphabet);
        layout.addView(view);
        layoutSeat.addView(layout);
    }
}