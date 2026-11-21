package com.martheus.bypasschargingqs;

import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DirectPowerTileService extends TileService {
    private final ExecutorService io = Executors.newSingleThreadExecutor();

    @Override public void onStartListening() { refresh(); }

    @Override public void onTileAdded() { refresh(); }

    @Override public void onClick() {
        io.execute(() -> {
            int current = DirectPower.read();
            if (current == DirectPower.UNSUPPORTED) { render(current); return; }
            render(DirectPower.set(current != DirectPower.ON));
        });
    }

    @Override public void onDestroy() { io.shutdown(); super.onDestroy(); }

    private void refresh() { io.execute(() -> render(DirectPower.read())); }

    private void render(int state) {
        new android.os.Handler(getMainLooper()).post(() -> {
            Tile t = getQsTile();
            if (t == null) return;
            switch (state) {
                case DirectPower.ON:
                    t.setState(Tile.STATE_ACTIVE); t.setSubtitle(getString(R.string.enabled)); break;
                case DirectPower.OFF:
                    t.setState(Tile.STATE_INACTIVE); t.setSubtitle(getString(R.string.disabled)); break;
                default:
                    t.setState(Tile.STATE_UNAVAILABLE); t.setSubtitle(getString(R.string.unsupported));
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                t.setStateDescription(t.getSubtitle());
            }
            t.updateTile();
        });
    }
}
