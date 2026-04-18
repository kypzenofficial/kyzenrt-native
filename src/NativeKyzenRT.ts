/**
 * KyzenRT — Universal edge AI inference runtime
 * Copyright (c) 2025 Kypzen (kypzen.com)
 *
 * Built on llama.cpp (MIT) — github.com/ggml-org/llama.cpp
 * Original binding: llama.rn (MIT) — github.com/mybigday/llama.rn
 *
 * SPDX-License-Identifier: MIT
 */
import type { TurboModule } from 'react-native'
import { TurboModuleRegistry } from 'react-native'

export interface Spec extends TurboModule {
  install(): Promise<boolean>
}

export default TurboModuleRegistry.get<Spec>('KyzenRT') as Spec
