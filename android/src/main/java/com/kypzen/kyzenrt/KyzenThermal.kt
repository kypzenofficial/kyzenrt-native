/**
 * KyzenRT — Universal edge AI inference runtime
 * Copyright (c) 2025 Kypzen (kypzen.com)
 *
 * Built on llama.cpp (MIT) — github.com/ggml-org/llama.cpp
 * Original binding: llama.rn (MIT) — github.com/mybigday/llama.rn
 *
 * SPDX-License-Identifier: MIT
 */
package com.kypzen.kyzenrt

import android.content.Context
import android.os.PowerManager
import android.os.Build

/**
 * KyzenRT Thermal Observer
 * Copyright (c) 2025 Kypzen (kypzen.com)
 * SPDX-License-Identifier: MIT
 */
class KyzenThermal(private val context: Context) {

    enum class ThermalState { NOMINAL, FAIR, SERIOUS, CRITICAL }

    private val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager

    fun getThermalState(): ThermalState {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            when (powerManager.currentThermalStatus) {
                PowerManager.THERMAL_STATUS_NONE -> ThermalState.NOMINAL
                PowerManager.THERMAL_STATUS_LIGHT -> ThermalState.FAIR
                PowerManager.THERMAL_STATUS_MODERATE -> ThermalState.FAIR
                PowerManager.THERMAL_STATUS_SEVERE -> ThermalState.SERIOUS
                PowerManager.THERMAL_STATUS_CRITICAL -> ThermalState.CRITICAL
                PowerManager.THERMAL_STATUS_EMERGENCY -> ThermalState.CRITICAL
                PowerManager.THERMAL_STATUS_SHUTDOWN -> ThermalState.CRITICAL
                else -> ThermalState.NOMINAL
            }
        } else {
            ThermalState.NOMINAL
        }
    }

    /**
     * Returns recommended n_batch based on thermal state.
     * Use this to automatically throttle inference under heat.
     */
    fun getRecommendedBatchSize(defaultBatch: Int): Int {
        return when (getThermalState()) {
            ThermalState.NOMINAL -> defaultBatch
            ThermalState.FAIR -> (defaultBatch * 0.75).toInt()
            ThermalState.SERIOUS -> (defaultBatch * 0.5).toInt()
            ThermalState.CRITICAL -> 64  // minimum safe batch
        }
    }

    /**
     * Returns hardware tier based on available RAM.
     */
    fun getHardwareTier(): String {
        val runtime = Runtime.getRuntime()
        val maxMemMB = runtime.maxMemory() / (1024 * 1024)
        return when {
            maxMemMB >= 6000 -> "high"
            maxMemMB >= 3000 -> "mid"
            else -> "low"
        }
    }
}
