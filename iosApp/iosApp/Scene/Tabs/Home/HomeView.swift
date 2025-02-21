//
//  HomeView.swift
//  iosApp
//
//  Created by Tô Tử Siêu on 21/2/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import logical

struct HomeView: View {
    
    private let component: HomeComponent
    
    @StateValue
    private var data: HomeComponentModel
    
    init(_ component: HomeComponent) {
        self.component = component
        _data = StateValue(component.data)
    }
    
    var body: some View {
        ScrollView {
            VStack {
              
                
            }
        }
    }
    
    @ViewBuilder
    func movieListView(category: HomeMovieCategory) -> some View {
        VStack(alignment: .leading, spacing: 20) {
            Text("New titles")
                .font(.title)
                .fontWeight(.bold)
            
            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 20) {
                    ForEach(data.movies[category] ?? []) { movie in
                        MovieCardView(movie)
                    }
                
            }
                    
        }
        
    }
}
