package Socket.Messages;

import model.cards.CardResource;

public class CentralCardResourceMessage extends Message{
        private CardResource cardResource;

        public CentralCardResourceMessage(CardResource cardResource) {
            super("CentralCardResource");
            this.cardResource = cardResource;
        }

        public CardResource getCardResource() {
            return cardResource;
        }
    }

