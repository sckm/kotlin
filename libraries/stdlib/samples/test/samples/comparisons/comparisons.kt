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

class Comparisons {
    @Sample
    fun compareValuesByWithSingleSelector() {
        val list = listOf("aa", "b", "", "bb", "a")

        val sorted = list.sortedWith(Comparator { a, b ->
            when {
                a == b -> 0
                a == "" -> 1
                b == "" -> -1
                else -> compareValuesBy(a, b) { it.length }
            }
        })

        assertPrints(sorted, "[b, a, aa, bb, ]")
    }

    @Sample
    fun compareValuesByWithSelectors() {
        val list = listOf("aa", "b", "", "bb", "a")

        val sorted = list.sortedWith(Comparator { a, b ->
            when {
                a == b -> 0
                a == "" -> 1
                b == "" -> -1
                else -> compareValuesBy(a, b, { it.length }, { it })
            }
        })

        assertPrints(sorted, "[a, b, aa, bb, ]")
    }

    @Sample
    fun compareValuesByWithComparator() {
        val list = listOf(1, 20, 0, 2, 100)

        val sorted = list.sortedWith(Comparator { a, b ->
            when {
                a == b -> 0
                a == 0 -> -1
                b == 0 -> 1
                else -> compareValuesBy(a, b, reverseOrder(), { v -> v.toString() })
            }
        })

        assertPrints(sorted, "[0, 20, 2, 100, 1]")
    }

    @Sample
    fun compareValues() {
        val list = listOf(4, null, 1, -2, 3)

        val sorted = list.sortedWith(
                Comparator { a, b -> compareValues(a, b) }
        )

        assertPrints(sorted, "[null, -2, 1, 3, 4]")
    }

    @Sample
    fun compareByWithSingleSelector() {
        val list = listOf("aa", "b", "bb", "a")

        val sorted = list.sortedWith(compareBy { it.length })

        assertPrints(sorted, "[b, a, aa, bb]")
    }

    @Sample
    fun compareByWithSelectors() {
        val list = listOf("aa", "b", "bb", "a")

        val sorted = list.sortedWith(compareBy(
                { it.length },
                { it }
        ))

        assertPrints(sorted, "[a, b, aa, bb]")
    }

    @Sample
    fun compareByWithComparator() {
        val list = listOf(1, 20, 2, 100)

        val sorted = list.sortedWith(
                compareBy(naturalOrder<String>()) { v -> v.toString() }
        )

        assertPrints(sorted, "[1, 100, 2, 20]")
    }

    @Sample
    fun compareByDescendingWithSingleSelector() {
        val list = listOf("aa", "b", "bb", "a")

        val sorted = list.sortedWith(compareByDescending { it.length })

        assertPrints(sorted, "[aa, bb, b, a]")
    }

    @Sample
    fun compareByDescendingWithComparator() {
        val list = listOf("aa", "b", "bb", "a")

        val sorted = list.sortedWith(compareByDescending(naturalOrder<Int>()) { it.length })

        assertPrints(sorted, "[aa, bb, b, a]")
    }

    @Sample
    fun thenBy() {
        val list = listOf("aa", "b", "bb", "a")

        val comparator = compareBy<String> { it.length }.thenBy { it }

        val sorted = list.sortedWith(comparator)

        assertPrints(sorted, "[a, b, aa, bb]")
    }

    @Sample
    fun thenByWithComparator() {
        val list = listOf("aa", "b", "bb", "a")

        val comparator = compareBy<String> { it.length }
                .thenBy(reverseOrder()) { it }

        val sorted = list.sortedWith(comparator)

        assertPrints(sorted, "[b, a, bb, aa]")
    }

    @Sample
    fun thenByDescending() {
        val map = mapOf("a" to 1, "b" to 2, "c" to 1, "d" to 0)

        val comparator = compareBy<Map.Entry<String, Int>> { it.value }
                .thenByDescending { it.key }

        val sorted = map.entries
                .sortedWith(comparator)
                .map { it.key }

        assertPrints(sorted, "[d, c, a, b]")
    }

    @Sample
    fun thenByDescendingWithComparator() {
        val map = mapOf("a" to 1, "b" to 2, "c" to 1, "d" to 0)

        val comparator = compareBy<Map.Entry<String, Int>> { it.value }
                .thenByDescending(naturalOrder()) { it.key }

        val sorted = map.entries
                .sortedWith(comparator)
                .map { it.key }

        assertPrints(sorted, "[d, c, a, b]")
    }

    @Sample
    fun thenComparator() {
        val map = mapOf("a" to 1, "b" to 2, "c" to 1, "d" to 0)

        val comparator = compareBy<Map.Entry<String, Int>> { it.value }
                .thenComparator({ a, b -> compareValues(a.key, b.key) })

        val sorted = map.entries
                .sortedWith(comparator)
                .map { it.key }

        assertPrints(sorted, "[d, a, c, b]")
    }

    @Sample
    fun then() {
        val map = mapOf("a" to 1, "b" to 2, "c" to 1, "d" to 0)

        val comparator = compareBy<Map.Entry<String, Int>> { it.value }
                .then(compareBy { it.key })

        val sorted = map.entries
                .sortedWith(comparator)
                .map { it.key }

        assertPrints(sorted, "[d, a, c, b]")
    }

    @Sample
    fun thenDescending() {
        val map = mapOf("a" to 1, "b" to 2, "c" to 1, "d" to 0)

        val comparator = compareBy<Map.Entry<String, Int>> { it.value }
                .thenDescending(compareBy { it.key })

        val sorted = map.entries
                .sortedWith(comparator)
                .map { it.key }

        assertPrints(sorted, "[d, c, a, b]")
    }

    @Sample
    fun nullsFirstComparator() {
        val list = listOf(4, null, -1, 1)

        val sortedList = list.sortedWith(nullsFirst())

        assertPrints(sortedList, "[null, -1, 1, 4]")
    }

    @Sample
    fun nullsFirstWithComparator() {
        val list = listOf(4, null, 1, -2, 3)

        val evenFirstComparator = Comparator { a: Int, b: Int ->
            if (a % 2 == b % 2) return@Comparator 0
            if (a % 2 == 0) -1 else 1
        }
        val sortedList = list.sortedWith(nullsFirst(evenFirstComparator))

        assertPrints(sortedList, "[null, 4, -2, 1, 3]")
    }

    @Sample
    fun nullsLastComparator() {
        val list = listOf(4, null, -1, 1)

        val sortedList = list.sortedWith(nullsLast())

        assertPrints(sortedList, "[-1, 1, 4, null]")
    }

    @Sample
    fun nullsLastWithComparator() {
        val list = listOf(4, null, 1, -2, 3)

        val evenFirstComparator = Comparator { a: Int, b: Int ->
            if (a % 2 == b % 2) return@Comparator 0
            if (a % 2 == 0) -1 else 1
        }
        val sortedList = list.sortedWith(nullsLast(evenFirstComparator))

        assertPrints(sortedList, "[4, -2, 1, 3, null]")
    }

    @Sample
    fun naturalOrderComparator() {
        val list = listOf(4, 1, -2, 1, 3)

        val sortedList = list.sortedWith(naturalOrder())

        assertPrints(sortedList, "[-2, 1, 1, 3, 4]")
    }

    @Sample
    fun reverseOrderComparator() {
        val list = listOf(4, 1, -2, 1, 3)

        val sortedList = list.sortedWith(reverseOrder())

        assertPrints(sortedList, "[4, 3, 1, 1, -2]")
    }

    @Sample
    fun reversed() {
        val list = listOf(4, 1, -2, 1, 3)

        val sortedList = list.sortedWith(naturalOrder<Int>().reversed())

        assertPrints(sortedList, "[4, 3, 1, 1, -2]")
    }
}