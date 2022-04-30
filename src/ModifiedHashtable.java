/*
 * Copyright (c) 1994, 2019, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
//this is a heavily modified version of the standard java implementation of Hashtable
//used for submission of school assignment on itu.kattis.com by Asmus Tørsleff 20/02/2021
import java.util.*;

/**
 * This class implements a hash table, which maps keys to values. Any
 * non-{@code null} object can be used as a key or as a value. <p>
 *
 * To successfully store and retrieve objects from a hashtable, the
 * objects used as keys must implement the {@code hashCode}
 * method and the {@code equals} method. <p>
 *
 * An instance of {@code Hashtable} has two parameters that affect its
 * performance: <i>initial capacity</i> and <i>load factor</i>.  The
 * <i>capacity</i> is the number of <i>buckets</i> in the hash table, and the
 * <i>initial capacity</i> is simply the capacity at the time the hash table
 * is created.  Note that the hash table is <i>open</i>: in the case of a "hash
 * collision", a single bucket stores multiple entries, which must be searched
 * sequentially.  The <i>load factor</i> is a measure of how full the hash
 * table is allowed to get before its capacity is automatically increased.
 * The initial capacity and load factor parameters are merely hints to
 * the implementation.  The exact details as to when and whether the rehash
 * method is invoked are implementation-dependent.<p>
 *
 * Generally, the default load factor (.75) offers a good tradeoff between
 * time and space costs.  Higher values decrease the space overhead but
 * increase the time cost to look up an entry (which is reflected in most
 * {@code Hashtable} operations, including {@code get} and {@code put}).<p>
 *
 * The initial capacity controls a tradeoff between wasted space and the
 * need for {@code rehash} operations, which are time-consuming.
 * No {@code rehash} operations will <i>ever</i> occur if the initial
 * capacity is greater than the maximum number of entries the
 * {@code Hashtable} will contain divided by its load factor.  However,
 * setting the initial capacity too high can waste space.<p>
 *
 * If many entries are to be made into a {@code Hashtable},
 * creating it with a sufficiently large capacity may allow the
 * entries to be inserted more efficiently than letting it perform
 * automatic rehashing as needed to grow the table. <p>
 *
 * This example creates a hashtable of numbers. It uses the names of
 * the numbers as keys:
 * <pre>   {@code
 *   Hashtable<String, Integer> numbers
 *     = new Hashtable<String, Integer>();
 *   numbers.put("one", 1);
 *   numbers.put("two", 2);
 *   numbers.put("three", 3);}</pre>
 *
 * <p>To retrieve a number, use the following code:
 * <pre>   {@code
 *   Integer n = numbers.get("two");
 *   if (n != null) {
 *     System.out.println("two = " + n);
 *   }}</pre>
 *
 * <p>The iterators returned by the {@code iterator} method of the collections
 * returned by all of this class's "collection view methods" are
 * <em>fail-fast</em>: if the Hashtable is structurally modified at any time
 * after the iterator is created, in any way except through the iterator's own
 * {@code remove} method, the iterator will throw a {@link
 * }.  Thus, in the face of concurrent
 * modification, the iterator fails quickly and cleanly, rather than risking
 * arbitrary, non-deterministic behavior at an undetermined time in the future.
 * The Enumerations returned by Hashtable's {@link # keys} and
 * {@link # elements} methods are <em>not</em> fail-fast; if the
 * Hashtable is structurally modified at any time after the enumeration is
 * created then the results of enumerating are undefined.
 *
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw {@code ConcurrentModificationException} on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness: <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i>
 *
 * <p>As of the Java 2 platform v1.2, this class was retrofitted to
 * implement the {@link } interface, making it a member of the
 * <a href="{@docRoot}/java.base/java/util/package-summary.html#CollectionsFramework">
 *
 * Java Collections Framework</a>.  Unlike the new collection
 * implementations, {@code Hashtable} is synchronized.  If a
 * thread-safe implementation is not needed, it is recommended to use
 * {@link } in place of {@code Hashtable}.  If a thread-safe
 * highly-concurrent implementation is desired, then it is recommended
 * to use {@link java.util.concurrent.ConcurrentHashMap} in place of
 * {@code Hashtable}.
 *
 *
 * @author  Arthur van Hoff
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see     Object#equals(java.lang.Object)
 * @see     Object#hashCode()
 * @since 1.0
 */
public class ModifiedHashtable {

    /**
     * The hash table data.
     */
    private transient Entry[] table;

    /**
     * The total number of entries in the hash table.
     */
    private transient int count;

    /**
     * The table is rehashed when its size exceeds this threshold.  (The
     * value of this field is (int)(capacity * loadFactor).)
     *
     * @serial
     */
    private int threshold;

    /**
     * The load factor for the hashtable.
     *
     * @serial
     */
    private float loadFactor;


    /**
     * Constructs a new, empty hashtable with a default initial capacity (11)
     * and load factor (0.75).
     */
    public ModifiedHashtable() {
        this.loadFactor = 0.75f;
        table = new Entry[11];
        threshold = (int)Math.min(11 * loadFactor, MAX_ARRAY_SIZE + 1);
    }


    /**
     * Returns the number of keys in this hashtable.
     *
     * @return  the number of keys in this hashtable.
     */
    public synchronized int size() {
        return count;
    }



    /**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = 1<<31 - 8;

    /**
     * Increases the capacity of and internally reorganizes this
     * hashtable, in order to accommodate and access its entries more
     * efficiently.  This method is called automatically when the
     * number of keys in the hashtable exceeds this hashtable's capacity
     * and load factor.
     */
    protected void rehash() {
        int oldCapacity = table.length;
        Entry[] oldMap = table;

        // overflow-conscious code
        int newCapacity = (oldCapacity << 1) + 1;
        if (newCapacity - MAX_ARRAY_SIZE > 0) {
            if (oldCapacity == MAX_ARRAY_SIZE)
                // Keep running with MAX_ARRAY_SIZE buckets
                return;
            newCapacity = MAX_ARRAY_SIZE;
        }
        Entry[] newMap = new Entry[newCapacity];

        threshold = (int)Math.min(newCapacity * loadFactor, MAX_ARRAY_SIZE + 1);
        table = newMap;

        for (int i = oldCapacity ; i-- > 0 ;) {
            for (Entry old = oldMap[i] ; old != null ; ) {
                Entry e = old;
                old = old.next;

                int index = (e.key & 0x7FFFFFFF) % newCapacity;
                e.next = newMap[index];
                newMap[index] = e;
            }
        }
    }

    private void addEntry(int key) {
        int index = (key & 0x7FFFFFFF) % table.length;
        Entry[] tab = table;
        if (count >= threshold) {
            // Rehash the table if the threshold is exceeded
            rehash();

            tab = table;
            index = (key & 0x7FFFFFFF) % tab.length;
        }

        // Creates the new entry.
        Entry e =  tab[index];
        tab[index] = new Entry(key, e);
        count++;
    }

    /**
     * Maps the specified {@code key} to the specified
     * {@code value} in this hashtable. Neither the key nor the
     * value can be {@code null}. <p>
     *
     * The value can be retrieved by calling the {@code get} method
     * with a key that is equal to the original key.
     *
     * @param      key     the hashtable key
     * @throws     NullPointerException  if the key or value is
     *               {@code null}
     * @see     Object#equals(Object)
     */
    public synchronized void put(int key) {
        addEntry(key);
    }

    /**
     * Removes the key (and its corresponding value) from this
     * hashtable. This method does nothing if the key is not in the hashtable.
     *
     * @param   key   the key that needs to be removed
     * @throws  NullPointerException  if the key is {@code null}
     */
    public synchronized void remove(int key) {
        Entry[] tab = table;
        int index = (key & 0x7FFFFFFF) % tab.length;
        Entry e = tab[index];
        for(Entry prev = null ; e != null ; prev = e, e = e.next) {
            if (e.key == key) {
                if (prev != null) {
                    prev.next = e.next;
                } else {
                    tab[index] = e.next;
                }
                count--;
                e.value = false;
                return;
            }
        }
    }


    public synchronized int remove() {
        Entry[] tab = table;
        int o = 0;
        for (int index = tab.length; --index >= 0; )
            if(tab[index] != null) {
                o = tab[index].key;
                tab[index] = tab[index].next;
                break;
            }
        count--;
        return o;
    }
    public synchronized void clearTo(ModifiedHashtable hashtable, int[] parent, int newParent) {
        Entry[] tab = table;
        for (int index = tab.length; --index >= 0; )
            if(tab[index] != null) {
                parent[tab[index].key] = newParent;
                hashtable.addEntry(tab[index].key);
                tab[index] = null;
            }
        count = 0;
    }

    /*private <T> Enumeration<T> getEnumeration() {
        if (count == 0) {
            return Collections.emptyEnumeration();
        } else {
            return new Enumerator<>(ModifiedHashtable.KEYS, false);
        }
    }*/

    // Views

    /**
     * Returns the hash code value for this Map as per the definition in the
     * Map interface.
     *
     * @see Map#hashCode()
     * @since 1.2
     */
    public synchronized int hashCode() {
        /*
         * This code detects the recursion caused by computing the hash code
         * of a self-referential hash table and prevents the stack overflow
         * that would otherwise result.  This allows certain 1.1-era
         * applets with self-referential hash tables to work.  This code
         * abuses the loadFactor field to do double-duty as a hashCode
         * in progress flag, so as not to worsen the space performance.
         * A negative load factor indicates that hash code computation is
         * in progress.
         */
        int h = 0;
        if (count == 0 || loadFactor < 0)
            return h;  // Returns zero

        loadFactor = -loadFactor;  // Mark hashCode computation in progress
        Entry[] tab = table;
        for (Entry entry : tab) {
            while (entry != null) {
                h += entry.hashCode();
                entry = entry.next;
            }
        }

        loadFactor = -loadFactor;  // Mark hashCode computation complete

        return h;
    }

    /**
     * Hashtable bucket collision list entry
     */
    private static class Entry {
        final int key;
        boolean value = true;
        Entry next;

        protected Entry(int key, Entry next) {
            this.key =  key;
            this.next = next;
        }

        public int hashCode() {
            return key ^ Objects.hashCode(value);
        }

        public String toString() {
            return key+"="+value;
        }
    }
}
