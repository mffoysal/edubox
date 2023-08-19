package com.edubox.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class shortCut extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private View button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_cut);


        if (getIntent().getAction() != null && getIntent().getAction().equals(Intent.ACTION_MAIN)) {
            String shortcutId = getIntent().getStringExtra("android.intent.extra.shortcut.ID");
            if (shortcutId != null) {
                // Perform actions based on the shortcut ID
                if (shortcutId.equals("shortcut1")) {
                    // Handle shortcut1 action
                } else if (shortcutId.equals("shortcut2")) {
                    // Handle shortcut2 action
                }
            }
        }


    }


    private void createAppShortcut() {
        Intent shortcutIntent = new Intent(this, activity_qr_scanner.class);
        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "edu");
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(this, R.mipmap.ic_launcher));
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        intent.putExtra("isLoggedIn", false);
        sendBroadcast(intent);
    }


    private void createShortcut() {
        Intent shortcutIntent = new Intent(this, activity_qr_scanner.class);
        shortcutIntent.setAction(Intent.ACTION_MAIN);
        shortcutIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Edubox");
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(this, R.mipmap.ic_launcher));
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        intent.putExtra("isLoggedIn", false);
        sendBroadcast(intent);
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}