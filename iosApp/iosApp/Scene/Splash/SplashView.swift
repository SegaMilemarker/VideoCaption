//
//  SplashView.swift
//  iosApp
//
//  Created by Tô Tử Siêu on 14/2/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct SplashView: View {
    
    @State private var size = 0.8
    @State private var opacity = 0.5
    
    var body: some View {
        VStack {
            Text("VideoCaption")
                .font(Font.custom("Arial Rounded MT Bold", size: 40))
                .foregroundColor(.blue)
                .scaleEffect(size)
                .opacity(opacity)
                .onAppear {
                    withAnimation(.easeIn(duration: 1.2)) {
                        self.size = 1.0
                        self.opacity = 1.0
                    }
                }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color.white)
    }
}

#Preview {
    SplashView()
}
