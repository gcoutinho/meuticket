object Config {

    object Application {
        const val applicationId = "com.meuticket.pos"
    }

    data class Flavor(val name: String) {

        override fun toString() = name

        companion object {

            val Safra = Flavor(
                name = "safra"
            )

            val PagSeguro = Flavor(
                name = "pagseguro"
            )

            fun values() = listOf(Safra, PagSeguro)
        }
    }
}