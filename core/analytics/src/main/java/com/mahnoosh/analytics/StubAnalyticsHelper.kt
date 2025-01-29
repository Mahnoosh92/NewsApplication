package com.mahnoosh.analytics

import android.util.Log
import javax.inject.Inject


private const val TAG = "StubAnalyticsHelper"
internal class StubAnalyticsHelper @Inject constructor() : AnalyticsHelper {
    override fun logEvent(event: AnalyticsEvent) {
        Log.d(TAG, "Received analytics event: $event")
    }
}