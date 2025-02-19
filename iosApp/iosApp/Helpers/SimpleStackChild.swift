//
//  SimpleStackChild.swift
//  iosApp
//
//  Created by Tô Tử Siêu on 14/2/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import logical

func simpleChildStack<T : AnyObject>(_ child: T) -> Value<ChildStack<AnyObject, T>> {
    return mutableValue(
        ChildStack(
            configuration: "config" as AnyObject,
            instance: child
        )
    )
}
