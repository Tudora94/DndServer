namespace DndServer.Inventory.Models
{
    public class AddItemToPlayerRequest : InventoryBaseRequest
    {
        private int _itemId;
        private int? _playerId;
        private int _campaignId;

        public int ItemId { get { return _itemId; } set { _itemId = value; } }
        public int? PlayerId { get { return _playerId; } set { _playerId = value; } }
        public int CampaignId { get { return _campaignId; } set { _campaignId = value; } }
    }
}
