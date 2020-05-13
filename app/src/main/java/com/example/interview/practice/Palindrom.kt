package com.example.interview.practice

class Palindrom {
    //Delete at most one character to make it a palindrome
    fun validPalindrome(s: String): Boolean {
        val length = s.length
        for (i in 0 until length / 2) {
            if (s[i] != s[length - i - 1]) {
                return (s.substring(i + 1).isPalindrome()
                        || s.substring(i, length - i - 2).isPalindrome()
                        )
            }
        }
        return true
    }

    fun String.isPalindrome() = true
}