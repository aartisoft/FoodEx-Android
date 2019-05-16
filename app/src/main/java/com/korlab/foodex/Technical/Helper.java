package com.korlab.foodex.Technical;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.util.Consumer;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.gson.Gson;
import com.korlab.foodex.Data.User;
import com.korlab.foodex.MainMenu;
import com.korlab.foodex.R;
import com.korlab.foodex.UI.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    private static Gson gson = new Gson();


    public static int getPrimaryColorFromTheme(Context context) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimary});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }

    public static void setTransparentStatusBar(Window w) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static void setMaxBrightness(Window w) {
        WindowManager.LayoutParams layout = w.getAttributes();
        layout.screenBrightness = 1F;
        w.setAttributes(layout);
    }

    public static void checkInternet(Activity activity, boolean isCheck) {
        boolean outcome = false;
        if (activity != null) {
            ConnectivityManager cm = (ConnectivityManager) activity
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] networkInfos = Objects.requireNonNull(cm).getAllNetworkInfo();
            for (NetworkInfo tempNetworkInfo : networkInfos) {
                if (tempNetworkInfo.isConnected()) {
                    outcome = true;
                    break;
                }
            }
        }
        if (!outcome && !isCheck) {
//            Intent intent = new Intent(activity, NoInternet.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            activity.startActivity(intent);
//            activity.finish();
        } else if (outcome && isCheck) {
//            Intent intent = new Intent(activity, Login.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            activity.startActivity(intent);
//            activity.finish();
        }
    }

    public static String saveBitmap(Bitmap bitmapImage, String dir, String filename, int quality, Context context) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir(dir, Context.MODE_PRIVATE);
        File mypath = new File(directory, filename);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, quality, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(fos).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public static Bitmap resizeBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public static Bitmap loadBitmap(String dir, String filename, Context context) {
        try {
            ContextWrapper cw = new ContextWrapper(context);
            File directory = cw.getDir(dir, Context.MODE_PRIVATE);
            File path = new File(directory, filename);
            return BitmapFactory.decodeStream(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Uri bitmapToUri(Bitmap inImage, Context context) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static byte[] bitmapToBytes(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static void setStatusBarColor(Window window, int color) {
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        int color = getPrimaryColor(context);
//        int colorDark = Color.parseColor("#030303");
//        int newColor = mixTwoColors(color, colorDark, 0.87f);
        log("Color: " + color);
        window.setStatusBarColor(color);
    }

    public static int mixTwoColors(int color1, int color2, float amount) {
        final byte ALPHA_CHANNEL = 24;
        final byte RED_CHANNEL = 16;
        final byte GREEN_CHANNEL = 8;
        final byte BLUE_CHANNEL = 0;
        final float inverseAmount = 1.0f - amount;
        int a = ((int) (((float) (color1 >> ALPHA_CHANNEL & 0xff) * amount) +
                ((float) (color2 >> ALPHA_CHANNEL & 0xff) * inverseAmount))) & 0xff;
        int r = ((int) (((float) (color1 >> RED_CHANNEL & 0xff) * amount) +
                ((float) (color2 >> RED_CHANNEL & 0xff) * inverseAmount))) & 0xff;
        int g = ((int) (((float) (color1 >> GREEN_CHANNEL & 0xff) * amount) +
                ((float) (color2 >> GREEN_CHANNEL & 0xff) * inverseAmount))) & 0xff;
        int b = ((int) (((float) (color1 & 0xff) * amount) +
                ((float) (color2 & 0xff) * inverseAmount))) & 0xff;
        return a << ALPHA_CHANNEL | r << RED_CHANNEL | g << GREEN_CHANNEL | b << BLUE_CHANNEL;
    }

    private static Map<String, Activity> mapInstance = new HashMap<>();

    public static void setInstance(Activity activity) {
        String activityName = activity.getClass().getSimpleName();
        log("Set instance for " + activityName);
        mapInstance.put(activityName, activity);
    }

    public static Activity getInstance(String activityName) {
        Activity activity = mapInstance.get(activityName);
        if (activity != null) {
            log("Return instance " + activityName);
            return activity;
        } else {
            log("No activity for the " + activityName);
            return null;
        }
    }

    public static void log(String message) {
        Log.d("FoodexDebug", message);
    }

    public static void logObjectToJson(Object obj) {
        log("Object \n\tName: " + obj.getClass().getSimpleName() + "\n\tFields: " + gson.toJson(obj));
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static User fromJson(String json, Object object) {
        return gson.fromJson(json, (Type) object);
    }

    public static void showKeyboard(Activity activity, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        view.requestFocus();
    }

    public static void setStatusBarIconWhite(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public static void hideKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showDialog(Activity activity, View dialogId, Consumer onPositive, Consumer onNegative) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogId);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.color.transparent);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        MaterialButton ok = dialog.findViewById(R.id.ok);
        MaterialButton cancel = dialog.findViewById(R.id.cancel);

        ok.setOnClickListener((v) -> {
            log("ok");
            if (onPositive != null) onPositive.accept(v);
            dialog.dismiss();
        });
        cancel.setOnClickListener(v -> {
            log("cancel");
            if (onNegative != null) onNegative.accept(v);
            dialog.dismiss();
        });

        dialog.getWindow().setAttributes(lp);
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.show();
    }

    public static void addRedAsterisk(EditText editText){
        String text = editText.getHint().toString();
        String asterisk = " *";
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(text);
        int start = builder.length();
        builder.append(asterisk);
        int end = builder.length();
        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(builder);
    }

    public static void enableButton(Activity activity, MaterialButton button) {
        button.setEnabled(true);
        button.setButtonColor(activity.getResources().getColor(R.color.colorPrimary));
        button.setBorderColor(activity.getResources().getColor(R.color.colorPrimary));

    }

    public static void disableButton(Activity activity, MaterialButton button) {
        button.setEnabled(false);
        button.setButtonColor(activity.getResources().getColor(R.color.colorPrimaryDisabled));
        button.setBorderColor(activity.getResources().getColor(R.color.colorPrimaryDisabled));
    }

    public static boolean isEmailValid(String email) {
        String regExp =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches())
            return true;
        else
            return false;
    }

    public static int getMaxNumbersInMonth(int year, int month) {
        Calendar calendar = new GregorianCalendar(year, month, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    public enum Translate { months, weekDays, dishTypes }
    enum DishTypes { salad, soup, hotter, garnish, drink }
    enum Months { january, february, march, april, may, june, july, august, september, october, november, december }
    enum WeekDays { monday, tuesday, wednesday, thursday, friday, saturday, sunday }

    public static List<String> getTranslate(Translate translate, Activity a) {
        List<String> res = new ArrayList<>();
        switch (translate) {
            case dishTypes:
                for (DishTypes dir : DishTypes.values())
                    res.add(a.getResources().getString(a.getResources().getIdentifier(dir.name(), "string", a.getPackageName())));
                break;
            case months:
                for (Months dir : Months.values())
                    res.add(a.getResources().getString(a.getResources().getIdentifier(dir.name(), "string", a.getPackageName())));
                break;
            case weekDays:
                for (WeekDays dir : WeekDays.values())
                    res.add(a.getResources().getString(a.getResources().getIdentifier(dir.name(), "string", a.getPackageName())));
                break;
        }
        return res;
    }
}
