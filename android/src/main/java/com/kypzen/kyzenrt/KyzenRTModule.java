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

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.turbomodule.core.CallInvokerHolderImpl;

@ReactModule(name = KyzenRTContext.NAME)
public class KyzenRTModule extends NativeRNLlamaSpec {
  public static final String NAME = KyzenRTContext.NAME;

  private ReactApplicationContext context;

  public KyzenRTModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.context = reactContext;
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @Override
  public void install(Promise promise) {
    try {
      boolean loaded = KyzenRTContext.loadNative(context);
      if (!loaded) {
        promise.resolve(false);
        return;
      }

      long jsContextPointer = context.getJavaScriptContextHolder().get();
      CallInvokerHolderImpl holder =
        (CallInvokerHolderImpl) context.getCatalystInstance().getJSCallInvokerHolder();

      if (jsContextPointer == 0 || holder == null) {
        promise.resolve(false);
        return;
      }

      installJSIBindings(jsContextPointer, holder);
      promise.resolve(true);
      return;
    } catch (UnsatisfiedLinkError e) {
    } catch (Exception e) {
    }
    promise.resolve(false);
  }

  private native void installJSIBindings(long jsContextPointer, CallInvokerHolderImpl callInvokerHolder);
  private native void cleanupJSIBindings();

  @Override
  public void invalidate() {
    try {
      cleanupJSIBindings();
    } catch (UnsatisfiedLinkError ignored) {
      // Native library may not be loaded if install was never called.
    }
    super.invalidate();
  }
}
