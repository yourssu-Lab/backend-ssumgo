package com.yourssu.ssumgo.common.application.domain.auth

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [SoongsilEmailValidator::class])
annotation class SoongsilEmail(
    val message: String = "숭실대학교 이메일 형식이 아닙니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class SoongsilEmailValidator : ConstraintValidator<SoongsilEmail, String> {
    companion object {
        const val SOONGSIL_EMAIL_REGEX = "^[a-zA-Z0-9]+@soongsil.ac.kr$"
    }
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        return value?.matches(Regex(SOONGSIL_EMAIL_REGEX)) ?: false
    }
}

