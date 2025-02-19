//
//  StateValue.swift
//  iosApp
//
//  Created by Tô Tử Siêu on 14/2/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import logical

@propertyWrapper struct StateValue<T : AnyObject>: DynamicProperty {
    @ObservedObject
    private var obj: ObservableValue<T>

    var wrappedValue: T { obj.value }

    init(_ value: Value<T>) {
        obj = ObservableValue(value)
    }
}
