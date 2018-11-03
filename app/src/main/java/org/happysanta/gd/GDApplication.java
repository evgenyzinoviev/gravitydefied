package org.happysanta.gd;

import android.app.Application;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import static org.acra.ReportField.ANDROID_VERSION;
import static org.acra.ReportField.APP_VERSION_CODE;
import static org.acra.ReportField.APP_VERSION_NAME;
import static org.acra.ReportField.CUSTOM_DATA;
import static org.acra.ReportField.DISPLAY;
import static org.acra.ReportField.INSTALLATION_ID;
import static org.acra.ReportField.LOGCAT;
import static org.acra.ReportField.PHONE_MODEL;
import static org.acra.ReportField.PRODUCT;
import static org.acra.ReportField.STACK_TRACE;
import static org.acra.ReportField.USER_CRASH_DATE;

@ReportsCrashes(
        formKey = "",
        formUri = "http://gdtr.net/report.php",
        customReportContent = {APP_VERSION_NAME, APP_VERSION_CODE, ANDROID_VERSION, PHONE_MODEL,
                PRODUCT, DISPLAY, STACK_TRACE, LOGCAT, USER_CRASH_DATE, INSTALLATION_ID, CUSTOM_DATA}
)

public class GDApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (Global.ACRA_ENABLED) {
            ACRA.init(this);
        }
    }
}
