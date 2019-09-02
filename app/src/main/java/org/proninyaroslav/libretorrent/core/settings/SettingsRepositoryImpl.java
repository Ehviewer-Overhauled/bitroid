/*
 * Copyright (C) 2019 Yaroslav Pronin <proninyaroslav@mail.ru>
 *
 * This file is part of LibreTorrent.
 *
 * LibreTorrent is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LibreTorrent is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LibreTorrent.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.proninyaroslav.libretorrent.core.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import org.proninyaroslav.libretorrent.R;
import org.proninyaroslav.libretorrent.core.sorting.TorrentSorting;
import org.proninyaroslav.libretorrent.core.system.SystemFacadeHelper;
import org.proninyaroslav.libretorrent.core.system.filesystem.FileSystemFacade;
import org.proninyaroslav.libretorrent.core.utils.Utils;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposables;

public class SettingsRepositoryImpl implements SettingsRepository
{
    private static class Default
    {
        /* Appearance settings */
        public static final String notifySound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION).toString();
        public static final boolean torrentFinishNotify = true;
        public static final boolean playSoundNotify = true;
        public static final boolean ledIndicatorNotify = true;
        public static final boolean vibrationNotify = true;
        public static int theme(@NonNull Context context) { return Integer.parseInt(context.getString(R.string.pref_theme_light_value)); }
        public static int ledIndicatorColorNotify(@NonNull Context context)
        {
            return ContextCompat.getColor(context, R.color.primary);
        }
        /* Behavior settings */
        public static final boolean autostart = false;
        public static final boolean keepAlive = true;
        public static final boolean shutdownDownloadsComplete = false;
        public static final boolean cpuDoNotSleep = false;
        public static final boolean onlyCharging = false;
        public static final boolean batteryControl = false;
        public static final boolean customBatteryControl = false;
        public static final int customBatteryControlValue = Utils.getDefaultBatteryLowLevel();
        public static final boolean unmeteredConnectionsOnly = false;
        public static final boolean enableRoaming = true;
        /* Network settings */
        public static final int portRangeFirst = SessionSettings.DEFAULT_PORT_RANGE_FIRST;
        public static final int portRangeSecond = SessionSettings.DEFAULT_PORT_RANGE_SECOND;
        public static final boolean enableDht = true;
        public static final boolean enableLsd = true;
        public static final boolean enableUtp = true;
        public static final boolean enableUpnp = true;
        public static final boolean enableNatPmp = true;
        public static final boolean useRandomPort = true;
        public static final boolean encryptInConnections = true;
        public static final boolean encryptOutConnections = true;
        public static final boolean enableIpFiltering = false;
        public static final String ipFilteringFile = null;
        public static int encryptMode(@NonNull Context context)
        {
            return Integer.parseInt(context.getString(R.string.pref_enc_mode_prefer_value));
        }
        public static final boolean showNatErrors = false;
        /* Storage settings */
        public static final String saveTorrentsIn(@NonNull Context context)
        {
            return "file://" + SystemFacadeHelper.getFileSystemFacade(context).getDefaultDownloadPath();
        }
        public static final boolean moveAfterDownload = false;
        public static final String moveAfterDownloadIn(@NonNull Context context)
        {
            return "file://" + SystemFacadeHelper.getFileSystemFacade(context).getDefaultDownloadPath();
        }
        public static final boolean saveTorrentFiles = false;
        public static final String saveTorrentFilesIn(@NonNull Context context)
        {
            return "file://" + SystemFacadeHelper.getFileSystemFacade(context).getDefaultDownloadPath();
        }
        public static final boolean watchDir = false;
        public static final String dirToWatch(@NonNull Context context)
        {
            return "file://" + SystemFacadeHelper.getFileSystemFacade(context).getDefaultDownloadPath();
        }
        /* Limitations settings */
        public static final int maxDownloadSpeedLimit = SessionSettings.DEFAULT_DOWNLOAD_RATE_LIMIT;
        public static final int maxUploadSpeedLimit = SessionSettings.DEFAULT_UPLOAD_RATE_LIMIT;
        public static final int maxConnections = SessionSettings.DEFAULT_CONNECTIONS_LIMIT;
        public static final int maxConnectionsPerTorrent = SessionSettings.DEFAULT_CONNECTIONS_LIMIT_PER_TORRENT;
        public static final int maxUploadsPerTorrent = SessionSettings.DEFAULT_UPLOADS_LIMIT_PER_TORRENT;
        public static final int maxActiveUploads = SessionSettings.DEFAULT_ACTIVE_SEEDS;
        public static final int maxActiveDownloads = SessionSettings.DEFAULT_ACTIVE_DOWNLOADS;
        public static final int maxActiveTorrents = SessionSettings.DEFAULT_ACTIVE_LIMIT;
        public static final boolean autoManage = false;
        /* Proxy settings */
        public static final int proxyType = ProxySettingsPack.ProxyType.NONE.value();
        public static final String proxyAddress = "";
        public static final int proxyPort = ProxySettingsPack.DEFAULT_PROXY_PORT;
        public static final boolean proxyPeersToo = true;
        public static final boolean proxyRequiresAuth = false;
        public static final String proxyLogin = "";
        public static final String proxyPassword = "";
        public static final boolean proxyChanged = false;
        /* Sorting settings */
        public static final String sortTorrentBy = TorrentSorting.SortingColumns.name.name();
        public static final String sortTorrentDirection = TorrentSorting.Direction.ASC.name();
        /* Scheduling settings */
        public static final boolean enableSchedulingStart = false;
        public static final boolean enableSchedulingShutdown = false;
        public static final int schedulingStartTime = 540; /* 9:00 am in minutes*/
        public static final int schedulingShutdownTime = 1260; /* 9:00 pm in minutes */
        public static final boolean schedulingRunOnlyOnce = false;
        public static final boolean schedulingSwitchWiFi = false;
        /* Feed settings */
        public static final long feedItemKeepTime = 4 * 86400000L; /* 4 days */
        public static final boolean autoRefreshFeeds = false;
        public static final long refreshFeedsInterval = 2 * 3600000L; /* 2 hours */
        public static final boolean autoRefreshFeedsUnmeteredConnectionsOnly = false;
        public static final boolean autoRefreshFeedsEnableRoaming = true;
        public static final boolean feedStartTorrents = true;
        public static final boolean feedRemoveDuplicates = true;
        /* Streaming settings */
        public static final boolean enableStreaming = true;
        public static final String streamingHostname = "127.0.0.1";
        public static final int streamingPort = 8800;
    }

    private Context appContext;
    private SharedPreferences pref;
    private FileSystemFacade fs;

    public SettingsRepositoryImpl(@NonNull Context appContext)
    {
        this.appContext = appContext;
        fs = SystemFacadeHelper.getFileSystemFacade(appContext);
        pref = PreferenceManager.getDefaultSharedPreferences(appContext);
    }

    /*
     * Returns Flowable with key
     */

    @Override
    public Flowable<String> observeSettingsChanged()
    {
        return Flowable.create((emitter) -> {
            SharedPreferences.OnSharedPreferenceChangeListener listener = (sharedPreferences, key) -> {
                if (!emitter.isCancelled())
                    emitter.onNext(key);
            };

            if (!emitter.isCancelled()) {
                pref.registerOnSharedPreferenceChangeListener(listener);
                emitter.setDisposable(Disposables.fromAction(() ->
                        pref.unregisterOnSharedPreferenceChangeListener(listener)));
            }

        }, BackpressureStrategy.LATEST);
    }

    @Override
    public SessionSettings readSessionSettings()
    {
        SessionSettings settings = new SessionSettings();
        settings.downloadRateLimit = pref.getInt(appContext.getString(R.string.pref_key_max_download_speed),
                                                 Default.maxDownloadSpeedLimit);
        settings.uploadRateLimit = pref.getInt(appContext.getString(R.string.pref_key_max_upload_speed),
                                               Default.maxUploadSpeedLimit);
        settings.connectionsLimit = pref.getInt(appContext.getString(R.string.pref_key_max_connections),
                                                Default.maxConnections);
        settings.connectionsLimitPerTorrent = pref.getInt(appContext.getString(R.string.pref_key_max_connections_per_torrent),
                                                          Default.maxConnectionsPerTorrent);
        settings.uploadsLimitPerTorrent = pref.getInt(appContext.getString(R.string.pref_key_max_uploads_per_torrent),
                                                      Default.maxUploadsPerTorrent);
        settings.activeDownloads = pref.getInt(appContext.getString(R.string.pref_key_max_active_downloads),
                                               Default.maxActiveDownloads);
        settings.activeSeeds = pref.getInt(appContext.getString(R.string.pref_key_max_active_uploads),
                                           Default.maxActiveUploads);
        settings.activeLimit = pref.getInt(appContext.getString(R.string.pref_key_max_active_torrents),
                                           Default.maxActiveTorrents);
        settings.portRangeFirst = pref.getInt(appContext.getString(R.string.pref_key_port_range_first),
                                              Default.portRangeFirst);
        settings.portRangeSecond = pref.getInt(appContext.getString(R.string.pref_key_port_range_second),
                                               Default.portRangeSecond);
        settings.dhtEnabled = pref.getBoolean(appContext.getString(R.string.pref_key_enable_dht), Default.enableDht);
        settings.lsdEnabled = pref.getBoolean(appContext.getString(R.string.pref_key_enable_lsd), Default.enableLsd);
        settings.utpEnabled = pref.getBoolean(appContext.getString(R.string.pref_key_enable_utp), Default.enableUtp);
        settings.upnpEnabled = pref.getBoolean(appContext.getString(R.string.pref_key_enable_upnp), Default.enableUpnp);
        settings.natPmpEnabled = pref.getBoolean(appContext.getString(R.string.pref_key_enable_natpmp), Default.enableNatPmp);
        settings.encryptInConnections = pref.getBoolean(appContext.getString(R.string.pref_key_enc_in_connections),
                                                        Default.encryptInConnections);
        settings.encryptOutConnections = pref.getBoolean(appContext.getString(R.string.pref_key_enc_out_connections),
                                                         Default.encryptOutConnections);
        int modeVal = pref.getInt(appContext.getString(R.string.pref_key_enc_mode), Default.encryptMode(appContext));
        settings.encryptMode = SessionSettings.EncryptMode.fromValue(modeVal);
        settings.autoManaged = pref.getBoolean(appContext.getString(R.string.pref_key_auto_manage), Default.autoManage);

        return settings;
    }


    @Override
    public String notifySound()
    {
        return pref.getString(appContext.getString(R.string.pref_key_notify_sound),
                Default.notifySound);
    }

    @Override
    public void notifySound(String val)
    {
        pref.edit()
                .putString(appContext.getString(R.string.pref_key_notify_sound), val)
                .apply();
    }

    @Override
    public boolean torrentFinishNotify()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_torrent_finish_notify),
                Default.torrentFinishNotify);
    }

    @Override
    public void torrentFinishNotify(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_torrent_finish_notify), val)
                .apply();
    }

    @Override
    public boolean playSoundNotify()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_play_sound_notify),
                Default.playSoundNotify);
    }

    @Override
    public void playSoundNotify(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_play_sound_notify), val)
                .apply();
    }

    @Override
    public boolean ledIndicatorNotify()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_led_indicator_notify),
                Default.ledIndicatorNotify);
    }

    @Override
    public void ledIndicatorNotify(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_led_indicator_notify), val)
                .apply();
    }

    @Override
    public boolean vibrationNotify()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_vibration_notify),
                Default.vibrationNotify);
    }

    @Override
    public void vibrationNotify(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_vibration_notify), val)
                .apply();
    }

    @Override
    public int theme()
    {
        return pref.getInt(appContext.getString(R.string.pref_key_theme),
                Default.theme(appContext));
    }

    @Override
    public void theme(int val)
    {
        pref.edit()
                .putInt(appContext.getString(R.string.pref_key_theme), val)
                .apply();
    }

    @Override
    public int ledIndicatorColorNotify()
    {
        return pref.getInt(appContext.getString(R.string.pref_key_led_indicator_color_notify),
            Default.ledIndicatorColorNotify(appContext));
    }

    @Override
    public void ledIndicatorColorNotify(int val)
    {
        pref.edit()
                .putInt(appContext.getString(R.string.pref_key_led_indicator_color_notify), val)
                .apply();
    }

    @Override
    public boolean autostart()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_autostart),
                Default.autostart);
    }

    @Override
    public void autostart(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_autostart), val)
                .apply();
    }

    @Override
    public boolean keepAlive()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_keep_alive),
                Default.keepAlive);
    }

    @Override
    public void keepAlive(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_keep_alive), val)
                .apply();
    }

    @Override
    public boolean shutdownDownloadsComplete()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_shutdown_downloads_complete),
                Default.shutdownDownloadsComplete);
    }

    @Override
    public void shutdownDownloadsComplete(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_shutdown_downloads_complete), val)
                .apply();
    }

    @Override
    public boolean cpuDoNotSleep()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_cpu_do_not_sleep),
                Default.cpuDoNotSleep);
    }

    @Override
    public void cpuDoNotSleep(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_cpu_do_not_sleep), val)
                .apply();
    }

    @Override
    public boolean onlyCharging()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_download_and_upload_only_when_charging),
                Default.onlyCharging);
    }

    @Override
    public void onlyCharging(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_download_and_upload_only_when_charging), val)
                .apply();
    }

    @Override
    public boolean batteryControl()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_battery_control),
                Default.batteryControl);
    }

    @Override
    public void batteryControl(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_battery_control), val)
                .apply();
    }

    @Override
    public boolean customBatteryControl()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_custom_battery_control),
                Default.customBatteryControl);
    }

    @Override
    public void customBatteryControl(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_custom_battery_control), val)
                .apply();
    }

    @Override
    public int customBatteryControlValue()
    {
        return pref.getInt(appContext.getString(R.string.pref_key_custom_battery_control_value),
                Default.customBatteryControlValue);
    }

    @Override
    public void customBatteryControlValue(int val)
    {
        pref.edit()
                .putInt(appContext.getString(R.string.pref_key_custom_battery_control_value), val)
                .apply();
    }

    @Override
    public boolean unmeteredConnectionsOnly()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_unmetered_connections_only),
                Default.unmeteredConnectionsOnly);
    }

    @Override
    public void unmeteredConnectionsOnly(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_unmetered_connections_only), val)
                .apply();
    }

    @Override
    public boolean enableRoaming()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_enable_roaming),
                Default.enableRoaming);
    }

    @Override
    public void enableRoaming(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_enable_roaming), val)
                .apply();
    }

    @Override
    public int portRangeFirst()
    {
        return pref.getInt(appContext.getString(R.string.pref_key_port_range_first),
                Default.portRangeFirst);
    }

    @Override
    public void portRangeFirst(int val)
    {
        pref.edit()
                .putInt(appContext.getString(R.string.pref_key_port_range_first), val)
                .apply();
    }

    @Override
    public int portRangeSecond()
    {
        return pref.getInt(appContext.getString(R.string.pref_key_port_range_second),
                Default.portRangeSecond);
    }

    @Override
    public void portRangeSecond(int val)
    {
        pref.edit()
                .putInt(appContext.getString(R.string.pref_key_port_range_second), val)
                .apply();
    }

    @Override
    public boolean enableDht()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_enable_dht),
                Default.enableDht);
    }

    @Override
    public void enableDht(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_enable_dht), val)
                .apply();
    }

    @Override
    public boolean enableLsd()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_enable_lsd),
                Default.enableLsd);
    }

    @Override
    public void enableLsd(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_enable_lsd), val)
                .apply();
    }

    @Override
    public boolean enableUtp()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_enable_utp),
                Default.enableUtp);
    }

    @Override
    public void enableUtp(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_enable_utp), val)
                .apply();
    }

    @Override
    public boolean enableUpnp()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_enable_upnp),
                Default.enableUpnp);
    }

    @Override
    public void enableUpnp(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_enable_upnp), val)
                .apply();
    }

    @Override
    public boolean enableNatPmp()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_enable_natpmp),
                Default.enableUpnp);
    }

    @Override
    public void enableNatPmp(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_enable_natpmp), val)
                .apply();
    }

    @Override
    public boolean useRandomPort()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_use_random_port),
                Default.useRandomPort);
    }

    @Override
    public void useRandomPort(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_use_random_port), val)
                .apply();
    }

    @Override
    public boolean encryptInConnections()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_enc_in_connections),
                Default.encryptInConnections);
    }

    @Override
    public void encryptInConnections(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_enc_in_connections), val)
                .apply();
    }

    @Override
    public boolean encryptOutConnections()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_enc_out_connections),
                Default.encryptOutConnections);
    }

    @Override
    public void encryptOutConnections(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_enc_out_connections), val)
                .apply();
    }

    @Override
    public boolean enableIpFiltering()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_enable_ip_filtering),
                Default.enableIpFiltering);
    }

    @Override
    public void enableIpFiltering(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_enable_ip_filtering), val)
                .apply();
    }

    @Override
    public String ipFilteringFile()
    {
        return fs.normalizeFileSystemPath(pref.getString(appContext.getString(R.string.pref_key_ip_filtering_file),
                Default.ipFilteringFile));
    }

    @Override
    public void ipFilteringFile(String val)
    {
        pref.edit()
                .putString(appContext.getString(R.string.pref_key_ip_filtering_file), val)
                .apply();
    }

    @Override
    public int encryptMode()
    {
        return pref.getInt(appContext.getString(R.string.pref_key_enc_mode),
                Default.encryptMode(appContext));
    }

    @Override
    public void encryptMode(int val)
    {
        pref.edit()
                .putInt(appContext.getString(R.string.pref_key_enc_mode), val)
                .apply();
    }

    @Override
    public boolean showNatErrors()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_show_nat_errors),
                Default.showNatErrors);
    }

    @Override
    public void showNatErrors(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_show_nat_errors), val)
                .apply();
    }

    @Override
    public String saveTorrentsIn()
    {
        return fs.normalizeFileSystemPath(pref.getString(appContext.getString(R.string.pref_key_save_torrents_in),
                Default.saveTorrentsIn(appContext)));
    }

    @Override
    public void saveTorrentsIn(String val)
    {
        pref.edit()
                .putString(appContext.getString(R.string.pref_key_save_torrents_in), val)
                .apply();
    }

    @Override
    public boolean moveAfterDownload()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_move_after_download),
                Default.moveAfterDownload);
    }

    @Override
    public void moveAfterDownload(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_move_after_download), val)
                .apply();
    }

    @Override
    public String moveAfterDownloadIn()
    {
        return fs.normalizeFileSystemPath(pref.getString(appContext.getString(R.string.pref_key_move_after_download_in),
                Default.moveAfterDownloadIn(appContext)));
    }

    @Override
    public void moveAfterDownloadIn(String val)
    {
        pref.edit()
                .putString(appContext.getString(R.string.pref_key_move_after_download_in), val)
                .apply();
    }

    @Override
    public boolean saveTorrentFiles()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_save_torrent_files),
                Default.saveTorrentFiles);
    }

    @Override
    public void saveTorrentFiles(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_save_torrent_files), val)
                .apply();
    }

    @Override
    public String saveTorrentFilesIn()
    {
        return fs.normalizeFileSystemPath(pref.getString(appContext.getString(R.string.pref_key_save_torrents_in),
                Default.saveTorrentFilesIn(appContext)));
    }

    @Override
    public void saveTorrentFilesIn(String val)
    {
        pref.edit()
                .putString(appContext.getString(R.string.pref_key_save_torrent_files_in), val)
                .apply();
    }

    @Override
    public boolean watchDir()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_watch_dir),
                Default.watchDir);
    }

    @Override
    public void watchDir(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_watch_dir), val)
                .apply();
    }

    @Override
    public String dirToWatch()
    {
        return fs.normalizeFileSystemPath(pref.getString(appContext.getString(R.string.pref_key_dir_to_watch),
                Default.dirToWatch(appContext)));
    }

    @Override
    public void dirToWatch(String val)
    {
        pref.edit()
                .putString(appContext.getString(R.string.pref_key_dir_to_watch), val)
                .apply();
    }

    @Override
    public int maxDownloadSpeedLimit()
    {
        return pref.getInt(appContext.getString(R.string.pref_key_max_download_speed),
                Default.maxDownloadSpeedLimit);
    }

    @Override
    public void maxDownloadSpeedLimit(int val)
    {
        pref.edit()
                .putInt(appContext.getString(R.string.pref_key_max_download_speed), val)
                .apply();
    }

    @Override
    public int maxUploadSpeedLimit()
    {
        return pref.getInt(appContext.getString(R.string.pref_key_max_upload_speed),
                Default.maxUploadSpeedLimit);
    }

    @Override
    public void maxUploadSpeedLimit(int val)
    {
        pref.edit()
                .putInt(appContext.getString(R.string.pref_key_max_upload_speed), val)
                .apply();
    }

    @Override
    public int maxConnections()
    {
        return pref.getInt(appContext.getString(R.string.pref_key_max_connections),
                Default.maxConnections);
    }

    @Override
    public void maxConnections(int val)
    {
        pref.edit()
                .putInt(appContext.getString(R.string.pref_key_max_connections), val)
                .apply();
    }

    @Override
    public int maxConnectionsPerTorrent()
    {
        return pref.getInt(appContext.getString(R.string.pref_key_max_connections_per_torrent),
                Default.maxConnectionsPerTorrent);
    }

    @Override
    public void maxConnectionsPerTorrent(int val)
    {
        pref.edit()
                .putInt(appContext.getString(R.string.pref_key_max_connections_per_torrent), val)
                .apply();
    }

    @Override
    public int maxUploadsPerTorrent()
    {
        return pref.getInt(appContext.getString(R.string.pref_key_max_uploads_per_torrent),
                Default.maxUploadsPerTorrent);
    }

    @Override
    public void maxUploadsPerTorrent(int val)
    {
        pref.edit()
                .putInt(appContext.getString(R.string.pref_key_max_uploads_per_torrent), val)
                .apply();
    }

    @Override
    public int maxActiveUploads()
    {
        return pref.getInt(appContext.getString(R.string.pref_key_max_active_uploads),
                Default.maxActiveUploads);
    }

    @Override
    public void maxActiveUploads(int val)
    {
        pref.edit()
                .putInt(appContext.getString(R.string.pref_key_max_active_uploads), val)
                .apply();
    }

    @Override
    public int maxActiveDownloads()
    {
        return pref.getInt(appContext.getString(R.string.pref_key_max_active_downloads),
                Default.maxActiveDownloads);
    }

    @Override
    public void maxActiveDownloads(int val)
    {
        pref.edit()
                .putInt(appContext.getString(R.string.pref_key_max_active_downloads), val)
                .apply();
    }

    @Override
    public int maxActiveTorrents()
    {
        return pref.getInt(appContext.getString(R.string.pref_key_max_active_torrents),
                Default.maxActiveTorrents);
    }

    @Override
    public void maxActiveTorrents(int val)
    {
        pref.edit()
                .putInt(appContext.getString(R.string.pref_key_max_active_torrents), val)
                .apply();
    }

    @Override
    public boolean autoManage()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_auto_manage),
                Default.autoManage);
    }

    @Override
    public void autoManage(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_auto_manage), val)
                .apply();
    }

    @Override
    public int proxyType()
    {
        return pref.getInt(appContext.getString(R.string.pref_key_max_active_torrents),
                Default.proxyType);
    }

    @Override
    public void proxyType(int val)
    {
        pref.edit()
                .putInt(appContext.getString(R.string.pref_key_proxy_type), val)
                .apply();
    }

    @Override
    public String proxyAddress()
    {
        return pref.getString(appContext.getString(R.string.pref_key_proxy_address),
                Default.proxyAddress);
    }

    @Override
    public void proxyAddress(String val)
    {
        pref.edit()
                .putString(appContext.getString(R.string.pref_key_proxy_address), val)
                .apply();
    }

    @Override
    public int proxyPort()
    {
        return pref.getInt(appContext.getString(R.string.pref_key_proxy_port),
                Default.proxyPort);
    }

    @Override
    public void proxyPort(int val)
    {
        pref.edit()
                .putInt(appContext.getString(R.string.pref_key_proxy_port), val)
                .apply();
    }

    @Override
    public boolean proxyPeersToo()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_proxy_peers_too),
                Default.proxyPeersToo);
    }

    @Override
    public void proxyPeersToo(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_proxy_peers_too), val)
                .apply();
    }

    @Override
    public boolean proxyRequiresAuth()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_proxy_requires_auth),
                Default.proxyRequiresAuth);
    }

    @Override
    public void proxyRequiresAuth(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_proxy_requires_auth), val)
                .apply();
    }

    @Override
    public String proxyLogin()
    {
        return pref.getString(appContext.getString(R.string.pref_key_proxy_login),
                Default.proxyLogin);
    }

    @Override
    public void proxyLogin(String val)
    {
        pref.edit()
                .putString(appContext.getString(R.string.pref_key_proxy_login), val)
                .apply();
    }

    @Override
    public String proxyPassword()
    {
        return pref.getString(appContext.getString(R.string.pref_key_proxy_password),
                Default.proxyPassword);
    }

    @Override
    public void proxyPassword(String val)
    {
        pref.edit()
                .putString(appContext.getString(R.string.pref_key_proxy_password), val)
                .apply();
    }

    @Override
    public boolean proxyChanged()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_proxy_changed),
                Default.proxyChanged);
    }

    @Override
    public void proxyChanged(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_proxy_password), val)
                .apply();
    }

    @Override
    public String sortTorrentBy()
    {
        return pref.getString(appContext.getString(R.string.pref_key_sort_torrent_by),
                Default.sortTorrentBy);
    }

    @Override
    public void sortTorrentBy(String val)
    {
        pref.edit()
                .putString(appContext.getString(R.string.pref_key_sort_torrent_by), val)
                .apply();
    }

    @Override
    public String sortTorrentDirection()
    {
        return pref.getString(appContext.getString(R.string.pref_key_sort_torrent_direction),
                Default.sortTorrentDirection);
    }

    @Override
    public void sortTorrentDirection(String val)
    {
        pref.edit()
                .putString(appContext.getString(R.string.pref_key_sort_torrent_direction), val)
                .apply();
    }

    @Override
    public boolean enableSchedulingStart()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_enable_scheduling_start),
                Default.enableSchedulingStart);
    }

    @Override
    public void enableSchedulingStart(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_enable_scheduling_start), val)
                .apply();
    }

    @Override
    public boolean enableSchedulingShutdown()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_enable_scheduling_shutdown),
                Default.enableSchedulingShutdown);
    }

    @Override
    public void enableSchedulingShutdown(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_enable_scheduling_shutdown), val)
                .apply();
    }

    @Override
    public int schedulingStartTime()
    {
        return pref.getInt(appContext.getString(R.string.pref_key_scheduling_start_time),
                Default.schedulingStartTime);
    }

    @Override
    public void schedulingStartTime(int val)
    {
        pref.edit()
                .putInt(appContext.getString(R.string.pref_key_scheduling_start_time), val)
                .apply();
    }

    @Override
    public int schedulingShutdownTime()
    {
        return pref.getInt(appContext.getString(R.string.pref_key_scheduling_shutdown_time),
                Default.schedulingShutdownTime);
    }

    @Override
    public void schedulingShutdownTime(int val)
    {
        pref.edit()
                .putInt(appContext.getString(R.string.pref_key_scheduling_shutdown_time), val)
                .apply();
    }

    @Override
    public boolean schedulingRunOnlyOnce()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_scheduling_run_only_once),
                Default.schedulingRunOnlyOnce);
    }

    @Override
    public void schedulingRunOnlyOnce(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_scheduling_run_only_once), val)
                .apply();
    }

    @Override
    public boolean schedulingSwitchWiFi()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_scheduling_switch_wifi),
                Default.schedulingSwitchWiFi);
    }

    @Override
    public void schedulingSwitchWiFi(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_scheduling_switch_wifi), val)
                .apply();
    }

    @Override
    public long feedItemKeepTime()
    {
        return pref.getLong(appContext.getString(R.string.pref_key_feed_keep_items_time),
                Default.feedItemKeepTime);
    }

    @Override
    public void feedItemKeepTime(long val)
    {
        pref.edit()
                .putLong(appContext.getString(R.string.pref_key_feed_keep_items_time), val)
                .apply();
    }

    @Override
    public boolean autoRefreshFeeds()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_feed_auto_refresh),
                Default.autoRefreshFeeds);
    }

    @Override
    public void autoRefreshFeeds(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_feed_auto_refresh), val)
                .apply();
    }

    @Override
    public long refreshFeedsInterval()
    {
        return pref.getLong(appContext.getString(R.string.pref_key_feed_refresh_interval),
                Default.refreshFeedsInterval);
    }

    @Override
    public void refreshFeedsInterval(long val)
    {
        pref.edit()
                .putLong(appContext.getString(R.string.pref_key_feed_refresh_interval), val)
                .apply();
    }

    @Override
    public boolean autoRefreshFeedsUnmeteredConnectionsOnly()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_feed_auto_refresh_unmetered_connections_only),
                Default.autoRefreshFeedsUnmeteredConnectionsOnly);
    }

    @Override
    public void autoRefreshFeedsUnmeteredConnectionsOnly(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_feed_auto_refresh_unmetered_connections_only), val)
                .apply();
    }

    @Override
    public boolean autoRefreshFeedsEnableRoaming()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_feed_auto_refresh_enable_roaming),
                Default.autoRefreshFeedsEnableRoaming);
    }

    @Override
    public void autoRefreshFeedsEnableRoaming(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_feed_auto_refresh_enable_roaming), val)
                .apply();
    }

    @Override
    public boolean feedStartTorrents()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_feed_start_torrents),
                Default.feedStartTorrents);
    }

    @Override
    public void feedStartTorrents(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_feed_start_torrents), val)
                .apply();
    }

    @Override
    public boolean feedRemoveDuplicates()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_feed_remove_duplicates),
                Default.feedRemoveDuplicates);
    }

    @Override
    public void feedRemoveDuplicates(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_feed_remove_duplicates), val)
                .apply();
    }

    @Override
    public boolean enableStreaming()
    {
        return pref.getBoolean(appContext.getString(R.string.pref_key_streaming_enable),
                Default.enableStreaming);
    }

    @Override
    public void enableStreaming(boolean val)
    {
        pref.edit()
                .putBoolean(appContext.getString(R.string.pref_key_streaming_enable), val)
                .apply();
    }

    @Override
    public String streamingHostname()
    {
        return pref.getString(appContext.getString(R.string.pref_key_streaming_hostname),
                Default.streamingHostname);
    }

    @Override
    public void streamingHostname(String val)
    {
        pref.edit()
                .putString(appContext.getString(R.string.pref_key_streaming_hostname), val)
                .apply();
    }

    @Override
    public int streamingPort()
    {
        return pref.getInt(appContext.getString(R.string.pref_key_streaming_port),
                Default.streamingPort);
    }

    @Override
    public void streamingPort(int val)
    {
        pref.edit()
                .putInt(appContext.getString(R.string.pref_key_streaming_port), val)
                .apply();
    }
}