# PlayngBoard

## attributes

1. 4 attributs for the central cards
2. 2 attributes for the objective
3. 2 attributes for saving the deck

## methosds

`Setcenttralcard(): void`

takes a card from one of the two decks and put it in the central card

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
