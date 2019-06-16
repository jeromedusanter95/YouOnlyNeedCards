class Game(private val repository: GameRepository) : Turn.Listener {

    fun start(parameters: MutableMap<String, Any>) {
        repository.initializeAllParameters(parameters)
        repository.distributeStackToPlayers()
        if (repository.isIncreaseBlindsEnabled()) {
            repository.startTimerIncreaseBlinds()
        }
        startTurn()
    }

    private fun startTurn() {
        val turn = Turn(repository, this)
        turn.start()
    }

    private fun end() {
        if (repository.isMoneyBetEnabled()) {
            repository.distributeMoneyToWinners()
        }
    }

    fun save() {
        repository.save()
    }

    fun recave() {
        repository.recave()
    }

    override fun onEndTurn() {
        if (repository.isGameOver()) {
            end()
        } else {
            if (repository.shouldIncreaseBlinds()
            ) {
                repository.increaseBlinds()
                repository.startTimerIncreaseBlinds()
            }
            startTurn()
        }
    }
}