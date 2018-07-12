import sun.java2d.cmm.ColorTransform.Out
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*
import java.util.stream.Collectors
import kotlin.math.roundToInt

object Load {


    fun solve(ships: ArrayList<Ship>, oldCont: ArrayList<ShippingContainer>, newCont: ArrayList<ShippingContainer>, rep: Int): ArrayList<ShippingContainer> {
        oldCont.sortWith(Collections.reverseOrder<ShippingContainer>())
        newCont.sortWith(Collections.reverseOrder<ShippingContainer>())
        val shipsLoaded = ArrayList<Array<BooleanArray>>()
        for (i in ships.indices) {
            val a = Array(ships[i].space[0]) { BooleanArray(ships[i].space[1]) }
            shipsLoaded.add(a)
        }

        val before = oldCont.size + newCont.size

        if (!oldCont.isEmpty()) {
            loadContainers(oldCont, ships, shipsLoaded)
        }
        if (!newCont.isEmpty()) {
            loadContainers(newCont, ships, shipsLoaded)
        }
        val after = oldCont.size + newCont.size
        val shipped = before - after

        val total = IntArray(5)
        val loaded = IntArray(5)
        for (s in ships.indices) {
            for (a in 0 until shipsLoaded[s].size) {
                for (b in 0 until shipsLoaded[s][a].size) {
                    total[s]++
                    if (shipsLoaded[s][a][b]) {
                        loaded[s]++
                    }
                }
            }
        }

        createTempFile("out", ".txt")

        var line = rep.toString() + ". Containers sent: " + shipped + ", "
        for (s in ships.indices) {
            val usage = loaded[s].toDouble() / total[s].toDouble()
            line = line + "Ship's fulfillment" + s + ": " + (usage * 100) + "%, "
        }
        line += System.lineSeparator()
        try {
            java.io.File("out.txt").appendText(line)
        } catch (e: IOException) {
            println("Error while writing to file")
        }

        val left = ArrayList<ShippingContainer>()
        left.addAll(oldCont)
        left.addAll(newCont)
        return left
    }

    private fun loadContainers(cont: ArrayList<ShippingContainer>, ships: ArrayList<Ship>, shipsLoaded: ArrayList<Array<BooleanArray>>) {
        val used = Stack<Int>()
        for (c in cont.indices) {                                                                                  //pętla po kontenerach
            var loaded = false
            for (s in ships.indices) {                                                                             //pętla po statkach
                for (x in 0 until ships[s].space[0]) {                                                             //pętla po x statku
                    var loopbreak = false
                    for (y in 0 until ships[s].space[1]) {                                                         //pętla po y statku
                        if (!shipsLoaded[s][x][y]) {                                                                    //jeżeli dane pole jest puste
                            val xc = cont[c].size[0]
                            val yc = cont[c].size[1]
                            if (x + xc <= ships[s].space[0] && y + yc <= ships[s].space[1]) {                           //jeżeli kontener się zmieści na statku
                                var chc = false
                                for (xt in x until x + xc) {                                                       //sprawdzenie czy nie ma zajętych miejsc na pozycji kontenera
                                    var tmp = false
                                    for (yt in y until y + yc) {                                                   //sprawdzenie czy nie ma zajętych miejsc na pozycji kontenera
                                        val test = shipsLoaded[s][xt][yt]
                                        if (test) {                                                                     //jeżeli występuje zajęte miejsce
                                            tmp = true
                                            break
                                        }
                                    }
                                    if (tmp) {
                                        break
                                    }
                                    chc = true                                                                          //jeżeli tu doszło, to znaczy że kontener się zmieści
                                }
                                if (chc) {                                                                              //wstawianie kontenera
                                    used.push(c)
                                    loaded = true
                                    for (xt in x until x + xc) {
                                        for (yt in y until y + yc) {
                                            shipsLoaded[s][xt][yt] = true
                                        }
                                    }
                                    loopbreak = true
                                    break
                                }
                            }
                        }
                    }
                    if (loopbreak) {
                        break
                    }
                }
                if (loaded) {
                    break
                }
            }
        }
        while (!used.isEmpty()) {
            cont.removeAt(used.pop().toInt())
        }
    }
}
