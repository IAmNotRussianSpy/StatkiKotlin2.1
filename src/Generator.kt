object Generator {

    fun randContainersToFile(N: Int, fileName: String, id: Int) {
        var id = id
        var epoch = System.currentTimeMillis()
        var space = IntArray(3)
        var mass: Int
        for (i in 0 until N) {
            space[0] = (Math.random() * 14).toInt() + 1
            space[1] = (Math.random() * 14).toInt() + 1
            mass = (Math.random() * 49).toInt() + 1
            val shippingContainer = ShippingContainer(epoch, id, space, mass)
            epoch += ((Math.random() * 15.0 * 60000.0).toInt() + 60000)
            id++
            shippingContainer.addToFile(fileName)
        }
    }


}
