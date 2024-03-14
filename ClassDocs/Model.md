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

# PlayngStation

## methosds

`AddCard(table, i, j): void`
add a card to the matrix of the table

If the card is playable it adds it to the the table and updates it, if it is not it returns a CardNotPlayableException or a CardNotPresnt.

# Player

## methosds

`Removecard(c: CardResources)`
remove the card c form the hand, it can generate a CardNotPresentException

`addcard(d: Deck)`
Pick a card form the deck d and put in the hand, it cand generate DeckNotPresentException

# Deck

The constructor take all the cards saved in memory and puts it in randomly in the list attributs.
