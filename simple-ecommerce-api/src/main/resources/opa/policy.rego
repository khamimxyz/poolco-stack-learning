package ecommerce.authorization

import future.keywords
import future.keywords.every

default allow := false

allow if {
    input.path = ["product"]
    allowed_roles := ["Product-Manager"]
    roles := {role | some role in input.roles; role.authority = allowed_roles[_]}
    count(roles) > 0
}

allow if {
    input.path = ["promotion"]
    allowed_roles := ["Promotion-Manager"]
    roles := {role | some role in input.roles; role.authority = allowed_roles[_]}
    count(roles) > 0
}

allow if {
    input.path = ["order"]
    allowed_roles := ["Order-Manager"]
    roles := {role | some role in input.roles; role.authority = allowed_roles[_]}
    count(roles) > 0
}