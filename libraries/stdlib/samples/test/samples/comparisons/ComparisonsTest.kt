/*
 * Copyright 2010-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package samples.comparisons

import samples.*
import kotlin.test.*

class ComparisonsTest {
    @Sample
    fun compareValuesByWithSingleSelector() {
        val list = listOf("aa", "b", "bb", "a")

        val sorted = list.sortedWith(Comparator { a, b ->
            compareValuesBy(a, b) { it.length }
        })

        assertEquals(listOf("b", "a", "aa", "bb"), sorted)
    }

    @Sample
    fun compareValuesByWithSelectors() {
        val list = listOf("aa", "b", "bb", "a")

        val sorted = list.sortedWith(Comparator { a, b ->
            val lengthSelector = { s: String -> s.length }
            val sourceSelector = { s: String -> s }
            compareValuesBy(a, b, lengthSelector, sourceSelector)
        })

        assertEquals(listOf("a", "b", "aa", "bb"), sorted)
    }

    @Sample
    fun compareValuesByWithComparator() {
        val list = listOf(1, 20, 2, 100)

        val sorted = list.sortedWith(Comparator { a, b ->
            compareValuesBy(a, b, naturalOrder(), { v -> v.toString() })
        })

        assertEquals(listOf(1, 100, 2, 20), sorted)
    }

    @Sample
    fun sampleCompareValues() {
        val list = listOf(4, null, 1, -2, 3)

        val sorted = list.sortedWith(
                Comparator { a, b -> compareValues(a, b) }
        )

        assertEquals(listOf(null, -2, 1, 3, 4), sorted)
    }

    @Sample
    fun compareByWithSingleSelector() {
        val list = listOf("aa", "b", "bb", "a")

        val sorted = list.sortedWith(compareBy { it.length })

        assertEquals(listOf("b", "a", "aa", "bb"), sorted)
    }

    @Sample
    fun compareByWithSelectors() {
        val list = listOf("aa", "b", "bb", "a")

        val sorted = list.sortedWith(compareBy(
                { s: String -> s.length },
                { s: String -> s }
        ))

        assertEquals(listOf("a", "b", "aa", "bb"), sorted)
    }

    @Sample
    fun compareByWithComparator() {
        val list = listOf(1, 20, 2, 100)

        val sorted = list.sortedWith(
                compareBy(naturalOrder<String>()) { v -> v.toString() }
        )

        assertEquals(listOf(1, 100, 2, 20), sorted)
    }

    @Sample
    fun compareByDescendingWithSingleSelector() {
        val list = listOf("aa", "b", "bb", "a")

        val sorted = list.sortedWith(compareByDescending { it.length })

        assertEquals(listOf("aa", "bb", "b", "a"), sorted)
    }

    @Sample
    fun compareByDescendingWithComparator() {
        val list = listOf("aa", "b", "bb", "a")

        val sorted = list.sortedWith(compareByDescending(naturalOrder<Int>()) { it.length })

        assertEquals(listOf("aa", "bb", "b", "a"), sorted)
    }

    @Sample
    fun sampleThenBy() {
        val list = listOf("aa", "b", "bb", "a")

        val comparator = compareBy<String> { it.length }
                .thenBy { it }

        val sorted = list.sortedWith(comparator)

        assertEquals(listOf("a", "b", "aa", "bb"), sorted)
    }

    @Sample
    fun sampleThenByWithComparator() {
        val list = listOf("aa", "b", "bb", "a")

        val comparator = compareBy<String> { it.length }
                .thenBy(reverseOrder()) { it }

        val sorted = list.sortedWith(comparator)

        assertEquals(listOf("b", "a", "bb", "aa"), sorted)
    }

    @Sample
    fun sampleThenByDescending() {
        val map = mapOf("a" to 1, "b" to 2, "c" to 1, "d" to 0)

        val sorted = map.entries.sortedWith(
                compareBy<Map.Entry<String, Int>> { it.value }
                        .thenByDescending { it.key })
                .map { it.key }

        assertEquals(listOf("d", "c", "a", "b"), sorted)
    }

    @Sample
    fun sampleThenByDescendingWithComparator() {
        val map = mapOf("a" to 1, "b" to 2, "c" to 1, "d" to 0)

        val sorted = map.entries.sortedWith(
                compareBy<Map.Entry<String, Int>> { it.value }
                        .thenByDescending(naturalOrder()) { it.key })
                .map { it.key }

        assertEquals(listOf("d", "c", "a", "b"), sorted)
    }

    @Sample
    fun sampleThen() {
        val map = mapOf("a" to 1, "b" to 2, "c" to 1, "d" to 0)

        val sorted = map.entries.sortedWith(
                compareBy<Map.Entry<String, Int>> { it.value }
                        .then(compareBy { it.key }))
                .map { it.key }

        assertEquals(listOf("d", "a", "c", "b"), sorted)
    }

    @Sample
    fun sampleThenDescending() {
        val map = mapOf("a" to 1, "b" to 2, "c" to 1, "d" to 0)

        val sorted = map.entries.sortedWith(
                compareBy<Map.Entry<String, Int>> { it.value }
                        .thenDescending(compareBy { it.key }))
                .map { it.key }

        assertEquals(listOf("d", "c", "a", "b"), sorted)
    }

    @Sample
    fun sampleNullsFirst() {
        val list = listOf(4, null, -1, 1)

        val sortedList = list.sortedWith(nullsFirst())

        assertEquals(listOf(null, -1, 1, 4), sortedList)
    }

    @Sample
    fun sampleNullsFirstWithComparator() {
        val list = listOf(4, null, 1, -2, 3)

        val evenFirstComparator = Comparator { a: Int, b: Int ->
            if (a % 2 == b % 2) return@Comparator 0
            if (a % 2 == 0) -1 else 1
        }
        val sortedList = list.sortedWith(nullsFirst(evenFirstComparator))

        assertEquals(listOf(null, 4, -2, 1, 3), sortedList)
    }

    @Sample
    fun sampleNullsLast() {
        val list = listOf(4, null, -1, 1)

        val sortedList = list.sortedWith(nullsLast())

        assertEquals(listOf(-1, 1, 4, null), sortedList)
    }

    @Sample
    fun sampleNullsLastWithComparator() {
        val list = listOf(4, null, 1, -2, 3)

        val evenFirstComparator = Comparator { a: Int, b: Int ->
            if (a % 2 == b % 2) return@Comparator 0
            if (a % 2 == 0) -1 else 1
        }
        val sortedList = list.sortedWith(nullsLast(evenFirstComparator))

        assertEquals(listOf(4, -2, 1, 3, null), sortedList)
    }
}