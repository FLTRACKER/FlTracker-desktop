package com.fl.tracker.model

data class UserDto(
	var id: Int,
	var username: String,
	var roles: List<RoleDto>? = null,
)
