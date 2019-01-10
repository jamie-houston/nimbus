//
// Copyright (c) 2019, Salesforce.com, inc.
// All rights reserved.
// SPDX-License-Identifier: BSD-3-Clause
// For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
//

import Foundation
import Nimbus
import WebKit

class DemoBridge {
    func currentTime() -> String {
        return Date().description
    }
}

extension DemoBridge: NimbusExtension {
    func preload(config: [String : String], webViewConfiguration: WKWebViewConfiguration, callback: @escaping (Bool) -> Void) {
        callback(true)
    }

    func load(config: [String : String], webView: WKWebView, callback: @escaping (Bool) -> Void) {
        let c = webView.addConnection(to: self, as: "DemoBridge")
        c.bind(DemoBridge.currentTime, as: "currentTime")

        callback(true)
    }
}