package com.example.interview.init

/**
 * Android questions
 * What happens when phone rotates: 1. Activity recreated, 2. onSaveInstance/onRetrieveInstance 3. Clean up resources 4.
 * Persistence methods: shared preference, sqlite, serialized file storage, cache
 *
 */
class FB {
    fun findTarget(a: IntArray, target: Int): Boolean {
        var start = 0
        var end = 0
        var sum = 0
        while (start <= end && end < a.size) {
            if (sum > target) {
                sum -= a[start]
                start++
            } else {
                sum += a[end]
                end++
            }
            if (sum == target) return true
        }
        return false
    }
}