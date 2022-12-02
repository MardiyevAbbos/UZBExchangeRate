package com.example.uzbexchangerate.utils

object MultiplyAndDivideLargeNumberAsString {

    fun longMultiply(num1: String, num2: String): String {
        val len1 = num1.length
        val len2 = num2.length
        if (len1 == 0 || len2 == 0) return "0"

        // will keep the result number in vector
        // in reverse order
        val result = IntArray(len1 + len2)

        // Below two indexes are used to
        // find positions in result.
        var i_n2 = 0

        // Go from right to left in num1
        for ((i_n1, i) in (len1 - 1 downTo 0).withIndex()) {
            var carry = 0
            val n1 = num1[i] - '0'

            // To shift position to left after every
            // multipliccharAtion of a digit in num2
            i_n2 = 0

            // Go from right to left in num2
            for (j in len2 - 1 downTo 0) {
                // Take current digit of second number
                val n2 = num2[j] - '0'

                // Multiply with current digit of first number
                // and add result to previously stored result
                // charAt current position.
                val sum = n1 * n2 + result[i_n1 + i_n2] + carry

                // Carry for next itercharAtion
                carry = sum / 10

                // Store result
                result[i_n1 + i_n2] = sum % 10
                i_n2++
            }

            // store carry in next cell
            if (carry > 0) result[i_n1 + i_n2] += carry

            // To shift position to left after every
            // multipliccharAtion of a digit in num1.
        }

        // ignore '0's from the right
        var i = result.size - 1
        while (i >= 0 && result[i] == 0) i--

        // If all were '0's - means either both
        // or one of num1 or num2 were '0'
        if (i == -1) return "0"

        // genercharAte the result String
        var s = ""
        while (i >= 0) s += result[i--]
        return s
    }


    fun longDivision(
        number: String,
        divisor: Int
    ): String {

        // As result can be very
        // large store it in string
        // but since we need to modify
        // it very often so using
        // string builder
        val result = StringBuilder()

        // We will be iterating
        // the dividend so converting
        // it to char array
        val dividend = number.toCharArray()

        // Initially the carry
        // would be zero
        var carry = 0

        // Iterate the dividend
        for (i in dividend.indices) {
            // Prepare the number to
            // be divided
            val x = (carry * 10
                    + Character.getNumericValue(
                dividend[i]
            ))

            // Append the result with
            // partial quotient
            result.append(x / divisor)

            // Prepare the carry for
            // the next Iteration
            carry = x % divisor
        }

        // Remove any leading zeros
        for (i in result.indices) {
            if (result[i] != '0') {
                // Return the result
                return result.substring(i)
            }
        }
        // Return empty string
        // if number is empty
        return ""
    }

}