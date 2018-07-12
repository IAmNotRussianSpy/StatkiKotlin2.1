import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val noOfShips = 5
        val N = 10000
        val interval = 100
        val ships = ArrayList<Ship>()
        var iter = 0
        for (i in 0 until noOfShips) {
            ships.add(Ship(Ship.lineFromFile("DataInputGroupPT1440_SHIPS.csv", i)))
        }

        var i = 0

        var left = ArrayList<ShippingContainer>()
        for (i in 0 until (N / interval)) {
            val containers = ArrayList<ShippingContainer>()
            for (c in 0 until interval) {
                var currentContainer = ShippingContainer(ShippingContainer.contFromFile("DataInputGroupPT1440.csv", (iter * 100 + c)))
                if (currentContainer.valid)
                    containers.add(currentContainer)

            }
            left = Load.solve(ships, left, containers, i)
            iter += 1
        }

        while (!left.isEmpty()) {
            left = Load.solve(ships, left, ArrayList<ShippingContainer>(), i)
            i++
        }

    }
}
