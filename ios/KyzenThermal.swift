/**
 * KyzenRT — Universal edge AI inference runtime
 * Copyright (c) 2025 Kypzen (kypzen.com)
 *
 * Built on llama.cpp (MIT) — github.com/ggml-org/llama.cpp
 * Original binding: llama.rn (MIT) — github.com/mybigday/llama.rn
 *
 * SPDX-License-Identifier: MIT
 */
import Foundation
import UIKit

/**
 * KyzenRT Thermal Observer (iOS)
 * Copyright (c) 2025 Kypzen (kypzen.com)
 * SPDX-License-Identifier: MIT
 */
@objc public class KyzenThermal: NSObject {

    @objc public enum ThermalState: Int {
        case nominal, fair, serious, critical
    }

    @objc public static func getThermalState() -> ThermalState {
        let state = ProcessInfo.processInfo.thermalState
        switch state {
        case .nominal:  return .nominal
        case .fair:     return .fair
        case .serious:  return .serious
        case .critical: return .critical
        @unknown default: return .nominal
        }
    }

    @objc public static func getRecommendedBatchSize(_ defaultBatch: Int) -> Int {
        switch getThermalState() {
        case .nominal:  return defaultBatch
        case .fair:     return Int(Double(defaultBatch) * 0.75)
        case .serious:  return Int(Double(defaultBatch) * 0.5)
        case .critical: return 64
        }
    }

    @objc public static func getHardwareTier() -> String {
        let totalRAM = ProcessInfo.processInfo.physicalMemory
        let gb = Double(totalRAM) / 1_073_741_824
        switch gb {
        case 6...:  return "high"
        case 3..<6: return "mid"
        default:    return "low"
        }
    }
}
