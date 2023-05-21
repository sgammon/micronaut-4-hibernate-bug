package com.example

import io.micronaut.core.annotation.Introspected
import io.micronaut.runtime.Micronaut.run
import jakarta.persistence.Entity

@Introspected(
	packages = ["com.example.model"],
	includedAnnotations = [Entity::class],
)
object Application {
	@JvmStatic fun main(args: Array<String>) {
		run(*args)
	}
}
