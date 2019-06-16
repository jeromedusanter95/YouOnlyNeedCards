class Turn(private val repository: GameRepository, private val listener: Listener) : PartTurn.Listener {

    fun start() {
        repository.initializeStateBlind()
        repository.resetStatePlayer()
        repository.resetStackBetTurn()
        startPartTurn(StateTurn.PreFlop)
    }

    private fun startPartTurn(stateTurn: StateTurn) {
        val partTurn = PartTurn(stateTurn, repository, this)
        partTurn.start()
    }

    private fun end() {
        repository.createAllPots()
        repository.distributeStackToWinners()
        listener.onEndTurn()
    }

    override fun onEndPartTurn() {
        if (repository.isTurnOver()) {
            end()
        } else {
            startPartTurn(repository.getNextPartTurn())
        }
    }

    interface Listener {
        fun onEndTurn()
    }
}