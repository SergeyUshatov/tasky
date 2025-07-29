package com.sergius.tasky

import androidx.test.platform.app.InstrumentationRegistry

object ResourceUtil {
    fun getStringResource(id: Int, vararg formatArgs: Any): String {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        return context.resources.getString(id, *formatArgs)
    }
}