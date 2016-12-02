package androidtermproject.christmastermproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Hattapong on 12/2/2016.
 */

public class ProjectPreferences {
    private static final String HIGH_SCORE = "HIGH_SCORE";
    private static final String CURRENT_SCORE = "CURRENT_SCORE";

    public static int getHighScore(Context ctx) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        return pref.getInt(HIGH_SCORE, 0);
    }

    public static void setHighScore(Context ctx, int highscore) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        pref.edit()
                .putInt(HIGH_SCORE, highscore)
                .apply();
    }

    public static int getCurrentScore(Context ctx) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        return pref.getInt(CURRENT_SCORE, 0);
    }

    public static void setCurrentScore(Context ctx, int currentScore) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        pref.edit()
                .putInt(CURRENT_SCORE, currentScore)
                .apply();
    }
}
