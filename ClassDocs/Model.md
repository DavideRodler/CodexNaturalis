# PlayngBoard

## attributes

1. 4 attributs for the central cards
2. 2 attributes for the objective
3. 2 attributes for saving the deck
4. 1 attribute for the players map?

## methosds

`Setcenttralcard(): void`

takes a card from one of the two decks and put it in the central card

`DrawcardStarting(): c: CardStarting`
take a card form the deck of CardStarting and returns it (we need this function at the start of the game)

`SetPLayers(): void`
function that poplulate the map? of player and put them in the map randomly, the key of the map represents the turn of the player

` PickTwoObjectives(): c[]: CardObjective`
pick two objective from the deck of the CardObjective and returns it, it is used at the start of the game to generate the two
objective for each player

# PlayngStation

## methosds

`AddCard(table, i, j): void`
add a card to the matrix of the table

If the card is playable it adds it to the the table and updates it, if it is not it returns a CardNotPlayableException or a CardNotPresnt.

# Player

## methosds

`Removecard(id: String)`
remove the card with the id, then calls the function addcard of the PlayngStation associated to the player.

when we wanto to play a card we need to save it locally

`addcard(d: Deck)`
Pick a card form the deck d and put in the hand, it cand generate DeckNotPresentException

# Deck

The constructor take all the cards saved in memory and puts it in randomly in the list attributs.

# CardGold

`CheckIsPlayable(table: Card[])`
check if there are enough resources to play the card
