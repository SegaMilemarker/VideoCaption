//
//  TabsView.swift
//  iosApp
//
//  Created by Tô Tử Siêu on 14/2/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import logical

struct TabsView: View {
    
    private let component: TabsComponent
    
    @StateValue
    private var stack: ChildStack<AnyObject, TabsComponentChild>
    
    private var activeChild: TabsComponentChild { stack.active.instance }
    
    @State private var selectedIndex: Int = 0
    
    
    
    init(_ component: TabsComponent) {
        self.component = component
        _stack = StateValue(component.stack)
        if let child = component.stack.active.instance as? TabsComponentChild {
            selectedIndex = child.index.intValue
        }
    }
    
    
    var body: some View {
        TabView(selection: $selectedIndex) {
            HomeView()
                .tabItem {
                    Image(systemName: "house.fill")
                    Text("Home")
                }
                .tag(TabsComponentAppTab.home.index)
            
            FeedsView()
                .tabItem {
                    Image(systemName: "list.bullet")
                    Text("Feeds")
                }
                .tag(TabsComponentAppTab.feeds.index)
            
            SettingsView()
                .tabItem {
                    Image(systemName: "gearshape.fill")
                    Text("Settings")
                }
                .tag(TabsComponentAppTab.settings.index)
            
        }.navigationBarBackButtonHidden(true)
            .onChange(of: selectedIndex) { newIndex in
                let tab = TabsComponentAppTab.Companion().fromIndex(index: Int32(newIndex))
                component.onTabClicked(tab: tab)
                
            }
    }
    
    
    private var selectionBinding: Binding<Int> {
        Binding(
            get: {
                self.activeChild.index.intValue
            },
            set: { newIndex in
                self.component.onTabClicked(tab: TabsComponentAppTab.Companion().fromIndex(index: newIndex.int32))
                print("Set index \(newIndex)")
            }
        )
    }
}

struct HomeView: View {
    var body: some View {
        VStack {
            Text("Home")
                .font(.largeTitle)
                .padding()
            Spacer()
        }
    }
}

struct FeedsView: View {
    var body: some View {
        VStack {
            Text("Feeds")
                .font(.largeTitle)
                .padding()
            Spacer()
        }
    }
}

struct SettingsView: View {
    var body: some View {
        VStack {
            Text("Settings")
                .font(.largeTitle)
                .padding()
            Spacer()
        }
    }
}

