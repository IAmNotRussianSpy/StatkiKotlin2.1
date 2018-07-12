import com.sun.javaws.exceptions.InvalidArgumentException
import com.sun.org.apache.xpath.internal.operations.Bool
import java.io.*
import java.util.Scanner
import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.File


class ShippingContainer : Comparable<ShippingContainer> {
    var timestamp: Long = 0
    var id: Int = 0
    var size = IntArray(2)
    var mass: Int = 0
    var valid: Boolean = true

    constructor(timestamp: Long, id: Int, space: IntArray, mass: Int) {
        this.timestamp = timestamp
        this.id = id
        this.size = space
        this.mass = mass
    }

    constructor(line: String) {
        var line = line
        try {
            line = line.replace("[", "")
            line = line.replace("]", "")
            val arr = line.split(",")
            this.timestamp = java.lang.Long.parseLong(arr[0])
            if (arr[1].toIntOrNull() is Int)
                this.id = Integer.parseInt(arr[1])
            else {
                println(line + " |Wrong id")
                throw ArgumentException("Invalid Argument provided!")
            }
            if (arr[2].toIntOrNull() is Int && Integer.parseInt(arr[2]) in 1..10)
                this.size[0] = Integer.parseInt(arr[2])
            else {
                println(line + " |Wrong size[0]")
                throw ArgumentException("Invalid Argument provided!")
            }
            if (arr[3].toIntOrNull() is Int && Integer.parseInt(arr[3]) in 1..10)
                this.size[1] = Integer.parseInt(arr[3])
            else {
                println(line + " |Wrong size [1]")
                throw ArgumentException("Invalid Argument provided!")
            }
            if (arr[4].toIntOrNull() is Int)
                this.mass = Integer.parseInt(arr[4])
            else {
                println(line + " |Wrong mass")
                throw ArgumentException("Invalid Argument provided!")
            }
        } catch (e: ArgumentException) {
            this.valid = false
        }
    }

    fun addToFile(fileName: String) {
        var line = ("" + this.timestamp + " " + this.id + " " + this.size[0] + " " + this.size[1] + " " + this.mass)
        java.io.File(fileName).appendText(line)
    }


    override fun compareTo(other: ShippingContainer): Int {
        return Integer.compare(this.size[0], other.size[0])
    }

    companion object {

        fun contFromFile(fileName: String, noOfLine: Int): String {
            return java.io.File(fileName).readLines()[noOfLine]
        }
    }
}