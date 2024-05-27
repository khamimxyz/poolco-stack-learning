package ecommerce.authorization

import future.keywords
import future.keywords.every

default allow := false

# Allow access if the user has a specific role for a module
allow if {
    user_has_role_for_module(input.role, input.module)
}

# Define user roles for specific modules
user_has_role_for_module(role, module) {
    roles := {
        "product": ["Product-Manager", "Product-Viewer"],
        "review": ["Customer"],
        "order": ["Customer", "Customer-Service"]
    }
    role in roles[module]
}
