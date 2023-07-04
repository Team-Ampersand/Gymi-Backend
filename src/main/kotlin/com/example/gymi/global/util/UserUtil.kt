package com.example.gymi.global.util

import com.example.gymi.domain.user.entity.User
import com.example.gymi.domain.user.exception.UserNotFoundException
import com.example.gymi.domain.user.repository.UserRepository
import com.example.gymi.global.security.auth.AuthDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class UserUtil(
    private val userRepository: UserRepository
) {
    fun currentUser(): User {
        val email = fetchUserEmail()
        return fetchUserByEmail(email)
    }

    fun fetchUserByEmail(email: String): User =
        userRepository.findByEmail(email) ?: throw UserNotFoundException()

    fun fetchUserEmail(): String {
        val principal = SecurityContextHolder.getContext().authentication.principal
        println(principal)
        println(principal is UserDetails)

        val email =
            if (principal is UserDetails) {
                (principal as AuthDetails).username
            } else {
                principal.toString()
            }
        return email
    }
}