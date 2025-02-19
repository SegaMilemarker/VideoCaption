//
//  RootView.swift
//  iosApp
//
//  Created by Tô Tử Siêu on 14/2/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import logical

struct RootView: View {
    private let root: RootComponent
    
    init(_ root: RootComponent) {
        self.root = root
    }
    
    var body: some View {
        StackView(
            stackValue: StateValue(root.stack),
            getTitle: { _ in "Hello" },
            onBack: root.onBackClicked
        ) { child in
            switch child {
            case let child as RootComponentChild.SplashChild:
                SplashView()
            case let child as RootComponentChild.TabsChild:
                TabsView(child.component)
            default:
                EmptyView()
            }
        }
    }
}

private typealias SplashChild = RootComponentChild.SplashChild
private typealias TabsChild = RootComponentChild.TabsChild
