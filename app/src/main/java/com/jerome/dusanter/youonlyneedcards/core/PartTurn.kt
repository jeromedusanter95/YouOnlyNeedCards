class PartTurn(
    private val stateTurn: StateTurn,
    private val repository: GameRepository,
    private val listener: Listener
) {

    fun start() {
        repository.resetStatePlayerExceptFoldedAndAllIn()
        repository.resetStackBetPartTurn()
        if (stateTurn.name != StateTurn.PreFlop.name) {
            repository.moveToFirstPlayerAfterBigBlind()
        } else {
            repository.moveToFirstPlayerAvailable()
        }
        repository.getPossibleActions()
    }

    fun handleActionPlayer(actionPlayer: ActionPlayer, stackRaised: Int) {
        when (actionPlayer) {
            ActionPlayer.AllIn -> {
                repository.allin()
            }
            ActionPlayer.Call -> {
                repository.call()
            }
            ActionPlayer.Check -> {
                repository.check()
            }
            ActionPlayer.Fold -> {
                repository.fold()
            }
            ActionPlayer.Raise -> {
                repository.raise(stackRaised = stackRaised)
            }
        }
        if (repository.isPartTurnOver()) {
            listener.onEndPartTurn()
        } else {
            repository.moveToNextPlayerAvailable()
            repository.getPossibleActions()
        }
    }

    interface Listener {
        fun onEndPartTurn()
    }
}

