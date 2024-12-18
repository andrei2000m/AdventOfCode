class PriorityQueue<T>(queue: List<T>, private val comparator: Comparator<T>) : Collection<T> {

    private val queue: List<T> = queue.sortedWith(comparator)

    override val size: Int
        get() = queue.size

    override fun isEmpty(): Boolean = queue.isEmpty()

    override fun iterator(): Iterator<T> = queue.iterator()

    override fun containsAll(elements: Collection<T>): Boolean = queue.containsAll(elements)

    override fun contains(element: T): Boolean = queue.contains(element)

    fun peek(): T = queue[0]

    operator fun plus(element: T): PriorityQueue<T> = PriorityQueue(queue + element, comparator)

    operator fun plus(elements: Collection<T>): PriorityQueue<T> = PriorityQueue(queue + elements, comparator)

    operator fun minus(element: T): PriorityQueue<T> = PriorityQueue(queue - element, comparator)

    operator fun minus(elements: Collection<T>): PriorityQueue<T> = PriorityQueue(queue - elements.toSet(), comparator)

    override fun toString(): String {
        return queue.toString()
    }
}