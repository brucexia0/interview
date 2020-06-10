package com.example.interview.practice

class PermSubsetComb {

    fun subsetsV1(nums: IntArray): List<List<Int>> {
        val result = ArrayList<List<Int>>()
        val bits = 1 shl nums.size
        for (i in 0 until bits) {
            val list = ArrayList<Int>()
            for (j in nums.indices) {
                if ((i and (1 shl j)) > 0) {
                    list.add(nums[j])
                }
            }
            result.add(list)
        }
        return result
    }
}