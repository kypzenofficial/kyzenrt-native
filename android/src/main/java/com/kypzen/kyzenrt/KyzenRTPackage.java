/**
 * KyzenRT — Universal edge AI inference runtime
 * Copyright (c) 2025 Kypzen (kypzen.com)
 *
 * Built on llama.cpp (MIT) — github.com/ggml-org/llama.cpp
 * Original binding: llama.rn (MIT) — github.com/mybigday/llama.rn
 *
 * SPDX-License-Identifier: MIT
 */
package com.kypzen.kyzenrt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.TurboReactPackage;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class KyzenRTPackage extends TurboReactPackage {

  @Nullable
  @Override
  public NativeModule getModule(String name, ReactApplicationContext reactContext) {
    if (name.equals(KyzenRTModule.NAME)) {
      return new com.kypzen.kyzenrt.KyzenRTModule(reactContext);
    } else {
      return null;
    }
  }

  @Override
  public ReactModuleInfoProvider getReactModuleInfoProvider() {
    return () -> {
      final Map<String, ReactModuleInfo> moduleInfos = new HashMap<>();
      boolean isTurboModule = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED;
      moduleInfos.put(
        KyzenRTModule.NAME,
        new ReactModuleInfo(
          KyzenRTModule.NAME,
          KyzenRTModule.NAME,
          false, // canOverrideExistingModule
          false, // needsEagerInit
          true, // hasConstants
          false, // isCxxModule
          isTurboModule // isTurboModule
        )
      );
      return moduleInfos;
    };
  }
}
