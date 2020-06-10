package com.example.interview.practice

import com.example.interview.swap

class SearchNSort {
    fun findKthLargest(nums: IntArray, k: Int): Int {
        nums.sortDescending()
        var count = k
        for (i in nums.indices) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                count--
            }
            if (count == 0) return nums[i]
        }
        return nums[0]
    }

    fun ksum(nums: IntArray, target: Int, k: Int): List<List<Int>> {
        nums.sort()
        return ksum(nums, target, 0, k)
    }

    fun ksum(
        nums: IntArray,
        target: Int,
        start: Int,
        k: Int
    ): List<List<Int>> {
        if (k == 2) return twoSumSorted(nums, target, start)
        val result = ArrayList<List<Int>>()
        var i = start
        while (i < nums.size) {
            if (i == 0 || nums[i] != nums[i - 1]) {

                val matchedList = ksum(nums, target - nums[i], i + 1, k - 1)
                if (matchedList.isNotEmpty()) {
                    matchedList.forEach {
                        val l = ArrayList(it)
                        l.add(0, nums[i])
                        result.add(l)
                    }
                }
            }
            i++
        }
        return result
    }

    fun twoSumSorted(nums: IntArray, target: Int, start: Int): List<List<Int>> {
        val result = ArrayList<List<Int>>()
        var i = start
        var j = nums.size - 1
        while (i < j) {
            val sum = nums[i] + nums[j]
            if (sum == target) {
                result.add(listOf(nums[i], nums[j]))
                while (i < j && nums[i] == nums[i + 1]) {
                    i++
                }
                while (i < j && nums[j - 1] == nums[j]) {
                    j--
                }
                i++
                j--
            } else if (sum < target) {
                i++
            } else {
                j--
            }
        }
        return result
    }

    private fun binarySearch(nums: IntArray, target: Int, start: Int, end: Int): Int {
        var left = start
        var right = end
        while (left < right) {
            val mid = (left + right + 1) / 2
            if (start == nums[mid]) {
                return mid
            } else if (target < nums[mid]) right = mid + 1
            else {
                left = mid - 1
            }
        }
        return -1
    }

    //Return the start index of found duplicates
    fun searchDuplicateSubString(s: String, length: Int): Int {
        val seen = HashSet<String>()
        seen.add(s.substring(0, length))
        for (i in 1 until s.length - length + 1) {
            val tmp = s.substring(i, i + length)
            if (seen.contains(tmp)) {
                return i
            } else {
                seen.add(tmp)
            }
        }
        return -1
    }

    fun longestDupSubstring(s: String): String {
        if (s.isNullOrEmpty()) return s
        val length = s.length
        var left = 1
        var right = length
        var foundIndex = -1
        var foundLength = 0
        while (left <= right) {
            val mid = (left + right) / 2
            val index = searchDuplicateSubString(s, mid)
            if (index != -1) {
                left = mid + 1
                foundIndex = index
                foundLength = mid
            } else {
                right = mid - 1
            }
        }
        return if (foundIndex != -1) s.substring(foundIndex, foundIndex + foundLength) else ""
    }

    //Most profit from stack trading given maximum k trades
    fun maxProfit(k: Int, prices: IntArray): Int {
        if (prices.size < 2 || k == 0) return 0
        val trades = Math.min(k, prices.size / 2)
        val buy = IntArray(trades) { Integer.MAX_VALUE }
        val sell = IntArray(trades)
        for (i in prices.indices) {
            for (j in 0 until trades) {
                val lastSellPrice = if (j == 0) 0 else sell[j - 1]
                buy[j] = Math.min(buy[j], prices[i] - lastSellPrice)
                sell[j] = Math.max(sell[j], prices[i] - buy[j])
            }
        }
        return sell.max()!!
    }

    fun findSubarrySum(a: IntArray, target: Int): Boolean {
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

    fun bulbSwitch(n: Int): Int {
        val bulbs = Array<Boolean>(n) { false }
        for (i in 0 until n) {
            val start = if (i == 0) 0 else i - 1
            for (j in start until n step i + 1) {
                bulbs[j] = !bulbs[j]
            }
        }
        return bulbs.sumBy { if (it) 1 else 0 }
    }

    fun setZeroes(matrix: Array<IntArray>): Unit {

    }

    // Rotate the array in o(1) space and mn time complexity
    fun rotate(nums: IntArray, k: Int): Unit {
        val size = nums.size
        val kCount = k % size
        var count = 0
        var i = 0
        while (i < size && count < size) {
            var current = i
            var prev = nums[i]
            do {
                var next = (current + kCount) % size
                val tmp = nums[next]
                nums[next] = prev
                prev = tmp
                current = next
                count++
            } while (current != i)
        }
    }

    fun isRobotBounded(instructions: String): Boolean {
        val currCor = IntArray(2)
        val dir = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(1, 0),
            intArrayOf(0, -1),
            intArrayOf(-1, 0)
        )
        var ind = 0
        for (i in instructions.indices) {
            if (instructions[i] == 'L') {
                ind = (ind + 3) % 4
                continue
            }
            if (instructions[i] == 'R') {
                ind = (ind + 1) % 4
                continue
            }
            currCor[0] += dir[ind][0]
            currCor[1] += dir[ind][1]
        }
        return currCor[0] == 0 && currCor[1] == 0 || ind > 0
    }

    fun quickSort(arr: IntArray) {
        quickSort(arr, 0, arr.size - 1)
    }

    fun quickSort(arr: IntArray, low: Int, high: Int) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is now
               at right place */
            val pi = partition(arr, low, high)

            quickSort(arr, low, pi - 1)  // Before pi
            quickSort(arr, pi + 1, high) // After pi
        }
    }

    fun partition(arr: IntArray, low: Int, high: Int): Int {
        val pivot = arr[high]
        var i = low  // index of smaller element
        for (j in low until high) {
            // If current element is smaller than the pivot
            if (arr[j] < pivot) {
                // swap arr[i] and arr[j]
                arr.swap(i, j)
                i++
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        arr.swap(i, high)
        return i
    }
}