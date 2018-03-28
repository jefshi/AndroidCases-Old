package cc.rengu.src.kitkat.java.lang;

import java.lang.ref.Reference;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description:
 * <p>Create Date: 2017/10/10
 * <p>Modify Date: 无
 *
 * @author 永丰基地
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class ThreadLocal<T> {
	/**
	 * Hash counter.
	 */
	private static AtomicInteger hashCounter = new AtomicInteger(0);

	/**
	 * Internal hash. We deliberately don't bother with #hashCode().
	 * Hashes must be even. This ensures that the result of
	 * (hash & (table.length - 1)) points to a key and not a value.
	 * <p>
	 * We increment by Doug Lea's Magic Number(TM) (*2 since keys are in
	 * every other bucket) to help prevent clustering.
	 */
	private final int hash = hashCounter.getAndAdd(0x61c88647 * 2);

	/**
	 * Returns the value of this variable for the current thread. If an entry
	 * doesn't yet exist for this variable on this thread, this method will
	 * create an entry, populating the value with the result of
	 * {@link #initialValue()}.
	 */
	@SuppressWarnings("unchecked")
	public T get() {
		// Optimized for the fast path.
		Thread currentThread = Thread.currentThread(); // 当前线程
		Values values = values(currentThread);
		if (values != null) {
			Object[] table = values.table;
			int index = hash & values.mask;
			if (this.reference == table[index]) {
				return (T) table[index + 1];
			}
		} else {
			values = initializeValues(currentThread);
		}

		return (T) values.getAfterMiss(this);
	}

	/**
	 * Sets the value of this variable for the current thread. If set to
	 * {@code null}, the value will be set to null and the underlying entry will
	 * still be present.
	 */
	public void set(T value) {
		Thread currentThread = Thread.currentThread();
		Values values = values(currentThread);
		if (values == null) {
			values = initializeValues(currentThread);
		}
		values.put(this, value);
	}

	/**
	 * Gets Values instance for this thread and variable type.
	 * 获取当前线程绑定的[Values]对象
	 */
	Values values(Thread current) {
		return current.localValues;
	}

	/**
	 * Provides the initial value of this variable for the current thread.
	 * The default implementation returns {@code null}.
	 *
	 * @return the initial value of the variable.
	 */
	protected T initialValue() {
		return null;
	}

	/**
	 * Creates Values instance for this thread and variable type.
	 * 创建当前线程绑定的[Values]对象
	 */
	Values initializeValues(Thread current) {
		return current.localValues = new Values();
	}

	/**
	 * Per-thread map of ThreadLocal instances to values.
	 * [ThreadLocal]数据对象
	 */
	static class Values {
		/**
		 * Map entries. Contains alternating keys (ThreadLocal) and values.
		 * The length is always a power of 2.
		 */
		private Object[] table;

		/**
		 * Number of live entries.
		 */
		private int size;

		/**
		 * Number of tombstones.
		 */
		private int tombstones; // 墓碑？？？

		private static final int INITIAL_SIZE = 16; // [table]初始化长度

		/**
		 * Used to turn hashes into indices.
		 */
		private int mask;

		/**
		 * Points to the next cell to clean up.
		 */
		private int clean;

		/**
		 * Maximum number of live entries and tombstones.
		 */
		private int maximumLoad;

		Values() {
			initializeTable(INITIAL_SIZE);
			this.size = 0;
			this.tombstones = 0;
		}

		/**
		 * Creates a new, empty table with the given capacity.
		 */
		private void initializeTable(int capacity) {
			this.table = new Object[capacity * 2];
			this.mask = table.length - 1;
			this.clean = 0;
			this.maximumLoad = capacity * 2 / 3; // 2/3
		}

		/**
		 * Sets entry for given ThreadLocal to given value, creating an
		 * entry if necessary.
		 */
		void put(ThreadLocal<?> key, Object value) {
			cleanUp();

			// Keep track of first tombstone. That's where we want to go back
			// and add an entry if necessary.
			int firstTombstone = -1;

			for (int index = key.hash & mask; ; index = next(index)) {
				Object k = table[index];

				if (k == key.reference) {
					// Replace existing entry.
					table[index + 1] = value;
					return;
				}

				if (k == null) {
					if (firstTombstone == -1) {
						// Fill in null slot.
						table[index] = key.reference;
						table[index + 1] = value;
						size++;
						return;
					}

					// Go back and replace first tombstone.
					table[firstTombstone] = key.reference;
					table[firstTombstone + 1] = value;
					tombstones--;
					size++;
					return;
				}

				// Remember first tombstone.
				if (firstTombstone == -1 && k == TOMBSTONE) {
					firstTombstone = index;
				}
			}
		}

		/**
		 * Cleans up after garbage-collected thread locals.
		 */
		private void cleanUp() {
			if (rehash()) {
				// If we rehashed, we needn't clean up (clean up happens as
				// a side effect).
				return;
			}

			if (size == 0) {
				// No live entries == nothing to clean.
				return;
			}

			// Clean log(table.length) entries picking up where we left off
			// last time.
			int index = clean;
			Object[] table = this.table;
			for (int counter = table.length; counter > 0; counter >>= 1,
					index = next(index)) {
				Object k = table[index];

				if (k == TOMBSTONE || k == null) {
					continue; // on to next entry
				}

				// The table can only contain null, tombstones and references.
				@SuppressWarnings("unchecked")
				Reference<ThreadLocal<?>> reference
						= (Reference<ThreadLocal<?>>) k;
				if (reference.get() == null) {
					// This thread local was reclaimed by the garbage collector.
					table[index] = TOMBSTONE;
					table[index + 1] = null;
					tombstones++;
					size--;
				}
			}

			// Point cursor to next index.
			clean = index;
		}

		/**
		 * Rehashes the table, expanding or contracting it as necessary.
		 * Gets rid of tombstones. Returns true if a rehash occurred.
		 * We must rehash every time we fill a null slot; we depend on the
		 * presence of null slots to end searches (otherwise, we'll infinitely
		 * loop).
		 */
		private boolean rehash() {
			if (tombstones + size < maximumLoad) {
				return false;
			}

			int capacity = table.length >> 1;

			// Default to the same capacity. This will create a table of the
			// same size and move over the live entries, analogous to a
			// garbage collection. This should only happen if you churn a
			// bunch of thread local garbage (removing and reinserting
			// the same thread locals over and over will overwrite tombstones
			// and not fill up the table).
			int newCapacity = capacity;

			if (size > (capacity >> 1)) {
				// More than 1/2 filled w/ live entries.
				// Double size.
				newCapacity = capacity * 2;
			}

			Object[] oldTable = this.table;

			// Allocate new table.
			initializeTable(newCapacity);

			// We won't have any tombstones after this.
			this.tombstones = 0;

			// If we have no live entries, we can quit here.
			if (size == 0) {
				return true;
			}

			// Move over entries.
			for (int i = oldTable.length - 2; i >= 0; i -= 2) {
				Object k = oldTable[i];
				if (k == null || k == TOMBSTONE) {
					// Skip this entry.
					continue;
				}

				// The table can only contain null, tombstones and references.
				@SuppressWarnings("unchecked")
				Reference<ThreadLocal<?>> reference
						= (Reference<ThreadLocal<?>>) k;
				ThreadLocal<?> key = reference.get();
				if (key != null) {
					// Entry is still live. Move it over.
					add(key, oldTable[i + 1]);
				} else {
					// The key was reclaimed.
					size--;
				}
			}

			return true;
		}

		/**
		 * Gets the next index. If we're at the end of the table, we wrap back
		 * around to 0.
		 */
		private int next(int index) {
			return (index + 2) & mask;
		}
	}
}
