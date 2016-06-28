package com.mbopartners.mbomobile.ui.activity.passcodelock;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mbopartners.mbomobile.ui.R;
import com.mbopartners.mbomobile.ui.activity.ArtisanedBaseActivity;

public abstract class AbstractPasscodeKeyboardActivity extends ArtisanedBaseActivity {

    private static final int WHAT_ID = 12346;

    /**
     * Milliseconds
     */
    private long DELAY_TO_USER_ABLE_SEE_4_DOT = 200L;

    protected Button button0;
    protected Button button1;
    protected Button button2;
    protected Button button3;
    protected Button button4;
    protected Button button5;
    protected Button button6;
    protected Button button7;
    protected Button button8;
    protected Button button9;
    protected ImageButton buttonErase;

    protected TextView pinCodeField1 = null;
    protected TextView pinCodeField2 = null;
    protected TextView pinCodeField3 = null;
    protected TextView pinCodeField4 = null;
    protected TextView topMessage = null;

    int currentEditPosition = 1;

    private Handler handler;

    Handler.Callback handlerCallback = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            unLockUi();
            onPinLockInserted();
            return false;
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(handlerCallback);
        setContentView(R.layout.app_passcode_keyboard);
        
        topMessage = (TextView) findViewById(R.id.top_message);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String message = extras.getString("message");
            if (message != null) {
                topMessage.setText(message);
            }
        }
        
        //Setup the pin fields row
        pinCodeField1 = (TextView) findViewById(R.id.pincode_1);
        pinCodeField2 = (TextView) findViewById(R.id.pincode_2);
        pinCodeField3 = (TextView) findViewById(R.id.pincode_3);
        pinCodeField4 = (TextView) findViewById(R.id.pincode_4);

        //setup the keyboard
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);

        button0.setOnClickListener(defaultButtonListener);
        button1.setOnClickListener(defaultButtonListener);
        button2.setOnClickListener(defaultButtonListener);
        button3.setOnClickListener(defaultButtonListener);
        button4.setOnClickListener(defaultButtonListener);
        button5.setOnClickListener(defaultButtonListener);
        button6.setOnClickListener(defaultButtonListener);
        button7.setOnClickListener(defaultButtonListener);
        button8.setOnClickListener(defaultButtonListener);
        button9.setOnClickListener(defaultButtonListener);

        buttonErase = ((ImageButton) findViewById(R.id.button_erase));
        buttonErase.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        switch (currentEditPosition) {
                            case 1: {
                                currentEditPosition = 1;
                                break;
                            }
                            case 2: {
                                pinCodeField1.setText("");
                                currentEditPosition = 1;
                                break;
                            }
                            case 3: {
                                pinCodeField2.setText("");
                                currentEditPosition = 2;
                                break;
                            }
                            case 4: {
                                pinCodeField3.setText("");
                                currentEditPosition = 3;
                                break;
                            }
                            default: {
                            }
                        }

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        initEnteringPasswordSpace();
    }

    private OnClickListener defaultButtonListener = new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            int currentValue = -1;
            int id = arg0.getId();
			if (id == R.id.button0) {
				currentValue = 0;
			} else if (id == R.id.button1) {
				currentValue = 1;
			} else if (id == R.id.button2) {
				currentValue = 2;
			} else if (id == R.id.button3) {
				currentValue = 3;
			} else if (id == R.id.button4) {
				currentValue = 4;
			} else if (id == R.id.button5) {
				currentValue = 5;
			} else if (id == R.id.button6) {
				currentValue = 6;
			} else if (id == R.id.button7) {
				currentValue = 7;
			} else if (id == R.id.button8) {
				currentValue = 8;
			} else if (id == R.id.button9) {
				currentValue = 9;
			} else {
			}
            
            //set the value and move the focus
            String currentValueString = String.valueOf(currentValue);
            switch (currentEditPosition) {
                case 1 :{
                    pinCodeField1.setText(currentValueString);
                    currentEditPosition = 2;
                    break;
                }
                case 2 :{
                    pinCodeField2.setText(currentValueString);
                    currentEditPosition = 3;
                    break;
                }
                case 3 :{
                    pinCodeField3.setText(currentValueString);
                    currentEditPosition = 4;
                    break;
                }
                case 4 :{
                    pinCodeField4.setText(currentValueString);
                    currentEditPosition = 100500;
                    break;
                }
                default : {
                }
            }

            if(pinCodeField4.getText().toString().length() > 0 &&
                    pinCodeField3.getText().toString().length() > 0 &&
                    pinCodeField2.getText().toString().length() > 0 &&
                    pinCodeField1.getText().toString().length() > 0
                    ) {

                doDelay_than_continue();
            }
        }
    };

    private void doDelay_than_continue() {
        lockUi();
        handler.sendEmptyMessageDelayed(WHAT_ID, DELAY_TO_USER_ABLE_SEE_4_DOT);
    }

    protected void showPasswordError(int resourceId) {
        topMessage.setText(resourceId);
        topMessage.setTextColor(getResources().getColor(R.color.mbo_error_message_text__color));
        topMessage.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12.0F);
    }

    protected void showPasswordMessage(int resourceId) {
        topMessage.setText(resourceId);
        topMessage.setTextColor(getResources().getColor(R.color.mbo_simple_edittext_font_color));
        topMessage.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18.0F);
    }
    
    protected abstract void onPinLockInserted();

    private InputFilter onlyNumber = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            
            if( source.length() > 1 )
                return "";

            if( source.length() == 0 ) //erase
                return null;

            try {
                int number = Integer.parseInt(source.toString());
                if( ( number >= 0 ) && ( number <= 9 ) )
                    return String.valueOf(number);
                else
                    return "";
            } catch (NumberFormatException e) {
                return "";
            }
        }
    };
    
    private OnTouchListener otl = new OnTouchListener() {
        @Override
        public boolean onTouch (View v, MotionEvent event) {
            if( v instanceof EditText ) {
                ((EditText)v).setText("");
            }
            return false;
        }
    };

    protected void lockUi() {
        setUiEnabled(false);
    }

    protected void unLockUi() {
        setUiEnabled(true);
    }

    protected void setUiEnabled(boolean value) {
        button0.setClickable(value);
        button1.setClickable(value);
        button2.setClickable(value);
        button3.setClickable(value);
        button4.setClickable(value);
        button5.setClickable(value);
        button6.setClickable(value);
        button7.setClickable(value);
        button8.setClickable(value);
        button9.setClickable(value);
        buttonErase.setClickable(value);
    }

    protected void initEnteringPasswordSpace() {
        pinCodeField1.setText("");
        pinCodeField2.setText("");
        pinCodeField3.setText("");
        pinCodeField4.setText("");

        currentEditPosition = 1;
    }

    protected String getPassLockWord() {
        return pinCodeField1.getText().toString() + pinCodeField2.getText().toString() +
                pinCodeField3.getText().toString() + pinCodeField4.getText();
    }
    
}