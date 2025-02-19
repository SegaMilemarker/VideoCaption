//
//  IntExt.swift
//  iosApp
//
//  Created by Tô Tử Siêu on 14/2/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation

extension Int32 {
    var floatValue: Float {
        Float(self)
    }
    
    var doubleValue: Double {
        Double(self)
    }
    
    var stringValue: String {
        String(self)
    }
    
    var intValue: Int {
        Int(self)
    }
}

extension Int {
    var int32: Int32 {
        return Int32(self)
    }

    var double: Double {
        return Double(self)
    }
    
    var string: String {
        return String(self)
    }
}
