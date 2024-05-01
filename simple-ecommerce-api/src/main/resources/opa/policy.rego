package ecommerce.authorization

import future.keywords
import future.keywords.every

default allow := false

allow if {
    input.module = "product"
    input.role = "Product-Manager"
}

allow if {
    input.module = "promotion"
    input.role = "Promotion-Manager"
}

allow if {
    input.module = "order"
    input.role = "Order-Manager"
}