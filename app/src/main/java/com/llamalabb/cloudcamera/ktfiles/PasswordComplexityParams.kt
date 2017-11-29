package com.llamalabb.cloudcamera.ktfiles

/**
 * Created by andy on 11/28/17.
 */

private val minPassLength = 8
private val maxPassLength = 32

sealed class ComplexityParams {
    object Email : RegexComplexityParam(Regex("""(?:[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])"""))
    object CompletePassword : ComplexityParam({Regex("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{$minPassLength})").containsMatchIn(it) && it.length <= maxPassLength})
    object Digit : RegexComplexityParam(Regex("\\d+"))
    object Upper : RegexComplexityParam(Regex("[A-Z]+"))
    object Lower : RegexComplexityParam(Regex("[a-z]+"))
    object Spchr : RegexComplexityParam(Regex("\\W+"))
    object Whtspc : RegexComplexityParam(Regex("\\s+"))
    object Length : ComplexityParam({ it.let { it.length in minPassLength .. maxPassLength } })
}

open class RegexComplexityParam(private val regex: Regex) : ComplexityParam({ regex.containsMatchIn(it) })
open class ComplexityParam(private val predicate: (charSeq : CharSequence) -> Boolean) : ComplexityParams() {
    fun passes(charSeq: CharSequence) : Boolean = predicate(charSeq)
}