import java.io.*

class Ship {
    var id: Int = 0
    var space = IntArray(2)
    var capacity: Int = 0

    constructor(id: Int, space: IntArray, capacity: Int) {
        this.id = id
        this.space = space
        this.capacity = capacity
    }

    constructor(line: String) {
        var line = line
        line = line.replace("[", "")
        line = line.replace("]", "")
        val arr = line.split(",")
        this.id = Integer.parseInt(arr[0])
        this.space[0] = Integer.parseInt(arr[1])
        this.space[1] = Integer.parseInt(arr[2])
        this.capacity = Integer.parseInt(arr[3])
    }

    companion object {

        fun lineFromFile(fileName: String, l: Int): String {
            return java.io.File(fileName).readLines()[l]
        }
    }
}
