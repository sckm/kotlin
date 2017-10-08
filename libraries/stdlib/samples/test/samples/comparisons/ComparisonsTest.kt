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